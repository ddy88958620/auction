package com.trump.auction.order.api;


import com.cf.common.util.page.Paging;
import com.trump.auction.order.model.OrderAppraisesModel;


public interface OrderAppraisesStubService {

	/**
	 * 根据userId获取评价记录
	 */
	Paging<OrderAppraisesModel> getAppraisesByUserId (String userId) ;
	
	/**
	 * 根据userId与订单ID获取最新评价记录
	 */
	OrderAppraisesModel getNewestAppraises (String userId,String orderId) ;
	
	
	/**
	 * 创建评价
	 * @param auctionLogisticsModel
	 * @return
	 */
	Integer createOrderAppraises(OrderAppraisesModel auctionLogisticsModel);
	
	
	/**
	 * 查询评价详情
	 * @param appraisesId
	 * @return
	 */
	OrderAppraisesModel queryOrderAppraises(String appraisesId);


	/**
	 * 评价审核
	 * @param appraisesId
	 * @param isShow
	 */
	void orderAppraisesCheck(String appraisesId, String isShow,String baseRewords,String showRewords,String level,String valueArray);


	/**
	 * 根据拍品期次ID查询所有晒单信息
	 * @param auctionNo
	 * @return
	 */
	Paging<OrderAppraisesModel> queryAppraisesByProductId(String productId,Integer pageNum, Integer pageSize);
	
}
