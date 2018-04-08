package com.trump.auction.trade.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.cf.common.utils.JsonResult;
import com.github.pagehelper.PageHelper;
import com.trump.auction.trade.dao.AuctionInfoDao;
import com.trump.auction.trade.domain.AuctionInfo;
import com.trump.auction.trade.enums.AuctionInfoStatus;
import com.trump.auction.trade.enums.AuctionProductStatusEnum;
import com.trump.auction.trade.enums.ResultEnum;
import com.trump.auction.trade.enums.SysConstant;
import com.trump.auction.trade.model.AuctionInfoModel;
import com.trump.auction.trade.model.AuctionInfoQuery;
import com.trump.auction.trade.model.AuctionProductInfoModel;
import com.trump.auction.trade.model.AuctionProductRecordModel;
import com.trump.auction.trade.model.AuctionRuleModel;
import com.trump.auction.trade.service.AuctionInfoService;
import com.trump.auction.trade.service.AuctionProdInfoService;
import com.trump.auction.trade.service.AuctionProdRecordService;
import com.trump.auction.trade.vo.AuctionProdHotVo;
import com.trump.auction.trade.vo.AuctionProdRecommendVo;
import com.trump.auction.trade.vo.AuctionProductInfoVo;
import com.trump.auction.trade.vo.AuctionProductRecordVo;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisCluster;

/**
 * @description: 拍品信息
 * @author: zhangqingqiang
 * @date: 2018-01-06 11:43
 **/
@Service
@Slf4j
public class AuctionInfoServiceImpl implements AuctionInfoService {

	@Autowired
	private AuctionInfoDao auctionInfoDao;
	@Autowired
	private BeanMapper beanMapper;
	@Autowired
	private AuctionProdInfoService auctionProdInfoService;
	@Autowired
	private AuctionProdRecordService auctionProdRecordService;
	@Autowired
	private JedisCluster jedisCluster;

	@Override
	public Paging<AuctionInfoModel> queryAuctionInfoByClassify(AuctionInfoQuery auctionQuery, int pageNum,
			int pageSize) {
		log.info("method:queryAuctionInfoByClassify start.  params:{}", auctionQuery);
		Paging<AuctionInfoModel> page = new Paging<AuctionInfoModel>();
		List<AuctionInfoModel> auctions = new ArrayList<>();
		try {
			// 分页查询上架拍品
			// 查询拍品最新一期拍卖信息
			Paging<AuctionProductInfoModel> prods = auctionProdInfoService.queryProdByClassify(auctionQuery, pageNum,
					pageSize);
			if (null != prods && null != prods.getList()) {
				for (AuctionProductInfoModel product : prods.getList()) {
					AuctionInfoModel auctionInfo = new AuctionInfoModel();
					AuctionInfo auctionInfo1 = auctionInfoDao.queryLastAuction(product.getId());
					if (null != auctionInfo1) {
						beanMapper.map(auctionInfo1, auctionInfo);
						auctions.add(auctionInfo);
					}
				}
			}
			// page =
			// PageUtils.page(auctionInfoDao.queryAuctionInfoByClassify(auctionQuery.getClassifyId()),
			// AuctionInfoModel.class, beanMapper);
			buildBidPrice(auctions);
			page.setPageNum(prods.getPageNum());
			page.setPageSize(prods.getPageSize());
			page.setPages(prods.getPages());
			page.setTotal(prods.getTotal());
			page.setSize(prods.getSize());
			page.setStartRow(prods.getStartRow());
			page.setPrePage(prods.getPrePage());
			page.setNextPage(prods.getNextPage());
			page.setLastPage(prods.getLastPage());
			page.setList(auctions);
			log.info("method:queryAuctionInfoByClassify end");
		} catch (Exception e) {
			log.error("method:queryAuctionInfoByClassify error.  msg:{}", e);
		}
		return page;
	}

	@Override
	public Paging<AuctionInfoModel> queryNewestAuctionInfo(AuctionInfoQuery auctionQuery, int pageNum, int pageSize) {
		log.info("method:queryNewestAuctionInfo start.  params:{}", auctionQuery);
		Paging<AuctionInfoModel> page = new Paging<AuctionInfoModel>();
		try {
			PageHelper.startPage(pageNum, pageSize);
			page = PageUtils.page(auctionInfoDao.queryNewestAuctionInfo(auctionQuery.getStatus()),
					AuctionInfoModel.class, beanMapper);
			buildBidPrice(page.getList());
			log.info("method:queryNewestAuctionInfo end.");
		} catch (Exception e) {
			log.error("method:queryNewestAuctionInfo error.  msg:{}", e);
		}
		return page;
	}

