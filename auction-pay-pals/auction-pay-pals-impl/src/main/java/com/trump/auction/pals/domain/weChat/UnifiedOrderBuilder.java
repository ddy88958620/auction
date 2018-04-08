package com.trump.auction.pals.domain.weChat;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.trump.auction.pals.util.weChatPay.PaymentHelper;

/**
 * <p>
 * Title: 统一下单的request body的构建器。 通过一系列的setter方法设置所需参数，最后调用build()方法生成xml格式的请求体
 * </p>
 * <p>
 * Description: 
 * </p>
 * 
 * @author youlianlai
 * @date 2017年11月14日下午6:12:26
 */
public class UnifiedOrderBuilder {

    private SortedMap<String, String> paramMap = new TreeMap<String, String>();

    private UnifiedOrder unifiedOrder;

    private String key;

    public static final String DEFAULT_TRADE_TYPE = "APP";

    public static final String DEFAULT_DEVICE_INFO = "WEB";

    public static final String DEFAULT_FEE_TYPE = "CNY";

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");

    public static final String PARAM_APPID = "appid";//1
    public static final String PARAM_MCHID = "mch_id";//1
    public static final String PARAM_DEVICE_INFO = "device_info";//1
    public static final String PARAM_NONCE = "nonce_str";//1
    public static final String PARAM_SIGN = "sign";//1
    public static final String PARAM_BODY = "body";//1
    public static final String PARAM_DETAIL = "detail";//1
    public static final String PARAM_ATTACH = "attach";//1
    public static final String PARAM_OUT_TRADE_NO = "out_trade_no";//1
    public static final String PARAM_FEE_TYPE = "fee_type";//1
    public static final String PARAM_TOTAL_FEE = "total_fee";//1
    public static final String PARAM_BILL_CREATE_IP = "spbill_create_ip";//1
    public static final String PARAM_TIME_START = "time_start";//1
    public static final String PARAM_TIME_EXPIRE = "time_expire";//1
    public static final String PARAM_GOODS_TAG = "goods_tag";//1
    public static final String PARAM_NOTIFY_URL = "notify_url";//1
    public static final String PARAM_TRADE_TYPE = "trade_type";//1

    private static final List<String> required = Arrays.asList(PARAM_APPID, PARAM_MCHID, PARAM_NONCE, PARAM_BODY, PARAM_OUT_TRADE_NO, PARAM_TOTAL_FEE,
            PARAM_BILL_CREATE_IP, PARAM_NOTIFY_URL, PARAM_TRADE_TYPE);

    public UnifiedOrderBuilder() {
        super();

        unifiedOrder = new UnifiedOrder();
        // 设置默认值

        setTradeType(DEFAULT_TRADE_TYPE);
        setDeviceInfo(DEFAULT_DEVICE_INFO);
        // setFeeType(DEFAULT_FEE_TYPE);
        setNonceStr(PaymentHelper.generateNonce());
        Date now = Calendar.getInstance().getTime();
        Calendar expiredC = Calendar.getInstance();
        expiredC.setTimeInMillis(now.getTime() + 1000 * 60 * 60 * 24);

        setTimeStart(DATE_FORMAT.format(now));
        setTimeExpire(DATE_FORMAT.format(expiredC.getTime()));
    }

