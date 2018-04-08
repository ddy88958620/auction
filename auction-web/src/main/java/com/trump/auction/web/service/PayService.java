package com.trump.auction.web.service;

import java.util.Map;

import com.trump.auction.web.util.HandleResult;

public interface PayService {


	/**
	 * <p>
	 * Title: 主动查询是否支付成功
	 * </p>
	 * <p>
	 * Description: 
	 * </p>
	 * @param outTradeNo
	 * @param type
	 * @return
	 */
	HandleResult isPaySuccess(String outTradeNo, String type);


	/**
	 * <p>
	 * Title: 微信支付回调
	 * </p>
	 * <p>
	 * Description: 
	 * </p>
	 * @param map
	 * @param body
	 * @return
	 */
	HandleResult handeWechatPayBack(Map<String, String> map, String body);

	/**
	 * <p>
	 * Title: 支付宝支付回调
	 * </p>
	 * <p>
	 * Description: 
	 * </p>
	 * @param params
	 * @param dataJson
	 * @return
	 */
	HandleResult handleAlipayBack(Map<String, String> params, String dataJson);


//	HandleResult testToPay(String bathNo,String type);


}
