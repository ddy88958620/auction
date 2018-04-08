package com.trump.auction.back.auctionProd.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.cf.common.utils.ServiceResult;
import com.github.pagehelper.PageHelper;
import com.trump.auction.back.auctionProd.dao.read.AuctionInfoDao;
import com.trump.auction.back.auctionProd.dao.read.AuctionProductInfoDao;
import com.trump.auction.back.auctionProd.dao.read.AuctionProductRecordDao;
import com.trump.auction.back.auctionProd.model.AuctionBidDetail;
import com.trump.auction.back.auctionProd.model.AuctionInfo;
import com.trump.auction.back.auctionProd.model.AuctionProductInfo;
import com.trump.auction.back.auctionProd.model.AuctionProductRecord;
import com.trump.auction.back.auctionProd.service.AuctionProductInfoService;
import com.trump.auction.back.auctionProd.vo.AuctionProdHotVo;
import com.trump.auction.back.auctionProd.vo.AuctionProdRecommendVo;
import com.trump.auction.back.auctionProd.vo.AuctionProdVo;
import com.trump.auction.back.product.model.ProductInfo;
import com.trump.auction.back.product.model.ProductPic;
import com.trump.auction.back.product.vo.ParamVo;
import com.trump.auction.back.rule.dao.read.AuctionRuleDao;
import com.trump.auction.back.rule.model.AuctionRule;
import com.trump.auction.back.sys.model.SysUser;
import com.trump.auction.back.util.common.SysContant;
import com.trump.auction.goods.api.ProductInventoryLogSubService;
import com.trump.auction.goods.model.ProductInventoryLogModel;
import com.trump.auction.goods.vo.ResultBean;
import com.trump.auction.trade.api.AuctionProdInfoStubService;
import com.trump.auction.trade.api.AuctionRuleStubService;
import com.trump.auction.trade.vo.AuctionProductInfoVo;
import com.trump.auction.trade.vo.AuctionProductPriceRuleVo;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisCluster;

@Slf4j
@Service
public class AuctionProductInfoServiceImpl implements AuctionProductInfoService {

	@Autowired
	private AuctionProductInfoDao auctionProductInfoDao;
	@Autowired
	private AuctionInfoDao auctionInfoDao;
	@Autowired
	private AuctionProductRecordDao auctionProductRecordDao;
	@Autowired
	private JedisCluster jedisCluster;
	@Autowired
	private AuctionRuleStubService auctionRuleStubService;

	@Autowired
	private AuctionProdInfoStubService auctionProdInfoStubService;
	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private AuctionRuleDao auctionRuleDao;

	@Autowired
	private ProductInventoryLogSubService productInventoryLogSubService;
	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * 热门拍品的redis key
	 */
	private final static String AUCTION_BACK_AUCTION_PROD_HOT = "hot_auction_trade_prods";

	/*
	 * @Autowired private DataSourceTransactionManager
	 * dataSourceTransactionManager;
	 */

