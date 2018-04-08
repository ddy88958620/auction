package com.trump.auction.order.api.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.cf.common.util.page.Paging;
import com.trump.auction.order.api.OrderAppraisesStubService;
import com.trump.auction.order.model.OrderAppraisesModel;
import com.trump.auction.order.service.OrderAppraisesService;




@Service(version = "1.0.0")
public class OrderAppraisesStubServiceImpl implements OrderAppraisesStubService {
	
	@Autowired
	private OrderAppraisesService orderAppraisesService;

	@Override
	public Paging<OrderAppraisesModel> getAppraisesByUserId(String userId) {
		return orderAppraisesService.getAppraisesByUserId(userId);
	}

	@Override
	public Integer createOrderAppraises(OrderAppraisesModel auctionLogisticsModel) {
		return orderAppraisesService.createOrderAppraises(auctionLogisticsModel);
	}

	@Override
	public OrderAppraisesModel queryOrderAppraises(String appraisesId) {
		return orderAppraisesService.queryOrderAppraises(appraisesId);
	}

	@Override
	public void orderAppraisesCheck(String appraisesId, String isShow,String baseRewords,String showRewords,String level,String valueArray) {
		orderAppraisesService.orderAppraisesCheck(appraisesId,isShow,baseRewords,showRewords,level,valueArray);
	}
	
	@Override
	public Paging<OrderAppraisesModel> queryAppraisesByProductId(String productId,Integer pageNum, Integer pageSize) {
		return orderAppraisesService.queryAppraisesByProductId(productId,pageNum,pageSize);
	}

	@Override
	public OrderAppraisesModel getNewestAppraises(String userId,String orderId) {
		return orderAppraisesService.getNewestAppraises(userId,orderId);
	}

	
}
