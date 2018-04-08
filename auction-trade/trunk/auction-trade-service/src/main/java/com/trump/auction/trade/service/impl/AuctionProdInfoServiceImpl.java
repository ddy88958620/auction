package com.trump.auction.trade.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.cf.common.utils.ServiceResult;
import com.github.pagehelper.PageHelper;
import com.trump.auction.trade.constant.SysContant;
import com.trump.auction.trade.dao.AuctionInfoDao;
import com.trump.auction.trade.dao.AuctionProductInfoDao;
import com.trump.auction.trade.dao.AuctionProductPriceRuleDao;
import com.trump.auction.trade.dao.AuctionProductRecordDao;
import com.trump.auction.trade.dao.AuctionRuleDao;
import com.trump.auction.trade.dao.ProductInventoryLogDao;
import com.trump.auction.trade.domain.AuctionInfo;
import com.trump.auction.trade.domain.AuctionProductInfo;
import com.trump.auction.trade.domain.AuctionProductPriceRule;
import com.trump.auction.trade.domain.AuctionProductRecord;
import com.trump.auction.trade.domain.AuctionRule;
import com.trump.auction.trade.domain.ProductInventoryLog;
import com.trump.auction.trade.enums.AuctionProductStatusEnum;
import com.trump.auction.trade.enums.ResultEnum;
import com.trump.auction.trade.model.AuctionInfoQuery;
import com.trump.auction.trade.model.AuctionProductInfoModel;
import com.trump.auction.trade.model.ProductInfo;
import com.trump.auction.trade.model.ProductPic;
import com.trump.auction.trade.service.AuctionProdInfoService;
import com.trump.auction.trade.vo.AuctionProductInfoVo;
import com.trump.auction.trade.vo.AuctionProductPriceRuleVo;

import lombok.extern.slf4j.Slf4j;

/**
 * @description: 拍品信息
 * @author:
 * @date: 2018-01-06 11:43
 **/
@Service("auctionProdInfoService")
@Slf4j
public class AuctionProdInfoServiceImpl implements AuctionProdInfoService {

	@Autowired
	private AuctionProductInfoDao auctionProductInfoDao;

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private AuctionRuleDao auctionRuleDao;
	@Autowired
	private AuctionInfoDao auctionInfoDao;
	@Autowired
	private AuctionProductPriceRuleDao auctionProductPriceRuleDao;
	@Autowired
	private AuctionProductRecordDao auctionProductRecordDao;
	@Autowired
	private ProductInventoryLogDao productInventoryLogDao;

	/**
	 * 获取拍品信息通过ID
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public AuctionProductInfoVo getAuctionProductById(Integer id) {
		log.info("method:getAuctionProductById start.  params:id{}", id);
		AuctionProductInfoVo vo = new AuctionProductInfoVo();
		try {
			if (id == null) {
				return vo;
			}
			AuctionProductInfo info = auctionProductInfoDao.selectByPrimaryKey(id);
			if (info != null) {
				beanMapper.map(info, vo);
			}
		} catch (Exception e) {
			log.error("method:getAuctionProductById error.  msg:{}", e);
		}
		return vo;
	}

	/**
	 * 拍品批量下架
	 * 
	 * @param auctionProductInfoVos
	 * @return
	 */
	@Override
	public ServiceResult batchOff(List<AuctionProductInfoVo> auctionProductInfoVos) throws Exception {
		ServiceResult serviceResult = new ServiceResult();
		log.info("batchOff param:" + JSON.toJSONString(auctionProductInfoVos));
		if (CollectionUtils.isEmpty(auctionProductInfoVos)) {
			throw new Exception("参数缺失");
		}
		AuctionProductInfo info = null;
		List<AuctionProductInfoVo> batchOffList = new ArrayList<>();

		for (AuctionProductInfoVo vo : auctionProductInfoVos) {
			info = auctionProductInfoDao.selectByPrimaryKey(vo.getId());
			if (info != null) {
				if (!(AuctionProductStatusEnum.OFF.getCode().equals(info.getStatus())
						|| AuctionProductStatusEnum.DELETE.getCode().equals(info.getStatus()))) {
					vo.setStatus(AuctionProductStatusEnum.OFF.getCode());
					vo.setOnShelfTime(null);
					batchOffList.add(vo);
				} else {
					log.info("该拍品已下架：" + vo.getId());
				}
			} else {
				log.info("未找到相应的拍品信息：" + vo.getId());
			}
		}
		if (CollectionUtils.isNotEmpty(batchOffList)) {
			auctionProductInfoDao.batchOff(batchOffList);
		}

		return serviceResult;
	}

