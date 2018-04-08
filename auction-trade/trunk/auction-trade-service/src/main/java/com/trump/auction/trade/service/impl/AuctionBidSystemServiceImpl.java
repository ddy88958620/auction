package com.trump.auction.trade.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cf.common.id.IdGenerator;
import com.cf.common.utils.ServiceResult;
import com.trump.auction.account.api.AccountInfoStubService;
import com.trump.auction.account.dto.AccountInfoRecordListDto;
import com.trump.auction.reactor.api.BidService;
import com.trump.auction.reactor.api.model.AccountCode;
import com.trump.auction.reactor.api.model.AuctionContext;
import com.trump.auction.reactor.api.model.AuctionStatus;
import com.trump.auction.reactor.api.model.BidCostDetail;
import com.trump.auction.reactor.api.model.BidRequest;
import com.trump.auction.reactor.api.model.BidType;
import com.trump.auction.reactor.api.model.Bidder;
import com.trump.auction.reactor.api.model.MultiBidRequest;
import com.trump.auction.reactor.repository.BidRepository;
import com.trump.auction.trade.dao.AuctionBidInfoDao;
import com.trump.auction.trade.dao.AuctionDetailDao;
import com.trump.auction.trade.dao.AuctionInfoDao;
import com.trump.auction.trade.dao.AuctionTxnDetailDao;
import com.trump.auction.trade.dao.sharding.ShardingAuctionBidetailDao;
import com.trump.auction.trade.dao.sharding.ShardingAuctionDetailDao;
import com.trump.auction.trade.domain.AuctionBidInfo;
import com.trump.auction.trade.domain.AuctionDetail;
import com.trump.auction.trade.domain.AuctionInfo;
import com.trump.auction.trade.domain.AuctionTxnDetail;
import com.trump.auction.trade.model.BidParam;
import com.trump.auction.trade.model.BidResult;
import com.trump.auction.trade.service.AuctionBidSystemService;
import com.trump.auction.trade.util.ConstantUtil;
import com.trump.auction.trade.util.TableNameSuffixUtil;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisCluster;

@Service
@Slf4j
public class AuctionBidSystemServiceImpl implements AuctionBidSystemService {

	@Autowired
	private IdGenerator snowflakeGenerator;

	@Autowired
	private AccountInfoStubService accountInfoStubService;

	@Autowired
	private BidService bidService;

	@Autowired
	private BidRepository bidRepository;
	@Autowired
	private JedisCluster jedisCluster;
	@Autowired
	private ShardingAuctionDetailDao shardingAuctionDetailDao;
	@Autowired
	private AuctionDetailDao auctionDetailDao;
	@Autowired
	private AuctionTxnDetailDao auctionTxnDetailDao;
	@Autowired
	private AuctionInfoDao auctionInfoDao;
	@Autowired
	private ShardingAuctionBidetailDao shardingAuctionBidetailDao;
	@Autowired
	private AuctionBidInfoDao auctionBidInfoDao;

