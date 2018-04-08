package com.trump.auction.trade.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.common.utils.ServiceResult;
import com.trump.auction.account.api.AccountInfoStubService;
import com.trump.auction.cust.api.UserInfoStubService;
import com.trump.auction.cust.model.UserInfoModel;
import com.trump.auction.order.api.OrderInfoStubService;
import com.trump.auction.order.model.OrderInfoModel;
import com.trump.auction.reactor.api.model.AccountCode;
import com.trump.auction.reactor.api.model.AuctionContext;
import com.trump.auction.reactor.api.model.BidHitResponse;
import com.trump.auction.reactor.api.model.BidResponse;
import com.trump.auction.reactor.api.model.BidStatistics;
import com.trump.auction.reactor.api.model.BidType;
import com.trump.auction.reactor.api.model.Bidder;
import com.trump.auction.reactor.ext.service.BidExtService;
import com.trump.auction.reactor.repository.BidRepository;
import com.trump.auction.trade.constant.SysContant;
import com.trump.auction.trade.dao.AuctionBidInfoDao;
import com.trump.auction.trade.dao.AuctionBidetailDao;
import com.trump.auction.trade.dao.AuctionDetailDao;
import com.trump.auction.trade.dao.AuctionInfoDao;
import com.trump.auction.trade.dao.AuctionTxnDetailDao;
import com.trump.auction.trade.dao.sharding.ShardingAuctionBidInfoDao;
import com.trump.auction.trade.dao.sharding.ShardingAuctionDetailDao;
import com.trump.auction.trade.domain.AuctionBidInfo;
import com.trump.auction.trade.domain.AuctionBidetail;
import com.trump.auction.trade.domain.AuctionDetail;
import com.trump.auction.trade.domain.AuctionInfo;
import com.trump.auction.trade.domain.AuctionTxnDetail;
import com.trump.auction.trade.service.BidManagerService;
import com.trump.auction.trade.util.Base64Utils;
import com.trump.auction.trade.util.ConstantUtil;
import com.trump.auction.trade.util.TableNameSuffixUtil;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisCluster;

@Slf4j
@Service
public class BidManagerServiceImpl implements BidManagerService, Ordered {
	@Autowired
	private JedisCluster jedisCluster;
	@Autowired
	private AuctionInfoDao auctionInfoDao;
	@Autowired
	private BidRepository bidRepository;
	@Autowired
	private ShardingAuctionDetailDao shardingAuctionDetailDao;
	@Autowired
	private AuctionDetailDao auctionDetailDao;
	@Autowired
	private AuctionBidetailDao auctionBidetailDao;
	@Autowired
	private ShardingAuctionBidInfoDao shardingAuctionBidInfoDao;

	@Autowired
	private AuctionBidInfoDao auctionBidInfoDao;

	@Autowired
	private BidExtService bidExtService;
	@Autowired
	private OrderInfoStubService orderInfoStubService;
	@Autowired
	private AccountInfoStubService accountInfoStubService;
	@Autowired
	private AuctionTxnDetailDao auctionTxnDetailDao;
	@Autowired
	private UserInfoStubService userInfoStubService;

	/**
	 * 拍品竞拍中处理
	 *
	 * @param bidResponse
	 */
	@Override
	public void bidHandle(BidResponse bidResponse) {
		log.info("BidManagerServiceImpl bidHandle bidResponse={}", bidResponse);
		jedisCluster.set(ConstantUtil.AUCTION_TRADE_TIME + ConstantUtil.getRedisKey(bidResponse.getAuctionNo()),
				String.valueOf(System.currentTimeMillis()));
		Long s1 = System.currentTimeMillis();
		if (bidResponse.isSuccess()) {
			AuctionInfo auctionInfo = auctionInfoDao.selectByPrimaryKey(Integer.valueOf(bidResponse.getAuctionNo()));
			AuctionContext auctionContext = bidRepository.getContext(String.valueOf(bidResponse.getAuctionNo()));
			// 修改用户订单出价记录
			updAuctionDetail(bidResponse, auctionInfo);
			// 修改总订单
			updAuctionInfo(bidResponse, auctionContext, auctionInfo);
			// 修改用户出价记录
			String bidInfoId = saveAuctionBidInfo(bidResponse, auctionInfo);
			// 修改用户出价记录详细
			saveAuctionBidetail(bidResponse, auctionInfo, bidInfoId);
		}
		Long s2 = System.currentTimeMillis();
		log.info("BidManagerServiceImpl times is ms={}", (s2 - s1));
	}

