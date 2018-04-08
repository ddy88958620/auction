package com.trump.auction.order.service;


import com.cf.common.util.page.Paging;
import com.trump.auction.order.model.OrderAppraisesModel;

/**
 *
 */
public interface OrderAppraisesService {

	/**
	 * 根据userId获取评价记录
	 */
	Paging<OrderAppraisesModel> getAppraisesByUserId (String userId) ;
	
	
	/**
	 * 创建评价
	 * @param auctionPaymentInfoModel
	 * @return
	 */
	Integer createOrderAppraises(OrderAppraisesModel auctionLogisticsModel);


	OrderAppraisesModel queryOrderAppraises(String appraisesId);


	void orderAppraisesCheck(String appraisesId, String isShow,String baseRewords,String showRewords,String level,String valueArray);


	Paging<OrderAppraisesModel> queryAppraisesByProductId(String productId, Integer pageNum, Integer pageSize);


	OrderAppraisesModel getNewestAppraises(String userId, String orderId);
	
}
