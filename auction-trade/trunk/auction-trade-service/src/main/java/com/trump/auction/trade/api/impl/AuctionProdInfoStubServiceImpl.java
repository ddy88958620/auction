package com.trump.auction.trade.api.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.utils.ServiceResult;
import com.trump.auction.trade.api.AuctionProdInfoStubService;
import com.trump.auction.trade.domain.AuctionProductInfo;
import com.trump.auction.trade.model.AuctionProductInfoModel;
import com.trump.auction.trade.service.AuctionProdInfoService;
import com.trump.auction.trade.vo.AuctionProductInfoVo;
import com.trump.auction.trade.vo.AuctionProductPriceRuleVo;

/**
 * @description: 拍品信息
 * @author: zhangqingqiang
 * @date: 2018-01-06 11:41
 **/
@Service(version = "1.0.0")
public class AuctionProdInfoStubServiceImpl implements AuctionProdInfoStubService {
	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private AuctionProdInfoService auctionProdInfoService;

	/**
	 * 获取拍品信息通过ID
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public AuctionProductInfoVo getAuctionProductById(Integer id) {
		return auctionProdInfoService.getAuctionProductById(id);
	}

	/**
	 * 拍品上架
	 * 
	 * @param auctionProductInfoVo
	 * @return
	 */
	@Override
	public int auctionProductOn(AuctionProductInfoVo auctionProductInfoVo) throws Exception {
		return auctionProdInfoService.auctionProductOn(auctionProductInfoVo);
	}

	/**
	 * 批量上架
	 * 
	 * @param auctionProductInfoVos
	 * @return
	 * @throws Exception
	 */
	@Override
	public int auctionProductBatchOn(List<AuctionProductInfoVo> auctionProductInfoVos) throws Exception {
		return auctionProdInfoService.auctionProductBatchOn(auctionProductInfoVos);
	}

	/**
	 * 定时上架
	 * 
	 * @param auctionProductInfoVos
	 * @return
	 * @throws Exception
	 */
	@Override
	public int auctionProductTimingBatchOn(List<AuctionProductInfoVo> auctionProductInfoVos) throws Exception {
		return auctionProdInfoService.auctionProductTimingBatchOn(auctionProductInfoVos);
	}

	/**
	 * 修改拍品信息
	 * 
	 * @param auctionProductInfoVo
	 * @return
	 */
	@Override
	public int updateAuctionProduct(AuctionProductInfoVo auctionProductInfoVo) throws Exception {
		return auctionProdInfoService.updateAuctionProduct(auctionProductInfoVo);
	}

	/**
	 * 批量更新拍品状态
	 * 
	 * @param ids
	 * @param status
	 * @return
	 */
	@Override
	public int updAuctionProdStatus(String[] ids, Integer status) {
		return auctionProdInfoService.updAuctionProdStatus(Arrays.asList(ids), status);
	}

	/**
	 * 定时更新拍品状态
	 * 
	 * @param auctionProductInfoModel
	 * @return
	 */
	@Override
	public int updAuctionProdDateAndStatus(AuctionProductInfoModel auctionProductInfoModel) {
		return auctionProdInfoService
				.updAuctionProdDateAndStatus(beanMapper.map(auctionProductInfoModel, AuctionProductInfo.class));
	}

	/**
	 * 拍品批量下架
	 * 
	 * @param auctionInfoModels
	 * @return
	 */
	@Override
	public ServiceResult batchOff(List<AuctionProductInfoVo> auctionInfoModels) throws Exception {
		return auctionProdInfoService.batchOff(auctionInfoModels);
	}

	/**
	 * 拍品保存
	 * 
	 * @param auctionProductInfoVo
	 * @return
	 */
	@Override
	public ServiceResult auctionProdSave(AuctionProductInfoVo auctionProductInfoVo) throws Exception {
		return auctionProdInfoService.auctionProdSave(auctionProductInfoVo);
	}

	/**
	 * 根据拍品id获取拍品价格浮动规则
	 * 
	 * @param productInfoId
	 * @return
	 */
	@Override
	public List<AuctionProductPriceRuleVo> findRulesByProductInfoId(Integer productInfoId) {
		// TODO Auto-generated method stub
		return auctionProdInfoService.findRulesByProductInfoId(productInfoId);
	}
}