	@Override
	public List<AuctionInfoModel> queryHotAuctionInfo(AuctionInfoQuery auctionQuery) {
		log.info("method:queryHotAuctionInfo start.  params:{}", auctionQuery);
		List<AuctionInfoModel> auctions = null;
		try {
			auctions = beanMapper.mapAsList(auctionInfoDao.queryHotAuctionInfo(auctionQuery.getAuctionIds()),
					AuctionInfoModel.class);
			buildBidPrice(auctions);
			log.info("method:queryHotAuctionInfo end.");
		} catch (Exception e) {
			log.error("method:queryHotAuctionInfo error.  msg:{}", e);
		}
		return auctions;
	}

	@Override
	public AuctionInfo selectByPrimaryKey(Integer id) {
		return auctionInfoDao.selectByPrimaryKey(id);
	}

	@Override
	public JsonResult saveAuctionInfo(AuctionInfoModel auctionInfoModel) {
		log.info("method:saveAuctionInfo start.");
		int resultCount = 0;
		JsonResult result = new JsonResult();
		try {
			AuctionInfo auctionInfo = new AuctionInfo();
			beanMapper.map(auctionInfoModel, auctionInfo);
			resultCount = auctionInfoDao.insert(auctionInfo);
			result.setCode(ResultEnum.SAVE_SUCCESS.getCode());
			result.setMsg(ResultEnum.SAVE_SUCCESS.getDesc());
			result.setData(auctionInfo.getId());
			log.info("method:saveAuctionInfo end. result:{}", resultCount);
		} catch (Exception e) {
			log.error("method:saveAuctionInfo error.  msg:{}", e);
			result.setCode(ResultEnum.SAVE_ERROR.getCode());
			result.setMsg(e.getMessage());
			result.setData(resultCount);
		}

		return result;
	}

	@Override
	public AuctionInfoModel queryAuctionByProductIdAndNo(AuctionInfoQuery auctionQuery) {
		log.info("method:queryAuctionByProductIdAndNo start.  params:{}", auctionQuery);
		AuctionInfoModel infoModel = null;
		try {
			AuctionInfo info = auctionInfoDao.queryAuctionByProductIdAndNo(auctionQuery.getAuctionProdId(),
					auctionQuery.getAuctionNo());
			if (null != info) {
				infoModel = new AuctionInfoModel();
				beanMapper.map(info, infoModel);
				buildPrice(infoModel);
			} else {
				return infoModel;
			}
		} catch (Exception e) {
			log.error("method:queryAuctionByProductIdAndNo error.  msg:{}", e);
		}
		return infoModel;
	}

	/**
	 * 获取拍品信息通过拍品期数ID
	 * 
	 * @param auctionId
	 * @return
	 */
	@Override
	public AuctionInfoModel getAuctionInfoById(Integer auctionId) {
		log.info("method:getAuctionInfoById start.  params:auctionId:{}", auctionId);
		AuctionInfoModel infoModel = null;
		try {
			if (auctionId == null) {
				return infoModel;
			}
			AuctionInfo info = auctionInfoDao.selectByPrimaryKey(auctionId);
			if (info != null) {
				infoModel = new AuctionInfoModel();
				beanMapper.map(info, infoModel);
				buildPrice(infoModel);
			}
		} catch (Exception e) {
			log.error("method:getAuctionInfoById error.  msg:{}", e);
		}
		return infoModel;
	}

	@Override
	public List<AuctionInfo> findAuctionInfoStatus(Integer status) {
		return auctionInfoDao.findAuctionInfoStatus(status);
	}

	@Override
	public List<String> findIds(Integer status) {
		return auctionInfoDao.findIds(status);
	}

	@Override
	public int upAcutionStatus(Integer id, Integer status) {
		return auctionInfoDao.upAcutionStatus(id, status);
	}