	/**
	 * 拍品保存
	 * 
	 * @param auctionProductInfoVo
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public ServiceResult auctionProdSave(AuctionProductInfoVo auctionProductInfoVo) throws Exception {
		ServiceResult result = new ServiceResult();
		AuctionProductRecord auctionProductRecord = new AuctionProductRecord();
		AuctionInfo auctionInfo = new AuctionInfo();
		AuctionProductInfo auctionProductInfo = new AuctionProductInfo();
		// 第一步

		beanMapper.map(auctionProductInfoVo, auctionProductInfo);
		// auctionProductInfo.setFloatPrice(auctionProductInfoVo.getFloatPrices());
		Integer productNum = 0;

		auctionProductInfo.setCreateTime(new Date());
		if (auctionProductInfo.getStatus().equals(1)) {
			auctionProductInfo.setOnShelfTime(auctionProductInfo.getCreateTime());
			auctionProductInfo.setAuctionStartTime(auctionProductInfo.getCreateTime());
			if (auctionProductInfo.getProductNum() != null && auctionProductInfo.getProductNum() > 0) {
				productNum = auctionProductInfo.getProductNum();
				auctionProductInfo.setProductNum(auctionProductInfo.getProductNum() - 1);
			} else {
				throw new Exception("新建拍品：上架库存异常");
			}
		}
		try {
			auctionProductInfoDao.insert(auctionProductInfo);
			ProductInventoryLog model = new ProductInventoryLog();
			model.setUserIp(auctionProductInfo.getUserIp());
			model.setUserId(auctionProductInfo.getUserId());
			model.setProductId(auctionProductInfo.getProductId());
			model.setProductNum(productNum);
			int subtractResult = productInventoryLogDao.subtractStock(model);
			if (subtractResult <= 0) {
				throw new Exception("新建拍品失败： 扣除库存失败");
			}
			auctionProductPriceRuleDao.deleteByAucProInfoId(auctionProductInfo.getId());
			if (CollectionUtils.isNotEmpty(auctionProductInfoVo.getRules())) {
				for (AuctionProductPriceRuleVo vo : auctionProductInfoVo.getRules()) {
					AuctionProductPriceRule record = new AuctionProductPriceRule();
					beanMapper.map(vo, record);
					record.setProductInfoId(auctionProductInfo.getId());
					auctionProductPriceRuleDao.insert(record);
				}
			}
			if (auctionProductInfo.getStatus().equals(1)) {
				AuctionRule rule = auctionRuleDao.selectByPrimaryKey(auctionProductInfoVo.getRuleId());
				// 第二步
				auctionInfo = getAuctionInfo(auctionProductInfoVo.getProductInfo(), rule, 1, auctionProductInfo);
				auctionInfoDao.insert(auctionInfo);
				// 第三步
				auctionProductRecord = getAuctionProductRecord(auctionProductInfoVo.getProductInfo(),
						auctionProductInfoVo.getProductPics(), rule, auctionInfo);
				auctionProductRecordDao.insert(auctionProductRecord);
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.setCode(ResultEnum.SAVE_ERROR.getCode());
			throw new Exception("新建拍品失败：" + e.getMessage());
		}
		return result;
	}

	/**
	 * 拍品上架
	 * 
	 * @param auctionProductInfoVo
	 * @return
	 */
	@Override
	public int auctionProductOn(AuctionProductInfoVo auctionProductInfoVo) throws Exception {
		log.info("batchOff param:" + JSON.toJSONString(auctionProductInfoVo));
		if (auctionProductInfoVo == null) {
			throw new Exception("参数缺失");
		}
		AuctionProductInfo info = auctionProductInfoDao.selectByPrimaryKey(auctionProductInfoVo.getId());
		if (info == null) {
			log.info("拍品信息不存在：" + auctionProductInfoVo.getId());
			throw new Exception("拍品信息不存在");
		}
		if (!AuctionProductStatusEnum.OFF.getCode().equals(info.getStatus())) {
			log.info("拍品已上架：" + auctionProductInfoVo.getId());
			throw new Exception("拍品已上架");
		}
		auctionProductInfoVo.setUnderShelfTime(null);
		auctionProductInfoVo.setStatus(AuctionProductStatusEnum.PREPARING.getCode());
		return auctionProductInfoDao.auctionOn(auctionProductInfoVo);
	}