	/**
	 * 拍品出价实现
	 * 
	 * @param bidParam
	 *            参数集合
	 * @return
	 */
	@Override
	public BidResult bidOperation(BidParam bidParam) {
		BidResult bidResult = new BidResult();
		if (!getIsBidParam(bidParam)) {
			return bidResult.errorParamBid();
		}
		log.info("AuctionBidServiceImpl bidOperation inove userId={},auctionNo={},bidCount={}", bidParam.getUserId(),
				bidParam.getAuctionId(), bidParam.getBidCount());
		// 是否已经结束
		AuctionContext auctionContext = bidRepository.getContext(String.valueOf(bidParam.getAuctionId()));
		if (AuctionStatus.COMPLETE.equals(auctionContext.getStatus())) {
			return bidResult.errorSBid(String.valueOf(bidParam.getAuctionId()));
		}
		// 是否开始
		// 判断是否为最高价
		if (auctionContext.getLastBidder() != null
				&&bidParam.getUserId()!= null
						&& bidParam.getUserId().toString().equals(auctionContext.getLastBidder().getUserId())) {
			bidResult.setCode("0003");
			bidResult.setMsg("当前为最高价不可出价");
			bidResult.setErrMsg("参数异常");
			return bidResult;
		}
		// 当前用户是否竞拍
		if (bidRepository.isWaitBid(String.valueOf(bidParam.getAuctionId()), getBidder(bidParam))) {
			return bidResult.errorRepeatBid();
		}
		// 用户是否频繁竞拍
		String user = jedisCluster.get(
				ConstantUtil.AUCTION_TRADE_BID_USER_ + ConstantUtil.getRedisKey(String.valueOf(bidParam.getUserId())));
		if (StringUtils.isNotEmpty(user)) {
			bidResult.setCode("0003");
			bidResult.setMsg("出价频繁请重试");
			bidResult.setErrMsg("参数异常");
			return bidResult;
		}
		// 数据落地
		AuctionInfo auctionInfo = auctionInfoDao.selectByPrimaryKey(bidParam.getAuctionId());
		AuctionTxnDetail auctionTxnDetail = addAuctionTxnDetail(bidParam, auctionInfo);
		Integer number = auctionTxnDetailDao.insert(auctionTxnDetail);
		if (number < 1) {
			return bidResult.errorTxnDetail();
		}
		try {
			// 请求扣款
			ServiceResult serviceResult = accountInfoStubService.paymentWithCoin(bidParam.getUserId(), null,
					auctionInfo.getProductName(), bidParam.getBidCount() * auctionInfo.getPoundage(),
					auctionTxnDetail.getReqSeqNo(), auctionInfo.getId().toString(), auctionInfo.getPreviewPic());
			if (serviceResult.isSuccessed()) {
				List<AccountInfoRecordListDto> listArd = (List<AccountInfoRecordListDto>) serviceResult.getExt();
				bidResult = userBid(listArd, auctionTxnDetail, bidResult);
				AuctionDetail auctionDetail = shardingAuctionDetailDao.selectByUserId(bidParam.getUserId(),
						bidParam.getAuctionId(), String.valueOf(bidParam.getUserId()));
				if (null == auctionDetail) {
					Integer detailNum = auctionDetailDao.saveAuctionDetail(getAuctionDetail(bidParam));
					if (detailNum < 1) {
						// 出价失败
						return bidResult.errorDetail();
					}
				}
				saveBidInfo(bidParam, auctionInfo, auctionTxnDetail);
				// 单一或者委托出价
				if (ConstantUtil.AUCTION_TRADE_BID_TYPE_ONE.equals(bidParam.getBidType())) {
					bidService.bid(getBidRequest(bidParam, auctionTxnDetail));
				} else {
					bidService.bid(getMultiBidRequest(bidParam, auctionTxnDetail, auctionInfo));
				}
				// 增加出价时间验证
				jedisCluster.setex(
						ConstantUtil.AUCTION_TRADE_BID_USER_
								+ ConstantUtil.getRedisKey(String.valueOf(bidParam.getUserId())),
						ConstantUtil.AUCTION_TRADE_BID_MS, String.valueOf(bidParam.getUserId()));
				log.info("trade AuctionBidServiceImpl error bidParam.getBidType={}", bidParam.getBidType());
			} else {
				log.info("trade AuctionBidServiceImpl paymentWithCoin error auction_id={},user_id={}",
						bidParam.getAuctionId(), bidParam.getUserId());
				return bidResult.errorPaymentBid();
			}
			return bidResult.successBid();
		} catch (RuntimeException e) {
			log.error("trade AuctionBidServiceImpl system error auctionId={}", bidParam.getAuctionId(), e);
			return bidResult.errorDetail();
		}
	}

