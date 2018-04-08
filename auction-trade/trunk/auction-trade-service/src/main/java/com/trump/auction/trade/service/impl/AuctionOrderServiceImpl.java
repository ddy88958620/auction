package com.trump.auction.trade.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.github.pagehelper.PageHelper;
import com.trump.auction.reactor.api.model.AuctionContext;
import com.trump.auction.reactor.api.model.BidRequest;
import com.trump.auction.reactor.api.model.BidResponse;
import com.trump.auction.reactor.api.model.Bidder;
import com.trump.auction.reactor.api.model.BidStatistics;
import com.trump.auction.reactor.api.model.MultiBid;
import com.trump.auction.reactor.ext.service.BidExtService;
import com.trump.auction.reactor.repository.BidRepository;
import com.trump.auction.trade.dao.AuctionInfoDao;
import com.trump.auction.trade.dao.AuctionProductInfoDao;
import com.trump.auction.trade.dao.AuctionProductRecordDao;
import com.trump.auction.trade.dao.sharding.ShardingAuctionBidInfoDao;
import com.trump.auction.trade.dao.sharding.ShardingAuctionBidetailDao;
import com.trump.auction.trade.dao.sharding.ShardingAuctionDetailDao;
import com.trump.auction.trade.domain.AuctionBidetail;
import com.trump.auction.trade.domain.AuctionDetail;
import com.trump.auction.trade.domain.AuctionInfo;
import com.trump.auction.trade.domain.AuctionProductInfo;
import com.trump.auction.trade.domain.AuctionProductRecord;
import com.trump.auction.trade.service.AuctionOrderService;
import com.trump.auction.trade.util.Base64Utils;
import com.trump.auction.trade.util.ConstantUtil;
import com.trump.auction.trade.vo.AuctionOrderForListVo;
import com.trump.auction.trade.vo.AuctionOrderVo;
import com.trump.auction.trade.vo.AuctionProductRecordVo;
import com.trump.auction.trade.vo.AuctionVo;
import com.trump.auction.trade.vo.BidVo;
import com.trump.auction.trade.vo.ParamVo;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisCluster;

/**
 * 竞拍订单信息service实现
 * 
 * @author Administrator
 * @date 2018/1/6
 */
@Service("auctionOrderService")
@Slf4j
public class AuctionOrderServiceImpl implements AuctionOrderService {
	@Autowired
	private ShardingAuctionBidetailDao shardingAuctionBidetailDao;
	@Autowired
	private ShardingAuctionDetailDao shardingAuctionDetailDao;
	@Autowired
	private AuctionProductInfoDao auctionProductInfoDao;

	@Autowired
	private AuctionInfoDao auctionInfoDao;
	@Autowired
	private AuctionProductRecordDao auctionProductRecordDao;
	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private ShardingAuctionBidInfoDao shardingAuctionBidInfoDao;
	@Autowired
	private BidExtService bidExtService;

	@Autowired
	private JedisCluster jedisCluster;
	@Autowired
	private BidRepository bidRepository;

	/**
	 * 查询竞拍订单信息分页列表
	 * 
	 * @param paramVo
	 * @return
	 */
	@Override
	public Paging<AuctionOrderVo> findAuctionOrder(ParamVo paramVo) {
		log.info("findAuctionOrder:参数：" + paramVo);
		PageHelper.startPage(paramVo.getPageNum(), paramVo.getPageSize());
		Paging<AuctionOrderVo> page = PageUtils.page(shardingAuctionDetailDao.find(paramVo));
		if (CollectionUtils.isNotEmpty(page.getList())) {
			AuctionInfo auctionInfo = null;
			for (AuctionOrderVo vo : page.getList()) {
				auctionInfo = auctionInfoDao.selectByPrimaryKey(vo.getAuctionId());
				if (auctionInfo != null) {
					vo.setAuctionNo(auctionInfo.getAuctionNo());
					vo.setEndTime(auctionInfo.getEndTime());
					vo.setPreviewPic(auctionInfo.getPreviewPic());
					vo.setProductPrice(auctionInfo.getProductPrice());
					vo.setBidPrice(getBidPrice(auctionInfo));
				}
			}
		}
		return page;
	}