	/**
	 * 定时生成下一期拍卖信息
	 * 
	 * @param prod
	 * @param rule
	 * @param auctionInfo
	 * @param lastRecord
	 * @return
	 */
	@Override
	@Transactional
	public JsonResult doAuctionTask(AuctionProductInfoModel prod, AuctionRuleModel rule, AuctionInfoModel auctionInfo,
			AuctionProductRecordModel lastRecord) {
		JsonResult auctionResult = null;

		try {
			if (0 == prod.getProductNum()) {
				log.info("task:auctionProductTask .updateProductStatus . for productNum is 0.");
				AuctionProductInfoVo auctionProduct = new AuctionProductInfoVo();
				auctionProduct.setId(prod.getId());
				auctionProduct.setStatus(AuctionProductStatusEnum.OFF.getCode());
				auctionProduct.setUnderShelfTime(new Date());
				int result = auctionProdInfoService.updateProductStatus(auctionProduct);
				if (result == 0) {
					return new JsonResult(ResultEnum.UPDATE_PROD_STATUS_ERROR.getCode(),
							ResultEnum.UPDATE_PROD_STATUS_ERROR.getDesc());
				}
				// 数量为0，自动下架的同时把热拍和推荐中的去掉
				removeAndSort(prod.getId());

				return new JsonResult(ResultEnum.PRODUCT_NUM_ZERO.getCode(), ResultEnum.PRODUCT_NUM_ZERO.getDesc());
			}

			int result = auctionProdInfoService.updateProductNum(prod.getId(), prod.getProductNum() - 1);
			log.info("task:auctionProductTask .updateProductNum . result:{}.", result);

			if (result == 0) {
				return new JsonResult(ResultEnum.UPDATE_PROD_NUM_ERROR.getCode(),
						ResultEnum.UPDATE_PROD_NUM_ERROR.getDesc());
			}

			// 新建一条期数信息
			AuctionInfoModel auction = new AuctionInfoModel();

			buildAuction(auction, prod, rule, auctionInfo, lastRecord);
			log.info("task:auctionProductTask .saveAuctionInfo .start.");
			auctionResult = saveAuctionInfo(auction);

			if (null == auctionResult || !"200".equals(auctionResult.getCode())
					|| auctionResult.getData().equals("0")) {
				log.info("task:auctionProductTask .saveAuctionInfo .result:{}", auctionResult);
				return new JsonResult(ResultEnum.SAVE_AUCTION_ERROR.getCode(), ResultEnum.SAVE_AUCTION_ERROR.getDesc());
			}
			log.info("task:auctionProductTask .saveAuctionInfo .end.");

			log.info("task:auctionProductTask .saveRecord .start. ");
			AuctionProductRecordVo record = new AuctionProductRecordVo();
			buildRecord(record, prod, rule, auctionResult, lastRecord);
			auctionResult = auctionProdRecordService.saveRecord(record);

		} catch (Exception e) {
			log.error("doAuctionTask error..for:{}", e);
			auctionResult = new JsonResult(ResultEnum.SAVE_ERROR.getCode(), ResultEnum.SAVE_ERROR.getDesc());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return auctionResult;
	}

	/**
	 * 返回拍品期数
	 * 
	 * @param auctionProdId
	 * @return
	 */
	@Override
	public Integer findAuctionById(Integer auctionProdId) {
		return auctionInfoDao.findById(auctionProdId);
	}

	private void buildRecord(AuctionProductRecordVo record, AuctionProductInfoModel prod, AuctionRuleModel rule,
			JsonResult auctionResult, AuctionProductRecordModel lastRecord) {
		beanMapper.map(rule, record);
		beanMapper.map(lastRecord, record);
		record.setAuctionNo((Integer) auctionResult.getData());
		record.setRuleId(prod.getRuleId());
		record.setCreateTime(new Date());
		record.setUpdateTime(null);
		record.setPoundage(rule.getPoundage());
		record.setClassify1(prod.getClassifyId() + "");
		record.setCountdownName(rule.getCountdownName());
		record.setStartBidName(rule.getStartBidName());
		record.setDifferenceName(rule.getDifferenceName());
		record.setIncreaseBidName(rule.getIncreaseBidName());
		record.setProportionName(rule.getProportionName());
		record.setPoundageName(rule.getPoundageName());
	}

	private void buildAuction(AuctionInfoModel auction, AuctionProductInfoModel prod, AuctionRuleModel rule,
			AuctionInfoModel auctionInfo, AuctionProductRecordModel auctionProductRecordModel) {
		Integer auctionNo = null == auctionInfo.getAuctionNo() ? 0 : auctionInfo.getAuctionNo();
		auctionNo = auctionNo + 1;
		int delayTime = null == prod.getShelvesDelayTime() ? 0 : prod.getShelvesDelayTime();
		Date startTime = addSecond(new Date(), delayTime);
		auction.setAuctionNo(auctionNo);
		auction.setProductId(prod.getProductId());
		auction.setProductName(prod.getProductName());
		auction.setProductPrice(prod.getProductPrice());
		auction.setRuleId(rule.getId());
		auction.setPageView(0);
		auction.setCollectCount(0);
		auction.setValidBidCount(0);
		auction.setFreeBidCount(0);
		auction.setTotalBidCount(0);
		auction.setRobotBidCount(0);
		auction.setPersonCount(0);
		auction.setStartTime(startTime);
		auction.setRuleName(rule.getRuleName());
		auction.setBuyFlag(rule.getDifferenceFlag());
		auction.setIncreasePrice(rule.getPremiumAmount());
		Integer countDown = null == rule.getCountdown() ? 10 : rule.getCountdown();
		auction.setCountDown(countDown);
		auction.setReturnPercent(rule.getRefundMoneyProportion());
		auction.setStartPrice(rule.getOpeningBid());
		auction.setStatus(AuctionInfoStatus.PREPARING.getCode());
		auction.setRobotWinFlag(rule.getRobotTakenIn());
		auction.setCreateTime(new Date());
		auction.setClassifyId(prod.getClassifyId());
		auction.setClassifyName(prod.getClassifyName());
		auction.setAuctionProdId(prod.getId());
		auction.setPoundage(rule.getPoundage().intValue());
		auction.setPreviewPic(auctionProductRecordModel.getPreviewPic());
		// 根据浮动设置获得保底价格,计算保底出价次数
		BigDecimal floorPrice = auctionProdInfoService.floorPrice(auction.getAuctionProdId(), auction.getProductPrice());
		auction.setFloorPrice(floorPrice);
		// 获取拍品最低有效出价次数 保底价格/(出价手续费+抬价幅度)
		if (floorPrice.compareTo(new BigDecimal(0)) != 0) {
			if(auction.getStartPrice()==null){
				auction.setStartPrice(new BigDecimal(0));
			}
			Integer floorBidCount = floorPrice.subtract(auction.getStartPrice())
					.divide(new BigDecimal(auction.getPoundage()).add(auction.getIncreasePrice()),0, RoundingMode.UP).intValue();
			auction.setFloorBidCount(floorBidCount);
		}
		if (auction.getFloorBidCount() == null || auction.getFloorBidCount() <= 0) {
			auction.setFloorBidCount(1);
		}
	}

	/**
	 * 获取拍品最低有效出价次数
	 * 
	 * @param floorPrice
	 *            保留价
	 * @param floatPrice
	 *            浮动比例
	 * @param increasePrice
	 *            每次增加金额
	 * @return
	 */
	private Integer getFloorBidCount(BigDecimal floorPrice, String floatPrice, BigDecimal increasePrice) {
		log.info("获取拍品最低有效出价次数 参数：{},{},{}", floorPrice, floatPrice, increasePrice);
		Integer result = 0;
		if (StringUtils.isNoneBlank(floatPrice) && floorPrice != null && increasePrice != null) {
			String[] floatStr = floatPrice.split("_");
			Random random = new Random();
			if (StringUtils.isNoneBlank(floatStr[0]) && StringUtils.isNoneBlank(floatStr[1])) {
				int maxtop = Integer.parseInt(floatStr[1]);
				int mintop = Integer.parseInt(floatStr[0]);
				int randomNum = random.ints(mintop, maxtop).iterator().nextInt();
				BigDecimal floarDiffer = floorPrice.add(
						floorPrice.multiply(new BigDecimal(String.valueOf(randomNum))).divide(new BigDecimal(100)));
				return floarDiffer.divide(increasePrice).setScale(0, RoundingMode.HALF_UP).intValue();
			} else {
				return floorPrice.divide(increasePrice).setScale(0, RoundingMode.HALF_UP).intValue();
			}
		}
		if (floorPrice != null && increasePrice != null) {
			return floorPrice.divide(increasePrice).setScale(0, RoundingMode.HALF_UP).intValue();
		}

		return result;
	}

	private void removeAndSort(Integer auctionProductId) {
		// 热拍
		String jedis = jedisCluster.get(SysConstant.AUCTION_BACK_AUCTION_PROD_HOT);
		if (StringUtils.isNoneBlank(jedis)) {
			List<AuctionProdHotVo> hotVos = JSONArray.parseArray(jedis, AuctionProdHotVo.class);
			List<AuctionProdHotVo> hotVosNew = new ArrayList<>();
			if (CollectionUtils.isNotEmpty(hotVos)) {
				if (jedisCluster.exists(SysConstant.AUCTION_BACK_AUCTION_PROD_HOT + "_" + auctionProductId)) {
					int sort = 1;
					for (AuctionProdHotVo vo : hotVos) {
						if (vo.getAuctionProdId().equals(auctionProductId)) {
							jedisCluster.del(SysConstant.AUCTION_BACK_AUCTION_PROD_HOT + "_" + auctionProductId);
						} else {
							vo.setSort(sort++);
							jedisCluster.set(SysConstant.AUCTION_BACK_AUCTION_PROD_HOT + "_" + auctionProductId,
									String.valueOf(vo.getSort()));
							hotVosNew.add(vo);
						}
					}
					jedisCluster.set(SysConstant.AUCTION_BACK_AUCTION_PROD_HOT, JSON.toJSONString(hotVosNew));
				}
			}
		}

		// 推荐
		String jedisReommend = jedisCluster.get(SysConstant.AUCTION_BACK_AUCTION_PROD_RECOMMEND);
		if (StringUtils.isNotBlank(jedisReommend)) {
			List<AuctionProdRecommendVo> hotVosRecomment = JSONArray.parseArray(jedisReommend,
					AuctionProdRecommendVo.class);
			List<AuctionProdRecommendVo> hotVosRecommentNew = new ArrayList<>();
			if (CollectionUtils.isNotEmpty(hotVosRecomment)) {
				if (jedisCluster.exists(SysConstant.AUCTION_BACK_AUCTION_PROD_RECOMMEND + "_" + auctionProductId)) {
					int sort = 1;
					for (AuctionProdRecommendVo vo : hotVosRecomment) {
						if (vo.getAuctionProdId().equals(auctionProductId)) {
							jedisCluster.del(SysConstant.AUCTION_BACK_AUCTION_PROD_RECOMMEND + "_" + auctionProductId);
						} else {
							vo.setSort(sort++);
							jedisCluster.set(SysConstant.AUCTION_BACK_AUCTION_PROD_RECOMMEND + "_" + auctionProductId,
									String.valueOf(vo.getSort()));
							hotVosRecommentNew.add(vo);
						}
					}
					jedisCluster.set(SysConstant.AUCTION_BACK_AUCTION_PROD_RECOMMEND,
							JSON.toJSONString(hotVosRecommentNew));
				}
			}
		}

	}

	private static Date addSecond(Date date, int second) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(13, second);
		return calendar.getTime();
	}