	/**
	 * 拍品信息修改
	 * 
	 * @param auctionProductInfoVo
	 * @return
	 */
	@Override
	public int updateAuctionProduct(AuctionProductInfoVo auctionProductInfoVo) throws Exception {
		log.info("updateAuctionProduct param:" + JSON.toJSONString(auctionProductInfoVo));
		if (auctionProductInfoVo == null) {
			throw new Exception("参数缺失");
		}
		AuctionProductInfo info = new AuctionProductInfo();
		beanMapper.map(auctionProductInfoVo, info);
		// info.setFloatPrice(auctionProductInfoVo.getFloatPrices());
		int result = auctionProductInfoDao.updateByPrimaryKeySelective(info);
		auctionProductPriceRuleDao.deleteByAucProInfoId(auctionProductInfoVo.getId());
		if (CollectionUtils.isNotEmpty(auctionProductInfoVo.getRules())) {
			for (AuctionProductPriceRuleVo vo : auctionProductInfoVo.getRules()) {
				AuctionProductPriceRule record = new AuctionProductPriceRule();
				beanMapper.map(vo, record);
				record.setProductInfoId(auctionProductInfoVo.getId());
				auctionProductPriceRuleDao.insert(record);
			}
		}
		return result;
	}

	@Override
	public int updAuctionProdStatus(List<String> ids, Integer status) {
		return auctionProductInfoDao.updAuctionProdStatus(ids, status);
	}

	@Override
	public int updAuctionProdDateAndStatus(AuctionProductInfo auctionProductInfo) {
		return auctionProductInfoDao.updAuctionProdDateAndStatus(auctionProductInfo);
	}