	/**
	 * 获取最新的出价记录
	 * 
	 * @param paramVo
	 * @return
	 */
	@Override
	public List<BidVo> getLastBidRecord(ParamVo paramVo) {
		log.info("getLastBidRecord......" + paramVo);
		List<BidVo> bidVos = new ArrayList<BidVo>();
		List<BidResponse> bidResponses = bidExtService.getLatest(String.valueOf(paramVo.getAuctionId()), 3);
		if (null != bidResponses && bidResponses.size() > 0) {
			for (BidResponse bidResponse : bidResponses) {
				BidVo bidVo = new BidVo();
				bidVo.setAddress(bidResponse.getBidder().getAddrArea());
				bidVo.setBidPrice(bidResponse.getLastPrice());
				bidVo.setHeadImg(bidResponse.getBidder().getHeadImgUrl());
				if (Bidder.isAutoBidder(bidResponse.getBidder())) {
					bidVo.setNickName(Base64Utils.encodeStr(bidResponse.getBidder().getName()));
					bidVo.setUserName(Base64Utils.encodeStr(bidResponse.getBidder().getName()));
				} else {
					bidVo.setNickName(bidResponse.getBidder().getName());
					bidVo.setUserName(bidResponse.getBidder().getName());
				}
				bidVos.add(bidVo);
			}
		} else {
			List<AuctionBidetail> auctionBidetails = shardingAuctionBidetailDao.findBidTimes(paramVo.getAuctionId(), 3);
			for (AuctionBidetail auctionBidetail : auctionBidetails) {
				BidVo bidVo = new BidVo();
				bidVo.setAddress(auctionBidetail.getAddress());
				bidVo.setBidPrice(auctionBidetail.getBidPrice());
				bidVo.setHeadImg(auctionBidetail.getHeadImg());
				bidVo.setUserName(auctionBidetail.getUserName());
				bidVo.setNickName(auctionBidetail.getNickName());
				bidVo.setCreateTime(auctionBidetail.getCreateTime());
				bidVos.add(bidVo);
			}
		}
		return bidVos;
	}