	/**
	 * 拍品竞拍完成处理
	 *
	 * @param bidHitResponse
	 */
	@Override
	public void bidFinish(BidHitResponse bidHitResponse) {

		Integer userId = Integer.valueOf(bidHitResponse.getLastBidder().getUserId());

		log.info("BidManagerServiceImpl bidFinish auctionId={},userId={}", bidHitResponse.getAuctionNo(), userId);
		AuctionInfo auctionInfo = auctionInfoDao.selectByPrimaryKey(Integer.valueOf(bidHitResponse.getAuctionNo()));
		if (Bidder.isAutoBidder(bidHitResponse.getLastBidder())) {
			bidHitResponse.getLastBidder().setName(Base64Utils.encodeStr(bidHitResponse.getLastBidder().getName()));
		}
		// 修改拍品成功信息
		auctionInfoDao.upAcutionSuccess(Integer.valueOf(bidHitResponse.getAuctionNo()), 2, userId,
				bidHitResponse.getLastBidder().getName(), bidHitResponse.getLastPrice());
		// 修改成功用户
		shardingAuctionDetailDao.updUserSuccess(userId, Integer.valueOf(bidHitResponse.getAuctionNo()), 2,
				bidHitResponse.getLastBidder().getSubId());
		// 修改未成功用户

		UserInfoModel userInfo = userInfoStubService.findUserInfoById(userId);
		if (1 == auctionInfo.getBuyFlag()) {
			BigDecimal returnPercent = auctionInfo.getReturnPercent();
			if (userInfo != null) {
				if (isHitReleaseVersion(userInfo.getAppInfo())) {
					returnPercent = new BigDecimal("100");
				}
			}
			shardingAuctionDetailDao.updUserfail(Integer.valueOf(bidHitResponse.getAuctionNo()), 3, returnPercent);
		} else {
			shardingAuctionDetailDao.updUserfail(Integer.valueOf(bidHitResponse.getAuctionNo()), 4,
					new BigDecimal("0"));
		}
		AuctionDetail auctionDetail = shardingAuctionDetailDao.selectByUserId(userId,
				Integer.valueOf(bidHitResponse.getAuctionNo()), bidHitResponse.getLastBidder().getSubId());
		// 新建订单
		getSaveOrder(auctionInfo, bidHitResponse, auctionDetail);
		// 返币
		ReturnBidCode(auctionInfo, bidHitResponse);
		// 推送redis
		successRedis(bidHitResponse, auctionInfo);

	}

	/**
	 * 修改出价记录
	 *
	 * @param
	 * @param auctionContext
	 */
	private void updAuctionInfo(BidResponse bidResponseb, AuctionContext auctionContext, AuctionInfo auctionInfo) {
		Long s1 = System.currentTimeMillis();
		auctionInfo.setValidBidCount(auctionContext.getValidBidCount());
		auctionInfo.setTotalBidCount(auctionContext.getTotalBidCount());
		Integer personCount = shardingAuctionDetailDao.findDetailBidCount(Integer.valueOf(bidResponseb.getAuctionNo()));
		auctionInfo.setPersonCount(personCount);
		auctionInfo.setFreeBidCount(auctionContext.getTotalBidCount() - auctionContext.getValidBidCount());
		Integer pageView = (personCount / 10 + personCount) > auctionInfo.getPageView() ? 1 : 0;
		auctionInfo.setPageView(pageView + auctionInfo.getPageView());
		if (BidType.AUTO.equals(bidResponseb.getBidType())) {
			auctionInfo.setRobotBidCount(auctionInfo.getRobotBidCount() + 1);
		}
		auctionInfoDao.updAuctionBidInfo(auctionInfo);
		long s2 = System.currentTimeMillis();
		log.info("BidManagerServiceImpl updAuctionInfo time  ms={}", (s2 - s1));
	}