	/**
	 * 数据落地
	 * 
	 * @return
	 */
	private AuctionTxnDetail addAuctionTxnDetail(BidParam bidParam, AuctionInfo auctionInfo) {
		AuctionTxnDetail auctionTxnDetail = new AuctionTxnDetail();
		auctionTxnDetail.setAuctionNo(bidParam.getAuctionId().toString());
		auctionTxnDetail.setAuctionProdId(bidParam.getAuctionProdId());
		auctionTxnDetail.setTxnSeqNo(String.valueOf(snowflakeGenerator.next()));
		auctionTxnDetail.setReqSeqNo(String.valueOf(snowflakeGenerator.next()));
		auctionTxnDetail.setTxnStatus(ConstantUtil.AUCTION_TRADE_TXN_STATUS_ONE);
		auctionTxnDetail.setTxnAmt(new BigDecimal(bidParam.getBidCount() * auctionInfo.getPoundage()));
		auctionTxnDetail.setUserId(bidParam.getUserId());
		auctionTxnDetail.setCreateTime(new Date());
		auctionTxnDetail.setBidStatus(ConstantUtil.AUCTION_TRADE_TXN_STATUS_ONE);
		return auctionTxnDetail;
	}

	/**
	 * 支付成功后更新支付记录
	 * 
	 * @param id
	 * @param status
	 * @param frreCount
	 * @param validCount
	 * @param currency
	 * @return
	 */
	private Boolean updAuctionTxnDetail(String id, Integer status, Integer frreCount, Integer validCount,
			Integer currency) {
		int number = auctionTxnDetailDao.updTxnStatus(id, status, frreCount, validCount, currency);
		if (number > 0) {
			return true;
		} else {

		}
		return false;
	}

	/**
	 * 数据封装
	 */
	private BidResult userBid(List<AccountInfoRecordListDto> listArd, AuctionTxnDetail auctionTxnDetail,
			BidResult bidResult) {
		try {
			// 扣除赠币
			Integer freeCount = 0;
			// 扣除拍币
			Integer validCount = 0;
			// 类型
			Integer type = 0;
			for (AccountInfoRecordListDto accountInfoRecordListDto : listArd) {
				if (accountInfoRecordListDto.getAccountType().equals(ConstantUtil.AUACTION_TRADE_BID_DETAIL_TYPE_ONE)) {
					validCount = accountInfoRecordListDto.getTransactionCoin();
					auctionTxnDetail.setCurrency(ConstantUtil.AUACTION_TRADE_BID_DETAIL_TYPE_ONE);
					// 拍币出价
					type = ConstantUtil.AUACTION_TRADE_BID_DETAIL_TYPE_ONE;
				} else if (accountInfoRecordListDto.getAccountType()
						.equals(ConstantUtil.AUACTION_TRADE_BID_DETAIL_TYPE_TWO)) {
					freeCount = accountInfoRecordListDto.getTransactionCoin();
					auctionTxnDetail.setCurrency(ConstantUtil.AUACTION_TRADE_BID_DETAIL_TYPE_TWO);
					// 赠币出价
					type = ConstantUtil.AUACTION_TRADE_BID_DETAIL_TYPE_TWO;
				}
			}
			if (listArd.size() > ConstantUtil.AUACTION_TRADE_BID_DETAIL_TYPE_TWO) {
				// 混合出价
				type = ConstantUtil.AUCTION_TRADE_TXN_TYPE_THREE;
				auctionTxnDetail.setCurrency(ConstantUtil.AUCTION_TRADE_TXN_TYPE_THREE);
			}
			// 修改支付记录 出价方式以及扣除具体拍币和赠币
			auctionTxnDetail.setFreeCount(Math.abs(freeCount));
			auctionTxnDetail.setValidCount(Math.abs(validCount));
			updAuctionTxnDetail(auctionTxnDetail.getId().toString(), ConstantUtil.AUCTION_TRADE_TXN_STATUS_TWO,
					Math.abs(freeCount), Math.abs(validCount), type);
		} catch (Exception e) {
			bidResult.setCode("0001");
			bidResult.setMsg("出价失败");
			bidResult.setErrMsg("支付成功落地失败");
			log.error("trade AuctionBidServiceImpl error userBid  ", e);
		}
		return bidResult;
	}

