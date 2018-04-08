package com.trump.auction.trade.service;

import java.math.BigDecimal;
import java.util.List;

import com.cf.common.util.page.Paging;
import com.cf.common.utils.ServiceResult;
import com.trump.auction.trade.domain.AuctionProductInfo;
import com.trump.auction.trade.model.AuctionInfoQuery;
import com.trump.auction.trade.model.AuctionProductInfoModel;
import com.trump.auction.trade.vo.AuctionProductInfoVo;
import com.trump.auction.trade.vo.AuctionProductPriceRuleVo;

/**
 * @author: .
 * @date: 2018/1/6 .
 * @Description: 拍品信息 .
 */
public interface AuctionProdInfoService {

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
	 * @param auctionInfoModels
	 * @return
	 */
	ServiceResult batchOff(List<AuctionProductInfoVo> auctionInfoModels) throws Exception;

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
	int updAuctionProdStatus(List<String> ids, Integer status);

	/**
	 * 定时更新拍品状态
	 * 
	 * @param auctionProductInfo
	 * @return
	 */
	int updAuctionProdDateAndStatus(AuctionProductInfo auctionProductInfo);

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
	 * 根据分类id查询上架拍品
	 * 
	 * @param auctionQuery
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	Paging<AuctionProductInfoModel> queryProdByClassify(AuctionInfoQuery auctionQuery, int pageNum, int pageSize)
			throws Exception;

	/**
	 * 修改拍品状态
	 * 
	 * @param auctionProduct
	 * @return
	 */
	int updateProductStatus(AuctionProductInfoVo auctionProduct);

	/**
	 * 修改拍品数量
	 * 
	 * @param id
	 * @param num
	 *            修改后数量
	 * @return
	 */
	int updateProductNum(Integer id, int num);

	/**
	 * 根据拍品获取设置的价格浮动规则
	 * 
	 * @param productInfoId
	 * @return
	 */
	List<AuctionProductPriceRuleVo> findRulesByProductInfoId(Integer productInfoId);

	/**
	 * 获取保底价格
	 *   
	 * @param auctionProdId 拍品id
	 * @param productPrice  商品市场价格 
	 * @return
	 */
	BigDecimal floorPrice(Integer auctionProdId, BigDecimal productPrice);
}