	/**
	 * 修改用户订单出价记录
	 *
	 * @return
	 */
	private void updAuctionDetail(BidResponse bidResponse, AuctionInfo auctionInfo) {
		Long s1 = System.currentTimeMillis();
		Integer userId = Integer.valueOf(bidResponse.getBidder().getUserId());
		Integer subUserId = Integer.valueOf(bidResponse.getBidder().getSubId());
		AuctionDetail auctionDetail = shardingAuctionDetailDao.selectByUserId(
				Integer.valueOf(bidResponse.getBidder().getUserId()), Integer.valueOf(bidResponse.getAuctionNo()),
				bidResponse.getBidder().getSubId());
		if (null != auctionDetail) {
			if (BidType.AUTO.equals(bidResponse.getBidType())) {
				shardingAuctionDetailDao.updDetailStatusByAuctionIdAndUserIdAndSubUserId(auctionInfo.getId(), userId,
						subUserId, 1, 1, 1);
				// shardingAuctionDetailDao.updDetailStatus(auctionDetail.getId(),
				// 1, 1, 1);
			} else {
				Integer zcoin = 0;
				Integer pcoin = 0;
				if (auctionInfo.getPoundage() > 1) {
					AuctionTxnDetail auctionTxnDetai = auctionTxnDetailDao.findByTxn(bidResponse.getBizNo());
					if (BidType.DELEGATE.equals(bidResponse.getBidType())) {
						String used = bidResponse.getBidCycle().split("-")[1];
						if (1 == Integer.valueOf(used)) {
							zcoin = Integer.valueOf(String.valueOf(auctionTxnDetai.getFreeCount())
									.substring(auctionTxnDetai.getFreeCount().toString().length() - 1));
							pcoin = Integer.valueOf(String.valueOf(auctionTxnDetai.getValidCount())
									.substring(auctionTxnDetai.getValidCount().toString().length() - 1));
							if (zcoin == 0 && pcoin == 0) {
								zcoin = AccountCode.FREE.equals(bidResponse.getAccountCode())
										? auctionInfo.getPoundage() : 0;
								pcoin = AccountCode.YIPPE.equals(bidResponse.getAccountCode())
										? auctionInfo.getPoundage() : 0;
							}
						} else {
							zcoin = AccountCode.FREE.equals(bidResponse.getAccountCode()) ? auctionInfo.getPoundage()
									: 0;
							pcoin = AccountCode.YIPPE.equals(bidResponse.getAccountCode()) ? auctionInfo.getPoundage()
									: 0;
						}
					} else {
						zcoin = auctionTxnDetai.getFreeCount();
						pcoin = auctionTxnDetai.getValidCount();
					}
				} else {
					zcoin = AccountCode.FREE.equals(bidResponse.getAccountCode()) ? auctionInfo.getPoundage() : 0;
					pcoin = AccountCode.YIPPE.equals(bidResponse.getAccountCode()) ? auctionInfo.getPoundage() : 0;
				}
				addBidCount(auctionDetail, auctionInfo.getId().toString(), userId.toString(), pcoin, zcoin);
				// shardingAuctionDetailDao.updDetailStatus(auctionDetail.getId(),1,zcoin,pcoin);
				shardingAuctionDetailDao.updDetailStatusByAuctionIdAndUserIdAndSubUserId(auctionInfo.getId(), userId,
						subUserId, 1, zcoin, pcoin);
			}
		} else {
			AuctionDetail detail = getAuctionDetail(bidResponse, auctionInfo);
			if (!BidType.AUTO.equals(bidResponse.getBidType())) {
				addBidCount(detail, auctionInfo.getId().toString(), userId.toString(), detail.getPCoin(),
						detail.getZCoin());
			}
			auctionDetailDao.saveAuctionDetail(detail);
		}
		long s2 = System.currentTimeMillis();
		log.info("BidManagerServiceImpl updAuctionDetail time  ms={}", (s2 - s1));
	}

	private void addBidCount(AuctionDetail auctionDetail, String auctionNo, String userId, Integer pcoin,
			Integer zcoin) {
		if (auctionDetail == null || auctionDetail.getBidCount() == null) {
			auctionDetail.setBidCount(0);
		}
		if (auctionDetail == null || auctionDetail.getPCoin() == null) {
			auctionDetail.setPCoin(0);
		}
		if (auctionDetail == null || auctionDetail.getZCoin() == null) {
			auctionDetail.setZCoin(0);
		}
		BidStatistics bid = new BidStatistics();
		bid.setBidCount(auctionDetail.getBidCount() + 1);
		bid.setPCoin(auctionDetail.getPCoin() + pcoin);
		bid.setZCoin(auctionDetail.getZCoin() + zcoin);
		bid.setUserId(userId);
		bidRepository.putBidStatistics(auctionNo, bid);
	}

