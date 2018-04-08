package com.trump.auction.back.auctionProd.model;

import java.math.BigDecimal;
import java.util.Date;

public class AuctionBidDetail {
    private Integer id;

    private String bidId;

    private Integer userId;

    private String userName;

    private Integer bidType;

    private Integer bidSubType;

    public Integer getBidSubType() {
        return bidSubType;
    }

    public void setBidSubType(Integer bidSubType) {
        this.bidSubType = bidSubType;
    }

    private Integer auctionProdId;

    private Integer auctionNo;

    private String userIp;

    private Date createTime;

    private Date updateTime;

    private BigDecimal bidPrice;

    private String headImg;

    private String nickName;

    private String address;

    public String getSubUserId() {
        return subUserId;
    }

    public void setSubUserId(String subUserId) {
        this.subUserId = subUserId;
    }

    /**
     * 机器人
     */
    private String subUserId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBidId() {
        return bidId;
    }

    public void setBidId(String bidId) {
        this.bidId = bidId == null ? null : bidId.trim();
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

    public Integer getBidType() {
        return bidType;
    }

    public void setBidType(Integer bidType) {
        this.bidType = bidType;
    }

    public Integer getAuctionProdId() {
        return auctionProdId;
    }

    public void setAuctionProdId(Integer auctionProdId) {
        this.auctionProdId = auctionProdId;
    }

    public Integer getAuctionNo() {
        return auctionNo;
    }

    public void setAuctionNo(Integer auctionNo) {
        this.auctionNo = auctionNo;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp == null ? null : userIp.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public BigDecimal getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(BigDecimal bidPrice) {
        this.bidPrice = bidPrice;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg == null ? null : headImg.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }
}