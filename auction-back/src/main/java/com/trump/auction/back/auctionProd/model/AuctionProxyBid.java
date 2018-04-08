package com.trump.auction.back.auctionProd.model;

import java.util.Date;

public class AuctionProxyBid {
    private Integer id;

    private Integer userId;

    private String userName;

    private Integer bidNums;

    private Integer bidPrice;

    private Integer auctionProdId;

    private Integer auctionId;

    private Integer surplusPrice;

    private Date sDate;

    private Date uDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Integer getBidNums() {
        return bidNums;
    }

    public void setBidNums(Integer bidNums) {
        this.bidNums = bidNums;
    }

    public Integer getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(Integer bidPrice) {
        this.bidPrice = bidPrice;
    }

    public Integer getAuctionProdId() {
        return auctionProdId;
    }

    public void setAuctionProdId(Integer auctionProdId) {
        this.auctionProdId = auctionProdId;
    }

    public Integer getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Integer auctionId) {
        this.auctionId = auctionId;
    }

    public Integer getSurplusPrice() {
        return surplusPrice;
    }

    public void setSurplusPrice(Integer surplusPrice) {
        this.surplusPrice = surplusPrice;
    }

    public Date getsDate() {
        return sDate;
    }

    public void setsDate(Date sDate) {
        this.sDate = sDate;
    }

    public Date getuDate() {
        return uDate;
    }

    public void setuDate(Date uDate) {
        this.uDate = uDate;
    }
}