	/**
	 * 获取动态数据
	 * 
	 * @param paramVo
	 * @return
	 */
	@Override
	public AuctionOrderVo getDynamicData(ParamVo paramVo) {
		log.info("getDynamicData......" + paramVo);
		Long startTime = System.currentTimeMillis();
		AuctionOrderVo auctionOrderVo = new AuctionOrderVo();
		if (paramVo == null || paramVo.getAuctionId() == null) {
			return auctionOrderVo;
		}
		AuctionInfo auctionInfo = auctionInfoDao.selectByPrimaryKey(paramVo.getAuctionId());
		if (auctionInfo != null) {
			auctionOrderVo.setUserBidCount(0);
			auctionOrderVo.setUsedCount(0);
			auctionOrderVo.setRemainBidCount(0);
			if (paramVo.getUserId() != null) {
				String auctionNo = paramVo.getAuctionId().toString();
				Bidder bidUser = Bidder.create(paramVo.getUserId().toString(), paramVo.getUserId().toString());
				// 判断是否在委托队列里
				if (bidRepository.isWaitBid(auctionNo, bidUser)) {
					Map<String, MultiBid> bidMap = bidRepository.getWaitBid(auctionNo);
					if (!org.springframework.util.CollectionUtils.isEmpty(bidMap)) {
						List<MultiBid> result = new ArrayList<>(bidMap.values());
						for (MultiBid multiBid : result) {
							Bidder bidder = multiBid.getBidder();
							// 是否在排队中，在排队中代表其中一次还没出
							if (bidder != null && bidder.getUserId().equals(paramVo.getUserId().toString())) {
								Integer leftBidCount = multiBid.getLeftBidCount();
								if (bidRepository.isInQueue(multiBid.getAuctionNo(), bidder)) {
									leftBidCount += 1;
								}
								auctionOrderVo.setUserBidCount(multiBid.getBidCount());
								auctionOrderVo.setRemainBidCount(leftBidCount);
								auctionOrderVo.setUsedCount(multiBid.getBidCount() - leftBidCount);
								break;
							}
						}
					}
				}
				if (auctionOrderVo.getRemainBidCount() <= 0 && bidRepository.isInQueue(auctionNo, bidUser)) {
					List<BidRequest> bidRequests = bidRepository.getBidQueue(auctionNo);
					for (BidRequest bidRequest : bidRequests) {
						if (bidRequest.getBidder().getUserId().equals(bidUser.getUserId())) {
							String[] bidcounts = bidRequest.getBidCycle().split("-");
							Integer bidCount = Integer.valueOf(bidcounts[0]);
							Integer leftBidCount = bidCount - Integer.valueOf(bidcounts[1])+1;
							auctionOrderVo.setUserBidCount(bidCount);
							auctionOrderVo.setRemainBidCount(leftBidCount);
							auctionOrderVo.setUsedCount(bidCount - leftBidCount);
							break;
						}
					}
				}
			}
			if (auctionInfo.getStartPrice() == null) {
				auctionInfo.setStartPrice(BigDecimal.ZERO);
			}
			if (auctionInfo.getIncreasePrice() == null) {
				auctionInfo.setIncreasePrice(BigDecimal.ZERO);
			}
			BigDecimal bidCount = BigDecimal.ZERO;
			AuctionContext auctionContext = bidRepository.getContext(String.valueOf(auctionInfo.getId()));
			if (auctionContext != null) {
				bidCount = new BigDecimal(auctionContext.getTotalBidCount());
			} else if (auctionInfo.getTotalBidCount() != null) {
				bidCount = new BigDecimal(auctionInfo.getTotalBidCount());
			}
			auctionOrderVo
					.setBidPrice(auctionInfo.getStartPrice().add(bidCount.multiply(auctionInfo.getIncreasePrice())));
			auctionOrderVo.setBidPrice(auctionOrderVo.getBidPrice().setScale(2, BigDecimal.ROUND_HALF_UP));
			auctionOrderVo.setPageView(auctionInfo.getPageView());
			auctionOrderVo.setCollectCount(auctionInfo.getCollectCount());
			auctionOrderVo.setPersonCount(auctionInfo.getPersonCount());
			auctionOrderVo.setCountdown(auctionInfo.getCountDown());
			auctionOrderVo.setStatus(auctionInfo.getStatus());
			auctionOrderVo.setEndTime(auctionInfo.getEndTime());

			ParamVo voBid = new ParamVo();
			voBid.setAuctionProdId(paramVo.getAuctionProdId());
			voBid.setAuctionId(paramVo.getAuctionId());
			auctionOrderVo.setBidVoList(getLastBidRecord(voBid));
			// 开拍中
			if (auctionInfo.getStatus().equals(1)) {
				if (CollectionUtils.isNotEmpty(auctionOrderVo.getBidVoList())) {
					String newTime = jedisCluster.get(ConstantUtil.AUCTION_TRADE_TIME
							+ ConstantUtil.getRedisKey(String.valueOf(auctionInfo.getId())));
					if (StringUtils.isNotBlank(newTime)) {
						Long time = Long.valueOf(newTime) + (auctionInfo.getCountDown() * 1000)
								- System.currentTimeMillis();
						auctionOrderVo.setDynamicCountdown(time <= 0 ? auctionInfo.getCountDown() * 1000 : time);
					} else {
						auctionOrderVo.setDynamicCountdown(auctionInfo.getCountDown() * 1000);
					}
				}
			}
			// 本期未开始
			if (auctionInfo.getStatus().equals(3)) {
				auctionOrderVo.setCurrentCountDown(
						(auctionInfo.getStartTime().getTime() - System.currentTimeMillis()) / 1000);
				auctionOrderVo.setDynamicCountdown(0L);
			}
		}
		// 说明是已经拍中的拍品，则查询下一期的信息
		if (auctionInfo != null && auctionInfo.getStatus().equals(2)) {
			AuctionInfo nextInfo = auctionInfoDao.getNextAuctionInfo(auctionInfo.getAuctionProdId());
			if (nextInfo != null && !nextInfo.getId().equals(auctionInfo.getId())) {
				auctionOrderVo.setNextAuctionId(nextInfo.getId());
				auctionOrderVo.setNextStartTime(nextInfo.getStartTime());
				auctionOrderVo.setNextAuctionProdId(nextInfo.getAuctionProdId());
				auctionOrderVo
						.setNextCountDown((nextInfo.getStartTime().getTime() - System.currentTimeMillis()) / 1000);
			}
			if (paramVo.getUserId() != null && Objects.equals(paramVo.getUserId(), auctionInfo.getWinUserId())) {
				// 当前用户已拍中
				auctionOrderVo.setStatus(4);
			}
			// 返回成交金额(付款金额)
		}
		auctionOrderVo.setBidCount(0);
		auctionOrderVo.setPCoin(0);
		auctionOrderVo.setZCoin(0);
		if (paramVo.getUserId() != null && bidRepository.hasBidStatistics(paramVo.getAuctionId().toString(),paramVo.getUserId().toString())) {
			BidStatistics bidCost = bidRepository.getBidStatistics(paramVo.getAuctionId().toString(),
					paramVo.getUserId().toString());
			if (bidCost != null) {
				auctionOrderVo.setBidCount(bidCost.getBidCount());
				auctionOrderVo.setPCoin(bidCost.getPCoin());
				auctionOrderVo.setZCoin(bidCost.getZCoin());
			}
		} else {
			AuctionDetail detail = null;
			if (null != paramVo.getUserId() && null != paramVo.getAuctionId()) {
				detail = shardingAuctionDetailDao.selectByUserId(paramVo.getUserId(), paramVo.getAuctionId(),
						String.valueOf(paramVo.getUserId()));
			}
			if (detail != null) {
				auctionOrderVo.setBidCount(detail.getBidCount());
				auctionOrderVo.setPCoin(detail.getPCoin());
				auctionOrderVo.setZCoin(detail.getZCoin());
			}
		}
		Long endTime = System.currentTimeMillis();
		log.info("getDynamicData......执行调用时间：" + (endTime - startTime));
		return auctionOrderVo;
	}

