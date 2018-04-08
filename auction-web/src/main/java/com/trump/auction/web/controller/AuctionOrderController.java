package com.trump.auction.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.trump.auction.order.enums.EnumOrderStatus;
import com.trump.auction.web.service.OrderRelatedService;
import com.trump.auction.web.util.HandleResult;
import com.trump.auction.web.util.JsonView;
import com.trump.auction.web.util.SpringUtils;
import com.trump.auction.web.vo.AppraiseParam;
import com.trump.auction.web.vo.OrderParam;

@RequestMapping("order/")
@Controller
public class AuctionOrderController extends BaseController {

	@Autowired
	private OrderRelatedService orderRelatedService;

	/**
	 * <p>
	 * Title: 查询竞拍记录记录 
	 * </p>
	 * 
	 * @param response
	 * @param pageNum
	 * @param pageSize
	 * @param type:
	 */
	@RequestMapping("list")
	public void list(HttpServletResponse response, HttpServletRequest request, Integer pageNum, Integer pageSize,
			Integer type) {
		String userId = super.getUserIdFromRedis(request);
		HandleResult result = orderRelatedService.getOrderListByPage(userId, pageNum, pageSize, type);
		SpringUtils.renderJson(response, JsonView.build(result.getCode(), result.getMsg(),
				result.getData()));
	}
	
	/**
	 * <p>
	 * Title: 订单列表
	 * </p>
	 * <p>
	 * Description: 
	 * </p>
	 * @param response
	 * @param request
	 * @param pageNum
	 * @param pageSize
	 * @param type
	 */
	@RequestMapping("list0")
	public void list0(HttpServletResponse response, HttpServletRequest request, Integer pageNum, Integer pageSize,
			Integer type) {
		String userId = super.getUserIdFromRedis(request);
		HandleResult result = orderRelatedService.getOrderListByPage0(userId, pageNum, pageSize, type);
		SpringUtils.renderJson(response, JsonView.build(result.getCode(), result.getMsg(),
				result.getData()));
	}
	
	

	/**
	 * <p>
	 * Title:订单详情
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param response
	 * @param request
	 * @param orderId
	 *            订单ID
	 */
	@RequestMapping("detail")
	public void detail(HttpServletResponse response, HttpServletRequest request,Integer auctionId,Integer auctionProdId, String orderId) {
		String userId = super.getUserIdFromRedis(request);
		HandleResult result = orderRelatedService.getOrderDetail(userId,auctionProdId,auctionId, orderId);
		SpringUtils.renderJson(response, JsonView.build(result.getCode(), result.getMsg(), result.getData()));

	}
	
	/**
	 * <p>
	 * Title: 移交详情
	 * </p>
	 * @param response
	 * @param request
	 * @param orderId
	 */
	@RequestMapping("transferDetail")
	public void transferDetail(HttpServletResponse response, HttpServletRequest request,String orderId) {
		String userId = getUserIdFromRedis(request);
		HandleResult result = orderRelatedService.getTransferDetail(userId,orderId);
		SpringUtils.renderJson(response, JsonView.build(result.getCode(), result.getMsg(), result.getData()));
	}

	/**
	 * <p>
	 * Title: 签收订单
	 * </p>
	 * 
	 * @param response
	 * @param request
	 * @param orderId
	 */
	@RequestMapping("receive")
	public void signOrder(HttpServletResponse response, HttpServletRequest request, String orderId) {
		String userId = super.getUserIdFromRedis(request);
		HandleResult result = orderRelatedService.updateOrderStatus(Integer.valueOf(userId), orderId,
				EnumOrderStatus.RECEIVED.getValue());
		SpringUtils.renderJson(response, JsonView.build(result.getCode(), result.getMsg()));
	}

	/**
	 * <p>
	 * Title: 取消订单
	 * </p>
	 * 
	 * @param response
	 * @param request
	 * @param orderId
	 */
	@RequestMapping("cancel")
	public void cancel(HttpServletResponse response, HttpServletRequest request, String orderId) {
		String userId = super.getUserIdFromRedis(request);
		HandleResult result = orderRelatedService.updateOrderStatus(Integer.valueOf(userId), orderId,
				EnumOrderStatus.CLOSE.getValue());
		SpringUtils.renderJson(response, JsonView.build(result.getCode(), result.getMsg()));
	}

	/**
	 * <p>
	 * Title: 下单并返回支付支付信息
	 * </p>
	 */
	@RequestMapping("create")
	public void createOrder(HttpServletRequest request, HttpServletResponse response, OrderParam orderParam) {
		String userId = getUserIdFromRedis(request);
		HandleResult result = orderRelatedService.createOrder(userId, orderParam);
		SpringUtils.renderJson(response, JsonView.build(result.getCode(), result.getMsg(),result.getData()));
	}
	
	/**
	 * <p>
	 * Title: 预支付
	 * </p>
	 * @param request
	 * @param response
	 * @param orderId 订单编号
	 * @param payType 1：微信 2：支付宝
	 */
	@RequestMapping("prePay")
	public void orderPrePay(HttpServletRequest request,HttpServletResponse response,String addressId,String orderId,String remark,Integer payType) {
		String userId = getUserIdFromRedis(request);
		HandleResult result = orderRelatedService.getPrePayInfo(request,userId,addressId,orderId,remark,payType);
		SpringUtils.renderJson(response, JsonView.build(result.getCode(), result.getMsg(),result.getData()));
	}

	/**
	 * <p>
	 * Title: 晒单评价
	 * </p>
	 */
	@RequestMapping("appraise")
	public void appraise(HttpServletRequest request, HttpServletResponse response, AppraiseParam appraiseParam) {
		String userId = getUserIdFromRedis(request);
		HandleResult result = orderRelatedService.appraise(userId, appraiseParam);
		SpringUtils.renderJson(response, JsonView.build(result.getCode(), result.getMsg(),result.getData()));
	}
	
}