	/**
	 * 验证参数的正确性
	 * 
	 * @return
	 */
	private Boolean getIsBidParam(BidParam bidParam) {
		if (null == bidParam) {
			log.info("trade AuctionBidServiceImpl error bidParam is null ");
			return false;
		}
		if (null == bidParam.getUserId()) {
			log.info("trade AuctionBidServiceImpl error bidParam.getUserId is  null or error");
			return false;
		}
		if (null == bidParam.getAuctionId()) {
			log.info("trade AuctionBidServiceImpl error bidParam.getAuctionId is  null or error userId=",
					bidParam.getUserId());
			return false;
		}
		if (null == bidParam.getAuctionProdId()) {
			log.info("trade AuctionBidServiceImpl error bidParam.getAuctionNo is  null or error userId=",
					bidParam.getUserId());
			return false;
		}

		if (null == bidParam.getBidType()) {
			log.info("trade AuctionBidServiceImpl error bidParam.getBidType is  null or error userId=",
					bidParam.getUserId());
			return false;
		}
		if (null == bidParam.getBidCount()) {
			log.info("trade AuctionBidServiceImpl error bidParam.getBidCount is  null or error userId=",
					bidParam.getUserId());
			return false;
		}
		if (StringUtils.isBlank(bidParam.getAddress())) {
			log.info("trade AuctionBidServiceImpl error bidParam.getAddress is  null or error userId=",
					bidParam.getUserId());
			return false;
		}
		return true;
	}

	/**
	 * 封装调用参数我（单一出价）
	 * 
	 * @param bidParam
	 *            用户出价参数对象
	 * @return
	 */
	private static BidRequest getBidRequest(BidParam bidParam, AuctionTxnDetail auctionTxnDetail) {
		// 单价出价参数
		BidRequest bidRequest = new BidRequest();
		bidRequest.setAuctionNo(String.valueOf(bidParam.getAuctionId()));
		bidRequest.setBidder(getBidder(bidParam));
		bidRequest.setBidType(BidType.DEFAULT);
		bidRequest.setBizNo(auctionTxnDetail.getTxnSeqNo());
		bidRequest.setAccountCode(AccountCode.of(String.valueOf(auctionTxnDetail.getCurrency())).get());
		return bidRequest;
	}

	/**
	 * 封装调用参数我（委托出价）
	 * 
	 * @param bidParam
	 *            用户出价参数对象
	 * @param auctionTxnDetail
	 * @return
	 */
	private static MultiBidRequest getMultiBidRequest(BidParam bidParam, AuctionTxnDetail auctionTxnDetail,
			AuctionInfo auctionInfo) {
		// 个人出价
		MultiBidRequest multiBidRequest = new MultiBidRequest();
		multiBidRequest.setAuctionNo(String.valueOf(bidParam.getAuctionId()));
		// 用户信息
		multiBidRequest.setBidder(getBidder(bidParam));
		Integer pcode = auctionTxnDetail.getValidCount() / auctionInfo.getPoundage();
		Integer zcode = auctionTxnDetail.getFreeCount() / auctionInfo.getPoundage();
		if ((pcode + zcode) < bidParam.getBidCount()) {
			if (0 < zcode) {
				++zcode;
			} else {
				++pcode;
			}
		}
		if (auctionTxnDetail.getValidCount() > 0) {
			multiBidRequest.withCostDetail(new BidCostDetail(AccountCode.YIPPE, pcode));
		}
		if (auctionTxnDetail.getFreeCount() > 0) {
			multiBidRequest.withCostDetail(new BidCostDetail(AccountCode.FREE, zcode));
		}
		multiBidRequest.setBizNo(auctionTxnDetail.getTxnSeqNo());
		return multiBidRequest;
	}