	/**
	 * 出价总表
	 *
	 * @param
	 * @return
	 */
	private String saveAuctionBidInfo(BidResponse bidResponse, AuctionInfo auctionInfo) {
		Long s1 = System.currentTimeMillis();
		log.info("AuctionBidServiceImpl getSaveAuctionBidInfo inove userId={},auctionId={}",
				bidResponse.getBidder().getUserId(), bidResponse.getAuctionNo());
		AuctionBidInfo auctionBidInfo = null;
		auctionBidInfo = shardingAuctionBidInfoDao.findBidInfo(Integer.valueOf(bidResponse.getBidder().getUserId()), 2,
				Integer.valueOf(bidResponse.getAuctionNo()));
		if (null != auctionBidInfo && BidType.DELEGATE.equals(bidResponse.getBidType())) {
			auctionBidInfo.setUsedCount(1);
			String used = bidResponse.getBidCycle().split("-")[1];
			Integer zcoin = 0;
			Integer pcoin = 0;
			if (auctionInfo.getPoundage() > 1) {
				AuctionTxnDetail auctionTxnDetai = auctionTxnDetailDao.findByTxn(bidResponse.getBizNo());
				if (BidType.DELEGATE.equals(bidResponse.getBidType())) {
					if (1 == Integer.valueOf(used)) {
						zcoin = Integer.valueOf(String.valueOf(auctionTxnDetai.getFreeCount())
								.substring(auctionTxnDetai.getFreeCount().toString().length() - 1));
						pcoin = Integer.valueOf(String.valueOf(auctionTxnDetai.getValidCount())
								.substring(auctionTxnDetai.getValidCount().toString().length() - 1));
						if (zcoin == 0 && pcoin == 0) {
							zcoin = AccountCode.FREE.equals(bidResponse.getAccountCode()) ? auctionInfo.getPoundage()
									: 0;
							pcoin = AccountCode.YIPPE.equals(bidResponse.getAccountCode()) ? auctionInfo.getPoundage()
									: 0;
						}
					} else {
						zcoin = AccountCode.FREE.equals(bidResponse.getAccountCode()) ? auctionInfo.getPoundage() : 0;
						pcoin = AccountCode.YIPPE.equals(bidResponse.getAccountCode()) ? auctionInfo.getPoundage() : 0;
					}
				} else {
					zcoin = auctionTxnDetai.getFreeCount();
					pcoin = auctionTxnDetai.getValidCount();
				}
			} else {
				zcoin = AccountCode.FREE.equals(bidResponse.getAccountCode()) ? auctionInfo.getPoundage() : 0;
				pcoin = AccountCode.YIPPE.equals(bidResponse.getAccountCode()) ? auctionInfo.getPoundage() : 0;
			}
			if (Integer.valueOf(used) >= (auctionBidInfo.getBidCount() == null ? 1 : auctionBidInfo.getBidCount())) {
				shardingAuctionBidInfoDao.updStatusByTxnSeqNo(auctionBidInfo.getTxnSeqNo(), 3, Integer.valueOf(used),
						pcoin, zcoin);
			} else {
				shardingAuctionBidInfoDao.updStatusByTxnSeqNo(auctionBidInfo.getTxnSeqNo(), auctionBidInfo.getStatus(),
						Integer.valueOf(used), pcoin, zcoin);
			}
		} else if (BidType.AUTO.equals(bidResponse.getBidType())) {
			auctionBidInfo = new AuctionBidInfo();
			if (Bidder.isAutoBidder(bidResponse.getBidder())) {
				auctionBidInfo.setUserName(Base64Utils.encodeStr(bidResponse.getBidder().getName()));
			} else {
				auctionBidInfo.setUserName((bidResponse.getBidder().getName()));
			}
			auctionBidInfo.setUserId(Integer.valueOf(bidResponse.getBidder().getUserId()));
			auctionBidInfo.setAuctionProdId(auctionInfo.getAuctionProdId());
			auctionBidInfo.setAuctionNo(Integer.valueOf(bidResponse.getAuctionNo()));
			auctionBidInfo.setCreateTime(new Date());
			auctionBidInfo.setTxnSeqNo(bidResponse.getBizNo());
			auctionBidInfo.setBidCount(1);
			auctionBidInfo.setStatus(getBidTypeStatus(bidResponse));
			auctionBidInfo.setSubUserId(bidResponse.getBidder().getSubId());
			auctionBidInfo.setPCoin(0);
			auctionBidInfo.setZCoin(1);

			String tableSuffix = TableNameSuffixUtil.getCurrentTableNameSuffix();
			auctionBidInfo.setTableSuffix(tableSuffix);
			auctionBidInfoDao.insert(auctionBidInfo);
		}else{
			auctionBidInfo = shardingAuctionBidInfoDao.findBidInfo(Integer.valueOf(bidResponse.getBidder().getUserId()), 3,
					Integer.valueOf(bidResponse.getAuctionNo()));
		}
		long s2 = System.currentTimeMillis();
		log.info("BidManagerServiceImpl saveAuctionBidInfo time  ms={}", (s2 - s1));
		if(auctionBidInfo==null){
			return null;
		}
		return String.valueOf(auctionBidInfo.getId());
	}

