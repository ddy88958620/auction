package com.trump.auction.web.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.util.HtmlUtils;

import com.alibaba.fastjson.JSONObject;
import com.cf.common.id.IdGenerator;
import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.util.page.Paging;
import com.cf.common.utils.ServiceResult;
import com.trump.auction.cust.api.UserInfoStubService;
import com.trump.auction.cust.api.UserShippingAddressStuService;
import com.trump.auction.cust.model.UserInfoModel;
import com.trump.auction.cust.model.UserShippingAddressModel;
import com.trump.auction.order.api.OrderAppraisesStubService;
import com.trump.auction.order.api.OrderInfoStubService;
import com.trump.auction.order.api.PaymentStubService;
import com.trump.auction.order.enums.EnumOrderStatus;
import com.trump.auction.order.enums.EnumOrderType;
import com.trump.auction.order.enums.EnumPaymentFlag;
import com.trump.auction.order.enums.EnumPaymentType;
import com.trump.auction.order.model.OrderAppraisesModel;
import com.trump.auction.order.model.OrderInfoModel;
import com.trump.auction.order.model.OrderInfoQuery;
import com.trump.auction.order.model.PaymentInfoModel;
import com.trump.auction.pals.api.AlipayStubService;
import com.trump.auction.pals.api.WeChatPayStubService;
import com.trump.auction.pals.api.constant.EnumPayFrom;
import com.trump.auction.pals.api.model.alipay.AlipayPayRequest;
import com.trump.auction.pals.api.model.alipay.AlipayPayResponse;
import com.trump.auction.pals.api.model.wechat.WeChatPayRequest;
import com.trump.auction.pals.api.model.wechat.WeChatPayResponse;
import com.trump.auction.trade.api.AuctionBidStubService;
import com.trump.auction.trade.api.AuctionInfoStubService;
import com.trump.auction.trade.api.AuctionOrderStubService;
import com.trump.auction.trade.model.AuctionInfoModel;
import com.trump.auction.trade.model.UserBidModel;
import com.trump.auction.trade.vo.AuctionOrderVo;
import com.trump.auction.trade.vo.ParamVo;
import com.trump.auction.web.service.OrderRelatedService;
import com.trump.auction.web.util.Base64Utils;
import com.trump.auction.web.util.HandleResult;
import com.trump.auction.web.util.HttpHelper;
import com.trump.auction.web.vo.AppraiseParam;
import com.trump.auction.web.vo.OrderDetailVo;
import com.trump.auction.web.vo.OrderInfoVo;
import com.trump.auction.web.vo.OrderParam;
import com.trump.auction.web.vo.TransferOrderDetail;
import com.trump.auction.web.vo.UserSupport;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderRelatedServiceImpl implements OrderRelatedService {

	@Autowired
	private OrderInfoStubService orderInfoStubService;
	
	@Autowired
	private OrderAppraisesStubService orderAppraisesStubService;
	
	@Autowired
	private BeanMapper beanMapper;
	
	@Autowired
	private UserInfoStubService userInfoStubService;
	
	@Autowired
	AuctionOrderStubService auctionOrderStubService;
	
	@Autowired
	private PaymentStubService paymentStubService;
	
	@Autowired
	private AlipayStubService alipayStubService;
	
    @Autowired
    private AuctionBidStubService auctionBidStubService;
    
	@Autowired
	private UserShippingAddressStuService userShippingAddressStuService;
	
	@Autowired
	private WeChatPayStubService weChatPayStubService;
	
    @Autowired
    private IdGenerator snowflakePayBatchNo;
    
    @Value("${aliyun.oss.domain}")
    private String aliyunOssDomain;
    
    @Autowired
    private AuctionInfoStubService auctionInfoStubService; 
		
	@Override
	public HandleResult getOrderListByPage(String userId,Integer pageNum,Integer pageSize,Integer type) {
		HandleResult result = new HandleResult(false);
		
		if(type == null){
			return result.setCode(1).setMsg("type不能为空");
		}

		List<Integer> typeList =Arrays.asList(0,1,2,3,4,5,6);
		if(!typeList.contains(type)){
			return result.setCode(2).setMsg("type值不合法");
		}
		
		pageNum = pageNum == null?1:pageNum;
		pageSize = pageSize == null?10:pageSize;
		
		ParamVo paramVo = new ParamVo();
		paramVo.setUserId(Integer.valueOf(userId));
		paramVo.setPageNum(pageNum);
		paramVo.setPageSize(pageSize);
		Integer orderStatus = this.transformQueryOrderStatus(type, paramVo);
	
		Integer pages = 0;
		List<OrderInfoVo> list = new ArrayList<>();
		try {
			if(orderStatus != null){//订单
				OrderInfoQuery orderInfo = new OrderInfoQuery();
				orderInfo.setBuyId(userId);
				orderInfo.setOrderStatus(orderStatus);
				Paging<OrderInfoModel> paging  = orderInfoStubService.findAllOrder(orderInfo, pageNum, pageSize);
				
				List<OrderInfoModel> modelList = paging.getList();
				if(modelList != null && modelList.size() > 0 ){
					for (OrderInfoModel orderInfoModel : modelList) {
						OrderInfoVo orderInfoVo = beanMapper.map(orderInfoModel, OrderInfoVo.class);
						orderInfoVo.setPreviewPic(aliyunOssDomain + orderInfoVo.getProductPic());
						UserBidModel userBidModel = auctionBidStubService.userBidInfo(Integer.valueOf(userId), orderInfoVo.getAuctionId());
						if(StringUtils.hasText(userBidModel.getUserName())){
							orderInfoVo.setUserName(Base64Utils.decodeStr(userBidModel.getUserName()));
						}else{
							orderInfoVo.setUserName("");
						}
						AuctionInfoModel auctionInfoModel = auctionInfoStubService.getAuctionInfoById(orderInfoVo.getAuctionId());
						orderInfoVo.setAuctionProdId(auctionInfoModel.getAuctionProdId());
						orderInfoVo.setOrderAmount(orderInfoModel.getPaidMoney());
						orderInfoVo.setBidPrice(userBidModel.getPaymentPrice());
						orderInfoVo.setFinalPrice(userBidModel.getFinalPrice());//成交价格
						if(orderInfoVo.getOrderStatus().equals(EnumOrderStatus.UNPAID.getValue())){
							orderInfoVo.setEndTime(orderInfoModel.getCreateTime());
						}else{
							orderInfoVo.setEndTime(orderInfoModel.getUpdateTime());
							if(orderInfoVo.getOrderStatus().equals(EnumOrderStatus.RECEIVED.getValue())){
								OrderAppraisesModel orderAppraisesModel = orderAppraisesStubService.getNewestAppraises(userId, orderInfoVo.getOrderId());
								
								if(orderAppraisesModel != null){
									String appraisesStatus = orderAppraisesModel.getStatus();
									Integer isShow = orderAppraisesModel.getIsShow();
									if(appraisesStatus.equals("1")){
										orderInfoVo.setAppraisesStatus(2);//晒单审核中
									}
									if(appraisesStatus.equals("2") && isShow.equals(2)){
										orderInfoVo.setAppraisesStatus(3);//晒单失败
									}
								}else{
									orderInfoVo.setAppraisesStatus(1);//晒单有奖
								}
							}
						}
						list.add(orderInfoVo);
					}
				}
				pages = paging.getPages();
			}
			
			if(orderStatus == null){//订单//记录
				Paging<AuctionOrderVo> page = auctionOrderStubService.findAuctionOrder(paramVo);
				List<AuctionOrderVo> auctionVos = page.getList();
				if(auctionVos != null && auctionVos.size() > 0 ){
					for (AuctionOrderVo vo : auctionVos) {
						this.packageOrderInfoData(userId, list, vo);
					}
				}
				pages = page.getPages();
			}
		} catch (Exception e) {
			log.error("getOrderListByPage-error,{}",e);
		}
		
		JSONObject json = new JSONObject();
		json.put("pages", pages);
		json.put("pageNum", pageNum);
		json.put("list", list==null?new ArrayList<>():list);
		
		return result.setResult(true).setDataPage(pageNum, pages, list);
	}
	
	
	
	@Override
	public HandleResult getOrderListByPage0(String userId, Integer pageNum, Integer pageSize, Integer type) {
		HandleResult result = new HandleResult(false);
		
		if(type == null){
			return result.setCode(1).setMsg("type不能为空");
		}

		List<Integer> typeList =Arrays.asList(0,4,5,6,7);
		if(!typeList.contains(type)){
			return result.setCode(2).setMsg("type值不合法");
		}
		
		pageNum = pageNum == null?1:pageNum;
		pageSize = pageSize == null?10:pageSize;
		
		ParamVo paramVo = new ParamVo();
		paramVo.setUserId(Integer.valueOf(userId));
		paramVo.setPageNum(pageNum);
		paramVo.setPageSize(pageSize);
		Integer orderStatus = this.transformQueryOrderStatus(type, paramVo);
	
		Integer pages = 0;
		List<OrderInfoVo> list = new ArrayList<>();
		try {
			OrderInfoQuery orderInfo = new OrderInfoQuery();
			orderInfo.setBuyId(userId);
			orderInfo.setOrderStatus(orderStatus);
			Paging<OrderInfoModel> paging  = orderInfoStubService.findAllOrder(orderInfo, pageNum, pageSize);
			
			List<OrderInfoModel> modelList = paging.getList();
			if(modelList != null && modelList.size() > 0 ){
				for (OrderInfoModel orderInfoModel : modelList) {
					OrderInfoVo orderInfoVo = beanMapper.map(orderInfoModel, OrderInfoVo.class);
					orderInfoVo.setPreviewPic(aliyunOssDomain + orderInfoVo.getProductPic());
					UserBidModel userBidModel = auctionBidStubService.userBidInfo(Integer.valueOf(userId), orderInfoVo.getAuctionId());
					if(StringUtils.hasText(userBidModel.getUserName())){
						orderInfoVo.setUserName(Base64Utils.decodeStr(userBidModel.getUserName()));
					}else{
						orderInfoVo.setUserName("");
					}
					AuctionInfoModel auctionInfoModel = auctionInfoStubService.getAuctionInfoById(orderInfoVo.getAuctionId());
					orderInfoVo.setAuctionProdId(auctionInfoModel.getAuctionProdId());
					orderInfoVo.setOrderAmount(orderInfoModel.getPaidMoney());
					orderInfoVo.setBidPrice(userBidModel.getPaymentPrice());
					orderInfoVo.setFinalPrice(userBidModel.getFinalPrice());//成交价格
					if(orderInfoVo.getOrderStatus().equals(EnumOrderStatus.UNPAID.getValue())){
						orderInfoVo.setEndTime(orderInfoModel.getCreateTime());
					}else{
						orderInfoVo.setEndTime(orderInfoModel.getUpdateTime());
						if(orderInfoVo.getOrderStatus().equals(EnumOrderStatus.RECEIVED.getValue())){
							OrderAppraisesModel orderAppraisesModel = orderAppraisesStubService.getNewestAppraises(userId, orderInfoVo.getOrderId());
							
							if(orderAppraisesModel != null){
								String appraisesStatus = orderAppraisesModel.getStatus();
								Integer isShow = orderAppraisesModel.getIsShow();
								if(appraisesStatus.equals("1")){
									orderInfoVo.setAppraisesStatus(2);//晒单审核中
								}
								if(appraisesStatus.equals("2") && isShow.equals(2)){
									orderInfoVo.setAppraisesStatus(3);//晒单失败
								}
							}else{
								orderInfoVo.setAppraisesStatus(1);//晒单有奖
							}
						}
					}
					list.add(orderInfoVo);
				}
			}
			pages = paging.getPages();
		} catch (Exception e) {
			log.error("getOrderListByPage-error,{}",e);
		}
		
		JSONObject json = new JSONObject();
		json.put("pages", pages);
		json.put("pageNum", pageNum);
		json.put("list", list==null?new ArrayList<>():list);
		
		return result.setResult(true).setDataPage(pageNum, pages, list);
	}
	

	private Integer transformQueryOrderStatus(Integer type, ParamVo paramVo) {
		Integer orderStatus = null;
		if(type == 0){//全部
			paramVo.setAuctionStatus(null);
		}	
		if(type == 1){//正在拍
			paramVo.setAuctionStatus(1);
		}
		if(type == 2){//已拍中
			paramVo.setAuctionStatus(2);
		}
		if(type == 3 ){//差价购
			paramVo.setAuctionStatus(3);
		}
		if(type == 4 ){//待付款
			orderStatus = EnumOrderStatus.UNPAID.getValue();
		}
		if(type == 5){//待签收
			orderStatus = 0;			
		}
		if(type == 6){//待晒单
			orderStatus = EnumOrderStatus.RECEIVED.getValue();
		}
		if(type == 7){//待发货
			orderStatus = 0;
		}
		return orderStatus;
	}

	private void packageOrderInfoData(String userId, List<OrderInfoVo> list, AuctionOrderVo vo) {
		Integer status = vo.getStatus();
		
		if(status.equals(1)){
			OrderInfoVo orderInfoVo = beanMapper.map(vo, OrderInfoVo.class);
			orderInfoVo.setDataType(1);
			orderInfoVo.setPreviewPic(aliyunOssDomain+orderInfoVo.getPreviewPic());
			orderInfoVo.setUserName("");
			list.add(orderInfoVo);
			return;
		}
		UserBidModel userBidModel = auctionBidStubService.userBidInfo(Integer.valueOf(userId), vo.getAuctionId());
		if(status.equals(4)){
			OrderInfoVo orderInfoVo = beanMapper.map(vo, OrderInfoVo.class);
			orderInfoVo.setPreviewPic(aliyunOssDomain+orderInfoVo.getPreviewPic());
			orderInfoVo.setStatus(4);
			orderInfoVo.setDataType(1);
			orderInfoVo.setFinalPrice(userBidModel.getFinalPrice());
			orderInfoVo.setBidPrice(userBidModel.getPaymentPrice());
			orderInfoVo.setUserName(Base64Utils.decodeStr(userBidModel.getUserName()));
			list.add(orderInfoVo);
			return;
		}
		
	
		OrderInfoModel orderInfoModel = orderInfoStubService.findOneOrderByAcNo(Integer.valueOf(userId), vo.getAuctionId());
		if(orderInfoModel == null){
			OrderInfoVo orderInfoVo = beanMapper.map(vo, OrderInfoVo.class);
			orderInfoVo.setDataType(1);
			orderInfoVo.setPreviewPic(aliyunOssDomain + vo.getPreviewPic());
			orderInfoVo.setUserName(Base64Utils.decodeStr(userBidModel.getUserName()));
			orderInfoVo.setOrderAmount(userBidModel.getFinalPrice());
			orderInfoVo.setBidPrice(userBidModel.getPaymentPrice());
			orderInfoVo.setReturnPrice(new BigDecimal(userBidModel.getReturnPrice().intValue()));
			orderInfoVo.setFinalPrice(userBidModel.getFinalPrice());
			AuctionInfoModel auctionInfoModel = auctionInfoStubService.getAuctionInfoById(vo.getAuctionId());
			orderInfoVo.setEndTime(auctionInfoModel.getEndTime());
			list.add(orderInfoVo);
			return;
		}
		// 状态（1正在拍 2已拍中 3未拍中）
		if(orderInfoModel != null && status.equals(2)){
			OrderInfoVo orderInfoVo = beanMapper.map(vo, OrderInfoVo.class);
			orderInfoVo.setPreviewPic(aliyunOssDomain + orderInfoModel.getProductPic());
			orderInfoVo.setStatus(2);
			orderInfoVo.setOrderType(orderInfoModel.getOrderType());
			orderInfoVo.setDataType(2);
			orderInfoVo.setOrderAmount(orderInfoModel.getPaidMoney());
			orderInfoVo.setOrderStatus(orderInfoModel.getOrderStatus());
			orderInfoVo.setOrderId(orderInfoModel.getOrderId());
			orderInfoVo.setBidPrice(userBidModel.getPaymentPrice());
			orderInfoVo.setUserName(Base64Utils.decodeStr(vo.getUserName()));
			orderInfoVo.setFinalPrice(userBidModel.getFinalPrice());
			if(orderInfoVo.getOrderStatus().equals(EnumOrderStatus.UNPAID.getValue())){
				orderInfoVo.setEndTime(orderInfoModel.getCreateTime());
			}else{
				orderInfoVo.setEndTime(orderInfoModel.getUpdateTime());
				if(orderInfoVo.getOrderStatus().equals(EnumOrderStatus.RECEIVED.getValue())){
					OrderAppraisesModel orderAppraisesModel = orderAppraisesStubService.getNewestAppraises(userId, orderInfoVo.getOrderId());
					if(orderAppraisesModel != null){
						String appraisesStatus = orderAppraisesModel.getStatus();
						Integer isShow = orderAppraisesModel.getIsShow();
						if(appraisesStatus.equals("1")){
							orderInfoVo.setAppraisesStatus(2);//晒单审核中
						}
						if(appraisesStatus.equals("2") && isShow.equals(2)){
							orderInfoVo.setAppraisesStatus(3);//晒单失败
						}
					}else{
						orderInfoVo.setAppraisesStatus(1);//晒单有奖
					}
				}
			}
			
			list.add(orderInfoVo);
			return;
		}
		
		if(orderInfoModel != null && status.equals(3) && !orderInfoModel.getOrderStatus().equals(EnumOrderStatus.CLOSE.getValue())){
			OrderInfoVo orderInfoVo = beanMapper.map(vo, OrderInfoVo.class);
			orderInfoVo.setPreviewPic(aliyunOssDomain+orderInfoVo.getPreviewPic());
			orderInfoVo.setStatus(3);
			orderInfoVo.setOrderId(orderInfoModel.getOrderId());
			orderInfoVo.setDataType(2);
			orderInfoVo.setFinalPrice(userBidModel.getFinalPrice());
			orderInfoVo.setUserName(Base64Utils.decodeStr(userBidModel.getUserName()));
			orderInfoVo.setOrderAmount(orderInfoModel.getPaidMoney());
			orderInfoVo.setReturnPrice(userBidModel.getReturnPrice());
			orderInfoVo.setOrderType(2);
			orderInfoVo.setOrderStatus(orderInfoModel.getOrderStatus());
			orderInfoVo.setBuyCoinMoney(userBidModel.getReturnPrice());
			if(orderInfoVo.getOrderStatus().equals(EnumOrderStatus.UNPAID.getValue())){
				orderInfoVo.setEndTime(orderInfoModel.getCreateTime());
			}else{
				orderInfoVo.setEndTime(orderInfoModel.getUpdateTime());
				if(orderInfoVo.getOrderStatus().equals(EnumOrderStatus.RECEIVED.getValue())){
					OrderAppraisesModel orderAppraisesModel = orderAppraisesStubService.getNewestAppraises(userId, orderInfoVo.getOrderId());
					
					if(orderAppraisesModel != null){
						String appraisesStatus = orderAppraisesModel.getStatus();
						Integer isShow = orderAppraisesModel.getIsShow();
						if(appraisesStatus.equals("1")){
							orderInfoVo.setAppraisesStatus(2);//晒单审核中
						}
						if(appraisesStatus.equals("2") && isShow.equals(2)){
							orderInfoVo.setAppraisesStatus(3);//晒单失败
						}
					}else{
						orderInfoVo.setAppraisesStatus(1);//晒单有奖
					}
				}
			}
			list.add(orderInfoVo);
			return;
		}else if(orderInfoModel != null && status.equals(3) && orderInfoModel.getOrderStatus().equals(EnumOrderStatus.CLOSE.getValue())){
			OrderInfoVo orderInfoVo = beanMapper.map(vo, OrderInfoVo.class);
			orderInfoVo.setDataType(1);
			orderInfoVo.setPreviewPic(aliyunOssDomain + vo.getPreviewPic());
			orderInfoVo.setUserName(Base64Utils.decodeStr(userBidModel.getUserName()));
			orderInfoVo.setOrderAmount(userBidModel.getFinalPrice());
			orderInfoVo.setBidPrice(userBidModel.getPaymentPrice());
			orderInfoVo.setReturnPrice(new BigDecimal(userBidModel.getReturnPrice().intValue()));
			orderInfoVo.setFinalPrice(userBidModel.getFinalPrice());
			AuctionInfoModel auctionInfoModel = auctionInfoStubService.getAuctionInfoById(vo.getAuctionId());
			orderInfoVo.setEndTime(auctionInfoModel.getEndTime());
			list.add(orderInfoVo);
			return;
		}
		
	}
	
	@Override
	public HandleResult getOrderDetail(String userId,Integer auctionProdId,Integer auctionId,String orderId) {
		HandleResult result = new HandleResult(false);
		OrderInfoModel orderInfo = null;
		if(StringUtils.hasText(orderId)){
			OrderInfoQuery query = new OrderInfoQuery();
			query.setBuyId(userId);
			query.setOrderId(orderId);
			orderInfo = orderInfoStubService.findOneOrder(query);
		}else if(auctionId != null){
			orderInfo = orderInfoStubService.findOneOrderByAcNo(Integer.valueOf(userId), auctionId);
			if(orderInfo != null && orderInfo.getOrderStatus().equals(EnumOrderStatus.CLOSE.getValue())){
				orderInfo = null;
			}
		}
		OrderDetailVo detailVo = new OrderDetailVo();
		
		if(orderInfo == null){
			
			List<UserShippingAddressModel>  addressList = userShippingAddressStuService.findUserAddressListByUserId(Integer.valueOf(userId));
			if(addressList != null && addressList.size() > 0 ){
				UserShippingAddressModel addressModel = addressList.get(0);
				detailVo.setUserShippingId(addressModel.getId());
				detailVo.setProvinceCode(addressModel.getProvinceCode());
				detailVo.setCityCode(addressModel.getPostCode());
				detailVo.setDistrictCode(addressModel.getDistrictCode() == null ? null : addressModel.getDistrictCode().toString());
				
				detailVo.setAddress(addressModel.getAddress());
				detailVo.setProvinceName(addressModel.getProvinceName());
				detailVo.setDistrictName(addressModel.getDistrictName());
				
				detailVo.setCityName(addressModel.getCityName());
				detailVo.setUserName(addressModel.getUserName());
				detailVo.setUserPhone(addressModel.getUserPhone());
			}else{
				detailVo.setUserShippingId(0);
				detailVo.setProvinceCode(0);
				detailVo.setCityCode("");
				detailVo.setDistrictCode("");
				
				detailVo.setAddress("");
				detailVo.setProvinceName("");
				detailVo.setDistrictName("");
				
				detailVo.setCityName("");
				detailVo.setUserName("");
				detailVo.setUserPhone("");
			}
			
			UserBidModel userBidModel = auctionBidStubService.userBidInfo(Integer.valueOf(userId), auctionId);
			detailVo.setProductPic(aliyunOssDomain + userBidModel.getPreviewPic());
			detailVo.setBuyCoinMoney(new BigDecimal(userBidModel.getReturnPrice().intValue()));
			detailVo.setOrderType(2);
			if(userBidModel.getReturnPrice().intValue() >= userBidModel.getSalePrice().intValue()){
				detailVo.setPaidMoney(new BigDecimal(0));
				detailVo.setBuyCoinMoney(new BigDecimal(userBidModel.getSalePrice().intValue()));
			}else{
				detailVo.setPaidMoney(userBidModel.getSalePrice().subtract(userBidModel.getReturnPrice()));
				detailVo.setBuyCoinMoney(new BigDecimal(userBidModel.getReturnPrice().intValue()));
			}
			detailVo.setProductPrice(userBidModel.getSalePrice());
			return result.setResult(true).setData(detailVo);
		}
		try {
			
			detailVo = beanMapper.map(orderInfo, OrderDetailVo.class);
			detailVo.setProductPic(aliyunOssDomain + detailVo.getProductPic());
			
			List<UserShippingAddressModel>  addressList = userShippingAddressStuService.findUserAddressListByUserId(Integer.valueOf(userId));
			if(addressList != null && addressList.size() > 0 ){
				UserShippingAddressModel addressModel = addressList.get(0);
				detailVo.setUserShippingId(addressModel.getId());
				detailVo.setProvinceCode(addressModel.getProvinceCode());
				detailVo.setCityCode(addressModel.getPostCode());
				detailVo.setDistrictCode(addressModel.getDistrictCode() == null ? null : addressModel.getDistrictCode().toString());
				
				detailVo.setAddress(addressModel.getAddress());
				detailVo.setProvinceName(addressModel.getProvinceName());
				detailVo.setDistrictName(addressModel.getDistrictName());
				
				detailVo.setCityName(addressModel.getCityName());
				detailVo.setUserName(addressModel.getUserName());
				detailVo.setUserPhone(addressModel.getUserPhone());
			}else{
				detailVo.setUserShippingId(0);
				detailVo.setProvinceCode(0);
				detailVo.setCityCode("");
				detailVo.setDistrictCode("");
				
				detailVo.setAddress("");
				detailVo.setProvinceName("");
				detailVo.setDistrictName("");
				
				detailVo.setCityName("");
				detailVo.setUserName("");
				detailVo.setUserPhone("");
			}
			
			if(detailVo.getOrderType().equals(EnumOrderType.AUCTION.getValue())){
				Date orderCreateTime = orderInfo.getCreateTime();
				long countDown = orderCreateTime.getTime()/1000 + 259200 -System.currentTimeMillis()/1000;
				if(countDown > 0){
					detailVo.setCountDown(countDown);
				}else{
					detailVo.setCountDown(0L);
				}
			}else{
				detailVo.setCountDown(0L);
			}
		} catch (Exception e) {
			log.error("getOrderDetail error, {}",e);
		}
		return result.setResult(true).setData(detailVo);
	}
	
	@Override
	public HandleResult appraise(String userId,AppraiseParam appraiseParam){
		HandleResult result = new HandleResult(false);
		JSONObject json = new JSONObject();
		json.put("appraiseAward", "200");
		
		//check参数
		if(appraiseParam == null){
			return result.setCode(1).setMsg("参数不能为空");
		}
		String content = appraiseParam.getContent().trim();
		if(!StringUtils.hasText(content)){
			return result.setCode(2).setMsg("评价内容不能为空");
		}
		if(content.length() > 200){
			return result.setCode(2).setMsg("评价内容最多200字");
		}
		content = HtmlUtils.htmlEscape(content);
		content = Base64Utils.encodeStr(content);
		try {
			UserInfoModel user = userInfoStubService.findUserInfoById(Integer.valueOf(userId));
			String buyNickName = UserSupport.getBase64UserNameByLoginType(user);
			String headImg = user.getHeadImg();
			if(StringUtils.hasText(headImg)){
				headImg = aliyunOssDomain + headImg;
			}else{
				headImg = UserSupport.getHeadImgByLoginType(user);
			}
			
			OrderInfoQuery orderInfo = new OrderInfoQuery();
			orderInfo.setOrderId(appraiseParam.getOrderId());
			OrderInfoModel orderInfoModel = orderInfoStubService.findOneOrder(orderInfo);
			
			OrderAppraisesModel appraise = new OrderAppraisesModel();
			appraise.setBuyNickName(buyNickName);
			appraise.setBuyPic(headImg);
			appraise.setAppraisesPic(appraiseParam.getAppraisesPic());
			appraise.setBuyId(userId);
			appraise.setContent(content);
			appraise.setMerchantId(orderInfoModel.getMerchantId());
			appraise.setOrderId(appraiseParam.getOrderId());
			appraise.setBidTimes(orderInfoModel.getBidTimes());
			appraise.setAuctionNo(orderInfoModel.getAuctionNo());
			appraise.setProductName(orderInfoModel.getProductName());
			AuctionInfoModel auctionInfoModel = auctionInfoStubService.getAuctionInfoById(orderInfoModel.getAuctionNo());
			appraise.setProductId(auctionInfoModel.getProductId());
			Integer res = orderAppraisesStubService.createOrderAppraises(appraise);
			if(res != null && res == 1){
				return result.setResult(true).setCode(0).setData(json);
			}
		} catch (Exception e) {
			log.error("createOrderAppraises error: {}",e);
			return result.setCode(1).setMsg("发布失败");
		}
		return result.setCode(1).setMsg("发布失败");
	}
	
	@Override
	public HandleResult createOrder(String userId,OrderParam orderParam) {
		HandleResult result = new HandleResult(false);
		
		if(orderParam == null){
			return result.setCode(1).setMsg("参数不能未空");
		}
		if(orderParam.getUserShippingId() == null ){
			return result.setCode(1).setMsg("请选择收货地址");
		}
		UserShippingAddressModel userAddressModel = userShippingAddressStuService.findUserAddressItemByAddressId(orderParam.getUserShippingId());
		if(!userAddressModel.getStatus().equals(0)){
			return result.setCode(1).setMsg("该地址已失效，请重新选择");
		}
		
		String orderId = null;
		try {
			Integer auctionId = orderParam.getAuctionId();
			if(auctionId == null){
				return result.setCode(1).setMsg("auctionId is null");
			}
			OrderInfoModel orderInfoModel = orderInfoStubService.findOneOrderByAcNo(Integer.valueOf(userId), auctionId);
			if(orderInfoModel == null || orderInfoModel.getOrderStatus().equals(EnumOrderStatus.CLOSE.getValue())){
				UserBidModel userBidModel = auctionBidStubService.userBidInfo(Integer.valueOf(userId), orderParam.getAuctionId());
				Integer auctionProdId = orderParam.getAuctionProdId();
				OrderInfoModel orderInfo = new OrderInfoModel();
				orderInfo.setAuctionNo(auctionId);
				orderInfo.setProductId(auctionProdId);
				orderInfo.setBuyId(userId);
				
				orderInfo.setUserShippingId(orderParam.getUserShippingId());
				orderInfo.setProductNum(1);
				orderInfo.setOrderStatus(EnumOrderStatus.UNPAID.getValue());
				orderInfo.setRemark(orderParam.getRemark());
				orderInfo.setOrderAmount(userBidModel.getSalePrice());
				
				if(userBidModel.getReturnPrice().intValue() >= userBidModel.getSalePrice().intValue()){
					orderInfo.setPaidMoney(new BigDecimal(0));
					orderInfo.setBuyCoinMoney(userBidModel.getSalePrice());
				}else{
					orderInfo.setBuyCoinMoney(userBidModel.getReturnPrice());
					orderInfo.setPaidMoney(userBidModel.getSalePrice().subtract(userBidModel.getReturnPrice()));
				}
				orderInfo.setBidTimes(userBidModel.getBidCount());
				
				ServiceResult serviceResult = orderInfoStubService.saveOrder(orderInfo);
				if(serviceResult != null && serviceResult.isSuccessed()){
					orderId = (String)serviceResult.getExt();
				}else{
					log.info("serviceResult :{}",serviceResult);
					if(serviceResult != null && serviceResult.getCode().equals("0004")){
						return result.setCode(1).setMsg("库存不足");
					}
				}
			}
			
			if(orderInfoModel != null){
				orderId = orderInfoModel.getOrderId();
			}
			
		} catch (Exception e) {
			log.error("createOrder error: {}",e);
		}
		if(!StringUtils.hasText(orderId)){
			return result.setCode(1).setMsg("提交订单失败");
		}
		JSONObject json = new JSONObject();
		json.put("orderId", orderId);
		return result.setResult(true).setData(json);
	}
	

	@Override
	public HandleResult updateOrderStatus(Integer userId, String orderId, Integer orderStatus) {
		HandleResult handleResult = new HandleResult(false);
		
		if(orderId == null){
			return handleResult.setCode(1).setMsg("orderId不能为空");
		}
		String errorMsg = "";
		if(orderStatus.equals(EnumOrderStatus.COMPLETE.getValue())){
			errorMsg = "确认收货失败";
		}
		if(orderStatus.equals(EnumOrderStatus.CLOSE.getValue())){
			errorMsg = "取消订单失败";
		}
		
		try {
			OrderInfoQuery query = new OrderInfoQuery();
			query.setBuyId(String.valueOf(userId));
			query.setOrderId(orderId);
			OrderInfoModel orderInfoModel = orderInfoStubService.findOneOrder(query);
			if(orderInfoModel == null){
				return handleResult.setCode(2).setMsg("该订单信息不存在");
			}
			
			if(!orderInfoModel.getBuyId().equals(String.valueOf(userId))){
				return handleResult.setCode(3).setMsg("该订单信息不存在");
			}
			
			OrderInfoModel orderInfo = new OrderInfoModel();
			orderInfo.setOrderId(orderId);
			orderInfo.setBuyId(String.valueOf(orderId));
			orderInfo.setOrderStatus(orderStatus);
			
			ServiceResult result =  orderInfoStubService.updateOrderStatus(orderInfo);
			if(result != null && ServiceResult.SUCCESS.equals(result.getCode())){
				return handleResult.setResult(true);
			}
		} catch (Exception e) {
			log.error("updateOrderStatus:{},orderId:{},exception: {}",errorMsg,orderId,e);
		}
		return handleResult.setCode(3).setMsg(errorMsg);
	}

	@Override
	public HandleResult getPrePayInfo(HttpServletRequest request,String userId,String addressId,String orderId,String remark, Integer payType) {
		HandleResult result = new HandleResult(false);
		
		if(orderId == null){
			return result.setCode(1).setMsg("订单编号不能为空");
		}
		
		if(payType == null){
			return result.setCode(1).setMsg("请选择支付类型");
		}
		
		List<Integer> typeList = Arrays.asList(1,2);
		if(!typeList.contains(payType)){
			return result.setCode(1).setMsg("请选择正确的支付类型");
		}
		
		OrderInfoQuery query = new OrderInfoQuery();
		query.setBuyId(userId);
		query.setOrderId(orderId);
		OrderInfoModel orderInfo = orderInfoStubService.findOneOrder(query);
		
		if(orderInfo == null){
			return result.setCode(1).setMsg("该订单信息不存在");
		}
		if(orderInfo.getOrderStatus() > EnumOrderStatus.UNPAID.getValue() && !orderInfo.getOrderStatus().equals(8)){
			return result.setCode(1000).setMsg("该订单已经支付完成");
		}
		
		if(orderInfo.getOrderType().equals(EnumOrderType.AUCTION.getValue())){
			Date orderCreateTime = orderInfo.getCreateTime();
			long countDown = orderCreateTime.getTime()/1000 + 259200 -System.currentTimeMillis()/1000;
			if(countDown <= 0){
				return result.setCode(2000).setMsg("订单支付超时，系统已关闭。");
			}
		}
		
		String batchNo = String.valueOf(snowflakePayBatchNo.next());
		
		if(orderInfo.getPaidMoney().doubleValue() == 0){
			
			HandleResult handleResult = createPaymentInfo(userId, orderId, payType,orderInfo, batchNo, batchNo);
			if(!handleResult.isResult()){
				return result.setCode(-1).setMsg("预支付失败");
			}
			PaymentInfoModel paymentInfoModel = new PaymentInfoModel();
			paymentInfoModel.setPayflag(EnumPaymentFlag.PAY.getValue());
			paymentInfoModel.setSerialNo(batchNo);
			Integer res = paymentStubService.updatePaymentInfoStatusSuc(paymentInfoModel);
			if(res != null && res == 1){
				return result.setCode(3000).setMsg("支付成功，请返回上一页面"); 
			}else{
				return result.setCode(1).setMsg("支付失败，请再次重试"); 
			}
		}
		
		if(addressId != null){
			OrderInfoModel model = new OrderInfoModel();
			model.setOrderId(orderId);
			UserShippingAddressModel userAddressModel = userShippingAddressStuService.findUserAddressItemByAddressId(Integer.valueOf(addressId));
			if(!userAddressModel.getStatus().equals(0)){
				return result.setCode(1).setMsg("该地址已失效，请重新选择");
			}
			if (null != userAddressModel) {
				 model.setProvinceCode(userAddressModel.getProvinceCode());
				 model.setCityCode(userAddressModel.getPostCode());
				 model.setDistrictCode(userAddressModel.getDistrictCode() == null ? null : userAddressModel.getDistrictCode().toString());
				 model.setTownCode(userAddressModel.getTownCode() == null ? null : userAddressModel.getTownCode().toString());
				 model.setAddress(userAddressModel.getAddress());
				 model.setProvinceName(userAddressModel.getProvinceName());
				 model.setDistrictName(userAddressModel.getDistrictName());
				 model.setTownName(userAddressModel.getTownName());
				 model.setCityName(userAddressModel.getCityName());
				 model.setUserName(userAddressModel.getUserName());
				 model.setUserPhone(userAddressModel.getUserPhone());
				 if(!StringUtils.hasText(remark)){
					 model.setRemark(remark);
				 }
				 ServiceResult serviceResult = orderInfoStubService.updateAddressByOrderId(model);
				 if(serviceResult == null || serviceResult.isFail()){
					 return result.setCode(-1).setMsg("预支付失败");
				 }
		    }
			
		}else{
			if(StringUtils.hasText(remark)){
				OrderInfoModel model = new OrderInfoModel();
				model.setOrderId(orderId);
				model.setRemark(remark);
				ServiceResult serviceResult = orderInfoStubService.updateAddressByOrderId(model);
				if(serviceResult == null || serviceResult.isFail()){
					return result.setCode(-1).setMsg("预支付失败");
				}
			}
		}
		
		
		JSONObject json = new JSONObject();
		json.put("payType", Integer.valueOf(payType));
		String outTradeNo = "";
		if(payType.equals(1)){//微信
			WeChatPayRequest weChatPayRequest = new WeChatPayRequest();
			weChatPayRequest.setBatchNo(batchNo);
			weChatPayRequest.setBody("开心拍卖订单支付");
			weChatPayRequest.setIp(HttpHelper.getIpAddr(request));
			weChatPayRequest.setMoney(orderInfo.getPaidMoney().multiply(new BigDecimal(100)).intValue());
			weChatPayRequest.setSubject("支付");
			weChatPayRequest.setPayFrom(EnumPayFrom.JPZF.getType());
			weChatPayRequest.setUserId(String.valueOf(userId));
			WeChatPayResponse weChatPayResponse = weChatPayStubService.toWeChatPay(weChatPayRequest);
			if(weChatPayResponse != null && weChatPayResponse.isSuccessed()){
				JSONObject wxPayBody= JSONObject.parseObject(weChatPayResponse.getPayBody());
				outTradeNo = wxPayBody.getString("prepayId");
				json.put("wechatPay", wxPayBody);
		        json.put("aliPay","");
			}else{
				 return result.setCode(-1).setMsg("预支付失败");
			}
		}
		if(payType.equals(2)){//支付宝
			AlipayPayRequest alipayPayRequest = new AlipayPayRequest();
			alipayPayRequest.setBatchNo(batchNo);
			alipayPayRequest.setBody("开心拍卖订单支付");
			alipayPayRequest.setMoney(orderInfo.getPaidMoney().multiply(new BigDecimal(100)).intValue());
			alipayPayRequest.setSubject("支付");
			alipayPayRequest.setPayFrom(EnumPayFrom.JPZF.getType());
			alipayPayRequest.setUserId(userId);
			AlipayPayResponse alipayPayResponse = alipayStubService.toAlipayPay(alipayPayRequest);
			log.info("AlipayPayResponse: {}",alipayPayResponse);
			if(alipayPayResponse.isSuccessed()){
				String paybody = alipayPayResponse.getPayBody();
				outTradeNo = alipayPayResponse.getOutTradeNo();
		        json.put("wechatPay", new Object());
		        json.put("aliPay",paybody);
			}else{
				 return result.setCode(-1).setMsg("预支付失败");
			}
		}
		
		HandleResult handleResult = createPaymentInfo(userId, orderId, payType,orderInfo, batchNo, outTradeNo);
		if(!handleResult.isResult()){
			return result.setCode(-1).setMsg("预支付失败");
		}
		return result.setResult(true).setData(json);
	}

	private HandleResult createPaymentInfo(String userId, String orderId, Integer payType,
			OrderInfoModel orderInfo, String batchNo, String outTradeNo) {
		HandleResult result = new HandleResult(false);
		UserBidModel userBidModel = auctionBidStubService.userBidInfo(Integer.valueOf(userId), orderInfo.getAuctionNo());
		PaymentInfoModel paymentInfoModel = new PaymentInfoModel();
		paymentInfoModel.setPaymentId(outTradeNo);
		paymentInfoModel.setSerialNo(batchNo);
		paymentInfoModel.setOrderId(orderId);
		paymentInfoModel.setUserId(Integer.valueOf(userId));
		paymentInfoModel.setPaymentAmount(userBidModel.getPaymentPrice());
		paymentInfoModel.setOrderAmount(userBidModel.getSalePrice());
		paymentInfoModel.setPaymentStatus(0);
		paymentInfoModel.setPayflag(EnumPaymentFlag.PAY.getValue());
		if(payType.equals(1)){//微信
			paymentInfoModel.setPaymentType(EnumPaymentType.WECHAT.getValue());
		}
		if(payType.equals(2)){//支付宝
			paymentInfoModel.setPaymentType(EnumPaymentType.ALIPAY.getValue());
		}
		Integer res = paymentStubService.createPaymentInfo(paymentInfoModel);
		if(res == null || res != 1){
			return result.setCode(-1).setMsg("预支付失败");
		}
		return result.setResult(true);
	}

	@Override
	public HandleResult getTransferDetail(String userId, String orderId) {
		HandleResult result = new HandleResult(false);
		if (orderId == null) {
			return result.setCode(1).setMsg("订单编号不能为空");
		}
		TransferOrderDetail detail = new TransferOrderDetail();
		try {
			OrderInfoQuery query = new OrderInfoQuery();
			query.setBuyId(userId);
			query.setOrderId(orderId);
			OrderInfoModel orderInfo = orderInfoStubService.findOneOrder(query);
			if(orderInfo == null){
				return result.setCode(1).setMsg("订单信息不存在");
			}
			detail = beanMapper.map(orderInfo, TransferOrderDetail.class);
			if(detail.getOrderStatus().equals(EnumOrderStatus.COMPLETE.getValue())){
				detail.setAppraisesTime(orderInfo.getUpdateTime());
			}
			
			UserBidModel userBidModel = auctionBidStubService.userBidInfo(Integer.valueOf(userId), orderInfo.getAuctionNo());
			detail.setProductPic(aliyunOssDomain + detail.getProductPic());
			detail.setWinnerName(Base64Utils.decodeStr(userBidModel.getUserName()));
			
			if(userBidModel.getReturnPrice().intValue() >= userBidModel.getSalePrice().intValue()){
				detail.setReturnPrice(new BigDecimal(userBidModel.getSalePrice().intValue()));
			}else{
				detail.setReturnPrice(new BigDecimal(userBidModel.getReturnPrice().intValue()));
			}
			
			if(orderInfo.getOrderType().equals(2)){
				if(userBidModel.getReturnPrice().intValue() >= userBidModel.getSalePrice().intValue()){
					detail.setFinalPrice(new BigDecimal(0));
				}else{
					detail.setFinalPrice(userBidModel.getSalePrice().subtract(userBidModel.getReturnPrice()));
				}
			}else{
				detail.setFinalPrice(orderInfo.getPaidMoney());
			}
			if(orderInfo.getLogisticsModel() != null){
				detail.setCompany(orderInfo.getLogisticsModel().getLogisticsName());
				detail.setExpressNo(orderInfo.getLogisticsModel().getLogisticsId());
			}
			return result.setResult(true).setData(detail);
		} catch (Exception e) {
			log.error("getTransferDetail error: {}",e);
		}
		return result;
	}

}