	@Override
	public Paging<AuctionProductInfo> findAuctionProdList(AuctionProdVo auctionProdVo) {
		PageHelper.startPage(auctionProdVo.getPage(), auctionProdVo.getLimit());
		return PageUtils.page(auctionProductInfoDao.findAuctionProdList(auctionProdVo));
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class, RuntimeException.class })
	@Override
	public String auctionProdSave(AuctionProductInfoVo vo) throws Exception {
		log.info("AuctionProductInfoService  save invoce : " + JSON.toJSONString(vo));
		String code = "0000";

		try {

			auctionProdInfoStubService.auctionProdSave(vo);
		} catch (Exception e) {
			code = "-1";
			throw new Exception("新建拍品失败：" + e.getMessage());
		}
		return code;
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
		/*
		 * if(auctionProductInfo.getStatus().equals(2)) {
		 * auctionInfo.setStatus(3); }else{ auctionInfo.setStatus(1); }
		 */
		auctionInfo.setStatus(3);
		auctionInfo.setRobotWinFlag(auctionRule.getRobotTakenIn());
		auctionInfo.setCreateTime(new Date());
		auctionInfo.setClassifyId(auctionProductInfo.getClassifyId());
		auctionInfo.setAuctionProdId(auctionProductInfo.getId());
		auctionInfo.setFloatPrice(auctionProductInfo.getFloatPrice());
		auctionInfo.setFloorPrice(auctionProductInfo.getFloorPrice());
		auctionInfo.setClassifyName(auctionInfo.getClassifyName());
		auctionInfo.setPoundage(auctionRule.getPoundage().intValue());

		auctionInfo.setFloorBidCount(getFloorBidCount(auctionProductInfo.getFloorPrice(),
				auctionProductInfo.getFloatPrice(), auctionRule.getPoundage()));
		// 获取拍品最低有效出价次数
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
				// auctionProductRecord.setMasterPic(pic.getPicUrl());
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
	public List<AuctionProductInfo> queryTimingProduct(Date date, Integer status) {
		return auctionProductInfoDao.queryTimingProduct(date, status);
	}

	/**
	 * 添加热门拍品
	 * 
	 * @param ids
	 */
	@Override
	public void saveAuctionProdHot(Integer[] ids, String key) throws Exception {
		if (ids == null || ids.length == 0) {
			throw new Exception("参数为空");
		}
		AuctionProdHotVo hotVo = null;
		List<AuctionProdHotVo> hotVos = new ArrayList<>();
		AuctionProductInfo auctionProductInfo = null;
		AuctionInfo info = null;
		Set<AuctionProdHotVo> set = new HashSet<>();
		int sort = 1;
		for (Integer id : ids) {
			hotVo = new AuctionProdHotVo();
			auctionProductInfo = auctionProductInfoDao.selectByPrimaryKey(id);
			if (auctionProductInfo != null) {
				hotVo.setAuctionProdId(String.valueOf(auctionProductInfo.getId()));

				hotVo.setProductName(auctionProductInfo.getProductName());
				hotVo.setAuctionProdId(String.valueOf(id));
				hotVo.setProductId(auctionProductInfo.getProductId());
				info = auctionInfoDao.queryLastOneAuctionByAuctionProdId(id);
				hotVo.setSort(sort++);
				if (info != null) {
					hotVo.setAuctionId(String.valueOf(info.getId()));
					hotVo.setPreviewPic(info.getPreviewPic());
					hotVo.setStatus(String.valueOf(info.getStatus()));
					if (info.getStartPrice() == null) {
						info.setStartPrice(BigDecimal.ZERO);
					}
					if (info.getIncreasePrice() == null) {
						info.setIncreasePrice(BigDecimal.ZERO);
					}
					BigDecimal bidCount = new BigDecimal("0");
					if (info.getTotalBidCount() != null) {
						bidCount = new BigDecimal(info.getTotalBidCount());
					}

					hotVo.setBidPrice(
							String.valueOf(info.getStartPrice().add(bidCount.multiply(info.getIncreasePrice()))));
				}
			}
			info = auctionInfoDao.queryLastOneAuctionByAuctionProdId(id);
			if (info != null) {
				hotVo.setPreviewPic(info.getPreviewPic());
				hotVo.setAuctionId(String.valueOf(info.getId()));
			}
			set.add(hotVo);
		}
		if (CollectionUtils.isNotEmpty(set)) {
			String jedis = jedisCluster.get(key);
			List<AuctionProdHotVo> hotVosOld = JSONArray.parseArray(jedis, AuctionProdHotVo.class);
			if (CollectionUtils.isNotEmpty(hotVosOld)) {
				set.addAll(hotVosOld);
			}
			hotVos.addAll(set);
			for (int i = 0; i < hotVos.size(); i++) {
				hotVos.get(i).setSort(i + 1);
				jedisCluster.set(key + "_" + hotVos.get(i).getAuctionProdId(),
						JSON.toJSONString(hotVos.get(i).getSort()));
			}
			jedisCluster.set(key, JSON.toJSONString(hotVos));

		} else {
			throw new Exception("添加热门拍品失败,请认真核实您的数据!");
		}

	}

	/**
	 * 添加推荐拍品
	 * 
	 * @param ids
	 * @param key
	 * @throws Exception
	 */
	@Override
	public void saveAuctionProdRecommend(Integer[] ids, String key) throws Exception {

		if (ids == null || ids.length == 0) {
			throw new Exception("参数为空");
		}

		AuctionProdRecommendVo recommendVo = null;
		List<AuctionProdRecommendVo> recommendVos = new ArrayList<>();
		AuctionProductInfo auctionProductInfo = null;
		AuctionInfo info = null;
		Set<AuctionProdRecommendVo> set = new HashSet<>();
		AuctionBidDetail bidDetail = null;
		int sort = 1;
		for (Integer id : ids) {
			recommendVo = new AuctionProdRecommendVo();
			auctionProductInfo = auctionProductInfoDao.selectByPrimaryKey(id);
			if (auctionProductInfo != null) {
				recommendVo.setAuctionProdId(String.valueOf(auctionProductInfo.getId()));
				recommendVo.setProductName(auctionProductInfo.getProductName());
				recommendVo.setAuctionProdId(String.valueOf(id));
				recommendVo.setBidPrice(String.valueOf(auctionProductInfo.getProductPrice()));
				recommendVo.setProductId(auctionProductInfo.getProductId());
			}
			info = auctionInfoDao.queryLastOneAuctionByAuctionProdId(id);
			if (info != null) {
				recommendVo.setPreviewPic(info.getPreviewPic());
				recommendVo.setAuctionId(String.valueOf(info.getId()));
				recommendVo.setAuctionProdId(String.valueOf(info.getAuctionProdId()));
				recommendVo.setStatus(String.valueOf(info.getStatus()));
				if (info.getStartPrice() == null) {
					info.setStartPrice(BigDecimal.ZERO);
				}
				if (info.getIncreasePrice() == null) {
					info.setIncreasePrice(BigDecimal.ZERO);
				}
				BigDecimal bidCount = new BigDecimal("0");
				if (info.getTotalBidCount() != null) {
					bidCount = new BigDecimal(info.getTotalBidCount());
				}
				recommendVo.setBidPrice(
						String.valueOf(info.getStartPrice().add(bidCount.multiply(info.getIncreasePrice()))));
			}
			recommendVo.setSort(sort++);
			set.add(recommendVo);
		}
		if (CollectionUtils.isNotEmpty(set)) {
			String jedis = jedisCluster.get(key);
			List<AuctionProdRecommendVo> recommendVosOld = JSONArray.parseArray(jedis, AuctionProdRecommendVo.class);
			if (CollectionUtils.isNotEmpty(recommendVosOld)) {
				set.addAll(recommendVosOld);
			}
			recommendVos.addAll(set);
			for (int i = 0; i < recommendVos.size(); i++) {
				recommendVos.get(i).setSort(i + 1);
				jedisCluster.set(key + "_" + recommendVos.get(i).getAuctionProdId(),
						JSON.toJSONString(recommendVos.get(i).getSort()));
			}
			jedisCluster.set(key, JSON.toJSONString(recommendVos));
		} else {
			throw new Exception("添加推荐拍品失败,请认真核实您的数据!");
		}
	}

	/**
	 * 根据id查询拍品信息
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public AuctionProductInfo findAuctionProductInfoById(Integer id) {
		long startTime = System.currentTimeMillis();
		log.info("AuctionProductInfo invoke,StartTime:{},params:{}", startTime, id);

		if (null == id) {
			throw new IllegalArgumentException("AuctionProductInfo param id is null");
		}

		AuctionProductInfo result = null;
		try {
			result = auctionProductInfoDao.selectByPrimaryKey(id);
		} catch (NumberFormatException e) {
			log.error("AuctionProductInfo error:", e);
		}

		long endTime = System.currentTimeMillis();
		log.info("AuctionProductInfo end,duration:{}", endTime - startTime);
		return result;
	}

	/**
	 * 更新拍品状态
	 * 
	 * @param ids
	 * @param status
	 * @return
	 */
	@Override
	public int updAuctionProdStatus(String[] ids, Integer status) {
		return auctionProductInfoDao.updAuctionProdStatus(Arrays.asList(ids), status);
	}

	/**
	 * 更新拍品定时的时间和状态
	 * 
	 * @param auctionProductInfo
	 * @return
	 */
	@Override
	public int updAuctionProdDateAndStatus(AuctionProductInfo auctionProductInfo) {
		return auctionProductInfoDao.updAuctionProdDateAndStatus(auctionProductInfo);
	}

	/**
	 * 根据状态和设定的时间查询所有的符合条件的拍品
	 * 
	 * @param date
	 * @param status
	 * @return
	 */
	@Override
	public List<AuctionProductInfo> getByStatusAndDate(String date, int status) {
		return auctionProductInfoDao.getByStatusAndDate(date, status);
	}

	@Override
	public int updateProductStatus(Integer prodId, Integer status) {
		return auctionProductInfoDao.updateProductStatus(prodId, status);
	}

	@Override
	public int updateProductNum(Integer prodId, int prodNum) {
		return auctionProductInfoDao.updateProductNum(prodId, prodNum);
	}

	/**
	 * 获取拍品管理
	 * 
	 * @param paramVo
	 * @return
	 */
	@Override
	public Paging<AuctionProdVo> findByPage(ParamVo paramVo) {
		PageHelper.startPage(paramVo.getPage(), paramVo.getLimit());
		Paging<AuctionProdVo> paging = PageUtils.page(auctionProductInfoDao.findOn(paramVo));
		if (CollectionUtils.isNotEmpty(paging.getList())) {
			AuctionRule rule = null;
			for (AuctionProdVo vo : paging.getList()) {
				rule = auctionRuleDao.findAuctionRuleById(vo.getRuleId());
				vo.setSort(jedisCluster.get(AUCTION_BACK_AUCTION_PROD_HOT + "_" + vo.getId()));
				if (rule != null) {
					vo.setRuleName(rule.getRuleName());
					vo.setPremiumAmount(rule.getPremiumAmount());
					vo.setOpeningBid(rule.getOpeningBid());
				}
			}
		}
		return paging;
	}

	/**
	 * 分页查询拍品下架信息
	 * 
	 * @param paramVo
	 * @return
	 */
	@Override
	public Paging<AuctionProdVo> findByPageOff(ParamVo paramVo) {
		PageHelper.startPage(paramVo.getPage(), paramVo.getLimit());
		return PageUtils.page(auctionProductInfoDao.findOff(paramVo));
	}

	/**
	 * 批量拍品下架
	 * 
	 * @param ids
	 * @param sysUser
	 * @return
	 * @throws Exception
	 */
	@Override
	public ResultBean<Integer> auctionBatchOff(Integer[] ids, SysUser sysUser) throws Exception {
		List<AuctionProductInfoVo> vos = new ArrayList<>();
		ResultBean<Integer> resultBean = new ResultBean<>();
		if (ids == null || ids.length <= 0) {
			throw new Exception("请核实您的数据");
		}
		AuctionProductInfoVo vo = null;
		for (Integer id : ids) {
			vo = new AuctionProductInfoVo();
			vo.setId(id);
			vo.setUserId(sysUser.getId());
			vo.setUserIp(sysUser.getAddIp());
			vos.add(vo);
		}
		ServiceResult result = auctionProdInfoStubService.batchOff(vos);
		return resultBean;
	}

	/**
	 * 拍品上架
	 * 
	 * @param vo
	 * @return
	 */
	@Override
	public int auctionOn(AuctionProductInfoVo vo) throws Exception {
		if (vo == null || vo.getId() == null) {
			throw new Exception("请核实您的数据");
		}
		return auctionProdInfoStubService.auctionProductOn(vo);
	}

	/**
	 * 保存修改拍品
	 * 
	 * @param auctionProductInfo
	 * @param productInfo
	 * @param productPics
	 * @param auctionRule
	 * @return
	 */
	@Override
	public String saveEditAuctionProd(AuctionProductInfoVo auctionProductInfo, ProductInfo productInfo,
			List<ProductPic> productPics, AuctionRule auctionRule) {
		log.info("saveEditAuctionProd  save ");
		String code = "0000";

		Integer updateNum = auctionProductInfo.getProductNum();

		AuctionProductInfo oldInfo = auctionProductInfoDao.selectByPrimaryKey(auctionProductInfo.getId());
		if (oldInfo != null) {
			// AuctionProductRecord auctionProductRecord = new
			// AuctionProductRecord();
			// AuctionInfo auctionInfo = new AuctionInfo();
			try {
				// 第一步
				auctionProductInfo.setUpdateTime(new Date());
				// if (auctionProductInfo.getStatus().equals(2)) {
				// auctionProductInfo.setStatus(AuctionProductEnum.PREPARING.getCode());
				auctionProductInfo.setProductNum(oldInfo.getProductNum() + updateNum);
				auctionProdInfoStubService.updateAuctionProduct(auctionProductInfo);
				// }
				// 修改商品库存
				ProductInventoryLogModel model = new ProductInventoryLogModel();
				model.setUserIp(auctionProductInfo.getUserIp());
				model.setUserId(auctionProductInfo.getUserId());
				model.setProductId(auctionProductInfo.getProductId());
				model.setProductNum(updateNum);
				productInventoryLogSubService.subtractStock(model);
				// if (auctionProductInfo.getStatus().equals(1)) {
				// if
				// (AuctionProductEnum.OFFSHELF.getCode().equals(oldInfo.getStatus()))
				// {
				// auctionProductInfo.setOnShelfTime(new Date());
				// auctionProductInfo.setStatus(AuctionProductEnum.AUCTIONING.getCode());
				// auctionProductInfoDao.updateByPrimaryKey(auctionProductInfo);
				// }else{
				// auctionProductInfoDao.updateByPrimaryKey(auctionProductInfo);
				// auctionProductInfo.setOnShelfTime(new Date());
				// //第二步
				// auctionInfo = getAuctionInfo(productInfo, auctionRule, 1,
				// auctionProductInfo);
				// auctionInfoDao.insert(auctionInfo);
				// //第三步
				// auctionProductRecord = getAuctionProductRecord(productInfo,
				// productPics, auctionRule, auctionInfo);
				// auctionProductRecordDao.insert(auctionProductRecord);
				// }
				// }
			} catch (Exception e) {
				log.error("saveEditAuctionProd save error", e);
				code = "1111";
			}

		}
		return code;
	}

	/**
	 * 根据规则ID查询拍品数量
	 * 
	 * @param ruleId
	 * @return
	 */
	@Override
	public Integer getProductNumByRuleId(Integer ruleId) {
		return auctionProductInfoDao.getProductNumByRuleId(ruleId);
	}

	@Override
	public List<AuctionProductPriceRuleVo> findRulesByProductInfoId(Integer productInfoId) {
		return auctionProdInfoStubService.findRulesByProductInfoId(productInfoId);
	}

}