	/**
	 * 出价明细表
	 *
	 * @param
	 * @param
	 * @return
	 */
	private void saveAuctionBidetail(BidResponse bidResponse, AuctionInfo auctionInfo, String bidId) {
		long s1 = System.currentTimeMillis();
		log.info("AuctionBidServiceImpl getSaveAuctionBidetail inove userId={},auctionId={}",
				bidResponse.getBidder().getUserId(), bidResponse.getAuctionNo());
		AuctionBidetail auctionBidetail = new AuctionBidetail();
		auctionBidetail.setBidId(bidId);
		auctionBidetail.setUserId(getUserId(bidResponse));
		if (Bidder.isAutoBidder(bidResponse.getBidder())) {
			auctionBidetail.setUserName(Base64Utils.encodeStr(bidResponse.getBidder().getName()));
			auctionBidetail.setNickName(Base64Utils.encodeStr(bidResponse.getBidder().getName()));
		} else {
			auctionBidetail.setUserName((bidResponse.getBidder().getName()));
			auctionBidetail.setNickName(bidResponse.getBidder().getName());
		}
		auctionBidetail.setBidType(getBidType(bidResponse));
		auctionBidetail.setAuctionProdId(auctionInfo.getAuctionProdId());
		auctionBidetail.setAuctionNo(auctionInfo.getId());
		auctionBidetail.setCreateTime(new Date());
		auctionBidetail.setHeadImg(bidResponse.getBidder().getHeadImgUrl());
		auctionBidetail.setAddress(bidResponse.getBidder().getAddrArea());
		auctionBidetail.setBidSubType(getBidUserType(bidResponse));
		auctionBidetail.setBidPrice(bidResponse.getLastPrice());
		auctionBidetail.setSubUserId(bidResponse.getBidder().getSubId());

		String tableSuffix = TableNameSuffixUtil.getCurrentTableNameSuffix();
		auctionBidetail.setTableSuffix(tableSuffix);
		auctionBidetailDao.saveAuctionBidetail(auctionBidetail);
		long s2 = System.currentTimeMillis();
		log.info("BidManagerServiceImpl saveAuctionBidetail time  ms={}", (s2 - s1));
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
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 返回VIP用户或正常用户id
	 *
	 * @param bidResponse
	 * @return
	 */
	private static Integer getUserId(BidResponse bidResponse) {
		// vip用户返回指定用户id
		if (BidType.AUTO.equals(bidResponse.getBidType())) {
			return 1;
		} else {
			return Integer.valueOf(bidResponse.getBidder().getUserId());
		}
	}

	/**
	 * 出价类型
	 *
	 * @param bidResponse
	 * @return
	 */
	private static Integer getBidType(BidResponse bidResponse) {
		if (BidType.AUTO.equals(bidResponse.getBidType())) {
			return ConstantUtil.AUACTION_TRADE_BID_DETAIL_TYPE_THREE;
		} else if (AccountCode.FREE.equals(bidResponse.getAccountCode())) {
			return ConstantUtil.AUACTION_TRADE_BID_DETAIL_TYPE_TWO;
		} else {
			return ConstantUtil.AUACTION_TRADE_BID_DETAIL_TYPE_ONE;
		}

	}

	/**
	 * 出价人类型
	 *
	 * @param bidResponse
	 * @return
	 */
	private static Integer getBidUserType(BidResponse bidResponse) {
		if (BidType.AUTO.equals(bidResponse.getBidType())) {
			return ConstantUtil.AUACTION_TRADE_BID_DETAIL_USER_TYPE_THREE;
		} else if (BidType.DELEGATE.equals(bidResponse.getBidType())) {
			return ConstantUtil.AUACTION_TRADE_BID_DETAIL_USER_TYPE_TWO;
		} else {
			return ConstantUtil.AUACTION_TRADE_BID_DETAIL_USER_TYPE_ONE;
		}
	}

	/***
	 * 出价状态
	 */
	private static Integer getBidTypeStatus(BidResponse bidResponse) {
		if (BidType.DELEGATE.equals(bidResponse.getBidType())) {
			return 2;
		} else {
			return 3;
		}
	}

	/**
	 * 新增订单
	 *
	 * @return
	 */
	private void getSaveOrder(AuctionInfo auctionInfo, BidHitResponse bidHitResponse, AuctionDetail auctionDetail) {
		OrderInfoModel orderInfoModel = new OrderInfoModel();
		orderInfoModel.setProductPrice(auctionInfo.getProductPrice());
		orderInfoModel.setOrderAmount(bidHitResponse.getLastPrice());
		orderInfoModel.setUserId(Integer.valueOf(bidHitResponse.getLastBidder().getUserId()));
		orderInfoModel.setAddress(bidHitResponse.getLastBidder().getAddrArea());
		orderInfoModel.setAuctionNo(auctionInfo.getId());
		orderInfoModel.setProductNum(1);
		orderInfoModel.setOrderType(1);
		orderInfoModel.setProductId(auctionInfo.getProductId());
		orderInfoModel.setBuyId(bidHitResponse.getLastBidder().getUserId());
		orderInfoModel.setPaidMoney(bidHitResponse.getLastPrice());
		orderInfoModel.setBidTimes(auctionDetail.getBidCount());
		orderInfoModel.setBuyCoinNum(auctionDetail.getBidCount());
		if (!Bidder.isAutoBidder(bidHitResponse.getLastBidder())) {
			orderInfoModel.setIsLiuPai(false);
		} else {
			orderInfoModel.setIsLiuPai(true);
		}
		orderInfoModel.setIsLiuPai(Bidder.isAutoBidder(bidHitResponse.getLastBidder()));
		orderInfoModel.setUserName(bidHitResponse.getLastBidder().getName());
		orderInfoModel.setUserPhone(bidHitResponse.getLastBidder().getSubId());
		orderInfoStubService.saveAuctionOrder(orderInfoModel);
	}

	/**
	 * 返回购物币
	 */
	private void ReturnBidCode(AuctionInfo auctionInfo, BidHitResponse bidHitResponse) {
		// 进行退购物币操作
		List<AuctionDetail> list = shardingAuctionDetailDao.findList(ConstantUtil.AUACTION_DETAIL_STATUS_THREE,
				auctionInfo.getId(), ConstantUtil.AUACTION_USER_TYPE_ONE);
		for (int i = 0; i < list.size(); i++) {
			AuctionDetail auctionDetail = list.get(i);
			if (auctionDetail.getReturnPrice().intValue() > 0
					&& ConstantUtil.AUACTION_USER_TYPE_ONE.equals(auctionDetail.getUserType())) {
				try {
					ServiceResult serviceResult = accountInfoStubService.backCoinOperation(
							String.valueOf(auctionInfo.getId()), auctionDetail.getReturnPrice().intValue(), 4,
							auctionInfo.getProductId(), auctionInfo.getProductName(), auctionInfo.getPreviewPic(), 1,
							null, auctionDetail.getUserId());
					if (serviceResult.isSuccessed()) {
						log.info("BidManagerServiceImpl ReturnBidCode success userId={},auctionId={},returnPice={}",
								auctionDetail.getUserId(), auctionDetail.getAuctionId(),
								auctionDetail.getReturnPrice());
					} else {
						log.info("BidManagerServiceImpl ReturnBidCode error userId={},auctionId={},returnPice={}",
								auctionDetail.getUserId(), auctionDetail.getAuctionId(),
								auctionDetail.getReturnPrice());
					}
				} catch (RuntimeException e) {
					log.error("BidManagerServiceImpl ReturnBidCode error", e);
				}
			}
		}
		// 进行退币操作
		if (!Bidder.isAutoBidder(bidHitResponse.getLastBidder())) {
			AuctionBidInfo auctionBidInfo = shardingAuctionBidInfoDao
					.findBidInfo(Integer.valueOf(bidHitResponse.getLastBidder().getUserId()), 2, auctionInfo.getId());
			AuctionTxnDetail auctionTxnDetail = auctionTxnDetailDao.findByTxn(auctionBidInfo.getTxnSeqNo());
			if (auctionTxnDetail != null) {
				ReturnCode(auctionTxnDetail, auctionBidInfo, auctionInfo);
			}
		}
	}

	/**
	 * 推送成功redis
	 */
	private void successRedis(BidHitResponse bidHitResponse, AuctionInfo auctionInfo) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("userName", Base64Utils.decodeStr(bidHitResponse.getLastBidder().getName()));
		jsonObject.put("auctionId", bidHitResponse.getAuctionNo());
		jsonObject.put("auctionProdId", auctionInfo.getProductId());
		jsonObject.put("productName", auctionInfo.getProductName());
		jsonObject.put("lastPrice", bidHitResponse.getLastPrice());
		jedisCluster.lpush(ConstantUtil.AUCTION_TRADE_SUCCESS_LIST, jsonObject.toJSONString());
		jedisCluster.ltrim(ConstantUtil.AUCTION_TRADE_SUCCESS_LIST, ConstantUtil.AUCTION_TRADE_TRIMSTART,ConstantUtil.AUCTION_TRADE_TRIMEND);
		jedisCluster.del(ConstantUtil.AUCTION_TRADE_ROBOT_TRADE_old + ConstantUtil.getRedisKey(String.valueOf(auctionInfo.getId())));
		jedisCluster.del(ConstantUtil.AUCTION_TRADE_ROBOT_TRADE + ConstantUtil.getRedisKey(String.valueOf(auctionInfo.getId())));
		jedisCluster.del(ConstantUtil.AUCTION_TRADE_ROBOT_NEW_COUNT + ConstantUtil.getRedisKey(String.valueOf(auctionInfo.getId())));
		jedisCluster.del(ConstantUtil.AUCTION_TRADE_TIME + ConstantUtil.getRedisKey(String.valueOf(auctionInfo.getId())));
		jedisCluster.del(ConstantUtil.AUCTION_REACTOR_AUCTION_CONTEXT + ConstantUtil.getRedisKey(String.valueOf(auctionInfo.getId())));
		jedisCluster.del(ConstantUtil.AUCTION_REACTOR_BID_COST + ConstantUtil.getRedisKey(String.valueOf(auctionInfo.getId())));
		jedisCluster.del(ConstantUtil.AUCTION_REACTOR_BID_STATISTICS + ConstantUtil.getRedisKey(String.valueOf(auctionInfo.getId())));
		jedisCluster.del(ConstantUtil.AUCTION_REACTOR_BID_RESULT_QUEUE + ConstantUtil.getRedisKey(String.valueOf(auctionInfo.getId())));
		jedisCluster.del(ConstantUtil.AUCTION_REACTOR_MULIT_BID_QUEUE + ConstantUtil.getRedisKey(String.valueOf(auctionInfo.getId())));
		jedisCluster.del(ConstantUtil.AUCTION_REACTOR_MULTI_BID_HASH + ConstantUtil.getRedisKey(String.valueOf(auctionInfo.getId())));

	}

