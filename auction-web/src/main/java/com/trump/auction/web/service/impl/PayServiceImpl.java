package com.trump.auction.web.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.cf.common.utils.ServiceResult;
import com.trump.auction.account.api.AccountInfoStubService;
import com.trump.auction.order.api.PaymentStubService;
import com.trump.auction.order.enums.EnumPaymentFlag;
import com.trump.auction.order.model.PaymentInfoModel;
import com.trump.auction.pals.api.AlipayStubService;
import com.trump.auction.pals.api.WeChatPayStubService;
import com.trump.auction.pals.api.constant.EnumPayFrom;
import com.trump.auction.pals.api.model.alipay.AlipayBackRequest;
import com.trump.auction.pals.api.model.alipay.AlipayBackResponse;
import com.trump.auction.pals.api.model.wechat.WeChatPayBackRequest;
import com.trump.auction.pals.api.model.wechat.WeChatPayBackResponse;
import com.trump.auction.web.service.PayService;
import com.trump.auction.web.util.HandleResult;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisCluster;

@Slf4j
@Service
public class PayServiceImpl implements PayService {

	@Autowired
	JedisCluster jedisCluster;

	@Autowired
	private AccountInfoStubService accountInfoStubService;

	@Autowired
	private AlipayStubService alipayStubService;

	@Autowired
	private PaymentStubService paymentStubService;

	@Autowired
	private WeChatPayStubService weChatPayStubService;



	@Override
	public HandleResult isPaySuccess(String outTradeNo,String type) {

		log.info("isPaySuccess params: {}, {}",outTradeNo,type);

		HandleResult result = new HandleResult(false);

		if(!StringUtils.hasText(outTradeNo)){
			return result.setCode(1).setMsg("outTradeNo 不能为空");
		}
		if(!StringUtils.hasText(type)){
			return result.setCode(1).setMsg("type 不能为空");
		}

		String batchNo = "";
		if("1".equals(type)){//微信
			batchNo = weChatPayStubService.queryBatchNoByPrePayId(outTradeNo);

		}else{
			batchNo = alipayStubService.queryBatchNoByOrderNo(outTradeNo);
		}
		log.info("isPaySuccess batchNo: ",batchNo);
		Boolean res = paymentStubService.queryIsPaidByBatchNo(batchNo);
		return result.setResult(res);
	}


	@Override
	public HandleResult handeWechatPayBack(Map<String, String> map, String body) {
		HandleResult result = new HandleResult(false);

		WeChatPayBackRequest weChatPayBackRequest = new WeChatPayBackRequest();
		weChatPayBackRequest.setParams(map);
		weChatPayBackRequest.setDataJson(body);

		try {

			WeChatPayBackResponse weChatPayBackResponse = weChatPayStubService.toWeChatPayBack(weChatPayBackRequest);
			log.info("weChatPayBackResponse result:{}",weChatPayBackResponse);
			if(weChatPayBackResponse != null && weChatPayBackResponse.isSuccessed()){
				String batchNo = weChatPayBackResponse.getBatchNo();

				if(weChatPayBackResponse.getPayFrom().equals(EnumPayFrom.JPCZ.getType())){
					log.info("rechargeCallback start:{}",batchNo);
					return rechargeCallback(body, batchNo);
				}

				if(weChatPayBackResponse.getPayFrom().equals(EnumPayFrom.JPZF.getType())){
					return ordrePayCallback(batchNo);
				}
			}

		} catch (Exception e) {
			log.error("PayServiceImpl handeWechatPayBack : {}",e);
		}
		return result;
	}




	@Override
	public HandleResult handleAlipayBack(Map<String, String> params, String dataJson) {
		HandleResult result = new HandleResult(false);

		AlipayBackRequest alipayBackRequest = new AlipayBackRequest();
		alipayBackRequest.setDataJson(dataJson);
		alipayBackRequest.setParams(params);

		try {
			AlipayBackResponse alipayBackResponse = alipayStubService.toAlipayBack(alipayBackRequest);
			if(alipayBackResponse != null && alipayBackResponse.isSuccessed()){
				String batchNo = alipayBackResponse.getBatchNo();

				if(alipayBackResponse.getPayFrom().equals(EnumPayFrom.JPCZ.getType())){
					return rechargeCallback(dataJson, batchNo);
				}

				if(alipayBackResponse.getPayFrom().equals(EnumPayFrom.JPZF.getType())){
					return ordrePayCallback(batchNo);
				}
			}
		} catch (Exception e) {
			log.error("PayServiceImpl handleAlipayBack : {}",e);
		}

		return result;
	}

	/**
	 * <p>
	 * Title: 充值回调
	 * </p>
	 * <p>
	 * Description: 
	 * </p>
	 * @param dataJson
	 * @param batchNo
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	private HandleResult rechargeCallback(String dataJson, String batchNo) {
		HandleResult result = new HandleResult(false);

		Boolean isPay = paymentStubService.queryIsPaidByBatchNo(batchNo);
		if(isPay){
			return result.setResult(true);
		}
		log.info("rechargeCallback continue:{}",batchNo);
		PaymentInfoModel paymentInfoModel = new PaymentInfoModel();
		paymentInfoModel.setSerialNo(batchNo);
		Integer res = paymentStubService.updatePaymentInfoStatusSuc(paymentInfoModel);
		if(res != null && res == 1){
			ServiceResult ServiceResult = accountInfoStubService.rechargeUserAccount(true, batchNo, dataJson);
			log.info("rechargeUserAccount result:{}",ServiceResult);
			if(ServiceResult != null && ServiceResult.isSuccessed()){
				return result.setResult(true);
			}else{
				throw new RuntimeException("充值回调失败：batchNo："+batchNo);
			}
		}
		return result;
	}

	/**
	 * <p>
	 * Title: 订单支付回调
	 * </p>
	 * <p>
	 * Description: 
	 * </p>
	 * @param batchNo
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	private HandleResult ordrePayCallback(String batchNo) {
		HandleResult result = new HandleResult(false);

		Boolean isPay = paymentStubService.queryIsPaidByBatchNo(batchNo);
		if(isPay){
			return result.setResult(true);
		}

		PaymentInfoModel paymentInfoModel = new PaymentInfoModel();
		paymentInfoModel.setPayflag(EnumPaymentFlag.PAY.getValue());
		paymentInfoModel.setSerialNo(batchNo);
		Integer res = paymentStubService.updatePaymentInfoStatusSuc(paymentInfoModel);

		if(res != null && res == 1){
			return result.setResult(true);
		}else{
			throw new RuntimeException("充值回调失败：batchNo："+batchNo);
		}

	}


//	@Override
//	public HandleResult testToPay(String bathNo,String type) {
//		if("1".equals(type)){
//			return rechargeCallback("{}", bathNo);
//		}else if("2".equals(type)){
//			return ordrePayCallback(bathNo);
//		}
//		return new HandleResult(false);
//	}

}
