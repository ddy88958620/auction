package com.trump.auction.pals.domain.weChat;

import java.util.SortedMap;
import java.util.TreeMap;

import com.trump.auction.pals.util.weChatPay.PaymentHelper;


/**
 * <p>
 * Title: PayRequest类的构建器。 通过一系列的setter方法设置所需参数，最后调用build()方法生成xml格式的请求体
 * </p>
 * <p>
 * Description: 
 * </p>
 * 
 * @author youlianlai
 * @date 2017年11月14日下午6:15:43
 */
public class PayRequestBuilder {

    private PayRequest payRequest;

    private String key;

    private SortedMap<String, String> paramMap = new TreeMap<String, String>();

    private static final String PARAM_APPID = "appid";
    private static final String PARAM_TIMESTAMP = "timestamp";
    private static final String PARAM_NONCE = "noncestr";
    private static final String PARAM_PACKAGE = "package";
    private static final String PARAM_PREPAY_ID = "prepayid";
    private static final String PARTNERID = "partnerid";

    public PayRequestBuilder() {
        super();
        payRequest = new PayRequest();
        paramMap.put(PARAM_PACKAGE, "Sign=WXPay");
        setNonceStr(PaymentHelper.generateNonce());
        setTimeStamp(String.valueOf(System.currentTimeMillis()));
    }

    public PayRequestBuilder setAppId(String appId) {
        paramMap.put(PARAM_APPID, appId);
        payRequest.setAppId(appId);
        return this;
    }


    public PayRequestBuilder setTimeStamp(String timeStamp) {
        paramMap.put(PARAM_TIMESTAMP, timeStamp);
        payRequest.setTimeStamp(timeStamp);
        return this;
    }

    public PayRequestBuilder setNonceStr(String nonceStr) {
        paramMap.put(PARAM_NONCE, nonceStr);
        payRequest.setNonceStr(nonceStr);
        return this;
    }

    public PayRequestBuilder setPrepayId(String prepayId) {
        paramMap.put(PARAM_PREPAY_ID, prepayId);
        payRequest.setPrepayId(prepayId);
        return this;
    }
    
    public PayRequestBuilder setKey(String key) {
        this.key = key;
        return this;
    }

    public PayRequestBuilder setPartnerId(String partnerId) {
    	paramMap.put(PARTNERID, partnerId);
    	//  paramMap.put(PARAM_PACKAGE, pkg);
        payRequest.setPartnerId(partnerId);
        return this;
    }


    public PayRequest build() {
        // check required param
        checkParam();

        // sign
        payRequest.setPaySign(PaymentHelper.sign(paramMap, key));

        return payRequest;
    }

    private void checkParam() {
        StringBuilder errorMsg = new StringBuilder();
        if (paramMap.get(PARAM_APPID) == null)
            errorMsg.append(PARAM_APPID).append(",");
        if (paramMap.get(PARAM_PREPAY_ID) == null)
            errorMsg.append(PARAM_PREPAY_ID).append(",");
        if (this.key == null)
            errorMsg.append("key,");
        if (errorMsg.length() > 0) {
            errorMsg.deleteCharAt(errorMsg.length() - 1);
            throw new IllegalStateException(errorMsg.append(" must not be null").toString());
        }
    }

    
}
