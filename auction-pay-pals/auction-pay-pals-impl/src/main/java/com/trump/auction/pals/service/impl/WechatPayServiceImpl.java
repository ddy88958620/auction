package com.trump.auction.pals.service.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cf.common.utils.GenerateNo;
import com.trump.auction.pals.api.constant.EnumOrderType;
import com.trump.auction.pals.api.constant.EnumPayMode;
import com.trump.auction.pals.api.constant.EnumPayStatus;
import com.trump.auction.pals.api.constant.EnumPayType;
import com.trump.auction.pals.api.model.wechat.WeChatPayBackRequest;
import com.trump.auction.pals.api.model.wechat.WeChatPayBackResponse;
import com.trump.auction.pals.api.model.wechat.WeChatPayQueryRequest;
import com.trump.auction.pals.api.model.wechat.WeChatPayQueryResponse;
import com.trump.auction.pals.api.model.wechat.WeChatPayRequest;
import com.trump.auction.pals.api.model.wechat.WeChatPayResponse;
import com.trump.auction.pals.dao.PayRecordDao;
import com.trump.auction.pals.domain.PayRecord;
import com.trump.auction.pals.domain.ThirdPartyAsk;
import com.trump.auction.pals.domain.weChat.PayRequest;
import com.trump.auction.pals.domain.weChat.PayRequestBuilder;
import com.trump.auction.pals.domain.weChat.PayRestClient;
import com.trump.auction.pals.domain.weChat.QueryOrderBuilder;
import com.trump.auction.pals.domain.weChat.UnifiedOrderBuilder;
import com.trump.auction.pals.service.ThirdPartyAskService;
import com.trump.auction.pals.service.WeChatPayService;
import com.trump.auction.pals.util.WeChatPayConstant;
import com.trump.auction.pals.util.weChatPay.SecurityUtil;

import redis.clients.jedis.JedisCluster;

/**
 * 微信支付相关API
 */
@Service
public class WechatPayServiceImpl implements WeChatPayService{

    private Logger logger = LoggerFactory.getLogger(WechatPayServiceImpl.class);

    @Autowired
    private JedisCluster jedisCluster;
    
    @Autowired
    private PayRecordDao payRecordDao;

    @Autowired
    private ThirdPartyAskService partyAskService;
    