	/**
	 *
	 * @param bidResponse
	 *            出价参数
	 * @return
	 */
	private static AuctionDetail getAuctionDetail(BidResponse bidResponse, AuctionInfo auctionInfo) {
		AuctionDetail auctionDetail = new AuctionDetail();
		auctionDetail = new AuctionDetail();
		auctionDetail.setAuctionId(auctionInfo.getId());
		auctionDetail.setAuctionProdId(auctionInfo.getAuctionProdId());
		auctionDetail.setAuctionStatus(ConstantUtil.AUCTION_TRADE_BID_TYPE_ONE);
		auctionDetail.setCreateTime(new Date());
		auctionDetail.setBidCount(1);
		auctionDetail.setUserName(Base64Utils.encodeStr(bidResponse.getBidder().getName()));
		auctionDetail.setUserId(Integer.valueOf(bidResponse.getBidder().getUserId()));
		auctionDetail.setNickName(Base64Utils.encodeStr(bidResponse.getBidder().getName()));
		auctionDetail.setHeadImg(bidResponse.getBidder().getHeadImgUrl());
		auctionDetail.setAddress(bidResponse.getBidder().getAddrArea());
		auctionDetail.setPCoin(0);
		auctionDetail.setZCoin(auctionInfo.getPoundage());
		auctionDetail.setSubUserId(String.valueOf(bidResponse.getBidder().getSubId()));
		// 用户类型必须为 机器人
		auctionDetail.setUserType(2);
		auctionDetail.setReturnPrice(new BigDecimal("0"));
		String tableSuffix = TableNameSuffixUtil.getCurrentTableNameSuffix();
		auctionDetail.setTableSuffix(tableSuffix);
		return auctionDetail;
	}