	/**
	 * 当前价格（起拍价+总出价次数*加价幅度）
	 */
	private void buildBidPrice(List<AuctionInfoModel> auctions) {
		try {
			if (CollectionUtils.isEmpty(auctions)) {
				return;
			}

			for (AuctionInfoModel auction : auctions) {
				buildPrice(auction);

				// 当前价格
				BigDecimal bidCount = BigDecimal.ZERO;
				if (auction.getTotalBidCount() != null) {
					bidCount = new BigDecimal(auction.getTotalBidCount());
				}
				auction.setBidPrice(
						roundUp(auction.getStartPrice().add(bidCount.multiply(auction.getIncreasePrice()))));

			}
		} catch (Exception e) {
			log.error("method:buildBidPrice error.  msg:{}", e);
		}
	}

	/**
	 * 价格处理 四舍五入
	 * 
	 * @param auction
	 */
	private void buildPrice(AuctionInfoModel auction) {
		if (auction.getStartPrice() == null) {
			auction.setStartPrice(BigDecimal.ZERO);
		}
		if (auction.getIncreasePrice() == null) {
			auction.setIncreasePrice(BigDecimal.ZERO);
		}
		if (null == auction.getFinalPrice()) {
			auction.setFinalPrice(BigDecimal.ZERO);
		}
		if (null == auction.getFloorPrice()) {
			auction.setFloorPrice(BigDecimal.ZERO);
		}
		if (null == auction.getProductPrice()) {
			auction.setProductPrice(BigDecimal.ZERO);
		}
		// 起拍价
		auction.setStartPrice(roundUp(auction.getStartPrice()));
		// 每次加价金额
		auction.setIncreasePrice(roundUp(auction.getIncreasePrice()));
		// 成交金额
		auction.setFinalPrice(roundUp(auction.getFinalPrice()));
		// 保留价
		auction.setFloorPrice(roundUp(auction.getFloorPrice()));
		// 商品价格
		auction.setProductPrice(roundUp(auction.getProductPrice()));
	}

	private static BigDecimal roundUp(BigDecimal result) {
		return result.setScale(2, BigDecimal.ROUND_UP);
	}

}