    /**
     * 最终构建统一下单的请求参数
     * 
     * @return
     */
    public UnifiedOrder build() {

        // 检查必填参数
        checkParam();

        // 生成参数签名
        String sign = PaymentHelper.sign(paramMap, key);
        paramMap.put(PARAM_SIGN, sign);
        unifiedOrder.setSign(sign);

        // 构造xml请求
        try {
            unifiedOrder.setResultXml(new String(PaymentHelper.mapToXml(paramMap).getBytes(), "ISO-8859-1"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return unifiedOrder;

    }

    private void checkParam() {

        StringBuilder errorMsg = new StringBuilder();
        // check 所有交易类型必需的字段
        for (String param : required) {

            if (paramMap.get(param) == null)
                errorMsg.append(param).append(",");

        }

        // 检查是否设置了key
        if (this.key == null) {
            errorMsg.append("key,");
        }
        // 不满足条件，抛出IllegalStateException异常
        if (errorMsg.length() > 0) {
            errorMsg.deleteCharAt(errorMsg.length() - 1);
            errorMsg.append(" must no be null!");
            throw new IllegalStateException(errorMsg.toString());
        }

    }

    public UnifiedOrderBuilder setKey(String key) {
        Assert.isTrue(StringUtils.hasLength(key), "key must be not null");

        this.key = key;
        return this;
    }

    /**
     * 设置公众账号ID
     * 
     * 调用build()前必须调用
     * 
     * @param appId
     * @return
     */
    public UnifiedOrderBuilder setAppId(String appId) {
        Assert.isTrue(StringUtils.hasLength(appId) && appId.length() < 33, "appid must be not null and within 32 characters");

        paramMap.put(PARAM_APPID, appId);
        unifiedOrder.setAppId(appId);
        return this;
    }

    /**
     * 设置微信支付分配的商户号
     * 
     * 调用build()前必须调用
     * 
     * @param mchId
     * @return
     */
    public UnifiedOrderBuilder setMchId(String mchId) {

        Assert.isTrue(StringUtils.hasLength(mchId) && mchId.length() < 33, "mch_id must be not null and within 32 characters");

        paramMap.put(PARAM_MCHID, mchId);
        unifiedOrder.setMchId(mchId);
        return this;
    }

    /**
     * 设置小于32位的随机字符串
     * 
     * 调用build()前必须调用
     * 
     * @param nonceStr
     * @return
     */
    public UnifiedOrderBuilder setNonceStr(String nonceStr) {

        Assert.isTrue(StringUtils.hasLength(nonceStr) && nonceStr.length() < 33, "nonce_str must be not null and within 32 characters");

        paramMap.put(PARAM_NONCE, nonceStr);
        unifiedOrder.setNonceStr(nonceStr);
        return this;
    }

    /**
     * 设置商品描述
     * 
     * 调用build()前必须调用
     * 
     * @param body
     * @return
     */
    public UnifiedOrderBuilder setBody(String body) {

        Assert.isTrue(StringUtils.hasLength(body) && body.length() < 33, "body must be not null and within 32 characters");

        paramMap.put(PARAM_BODY, body);
        unifiedOrder.setBody(body);
        return this;
    }

    /**
     * 设置小于32位的商户订单号
     * 
     * 调用build()前必须调用
     * 
     * @param outTradeNo
     * @return
     */
    public UnifiedOrderBuilder setOutTradeNo(String outTradeNo) {

        Assert.isTrue(StringUtils.hasLength(outTradeNo) && outTradeNo.length() < 33, "out_trade_no must be not null and within 32 characters");

        paramMap.put(PARAM_OUT_TRADE_NO, outTradeNo);
        unifiedOrder.setOutTradeNo(outTradeNo);
        return this;
    }

    /**
     * 设置订单总金额，单位为分
     * 
     * 调用build()前必须调用
     * 
     * @param totalFee
     * @return
     */
    public UnifiedOrderBuilder setTotalFee(Integer totalFee) {

        Assert.isTrue(totalFee != null && totalFee > 0, "total_fee must be greater than zero");

        paramMap.put(PARAM_TOTAL_FEE, String.valueOf(totalFee));
        unifiedOrder.setTotalFee(totalFee);
        return this;
    }

    /**
     * 设置支付终端的IP，APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP
     * 
     * 调用build()前必须调用
     * 
     * @param ip
     * @return
     */
    public UnifiedOrderBuilder setBillCreateIP(String ip) {

        Assert.isTrue(StringUtils.hasLength(ip) && ip.length() < 17, "spbill_create_ip must be not null and within 16 characters");

        paramMap.put(PARAM_BILL_CREATE_IP, ip);
        unifiedOrder.setCreateIp(ip);
        return this;
    }

    /**
     * 设置微信支付异步通知的回调地址
     * 
     * 调用build()前必须调用
     * 
     * @param notifyUrl
     * @return
     */
    public UnifiedOrderBuilder setNotifyUrl(String notifyUrl) {

        Assert.isTrue(StringUtils.hasLength(notifyUrl) && notifyUrl.length() < 256, "spbill_create_ip must be not null and within 256 characters");

        paramMap.put(PARAM_NOTIFY_URL, notifyUrl);
        unifiedOrder.setNotifyUrl(notifyUrl);
        return this;
    }

    /**
     * 设置交易类型，取值如下：JSAPI，NATIVE，APP，WAP
     * 
     * 默认设置为：JSAPI
     * 
     * @param tradeType
     * @return
     */
    public UnifiedOrderBuilder setTradeType(String tradeType) {

        Assert.isTrue(Arrays.asList("JSAPI", "NATIVE", "APP", "WAP").contains(tradeType), "trade_type must be one of JSAPI,NATIVE,APP,WAP");

        paramMap.put(PARAM_TRADE_TYPE, tradeType);
        unifiedOrder.setTradeType(tradeType);
        return this;
    }


    /* =================以下为可选项=========================== */

    /**
     * 设置终端设备号，PC网页或公众号内支付传"WEB"
     * 
     * 默认设置为“WEB”
     * 
     * @param deviceInfo
     * @return
     */
    public UnifiedOrderBuilder setDeviceInfo(String deviceInfo) {

        Assert.isTrue(StringUtils.hasLength(deviceInfo) && deviceInfo.length() < 33,
                "device_info must be not null and within 32 characters if you invoke the setter");

        paramMap.put(PARAM_DEVICE_INFO, deviceInfo);
        unifiedOrder.setDeviceInfo(deviceInfo);
        return this;
    }

    /**
     * 设置商品详情
     * 
     * 默认不设置
     * 
     * @param detail
     * @return
     */
    public UnifiedOrderBuilder setDetail(String detail) {

        Assert.isTrue(StringUtils.hasLength(detail), "detail must be not null if you invoke the setter");

        paramMap.put(PARAM_DETAIL, detail);
        unifiedOrder.setDetail(detail);
        return this;
    }

    /**
     * 设置支付的附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
     * 
     * 默认不设置
     * 
     * @param attach
     * @return
     */
    public UnifiedOrderBuilder setAttach(String attach) {

        Assert.isTrue(StringUtils.hasLength(attach) && attach.length() < 128,
                "attach must be not null and within 127 characters if you invoke the setter");

        paramMap.put(PARAM_ATTACH, attach);
        unifiedOrder.setAttach(attach);
        return this;
    }

    /**
     * 设置支付的货币类型，符合ISO 4217标准的三位字母代码
     * 
     * 默认设置为“CNY”
     * 
     * @param feeType
     * @return
     */
    public UnifiedOrderBuilder setFeeType(String feeType) {

        Assert.isTrue(StringUtils.hasLength(feeType) && feeType.length() == 3, "the length of fee_type must be 3 if you invoke the setter");

        paramMap.put(PARAM_FEE_TYPE, feeType);
        unifiedOrder.setFeeType(feeType);
        return this;
    }

    /**
     * 设置交易起始时间
     * 
     * 默认设置为当前时间
     * 
     * @param timeStart
     * @return
     */
    public UnifiedOrderBuilder setTimeStart(String timeStart) {

        Assert.isTrue(StringUtils.hasLength(timeStart), "time_start must be not null if you invoke the setter");

        paramMap.put(PARAM_TIME_START, timeStart);
        unifiedOrder.setTimeStart(timeStart);
        return this;
    }

    /**
     * 设置交易的结束时间
     * 
     * 默认设置为起始时间1天后
     * 
     * @param timeExpire
     * @return
     */
    public UnifiedOrderBuilder setTimeExpire(String timeExpire) {

        Assert.isTrue(StringUtils.hasLength(timeExpire), "time_expire must be not null if you invoke the setter");

        paramMap.put(PARAM_TIME_EXPIRE, timeExpire);
        unifiedOrder.setTimeExpire(timeExpire);
        return this;
    }

    /**
     * 设置商品标记，代金券或立减优惠功能的参数
     * 
     * 默认不设置
     * 
     * @param goodTag
     * @return
     */
    public UnifiedOrderBuilder setGoodSTag(String goodsTag) {

        Assert.isTrue(StringUtils.hasLength(goodsTag) && goodsTag.length() < 33,
                "goods_tag must be not null and within 32 characters if you invoke the setter");

        paramMap.put(PARAM_GOODS_TAG, goodsTag);
        unifiedOrder.setGoodsTag(goodsTag);

        return this;
    }

    /**
     * 以不可变map形式返回统一下单的参数，避免直接修改私有的map
     * 
     * @return
     */
    public Map<String, String> getParams() {
        return Collections.unmodifiableMap(paramMap);
    }

    public static void main(String[] args) {

        UnifiedOrderBuilder builder = new UnifiedOrderBuilder();
        builder.setAppId("wxc6009c62e5c06c2c");
        builder.setBody("body");
        builder.setMchId("1232069402");
        builder.setNonceStr("12345");
        builder.setNotifyUrl("http://zwz.pagekite.me/patient/paynotify.html");
       // builder.setOpenId("123456789");
        builder.setOutTradeNo("1234567890");
        builder.setBillCreateIP("127.0.0.1");
        builder.setTotalFee(10000);
        builder.setTradeType("APP");
        builder.setKey("8C42862741d2c38674cfee3fdfca476b");
        System.out.println(builder.build().toXml());
    }

}
