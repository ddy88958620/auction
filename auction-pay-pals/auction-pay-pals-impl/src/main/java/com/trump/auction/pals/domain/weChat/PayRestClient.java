package com.trump.auction.pals.domain.weChat;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.trump.auction.pals.util.weChatPay.PaymentHelper;


public class PayRestClient {

	private static final String UNIFIED_ORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	private static final String QUERY_ORDER = "https://api.mch.weixin.qq.com/pay/orderquery";

	private static RestTemplate restTemplate = new RestTemplate();

	private static Logger logger = LoggerFactory.getLogger(PayRestClient.class);

	static {
	}

	public static Map<String, String> unifiedOrder(String requestBody) {

		ResponseEntity<String> res = restTemplate.postForEntity(UNIFIED_ORDER, requestBody, String.class);
		if (HttpStatus.OK.equals(res.getStatusCode())) {
			Map<String, String> resultMap = PaymentHelper.xmlToMap(res.getBody());
			String prepayId = resultMap.get("prepay_id");
			if (prepayId == null) {
				logger.error("invoke wechat unified order interface failed! response body is:" + res.getBody());
			}
			return resultMap;
		}
		else {
			logger.error("invoke wechat unified order interface failed! http status is :" + res.getStatusCode());
		}
		return null;
	}

	public static Map<String, String>  queryOrder(String requestBody) {

		ResponseEntity<String> res = restTemplate.postForEntity(QUERY_ORDER, requestBody, String.class);
		if (HttpStatus.OK.equals(res.getStatusCode())) {
			Map<String, String> resultMap = PaymentHelper.xmlToMap(res.getBody());
			return resultMap;
		}
		else {
			logger.error("invoke wechat unified order interface failed! http status is :" + res.getStatusCode());
		}
		return null;
	}

	public static void closeOrder() {
		throw new UnsupportedOperationException();
	}

	public static void refund() {
		throw new UnsupportedOperationException();
	}

	public static void queryRefund() {
		throw new UnsupportedOperationException();
	}

}
