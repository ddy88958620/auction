package com.trump.auction.trade.api;

import com.cf.common.utils.ServiceResult;
import com.trump.auction.trade.model.AuctionProductInfoModel;
import com.trump.auction.trade.vo.AuctionProductInfoVo;
import com.trump.auction.trade.vo.AuctionProductPriceRuleVo;

import java.util.List;

/**
 * @author:
 * @date: 2018/1/6 0006.
 * @Description: 拍品 .
 */
public interface AuctionProdInfoStubService {

	/**
	 * 获取拍品信息通过ID
	 * 
	 * @param id
	 * @return
	 */
	AuctionProductInfoVo getAuctionProductById(Integer id);

	/**
	 * 拍品批量下架
	 * 
	 * @param auctionProductInfoVos
	 * @return
	 */
	ServiceResult batchOff(List<AuctionProductInfoVo> auctionProductInfoVos) throws Exception;

	/**
	 * 拍品保存
	 * 
	 * @param auctionProductInfoVo
	 * @return
	 */
	ServiceResult auctionProdSave(AuctionProductInfoVo auctionProductInfoVo) throws Exception;

	/**
	 * 拍品上架
	 * 
	 * @param auctionProductInfoVo
	 * @return
	 */
	int auctionProductOn(AuctionProductInfoVo auctionProductInfoVo) throws Exception;

	/**
	 * 批量上架
	 * 
	 * @param auctionProductInfoVos
	 * @return
	 */
	int auctionProductBatchOn(List<AuctionProductInfoVo> auctionProductInfoVos) throws Exception;

	/**
	 * 定时上架
	 * 
	 * @param auctionProductInfoVos
	 * @return
	 */
	int auctionProductTimingBatchOn(List<AuctionProductInfoVo> auctionProductInfoVos) throws Exception;

	/**
	 * 修改拍品信息
	 * 
	 * @param auctionProductInfoVo
	 * @return
	 */
	int updateAuctionProduct(AuctionProductInfoVo auctionProductInfoVo) throws Exception;

	/**
	 * 批量更新拍品状态
	 * 
	 * @param ids
	 * @param status
	 * @return
	 */
	int updAuctionProdStatus(String[] ids, Integer status);

	/**
	 * 定时更新拍品状态
	 * 
	 * @param auctionProductInfoModel
	 * @return
	 */
	int updAuctionProdDateAndStatus(AuctionProductInfoModel auctionProductInfoModel);

	/**
	 * 根据拍品id获取拍品价格浮动规则
	 * 
	 * @param productInfoId
	 * @return
	 */
	List<AuctionProductPriceRuleVo> findRulesByProductInfoId(Integer productInfoId);

}