	/**
	 * 获取列表上的价格和倒计时动态数据
	 * @param paramVo
	 * @return
	 */
	@Override
	public AuctionOrderForListVo getDynamicDataForList(ParamVo paramVo) {
		AuctionOrderForListVo auctionOrderForListVo = new AuctionOrderForListVo();
		AuctionOrderVo auctionOrderVo = getDynamicData(paramVo);
		auctionOrderForListVo.setAuctionId(paramVo.getAuctionId());
		auctionOrderForListVo.setBidPrice(auctionOrderVo.getBidPrice());
		auctionOrderForListVo.setDynamicCountdown(auctionOrderVo.getDynamicCountdown());
		return auctionOrderForListVo;
	}

	/**
	 * 获取拍品基础数据
	 * 
	 * @param paramVo
	 * @return
	 */
	@Override
	public AuctionOrderVo getAuctionBaseData(ParamVo paramVo) {

		log.info("getAuctionBaseData......" + paramVo);
		AuctionOrderVo auctionOrderVo = new AuctionOrderVo();
		if (paramVo == null || paramVo.getAuctionId() == null) {
			return auctionOrderVo;
		}
		AuctionInfo auctionInfo = auctionInfoDao.selectByPrimaryKey(paramVo.getAuctionId());
		if (auctionInfo != null) {
			auctionOrderVo.setProductPrice(auctionInfo.getProductPrice());
			auctionOrderVo.setProductPrice(auctionOrderVo.getProductPrice().setScale(2, BigDecimal.ROUND_HALF_UP));
			auctionOrderVo.setStartPrice(auctionInfo.getStartPrice());
			auctionOrderVo.setStartPrice(auctionOrderVo.getStartPrice().setScale(2, BigDecimal.ROUND_HALF_UP));
			auctionOrderVo.setIncreasePrice(auctionInfo.getIncreasePrice());
			auctionOrderVo.setIncreasePrice(auctionOrderVo.getIncreasePrice().setScale(2, BigDecimal.ROUND_HALF_UP));
			auctionOrderVo.setCountdown(auctionInfo.getCountDown());
			auctionOrderVo.setBuyFlag(auctionInfo.getBuyFlag());
			auctionOrderVo.setReturnPercent(auctionInfo.getReturnPercent());
			auctionOrderVo.setAuctionProdId(auctionInfo.getAuctionProdId());
			auctionOrderVo.setProductName(auctionInfo.getProductName());
			AuctionProductInfo auctionProductInfo = auctionProductInfoDao
					.selectByPrimaryKey(auctionInfo.getAuctionProdId());
			if (auctionProductInfo != null) {
				auctionOrderVo.setAuctionRule(auctionProductInfo.getAuctionRule());
			}
		}

		AuctionProductRecord record = auctionProductRecordDao.getRecordByAuctionNum(paramVo.getAuctionId());
		if (record != null) {
			auctionOrderVo.setPreviewPic(record.getPreviewPic());
			auctionOrderVo.setPicUrls(record.getPicUrls());
			auctionOrderVo.setMasterPic(record.getMasterPic());
			auctionOrderVo.setPoundageName(record.getPoundageName());
			auctionOrderVo.setProportionName(record.getProportionName());
			auctionOrderVo.setIncreaseBidName(record.getIncreaseBidName());
			auctionOrderVo.setDifferenceName(record.getDifferenceName());
			auctionOrderVo.setCountdownName(record.getCountdownName());
			auctionOrderVo.setStartBidName(record.getStartBidName());
			auctionOrderVo.setIncreasePrice(auctionInfo.getIncreasePrice());
			auctionOrderVo.setPoundage(record.getPoundage());
			auctionOrderVo.setIncreaseBidName(record.getIncreaseBidName());
		}
		return auctionOrderVo;
	}

