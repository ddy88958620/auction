package com.trump.auction.back.auctionProd.model;

import java.util.Date;

public class AuctionBidInfo {
    private Integer id;

    private String txnSeqNo;

    private Integer userId;

    private String userName;

    private Integer bidCount;

    private Integer auctionProdId;

    private Integer auctionNo;

    private Integer usedCount;

    private Date createTime;

    private Date updateTime;
    private Integer status;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTxnSeqNo() {
        return txnSeqNo;
    }

    public void setTxnSeqNo(String txnSeqNo) {
        this.txnSeqNo = txnSeqNo == null ? null : txnSeqNo.trim();
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

    public Integer getBidCount() {
        return bidCount;
    }

    public void setBidCount(Integer bidCount) {
        this.bidCount = bidCount;
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

    public Integer getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(Integer usedCount) {
        this.usedCount = usedCount;
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
}