	@Override
	public int getOrder() {
		return Integer.MAX_VALUE;
	}

	private void calculateCode(Integer pcoin, Integer zcoin, BidResponse bidResponse, Integer poundage) {
		zcoin = AccountCode.FREE.equals(bidResponse.getAccountCode()) ? poundage : 0;
		pcoin = AccountCode.YIPPE.equals(bidResponse.getAccountCode()) ? poundage : 0;
	}

	/**
	 * 进行退币
	 * 
	 * @return
	 */
	private void ReturnCode(AuctionTxnDetail auctionTxnDetail, AuctionBidInfo auctionBidInfo, AuctionInfo auctionInfo) {
		// 进行退币计算
		Integer pcoin = 0;
		Integer zcoin = 0;
		if (auctionBidInfo.getBidCount() > auctionBidInfo.getUsedCount()) {
			pcoin = auctionTxnDetail.getValidCount() - auctionBidInfo.getPCoin();
			zcoin = auctionTxnDetail.getFreeCount() - auctionBidInfo.getZCoin();
			if (pcoin > 0) {
				ServiceResult serviceResult = accountInfoStubService.backCoinOperation(
						String.valueOf(auctionInfo.getId()), (pcoin), 1, auctionInfo.getProductId(),
						auctionInfo.getProductName(), auctionInfo.getPreviewPic(), 1, null, auctionBidInfo.getUserId());
				log.info("BidManagerServiceImpl ReturnBidCode success userId={},auctionId={},pcoin={}",
						auctionBidInfo.getUserId(), auctionBidInfo.getAuctionNo(),
						(auctionBidInfo.getBidCount() - auctionBidInfo.getUsedCount()));
			}
			if (zcoin > 0) {
				ServiceResult serviceResult = accountInfoStubService.backCoinOperation(
						String.valueOf(auctionInfo.getId()), (zcoin), 2, auctionInfo.getProductId(),
						auctionInfo.getProductName(), auctionInfo.getPreviewPic(), 1, null, auctionBidInfo.getUserId());
				log.info("BidManagerServiceImpl ReturnBidCode success userId={},auctionId={},pcoin={}",
						auctionBidInfo.getUserId(), auctionBidInfo.getAuctionNo(),
						(auctionBidInfo.getBidCount() - auctionBidInfo.getUsedCount()));
			}
		}
	}