	/**
	 * 通过拍品期数ID查询记录
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public AuctionProductRecordVo getRecordByAuctionId(Integer id) {
		AuctionProductRecordVo vo = new AuctionProductRecordVo();
		if (id == null) {
			return null;
		}
		AuctionProductRecord record = auctionProductRecordDao.getRecordByAuctionNum(id);
		if (record != null) {
			beanMapper.map(record, vo);
		} else {
			return null;
		}
		return vo;
	}

	/**
	 * 分页查询
	 * 
	 * @param paramVo
	 * @return
	 */
	@Override
	public Paging<BidVo> getBidRecordPage(ParamVo paramVo) {
		log.info("getBidRecordPage:参数：" + paramVo);
		Paging<BidVo> page = new Paging<BidVo>();
		List<BidVo> bidVos = new ArrayList<BidVo>();
		List<BidResponse> bidResponses = bidExtService.getLatest(String.valueOf(paramVo.getAuctionId()), 100);
		if (null != bidResponses && bidResponses.size() > 0) {
			for (BidResponse bidResponse : bidResponses) {
				BidVo bidVo = new BidVo();
				if (Bidder.isAutoBidder(bidResponse.getBidder())) {
					bidVo.setUserName(Base64Utils.encodeStr(bidResponse.getBidder().getName()));
					bidVo.setNickName(Base64Utils.encodeStr(bidResponse.getBidder().getName()));
				} else {
					bidVo.setUserName(bidResponse.getBidder().getName());
					bidVo.setNickName(bidResponse.getBidder().getName());
				}
				bidVo.setHeadImg(bidResponse.getBidder().getHeadImgUrl());
				bidVo.setBidPrice(bidResponse.getLastPrice());
				bidVo.setAddress(bidResponse.getBidder().getAddrArea());
				bidVos.add(bidVo);
			}
		} else {
			List<AuctionBidetail> auctionBidetails = shardingAuctionBidetailDao.findBidTimes(paramVo.getAuctionId(),
					100);
			for (AuctionBidetail auctionBidetail : auctionBidetails) {
				BidVo bidVo = new BidVo();
				bidVo.setAddress(auctionBidetail.getAddress());
				bidVo.setBidPrice(auctionBidetail.getBidPrice());
				bidVo.setHeadImg(auctionBidetail.getHeadImg());
				bidVo.setUserName(auctionBidetail.getUserName());
				bidVo.setNickName(auctionBidetail.getNickName());
				bidVo.setCreateTime(auctionBidetail.getCreateTime());
				bidVos.add(bidVo);
			}
		}
		page.setList(bidVos);
		return page;
	}

	/**
	 * 往期订单信息分页列表
	 * 
	 * @param paramVo
	 * @return
	 */
	@Override
	public Paging<AuctionOrderVo> findPastOrder(ParamVo paramVo) {
		log.info("findAuctionOrder:参数：" + paramVo);
		paramVo.setAuctionStatus(2);
		PageHelper.startPage(paramVo.getPageNum(), paramVo.getPageSize());
		Paging<AuctionOrderVo> page = PageUtils.page(shardingAuctionDetailDao.find(paramVo));
		if (CollectionUtils.isNotEmpty(page.getList())) {
			AuctionInfo auctionInfo = null;
			for (AuctionOrderVo vo : page.getList()) {
				auctionInfo = auctionInfoDao.selectByPrimaryKey(vo.getAuctionId());
				if (auctionInfo != null) {
					vo.setEndTime(auctionInfo.getEndTime());
					vo.setPreviewPic(auctionInfo.getPreviewPic());
					vo.setProductPrice(auctionInfo.getProductPrice());
					vo.setProductName(auctionInfo.getProductName());
					vo.setBidPrice(getBidPrice(auctionInfo));
				}
			}
		}
		return page;
	}

