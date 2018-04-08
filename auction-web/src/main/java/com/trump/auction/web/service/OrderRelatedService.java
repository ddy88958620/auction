package com.trump.auction.web.service;

import javax.servlet.http.HttpServletRequest;

import com.trump.auction.web.util.HandleResult;
import com.trump.auction.web.vo.AppraiseParam;
import com.trump.auction.web.vo.OrderParam;

public interface OrderRelatedService {

	HandleResult getOrderListByPage(String userId,Integer pageNum,Integer pageSize,Integer type);

	HandleResult getOrderDetail(String userId,Integer auctionProdId,Integer auctionId, String orderId);

	HandleResult appraise(String userId, AppraiseParam appraiseParam);

	HandleResult updateOrderStatus(Integer valueOf, String orderId, Integer orderStatus);

	HandleResult createOrder(String userId, OrderParam orderParam);

	HandleResult getPrePayInfo(HttpServletRequest request,String userId,String addressId,String orderId,String remark, Integer payType);

	HandleResult getTransferDetail(String userId, String orderId);

	HandleResult getOrderListByPage0(String userId, Integer pageNum, Integer pageSize, Integer type);

}