	@Override
	public WeChatPayResponse toWeChatPay(WeChatPayRequest apr) {
		
		WeChatPayResponse response = null;
		try {
			String appid = WeChatPayConstant.getAppId(jedisCluster);
			String mchId = WeChatPayConstant.getMchId(jedisCluster);
			String key = WeChatPayConstant.getWeChatKey(jedisCluster);
			String notifyUrl = WeChatPayConstant.getNotifyUrl(jedisCluster);
			
			String orderNo = GenerateNo.getInstance().payRecordNo16(EnumOrderType.WX.getType());
			logger.error("toWeChatPay orderNo:{}",orderNo);
			
			UnifiedOrderBuilder builder = new UnifiedOrderBuilder();
			builder.setAppId(appid);
			builder.setBody("竞拍微信支付");
			builder.setMchId(mchId);
			builder.setNonceStr(SecurityUtil.getRandomString(16));
			builder.setNotifyUrl(notifyUrl);
			builder.setOutTradeNo(orderNo);
			builder.setBillCreateIP(apr.getIp());
			builder.setTotalFee(apr.getMoney());
			//builder.setTotalFee(1);//测试
			builder.setTradeType("APP");
			builder.setKey(key);
			
			Map<String, String> map = PayRestClient.unifiedOrder(builder.build().toXml());
			
			String repay_id = map.get("prepay_id");
			
			PayRequestBuilder prBuilder = new PayRequestBuilder();
			prBuilder.setAppId(appid);
			
			prBuilder.setPartnerId(mchId);
			prBuilder.setPrepayId(repay_id);
			prBuilder.setNonceStr(SecurityUtil.getRandomString(16));
			prBuilder.setTimeStamp(String.valueOf(System.currentTimeMillis() / 1000));
			prBuilder.setKey(key);
			PayRequest payRequest = prBuilder.build();
			
			ThirdPartyAsk partyAsk = new ThirdPartyAsk();
			partyAsk.setUserId(apr.getUserId());
			partyAsk.setOrderType(EnumPayType.WECHAT.getType());
			partyAsk.setOrderNo(orderNo);
			partyAsk.setReqParams(builder.build().toXml());
			partyAsk.setNotifyParams(JSONObject.toJSONString(payRequest));
			partyAsk.setStatus(EnumPayStatus.STATUS_WAIT.getType());
			partyAsk.setAct(apr.getPayFrom());//用来标识 充值还是支付
			
			partyAskService.insert(partyAsk);
			
			PayRecord payRecord = new PayRecord();
			payRecord.setBatchNo(apr.getBatchNo());
			payRecord.setOrderNo(orderNo);
			payRecord.setPrepayId(repay_id);
			payRecord.setMerchantId(mchId);
			//payRecord.setPayAmount(apr.getMoney());
			payRecord.setPayAmount(1);//测试
			
			payRecord.setPayFrom(apr.getPayFrom());
			payRecord.setPayMode(EnumPayMode.ZB.getType());
			payRecord.setPayType(EnumPayType.WECHAT.getType());
			payRecord.setStatus(EnumPayStatus.STATUS_WAIT.getType());
			payRecord.setUserId(apr.getUserId());
			
			payRecordDao.insertSelective(payRecord);
			
			response = new WeChatPayResponse(WeChatPayResponse.SUCCESS, "支付成功", JSONObject.toJSONString(payRequest));
		} catch (Exception e) {
			logger.error("wechatPay happen error:{}",e);
			response = new WeChatPayResponse(WeChatPayResponse.FAILED, "支付失败");
		}
		return response;
	}

	@Override
	public WeChatPayBackResponse toWeChatPayBack(WeChatPayBackRequest abr) {
		
		Map<String,String> params = abr.getParams();
        logger.info("微信回调结果参数：{}",JSONObject.toJSONString(abr.getParams()));
		
		String orderNo = params.get("out_trade_no");
		String trade_status = params.get("return_code");
		String total_fee = params.get("total_fee");
		
        WeChatPayBackResponse response = new WeChatPayBackResponse(WeChatPayBackResponse.FAILED, "支付失败");
        
        try {
			if (StringUtils.isNotBlank(orderNo)) {
			    ThirdPartyAsk partyAsk = partyAskService.findByOrderNo(orderNo);
			    PayRecord payRecord = payRecordDao.selectByOrderNo(orderNo);
			    
			    if(null != payRecord){
			    	//验证金额
			    	if(total_fee.equals(payRecord.getPayAmount().toString())){
			    		
			    		if (null != partyAsk) {
			            	if(!EnumPayStatus.STATUS_SUC.getType().equals(partyAsk.getStatus())){
			            		ThirdPartyAsk partyAskNew = new ThirdPartyAsk();
			                    partyAskNew.setOrderNo(partyAsk.getOrderNo());
			                    
			                    if ("SUCCESS".equals(trade_status)) {
			                        partyAskNew.setStatus(EnumPayStatus.STATUS_SUC.getType());
			                        payRecord.setStatus(EnumPayStatus.STATUS_SUC.getType());
			                        response = new WeChatPayBackResponse(WeChatPayBackResponse.SUCCESS, "支付成功", payRecord.getBatchNo(), payRecord.getPayFrom());
			                    }
			                    else{
			                        partyAskNew.setStatus(EnumPayStatus.STATUS_FAILED.getType());
			                        payRecord.setStatus(EnumPayStatus.STATUS_FAILED.getType());
			                        response = new WeChatPayBackResponse(WeChatPayBackResponse.FAILED, "支付失败");
			                    }
			                    
			                    partyAskService.updateByOrderNo(partyAskNew);
			                    payRecordDao.updateByOrderNo(payRecord);
			            	}else{
			            		response = new WeChatPayBackResponse(WeChatPayBackResponse.SUCCESS, "该订单已支付成功过", payRecord.getBatchNo(), payRecord.getPayFrom());
			            	}
			            }
			    		
			    	}else{
			    		response = new WeChatPayBackResponse(WeChatPayBackResponse.FAILED, "该支付金额有误！");
			    	}
			    }else{
			    	response = new WeChatPayBackResponse(WeChatPayBackResponse.FAILED, "该支付信息有误！");
			    }
			}
		} catch (Exception e) {
			logger.error("toWeChatPayBack error orderNo:{}  e:{}",orderNo,e);
		}
        return response;
	}