	private BigDecimal getBidPrice(AuctionInfo auctionInfo) {
		if (auctionInfo.getStartPrice() == null) {
			auctionInfo.setStartPrice(new BigDecimal("0"));
		}
		if (auctionInfo.getIncreasePrice() == null) {
			auctionInfo.setIncreasePrice(new BigDecimal("0"));
		}
		BigDecimal bidCount = new BigDecimal("0");
		if (auctionInfo.getTotalBidCount() != null) {
			bidCount = new BigDecimal(auctionInfo.getTotalBidCount());
		}
		BigDecimal bidPrice = auctionInfo.getStartPrice().add(bidCount.multiply(auctionInfo.getIncreasePrice()));

		return bidPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 获取往期订单信息
	 * 
	 * @param auctionId
	 * @return
	 */
	@Override
	public AuctionOrderVo getPastOrder(Integer auctionId) {
		AuctionOrderVo vo = new AuctionOrderVo();
		log.info("getPastOrder:参数：" + auctionId);
		if (auctionId == null) {
			return vo;
		}
		ParamVo paramVo = new ParamVo();
		paramVo.setAuctionStatus(2);
		paramVo.setAuctionId(auctionId);
		vo = shardingAuctionDetailDao.selectByAuctionId(paramVo);
		if (vo != null) {
			AuctionInfo auctionInfo = auctionInfoDao.selectByPrimaryKey(vo.getAuctionId());
			if (auctionInfo != null) {
				vo.setEndTime(auctionInfo.getEndTime());
				vo.setPreviewPic(auctionInfo.getPreviewPic());
				vo.setProductPrice(auctionInfo.getProductPrice());
				vo.setProductName(auctionInfo.getProductName());
				/*
				 * if(auctionInfo.getStartPrice() == null){
				 * auctionInfo.setStartPrice(new BigDecimal("0")); }
				 * if(auctionInfo.getIncreasePrice() == null){
				 * auctionInfo.setIncreasePrice(new BigDecimal("0")); }
				 * BigDecimal bidCount = new BigDecimal("0");
				 * if(auctionInfo.getTotalBidCount() != null){ bidCount = new
				 * BigDecimal(auctionInfo.getTotalBidCount()); }
				 * vo.setBidPrice(auctionInfo.getStartPrice().add(bidCount.
				 * multiply(auctionInfo.getIncreasePrice())));
				 * vo.setBidPrice(vo.getBidPrice().setScale(2,BigDecimal.
				 * ROUND_HALF_UP));
				 */
				vo.setBidPrice(getBidPrice(auctionInfo));
			}

		}
		return vo;
	}

	/**
	 * 获取指定用户的拍中订单
	 * 
	 * @param paramVos
	 * @return
	 */
	@Override
	public List<AuctionVo> getUserOrder(List<ParamVo> paramVos) throws Exception {
		if (CollectionUtils.isEmpty(paramVos)) {
			throw new Exception("参数不能为空");
		}

		Map<String, Object> param = null;
		AuctionVo auctionVo = null;
		AuctionInfo info = null;
		List<AuctionVo> auctionVos = new ArrayList<>();
		for (ParamVo vo : paramVos) {
			param = new HashMap<>();
			param.put("userId", vo.getUserId());
			param.put("auctionId", vo.getAuctionId());
			info = auctionInfoDao.getShotAuctionInfo(param);
			auctionVo = new AuctionVo();
			if (info != null) {
				auctionVo.setUserId(info.getWinUserId());
				auctionVo.setStatus(info.getStatus());
				auctionVo.setAuctionId(info.getId());
			} else {
				auctionVo.setUserId(vo.getUserId());
				auctionVo.setStatus(3);
				auctionVo.setAuctionId(vo.getAuctionId());
			}
			auctionVos.add(auctionVo);
		}
		return auctionVos;
	}

	public List<String> findIds(Integer status) {
		return auctionInfoDao.findIds(status);
	}
}
