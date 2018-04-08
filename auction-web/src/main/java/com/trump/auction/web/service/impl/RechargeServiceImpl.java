package com.trump.auction.web.service.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cf.common.id.IdGenerator;
import com.cf.common.utils.ServiceResult;
import com.trump.auction.account.api.AccountInfoStubService;
import com.trump.auction.account.enums.EnumTransactionTag;
import com.trump.auction.cust.api.UserInfoStubService;
import com.trump.auction.cust.model.UserInfoModel;
import com.trump.auction.order.api.PaymentStubService;
import com.trump.auction.order.enums.EnumPaymentFlag;
import com.trump.auction.order.enums.EnumPaymentType;
import com.trump.auction.order.model.PaymentInfoModel;
import com.trump.auction.pals.api.AlipayStubService;
import com.trump.auction.pals.api.WeChatPayStubService;
import com.trump.auction.pals.api.constant.EnumPayFrom;
import com.trump.auction.pals.api.model.alipay.AlipayPayRequest;
import com.trump.auction.pals.api.model.alipay.AlipayPayResponse;
import com.trump.auction.pals.api.model.wechat.WeChatPayRequest;
import com.trump.auction.pals.api.model.wechat.WeChatPayResponse;
import com.trump.auction.web.service.RechargeService;
import com.trump.auction.web.util.HandleResult;
import com.trump.auction.web.util.HttpHelper;
import com.trump.auction.web.vo.UserSupport;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by songruihuan on 2017/12/21.
 */
@Slf4j
@Service
public class RechargeServiceImpl implements RechargeService {

	@Autowired
	private AccountInfoStubService accountInfoStubService;
	
	@Autowired
	private UserInfoStubService userInfoStubService;
    
    @Autowired
    private AlipayStubService alipayStubService;
    
    @Autowired
    private WeChatPayStubService weChatPayStubService;
    
    @Autowired
    private PaymentStubService paymentStubService;
    
    @Autowired
    private IdGenerator snowflakePayBatchNo;
	
	
	@Override
	public HandleResult prePay(HttpServletRequest request,Integer userId, Integer money, String payType) {
		HandleResult result = new HandleResult(false);
		
		if(!checkPrePayParams(money, payType, result)){
			return result;
		}
		Integer rechargeType = 2;
		payType = payType == null? "1":payType;
		
		String batchNo = String.valueOf(snowflakePayBatchNo.next());
		if("1".equals(payType)){//微信
			rechargeType = EnumTransactionTag.TRANSACTION_WX_RECHARGE.getKey();
		}else{//支付宝
			rechargeType = EnumTransactionTag.TRANSACTION_ALI_RECHARGE.getKey();
		}

		try {
			UserInfoModel userInfoModel = userInfoStubService.findUserInfoById(userId);
			String userName = UserSupport.getBase64UserNameByLoginType(userInfoModel);
			
			ServiceResult serviceResultesult = accountInfoStubService.createAccountRechargeOrder(userId, userName,
					userInfoModel.getUserPhone(), new BigDecimal(money), rechargeType,batchNo);
			
			if(serviceResultesult == null ||  serviceResultesult.isFail()){
				return result.setCode(1).setMsg("预支付失败");
			}
			
			String orderId = (String) serviceResultesult.getExt();
			JSONObject json = new JSONObject(); 
			json.put("payType", Integer.valueOf(payType));
			String outTradeNo = "";
			if("1".equals(payType)){
				
				WeChatPayRequest weChatPayRequest = new WeChatPayRequest();
				weChatPayRequest.setBatchNo(batchNo);
				weChatPayRequest.setBody("开心拍卖充值");
				weChatPayRequest.setIp(HttpHelper.getIpAddr(request));
				weChatPayRequest.setMoney(money * 100);
				weChatPayRequest.setSubject("充值");
				weChatPayRequest.setPayFrom(EnumPayFrom.JPCZ.getType());
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
				
			}else{
				
				AlipayPayRequest alipayPayRequest = new AlipayPayRequest();
				alipayPayRequest.setBatchNo(batchNo);
				alipayPayRequest.setBody("开心拍卖充值");
				alipayPayRequest.setMoney(money * 100);
				alipayPayRequest.setSubject("充值");
				alipayPayRequest.setPayFrom(EnumPayFrom.JPCZ.getType());
				alipayPayRequest.setUserId(userId.toString());
				AlipayPayResponse alipayPayResponse = alipayStubService.toAlipayPay(alipayPayRequest);
				log.info("AlipayPayResponse: {}",alipayPayResponse);
				if(alipayPayResponse != null && alipayPayResponse.isSuccessed()){
					String paybody = alipayPayResponse.getPayBody();
					outTradeNo = alipayPayResponse.getOutTradeNo();
			        json.put("wechatPay", new Object());
			        json.put("aliPay",paybody);
				}else{
					return result.setCode(-1).setMsg("预支付失败");
				}
			}
			
			PaymentInfoModel paymentInfoModel = new PaymentInfoModel();
			paymentInfoModel = new PaymentInfoModel();
			paymentInfoModel.setPaymentId(outTradeNo);
			paymentInfoModel.setSerialNo(batchNo);
			paymentInfoModel.setOrderId(orderId);
			paymentInfoModel.setUserId(Integer.valueOf(userId));
			paymentInfoModel.setPaymentAmount(new BigDecimal(money));
			paymentInfoModel.setOrderAmount(new BigDecimal(money));
			paymentInfoModel.setPaymentStatus(0);
			paymentInfoModel.setPayflag(EnumPaymentFlag.RECHARGE.getValue());
			if(payType.equals("1")){//微信
				paymentInfoModel.setPaymentType(EnumPaymentType.WECHAT.getValue());
			}
			if(payType.equals("2")){//支付宝
				paymentInfoModel.setPaymentType(EnumPaymentType.ALIPAY.getValue());
			}
			
			Integer res = paymentStubService.createPaymentInfo(paymentInfoModel);
			if(res == null || res != 1){
				return result.setCode(-1).setMsg("预支付失败");
			}
			return result.setResult(true).setData(json);
		} catch (Exception e) {
			log.error("prePay error: {}",e);
		}
		return result.setCode(-1).setMsg("预支付失败");
	}

	

	/**
	 * <p>
	 * Title: 检查预支付参数
	 * </p>
	 * <p>
	 * Description: 
	 * </p>
	 * @param money
	 * @param payType
	 * @param result
	 * @return
	 */
	private boolean checkPrePayParams(Integer money, String payType, HandleResult result) {
		if(money == null){
			result.setCode(1).setMsg("money不能为空");
			return false;
		}
		
		if(money <= 0){
			result.setCode(1).setMsg("充值金额要大于0");
			return false;
		}

		if(money >=0 && money <10 ){
			result.setCode(1).setMsg("充值金额最低10元");
			return false;
		}
		
		if(money > 100000){
			result.setCode(1).setMsg("最多可充值10万元整");
			return false;
		}
		
		List<String> typeList = Arrays.asList("1","2");
		if(!typeList.contains(payType)){
			result.setCode(1).setMsg("请选择正确的充值方式");
			return false;
		}
		
		return true;
	}


}
