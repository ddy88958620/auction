package com.trump.auction.trade.pay.wechat;

public class UnifiedOrder {

    private String appId;
    private String mchId;
    private String deviceInfo;
    private String nonceStr;
    private String sign;
    private String body;
    private String attach;
    private String detail;
    private String outTradeNo;
    private Integer totalFee;
    private String createIp;
    private String timeStart;
    private String timeExpire;
    private String goodsTag;
    private String notifyUrl;
    private String tradeType;
    private String openId;
    private String productId;
    private String feeType;

    private String resultXml;

    public String getAppId() {
        return appId;
    }

    protected void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMchId() {
        return mchId;
    }

    protected void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    protected void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    protected void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSign() {
        return sign;
    }

    protected void setSign(String sign) {
        this.sign = sign;
    }

    public String getBody() {
        return body;
    }

    protected void setBody(String body) {
        this.body = body;
    }

    public String getAttach() {
        return attach;
    }

    protected void setAttach(String attach) {
        this.attach = attach;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    protected void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    protected void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public String getCreateIp() {
        return createIp;
    }

    protected void setCreateIp(String createIp) {
        this.createIp = createIp;
    }

    public String getTimeStart() {
        return timeStart;
    }

    protected void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeExpire() {
        return timeExpire;
    }

    protected void setTimeExpire(String timeExpire) {
        this.timeExpire = timeExpire;
    }

    public String getGoodsTag() {
        return goodsTag;
    }

    protected void setGoodsTag(String goodsTag) {
        this.goodsTag = goodsTag;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    protected void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getTradeType() {
        return tradeType;
    }

    protected void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getOpenId() {
        return openId;
    }

    protected void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getProductId() {
        return productId;
    }

    public String getDetail() {
        return detail;
    }

    protected void setDetail(String detail) {
        this.detail = detail;
    }

    protected void setProductId(String productId) {
        this.productId = productId;
    }

    public String getFeeType() {
        return feeType;
    }

    protected void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    protected void setResultXml(String resultXml) {
        this.resultXml = resultXml;
    }

    public String toXml() {
        return this.resultXml;
    }

}