	@Override
	public WeChatPayQueryResponse queryWeChatPay(WeChatPayQueryRequest wcp) {
		
		logger.error("queryWeChatPay start batchNo：{}", wcp.getBatchNo());
		
		String appid = WeChatPayConstant.getAppId(jedisCluster);
		String mchId = WeChatPayConstant.getMchId(jedisCluster);
		String key = WeChatPayConstant.getWeChatKey(jedisCluster);
		WeChatPayQueryResponse response = new WeChatPayQueryResponse(WeChatPayQueryResponse.FAILED, "支付失败");
		
		PayRecord payRecord = payRecordDao.selectByBatchNo(wcp.getBatchNo());
		
		QueryOrderBuilder builder = new QueryOrderBuilder();
		try {
			builder.setAppId(appid);
			builder.setMchId(mchId);
			builder.setOutTradeNo(payRecord.getOrderNo());
			builder.setNonceStr(SecurityUtil.getRandomString(16));
			builder.setKey(key);
			Map<String, String> map = PayRestClient.queryOrder(builder.build().toXml());
			
			String return_code = map.get("return_code");
			String return_msg = map.get("return_msg");
			String trade_state = map.get("trade_state");
			
			logger.error("batchNo：{} return_code:{} return_msg:{}  trade_state:{}",wcp.getBatchNo(),return_code,return_msg,trade_state);
			
			ThirdPartyAsk partyAsk = partyAskService.findByOrderNo(payRecord.getOrderNo());
			
			if("SUCCESS".equals(return_code)){
				if (null != partyAsk && !EnumPayStatus.STATUS_SUC.getType().equals(partyAsk.getStatus())) {
			         ThirdPartyAsk partyAskNew = new ThirdPartyAsk();
			         partyAskNew.setOrderNo(partyAsk.getOrderNo());
			         
			         if("SUCCESS".equals(trade_state)){
			        	 partyAskNew.setStatus(EnumPayStatus.STATUS_SUC.getType());
			        	 response = new WeChatPayQueryResponse(WeChatPayQueryResponse.SUCCESS, "支付成功");
			 		}else if("USERPAYING".equals(trade_state)){
			 			partyAskNew.setStatus(EnumPayStatus.STATUS_WAIT.getType());
			            response = new WeChatPayQueryResponse(WeChatPayQueryResponse.WAITING, "支付中");
			        }else{
			        	partyAskNew.setStatus(EnumPayStatus.STATUS_FAILED.getType());
			            response = new WeChatPayQueryResponse(WeChatPayQueryResponse.FAILED, "支付失败");
			        }
			         partyAskService.updateByOrderNo(partyAskNew);
				 }else{
			         response = new WeChatPayQueryResponse(WeChatPayQueryResponse.SUCCESS, "该订单已支付成功过");
			     }
			}else{
				 response = new WeChatPayQueryResponse(WeChatPayQueryResponse.FAILED, "请求微信失败");
				 logger.error("wx return_msg:{} BatchNo:{}",return_msg,wcp.getBatchNo());
			}
		} catch (Exception e) {
			logger.error("queryWeChatPay happen error orderNo:{} e:{}",payRecord.getOrderNo(),e);
		}
		return response;
	}

	@Override
	public String queryBatchNoByPrePayId(String prepayId) {
		return payRecordDao.queryBatchNoByPrePayId(prepayId);
	}
}

