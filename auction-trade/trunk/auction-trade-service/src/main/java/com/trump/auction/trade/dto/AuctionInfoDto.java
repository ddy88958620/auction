package com.trump.auction.trade.dto;

import java.io.Serializable;



public class AuctionInfoDto implements Serializable {
    static final long serialVersionUID = 1L;
    private int auctionId;   //本期竞拍ID
    private int requestId;   //本次请求ID
    private int requestReason;   //发起请求的原因，1:倒计时最后一秒  2：新出价重置倒计时
    private double productionPrice;   //商品预设竞拍价格
    private double validBiddingAmount;   //有效出价金额
    private double totalBiddingAmount;   //总出价金额
    private int bidderCountNow;   //当前出价人数，含机器人
    private int bidderCountFinal;   //预设本次拍卖最小出价人数
    private double highestBiddingPriceNow;   //当前最高出价
    private int bidderCountByPrice;   //当前出价对应所需出价人数
    private int highestPriceBidder;   //当前最高出价者角色，1：真实用户；2：机器人
    private int recent3Bidder;   //最近三次出价是否机器人，1：是；0：否
    private int result;   //本次请求决策结果，1：用户拍得；2：机器人拍得；3：新机器人出价；4：老机器人出价
    private String robotId;   //机器人ID
    private String robotName;   //机器人姓名
    private String robotDistrict;   //机器人地区
    private String robotIP;   //机器人IP
    private String robotPic;   //机器人头像

    public int getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(int auctionId) {
        this.auctionId = auctionId;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getRequestReason() {
        return requestReason;
    }

    public void setRequestReason(int requestReason) {
        this.requestReason = requestReason;
    }

    public double getProductionPrice() {
        return productionPrice;
    }

    public void setProductionPrice(double productionPrice) {
        this.productionPrice = productionPrice;
    }

    public double getValidBiddingAmount() {
        return validBiddingAmount;
    }

    public void setValidBiddingAmount(double validBiddingAmount) {
        this.validBiddingAmount = validBiddingAmount;
    }

    public double getTotalBiddingAmount() {
        return totalBiddingAmount;
    }

    public void setTotalBiddingAmount(double totalBiddingAmount) {
        this.totalBiddingAmount = totalBiddingAmount;
    }

    public int getBidderCountNow() {
        return bidderCountNow;
    }

    public void setBidderCountNow(int bidderCountNow) {
        this.bidderCountNow = bidderCountNow;
    }

    public int getBidderCountFinal() {
        return bidderCountFinal;
    }

    public void setBidderCountFinal(int bidderCountFinal) {
        this.bidderCountFinal = bidderCountFinal;
    }

    public double getHighestBiddingPriceNow() {
        return highestBiddingPriceNow;
    }

    public void setHighestBiddingPriceNow(double highestBiddingPriceNow) {
        this.highestBiddingPriceNow = highestBiddingPriceNow;
    }

    public int getBidderCountByPrice() {
        return bidderCountByPrice;
    }

    public void setBidderCountByPrice(int bidderCountByPrice) {
        this.bidderCountByPrice = bidderCountByPrice;
    }

    public int getHighestPriceBidder() {
        return highestPriceBidder;
    }

    public void setHighestPriceBidder(int highestPriceBidder) {
        this.highestPriceBidder = highestPriceBidder;
    }

    public int getRecent3Bidder() {
        return recent3Bidder;
    }

    public void setRecent3Bidder(int recent3Bidder) {
        this.recent3Bidder = recent3Bidder;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getRobotId() {
        return robotId;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public String getRobotName() {
        return robotName;
    }

    public void setRobotName(String robotName) {
        this.robotName = robotName;
    }

    public String getRobotDistrict() {
        return robotDistrict;
    }

    public void setRobotDistrict(String robotDistrict) {
        this.robotDistrict = robotDistrict;
    }

    public String getRobotIP() {
        return robotIP;
    }

    public void setRobotIP(String robotIP) {
        this.robotIP = robotIP;
    }

    public String getRobotPic() {
        return robotPic;
    }

    public void setRobotPic(String robotPic) {
        this.robotPic = robotPic;
    }
}