	/**
	 * 发布退币比例-: 是否命中审核中的版本
	 * 
	 * @param appInfo
	 * @return
	 */
	private boolean isHitReleaseVersion(String appInfo) {
		if (appInfo == null) {
			return false;
		}
		try {
			JSONObject obj = JSON.parseObject(appInfo);
			Integer appVersion = Integer.valueOf(obj.get("appVersion").toString().replace(".", ""));
			String appMarket = obj.get("appMarket").toString();
			// 从Redis获取已经配置好的值
			Map<String, String> systemMap = jedisCluster.hgetAll(SysContant.APP_COIN_RULE);
			if (null == systemMap || systemMap.isEmpty()
					|| StringUtils.isBlank(systemMap.get(SysContant.APP_COIN_RULE))) {
				return false;
			}
			String appCoinRule = systemMap.get(SysContant.APP_COIN_RULE);
			JSONArray appCoinRuleArray = JSONArray.parseArray(appCoinRule);

			for (Object appCoinRuleObj : appCoinRuleArray) {
				JSONObject shelfObj = (JSONObject) appCoinRuleObj;
				int ruleVersion = Integer.parseInt(shelfObj.get("appVersion").toString().replace(".", ""));
				if ((appVersion == ruleVersion) && (appMarket.equals(shelfObj.get("appMarket").toString()))) {
					return true;
				}
			}
		} catch (Exception e) {
			log.error("releaseReturn error: appInfo:{}", appInfo, e);
		}
		return false;

	}
}