	/**
	 * 批量上架
	 * 
	 * @param auctionProductInfoVos
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public int auctionProductBatchOn(List<AuctionProductInfoVo> auctionProductInfoVos) throws Exception {

		log.info("auctionProductBatchOn param:" + JSON.toJSONString(auctionProductInfoVos));
		if (CollectionUtils.isEmpty(auctionProductInfoVos)) {
			throw new Exception("上架失败，参数缺失");
		}
		AuctionRule rule = null;
		AuctionInfo info = null;
		AuctionInfo infoNew = null;
		AuctionProductRecord record = null;
		AuctionProductInfo auctionProductInfo = null;
		int successNum = 0;
		for (AuctionProductInfoVo vo : auctionProductInfoVos) {

			try {
				rule = auctionRuleDao.selectByPrimaryKey(vo.getRuleId());
				info = auctionInfoDao.getLastInfo(vo.getId());
				auctionProductInfo = auctionProductInfoDao.selectByPrimaryKey(vo.getId());
				if (auctionProductInfo != null && auctionProductInfo.getProductNum() > 0) {
					// 原先下架的拍品如果在上架只需要更改状态，不需要重新生成期次信息
					if (auctionProductInfo.getStatus().equals(AuctionProductStatusEnum.OFF.getCode())) {
						auctionProductInfo.setStatus(AuctionProductStatusEnum.AUCTIONINT.getCode());
						auctionProductInfo.setOnShelfTime(new Date());
						auctionProductInfo.setUnderShelfTime(null);
						auctionProductInfoDao.updAuctionProdStatusTime(auctionProductInfo);
					} else {
						if (rule != null) {
							auctionProductInfo.setAuctionStartTime(new Date());

							// 上架时aution_info状态置为未开始
							if (info != null) {
								infoNew = getAuctionInfo(vo.getProductInfo(), rule, info.getAuctionNo() + 1,
										auctionProductInfo);
							} else {
								infoNew = getAuctionInfo(vo.getProductInfo(), rule, 1, auctionProductInfo);
							}
							record = getAuctionProductRecord(vo.getProductInfo(), vo.getProductPics(), rule, infoNew);
							auctionProductInfo.setStatus(AuctionProductStatusEnum.AUCTIONINT.getCode());
							auctionProductInfo.setOnShelfTime(new Date());
							auctionProductInfo.setUnderShelfTime(null);
							auctionProductInfoDao.updAuctionProdStatusTime(auctionProductInfo);
							auctionInfoDao.insert(infoNew);
							auctionProductRecordDao.insert(record);
						}
					}
					successNum++;
				}
			} catch (Exception e) {
				log.error("上架失败：" + vo.getId());
				log.error(e.getMessage(), e);
				throw new Exception("批量上架失败");
			}

		}

		// 拍品上架，生成期次信息和快照表
		return successNum;
	}

	/**
	 * 定时上架
	 * 
	 * @param auctionProductInfoVos
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public int auctionProductTimingBatchOn(List<AuctionProductInfoVo> auctionProductInfoVos) throws Exception {
		log.info("auctionProductTimingBatchOn param:" + JSON.toJSONString(auctionProductInfoVos));
		if (CollectionUtils.isEmpty(auctionProductInfoVos)) {
			throw new Exception("定时上架失败，参数缺失");
		}
		AuctionRule rule = null;
		AuctionInfo info = null;
		AuctionInfo infoNew = null;
		AuctionProductRecord record = null;
		AuctionProductInfo auctionProductInfo = null;
		int successNum = 0;
		for (AuctionProductInfoVo vo : auctionProductInfoVos) {
			try {
				rule = auctionRuleDao.selectByPrimaryKey(vo.getRuleId());
				info = auctionInfoDao.getLastInfo(vo.getId());
				auctionProductInfo = auctionProductInfoDao.selectByPrimaryKey(vo.getId());

				if (auctionProductInfo != null && auctionProductInfo.getProductNum() > 0) {
					auctionProductInfo.setAuctionStartTime(vo.getAuctionStartTime());
					// 原先下架的拍品如果在上架只需要更改状态，不需要重新生成期次信息
					if (auctionProductInfo.getStatus().equals(AuctionProductStatusEnum.OFF.getCode())) {
						auctionProductInfo.setStatus(AuctionProductStatusEnum.AUCTIONINT.getCode());
						auctionProductInfo.setOnShelfTime(vo.getAuctionStartTime());
						auctionProductInfo.setUnderShelfTime(null);
						auctionProductInfoDao.updAuctionProdStatusTime(auctionProductInfo);
					} else {
						if (rule != null) {
							if (info != null) {
								infoNew = getAuctionInfo(vo.getProductInfo(), rule, info.getAuctionNo() + 1,
										auctionProductInfo);
							} else {
								infoNew = getAuctionInfo(vo.getProductInfo(), rule, 1, auctionProductInfo);
							}
							record = getAuctionProductRecord(vo.getProductInfo(), vo.getProductPics(), rule, infoNew);
							auctionProductInfo.setStatus(AuctionProductStatusEnum.AUCTIONINT.getCode());
							auctionProductInfo.setOnShelfTime(vo.getAuctionStartTime());
							auctionProductInfo.setUnderShelfTime(null);
							auctionProductInfoDao.updAuctionProdStatusTime(auctionProductInfo);
							auctionInfoDao.insert(infoNew);
							auctionProductRecordDao.insert(record);
						}
					}
				}
				successNum++;
			} catch (Exception e) {
				log.error("上架失败：" + vo.getId());
				log.error(e.getMessage(), e);
				throw new Exception("定时上架失败");
			}

		}

		// 拍品上架，生成期次信息和快照表
		return successNum;
	}

	@Override
	public Paging<AuctionProductInfoModel> queryProdByClassify(AuctionInfoQuery auctionQuery, int pageNum, int pageSize)
			throws Exception {
		PageHelper.startPage(pageNum, pageSize);
		return PageUtils.page(auctionProductInfoDao.queryProdByClassify(auctionQuery.getClassifyId()),
				AuctionProductInfoModel.class, beanMapper);
	}

	@Override
	public int updateProductStatus(AuctionProductInfoVo auctionProduct) {
		return auctionProductInfoDao.updateProductStatus(auctionProduct);
	}

	@Override
	public int updateProductNum(Integer id, int num) {
		return auctionProductInfoDao.updateProductNum(id, num);
	}

	/**
	 * 拼接拍品期数数据
	 * 
	 * @param productInfo
	 *            商品信息
	 * @param auctionRule
	 *            规则信息
	 * @return
	 */
	private AuctionInfo getAuctionInfo(ProductInfo productInfo, AuctionRule auctionRule, Integer no,
			AuctionProductInfo auctionProductInfo) {
		AuctionInfo auctionInfo = new AuctionInfo();
		auctionInfo.setAuctionNo(no);
		auctionInfo.setProductId(productInfo.getId());
		auctionInfo.setProductName(productInfo.getProductName());
		auctionInfo.setProductPrice(new BigDecimal(productInfo.getSalesAmount()));
		auctionInfo.setPreviewPic(productInfo.getPicUrl());
		auctionInfo.setRuleId(auctionRule.getId());
		auctionInfo.setPageView(SysContant.PAGE_VIEW);
		auctionInfo.setCollectCount(SysContant.COLLECTION_COUNT);
		auctionInfo.setValidBidCount(0);
		auctionInfo.setFreeBidCount(0);
		auctionInfo.setTotalBidCount(0);
		auctionInfo.setRobotBidCount(0);
		auctionInfo.setPersonCount(0);
		auctionInfo.setStartTime(auctionProductInfo.getAuctionStartTime());
		auctionInfo.setRuleName(auctionRule.getRuleName());
		auctionInfo.setBuyFlag(auctionRule.getDifferenceFlag());
		auctionInfo.setIncreasePrice(auctionRule.getPremiumAmount());
		auctionInfo.setCountDown(auctionRule.getCountdown());
		auctionInfo.setReturnPercent(auctionRule.getRefundMoneyProportion());
		auctionInfo.setStartPrice(auctionRule.getOpeningBid());
		auctionInfo.setStatus(3);
		auctionInfo.setRobotWinFlag(auctionRule.getRobotTakenIn());
		auctionInfo.setCreateTime(new Date());
		auctionInfo.setClassifyId(auctionProductInfo.getClassifyId());
		auctionInfo.setAuctionProdId(auctionProductInfo.getId());
		auctionInfo.setClassifyName(auctionInfo.getClassifyName());
		auctionInfo.setPoundage(auctionRule.getPoundage().intValue());
		// 根据浮动设置获得保底价格,计算保底出价次数
		BigDecimal floorPrice = floorPrice(auctionInfo.getAuctionProdId(), auctionInfo.getProductPrice());
		auctionInfo.setFloorPrice(floorPrice);
		// 获取拍品最低有效出价次数 保底价格/(出价手续费+抬价幅度)
		if (floorPrice.compareTo(new BigDecimal(0)) != 0) {
			if (auctionInfo.getStartPrice() == null) {
				auctionInfo.setStartPrice(new BigDecimal(0));
			}
			Integer floorBidCount = floorPrice.subtract(auctionInfo.getStartPrice())
					.divide(new BigDecimal(auctionInfo.getPoundage()).add(auctionInfo.getIncreasePrice()),0, RoundingMode.UP).intValue();
			auctionInfo.setFloorBidCount(floorBidCount);
		}
		if (auctionInfo.getFloorBidCount() == null || auctionInfo.getFloorBidCount() <= 0) {
			auctionInfo.setFloorBidCount(1);
		}
		return auctionInfo;
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
	public Integer getFloorBidCount(BigDecimal floorPrice, String floatPrice, BigDecimal increasePrice) {
		log.info("获取拍品最低有效出价次数 参数：" + floorPrice + "," + floatPrice + "," + increasePrice);
		Integer result = 0;
		if (StringUtils.isNoneBlank(floatPrice) && floorPrice != null && increasePrice != null) {
			String[] floatStr = floatPrice.split("_");
			Random random = new Random();
			if (StringUtils.isNoneBlank(floatStr[0]) && StringUtils.isNoneBlank(floatStr[1])) {
				int maxtop = Integer.parseInt(floatStr[1]);
				int mintop = Integer.parseInt(floatStr[0]);
				// int randomNum = random.nextInt(maxtop) % (maxtop - mintop +
				// 1) + mintop;
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

	@Override
	public BigDecimal floorPrice(Integer auctionProdId, BigDecimal productPrice) {
		List<AuctionProductPriceRule> rules = auctionProductPriceRuleDao.queryRuleByAucProInfoId(auctionProdId);
		if (CollectionUtils.isEmpty(rules)) {
			rules = defaultRule();
		}
		int maxRandom = 0;
		if (rules.size() == 1) {
			maxRandom = rules.get(0).getRandomRate();
		} else {
			for (int i = 1; i < rules.size(); i++) {
				AuctionProductPriceRule rule = rules.get(i);
				AuctionProductPriceRule rule_ = rules.get(i - 1);
				rule.setRandomRate(rule.getRandomRate() + rule_.getRandomRate());
				maxRandom = rule.getRandomRate();
			}
		}
		Random random = new Random();
		int randomNum = random.ints(0, maxRandom).iterator().nextInt();
		int maxtop = 0;
		int mintop = 0;
		for (AuctionProductPriceRule rule : rules) {
			if (rule.getRandomRate() > randomNum) {
				mintop = rule.getMinFloatRate();
				maxtop = rule.getMaxFloatRate();
				break;
			}
		}
		randomNum = random.ints(mintop, maxtop).iterator().nextInt();
		// 1. 保底价=商品成本价*（1+浮动范围）
		BigDecimal floorPrice = productPrice.add(productPrice.multiply(new BigDecimal((double) randomNum / 100)));
		return floorPrice;
	}

	private List<AuctionProductPriceRule> defaultRule() {
		List<AuctionProductPriceRule> defaultRules = new ArrayList<>();
		AuctionProductPriceRule rule1 = new AuctionProductPriceRule();
		rule1.setMinFloatRate(-100);
		rule1.setMaxFloatRate(-50);
		rule1.setRandomRate(15);
		defaultRules.add(rule1);

		AuctionProductPriceRule rule2 = new AuctionProductPriceRule();
		rule2.setMinFloatRate(-50);
		rule2.setMaxFloatRate(0);
		rule2.setRandomRate(20);
		defaultRules.add(rule2);

		AuctionProductPriceRule rule3 = new AuctionProductPriceRule();
		rule3.setMinFloatRate(0);
		rule3.setMaxFloatRate(50);
		rule3.setRandomRate(35);
		defaultRules.add(rule3);

		AuctionProductPriceRule rule4 = new AuctionProductPriceRule();
		rule4.setMinFloatRate(50);
		rule4.setMaxFloatRate(100);
		rule4.setRandomRate(15);
		defaultRules.add(rule4);

		AuctionProductPriceRule rule5 = new AuctionProductPriceRule();
		rule5.setMinFloatRate(100);
		rule5.setMaxFloatRate(200);
		rule5.setRandomRate(10);
		defaultRules.add(rule5);

		AuctionProductPriceRule rule6 = new AuctionProductPriceRule();
		rule6.setMinFloatRate(200);
		rule6.setMaxFloatRate(400);
		rule6.setRandomRate(5);
		defaultRules.add(rule6);
		Collections.sort(defaultRules, new Comparator<AuctionProductPriceRule>() {
			@Override
			public int compare(AuctionProductPriceRule o1, AuctionProductPriceRule o2) {
				// 降序
				return o2.getMinFloatRate().compareTo(o1.getMinFloatRate());
			}
		});
		return defaultRules;
	}

	/**
	 * 拍品快照
	 * 
	 * @return
	 */
	public AuctionProductRecord getAuctionProductRecord(ProductInfo productInfo, List<ProductPic> pics,
			AuctionRule auctionRule, AuctionInfo auctionInfo) {
		AuctionProductRecord auctionProductRecord = new AuctionProductRecord();
		auctionProductRecord.setAuctionNo(auctionInfo.getId());
		auctionProductRecord.setProductId(productInfo.getId());
		auctionProductRecord.setProductName(productInfo.getProductName());
		auctionProductRecord.setProductPrice(new BigDecimal(productInfo.getSalesAmount()));
		auctionProductRecord.setRuleId(auctionRule.getId());
		auctionProductRecord.setClassify1(auctionInfo.getClassifyId().toString());
		auctionProductRecord.setProductAmount(new BigDecimal(productInfo.getProductAmount()));
		auctionProductRecord.setSalesAmount(new BigDecimal(productInfo.getSalesAmount()));
		auctionProductRecord.setSkuInfo(productInfo.getSkuInfo());
		auctionProductRecord.setRemarks(productInfo.getRemarks());
		auctionProductRecord.setCountdownName(auctionRule.getCountdownName());
		auctionProductRecord.setStartBidName(auctionRule.getStartBidName());
		auctionProductRecord.setDifferenceName(auctionRule.getDifferenceName());
		auctionProductRecord.setIncreaseBidName(auctionRule.getIncreaseBidName());
		auctionProductRecord.setProportionName(auctionRule.getProportionName());
		auctionProductRecord.setPoundageName(auctionRule.getPoundageName());
		StringBuffer masterPic = new StringBuffer();
		StringBuffer picUrl = new StringBuffer();
		for (ProductPic pic : pics) {
			if ("2".equals(pic.getPicType())) {
				// 预览
				auctionProductRecord.setPreviewPic(pic.getPicUrl());
			} else if ("1".equals(pic.getPicType())) {
				// 商品
				masterPic.append(pic.getPicUrl() + ",");
			} else if ("0".equals(pic.getPicType())) {
				// 详情
				picUrl.append(pic.getPicUrl() + ",");
			}
		}
		if (masterPic.length() > 0) {
			auctionProductRecord.setMasterPic(masterPic.substring(0, masterPic.length() - 1));
		}
		if (picUrl.length() > 0) {
			auctionProductRecord.setPicUrls(picUrl.substring(0, picUrl.length() - 1));
		}
		auctionProductRecord.setCreateTime(new Date());
		auctionProductRecord.setPoundage(auctionRule.getPoundage());
		auctionProductRecord.setMerchantId(productInfo.getMerchantId());
		return auctionProductRecord;
	}

	@Override
	public List<AuctionProductPriceRuleVo> findRulesByProductInfoId(Integer productInfoId) {
		List<AuctionProductPriceRule> rules = new ArrayList<>();
		if (productInfoId != null && productInfoId > 0) {
			rules = auctionProductPriceRuleDao.queryRuleByAucProInfoId(productInfoId);
		}
		if (CollectionUtils.isEmpty(rules)) {
			rules = defaultRule();
		}
		List<AuctionProductPriceRuleVo> voList = new ArrayList<>();
		for (AuctionProductPriceRule rule : rules) {
			AuctionProductPriceRuleVo vo = new AuctionProductPriceRuleVo();
			beanMapper.map(rule, vo);
			voList.add(vo);
		}
		return voList;
	}

}
