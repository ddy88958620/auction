package com.trump.auction.web.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.trump.auction.web.service.PayService;
import com.trump.auction.web.util.HandleResult;
import com.trump.auction.web.util.JsonView;
import com.trump.auction.web.util.PaymentHelper;
import com.trump.auction.web.util.SpringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * Title: 支付相关回调，check是否支付成功
 * </p>
 * 
 * @author youlianlai
 * @date 2017年12月26日下午7:36:13
 */
@Slf4j
@Controller
@RequestMapping("pay/")
public class PayController {
	
	@Autowired
	PayService payService;
	
	/**
	 * <p>
	 * Title: 处理微信回调
	 * </p>
	 * <p>
	 * Description: 1.验签，2验签成功后，开始业务处理
	 * </p>
	 * @param body
	 * @return
	 */
	@RequestMapping(value="notify/wxpay",method = RequestMethod.POST)
	@ResponseBody
	public String handleWxCallBack(@RequestBody String body) {
		Map<String, String> map = PaymentHelper.xmlToMap(body);
		log.info("微信支付回调参数: {}",body);
		try {
			HandleResult handleResult = payService.handeWechatPayBack(map,body);
			if(handleResult.isResult()){
				return setXML("SUCCESS", "OK");
			}
		} catch (Exception e) {
			log.error("handleWxCallBack error:{}",e);
			return setXML("ERROR", "");
		}
		return setXML("ERROR", "");
	}
	
	/**
	 * <p>
	 * Title: 处理支付宝回调
	 * </p>
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="notify/alipay",method = RequestMethod.POST)
	public void handleAlipayCallBack(HttpServletRequest request,HttpServletResponse response, @RequestBody String dataJson) throws UnsupportedEncodingException {
		Map<String,String> params = new HashMap<String,String>();
		Map<?, ?> requestParams = request.getParameterMap();
		for (Iterator<?> iter = requestParams.keySet().iterator(); iter.hasNext();) {
		    String name = (String) iter.next();
		    String[] values = (String[]) requestParams.get(name);
		    String valueStr = "";
		    for (int i = 0; i < values.length; i++) {
		        valueStr = (i == values.length - 1) ? valueStr + values[i]
		                    : valueStr + values[i] + ",";
		  	}
		    //乱码解决，这段代码在出现乱码时使用。
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		log.info("支付宝回调结果参数：{}",JSONObject.toJSONString(params));
		
		try {
			HandleResult result = payService.handleAlipayBack(params,dataJson);
			if(result.isResult()){
				SpringUtils.renderJson(response, "success");
				return;
			}
		} catch (Exception e) {
			SpringUtils.renderJson(response, "fail");
			return;
		}
		SpringUtils.renderJson(response, "fail");
	}
	
	


	/**
	 * @Description：返回给微信的参数
	 * @param return_code 返回编码
	 * @param return_msg 返回信息
	 * @return
	 */
	private static String setXML(String return_code, String return_msg) {
		return "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA[" + return_msg
				+ "]]></return_msg></xml>";
	}
	
	
	/**
	 * <p>
	 * Title: 是否支付成功
	 * </p>
	 * @param request
	 * @param response
	 * @param outTradeNo：微信支付的话，传prepay_id，支付宝传out_trade_no
	 */
	@RequestMapping("isSuccess")
	public void isSuccess(HttpServletRequest request, HttpServletResponse response, String outTradeNo,String type) {
		Integer result = 0;
		try {
			//AH
			//WeChatPayStubService.queryBatchNoByOrderNo(outTradeNo);
			HandleResult ispay= payService.isPaySuccess(outTradeNo,type);
			if(ispay.isResult()){
				result = 1;
			}
		} catch (Exception e) {
			log.error("isSuccess error:{}",e);
		}
		JSONObject json = new JSONObject();
		json.put("result",result);
		log.info("isSuccess outTradeNo:{}  json:{}",outTradeNo,json);
		SpringUtils.renderJson(response, JsonView.build(0, "success",json));
	}
}