	/**
	 * 用户信息
	 * 
	 * @param bidParam
	 * @return
	 */
	private static Bidder getBidder(BidParam bidParam) {
		// 用户信息
		Bidder bidder = new Bidder();
		bidder.setName(bidParam.getUserName());
		bidder.setAddrArea(bidParam.getAddress());
		bidder.setSubId(String.valueOf(bidParam.getUserId()));
		bidder.setUserId(String.valueOf(bidParam.getUserId()));
		bidder.setHeadImgUrl(bidParam.getHdeaImg());
		return bidder;
	}

	/**
	 *
	 * @param bidParam
	 *            出价参数
	 * @return
	 */
	private static AuctionDetail getAuctionDetail(BidParam bidParam) {
		AuctionDetail auctionDetail = new AuctionDetail();
		auctionDetail.setAuctionId(bidParam.getAuctionId());
		auctionDetail.setAuctionProdId(bidParam.getAuctionProdId());
		auctionDetail.setAuctionStatus(ConstantUtil.AUCTION_TRADE_BID_TYPE_ONE);
		auctionDetail.setCreateTime(new Date());
		auctionDetail.setBidCount(0);
		auctionDetail.setUserName(bidParam.getUserName());
		auctionDetail.setUserId(bidParam.getUserId());
		auctionDetail.setNickName(bidParam.getUserName());
		auctionDetail.setHeadImg(bidParam.getHdeaImg());
		auctionDetail.setAddress(bidParam.getAddress());
		auctionDetail.setPCoin(0);
		auctionDetail.setZCoin(0);
		auctionDetail.setSubUserId(String.valueOf(bidParam.getUserId()));
		// 用户类型必须为 有效用户
		auctionDetail.setUserType(1);
		// 判断支付类型
		auctionDetail.setReturnPrice(new BigDecimal("0"));

		String tableSuffisx = TableNameSuffixUtil.getCurrentTableNameSuffix();
		auctionDetail.setTableSuffix(tableSuffisx);
		return auctionDetail;
	}

	/**
	 * 是否有效支付
	 * 
	 * @param type
	 *            支付类型 1拍币 2赠币
	 * @return
	 */
	private static boolean isValid(int type) {
		if (ConstantUtil.AUCTION_TRADE_BID_TYPE_ONE == type) {
			return false;
		} else {
			return true;
		}
	}

	private void saveBidInfo(BidParam bidParam, AuctionInfo auctionInfo, AuctionTxnDetail auctionTxnDetail) {
		AuctionBidInfo auctionBidInfo = new AuctionBidInfo();
		auctionBidInfo.setUserId(Integer.valueOf(bidParam.getUserId()));
		auctionBidInfo.setUserName(bidParam.getUserName());
		auctionBidInfo.setAuctionProdId(auctionInfo.getAuctionProdId());
		auctionBidInfo.setAuctionNo(Integer.valueOf(bidParam.getAuctionId()));
		auctionBidInfo.setCreateTime(new Date());
		auctionBidInfo.setTxnSeqNo(auctionTxnDetail.getTxnSeqNo());
		auctionBidInfo.setBidCount(bidParam.getBidCount());
		if (bidParam.getBidCount() > 1) {
			auctionBidInfo.setPCoin(0);
			auctionBidInfo.setZCoin(0);
			auctionBidInfo.setStatus(2);
		} else {
			auctionBidInfo.setPCoin(auctionTxnDetail.getValidCount());
			auctionBidInfo.setZCoin(auctionTxnDetail.getFreeCount());
			auctionBidInfo.setStatus(3);
		}
		String tableSuffix = TableNameSuffixUtil.getCurrentTableNameSuffix();
		auctionBidInfo.setTableSuffix(tableSuffix);
		auctionBidInfoDao.insert(auctionBidInfo);
	}
}
