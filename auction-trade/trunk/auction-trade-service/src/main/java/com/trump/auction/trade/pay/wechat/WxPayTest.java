package com.trump.auction.trade.pay.wechat;

import java.util.Map;

import com.trump.auction.trade.model.PayRequest;



public class WxPayTest {
	
 
	public static  void main(String[] args) {
		
		String outTradeNo = "";
		String realIp = "";
		
		Integer totalFee = 100;
		
		String appid = "";//jedisCluster.get("WX_PAY_APPID");
		String mchId = "";//jedisCluster.get("WX_PAY_MCHID");
		String key = "";//jedisCluster.get("WX_PAY_KEY");
		String notifyUrl = "";//jedisCluster.get("WX_PAY_CALLBACK_URL");
		
		UnifiedOrderBuilder builder = new UnifiedOrderBuilder();
        builder.setAppId(appid);
        builder.setBody("企鹅抓娃娃游戏币充值");
        builder.setMchId(mchId);
        builder.setNonceStr(SecurityUtil.getRandomString(16));
        builder.setNotifyUrl(notifyUrl);
        builder.setOutTradeNo(outTradeNo);
        builder.setBillCreateIP(realIp);
        builder.setTotalFee(totalFee);
        builder.setTradeType("APP");
        builder.setKey(key);
        
        Map<String, String> map = PayRestClient.unifiedOrder(builder.build().toXml());
        
        //outTradeNo repay_id 
        //入库，并且提供接口  根据repay_id查询订单是否支付成功的接口
        String repay_id = map.get("prepay_id");
        
        PayRequestBuilder prBuilder = new PayRequestBuilder();
		prBuilder.setAppId(appid);
		
		prBuilder.setPartnerId(mchId);
		prBuilder.setPrepayId(repay_id);
		prBuilder.setNonceStr(SecurityUtil.getRandomString(16));
		prBuilder.setTimeStamp(String.valueOf(System.currentTimeMillis() / 1000));
		prBuilder.setKey(key);
		//PayRequest payRequest = prBuilder.build();
		

        
        
	}
}
