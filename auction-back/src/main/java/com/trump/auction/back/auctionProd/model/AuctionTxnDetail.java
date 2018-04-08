package com.trump.auction.back.auctionProd.model;

import java.math.BigDecimal;
import java.util.Date;

public class AuctionTxnDetail {
    private Integer id;

    private String txnSeqNo;

    private String reqSeqNo;

    private String outSeqNo;

    private Integer txnStatus;

    private Integer currency;

    private Integer userId;

    private BigDecimal txnAmt;

    private Integer auctionProdId;

    private String auctionNo;

    private Date txnFinishTime;

    private Date createTime;

    private Integer bidStatus;

    private String remarks;

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

    public String getReqSeqNo() {
        return reqSeqNo;
    }

    public void setReqSeqNo(String reqSeqNo) {
        this.reqSeqNo = reqSeqNo == null ? null : reqSeqNo.trim();
    }

    public String getOutSeqNo() {
        return outSeqNo;
    }

    public void setOutSeqNo(String outSeqNo) {
        this.outSeqNo = outSeqNo == null ? null : outSeqNo.trim();
    }

    public Integer getTxnStatus() {
        return txnStatus;
    }

    public void setTxnStatus(Integer txnStatus) {
        this.txnStatus = txnStatus;
    }

    public Integer getCurrency() {
        return currency;
    }

    public void setCurrency(Integer currency) {
        this.currency = currency;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(BigDecimal txnAmt) {
        this.txnAmt = txnAmt;
    }

    public Integer getAuctionProdId() {
        return auctionProdId;
    }

    public void setAuctionProdId(Integer auctionProdId) {
        this.auctionProdId = auctionProdId;
    }

    public String getAuctionNo() {
        return auctionNo;
    }

    public void setAuctionNo(String auctionNo) {
        this.auctionNo = auctionNo == null ? null : auctionNo.trim();
    }

    public Date getTxnFinishTime() {
        return txnFinishTime;
    }

    public void setTxnFinishTime(Date txnFinishTime) {
        this.txnFinishTime = txnFinishTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getBidStatus() {
        return bidStatus;
    }

    public void setBidStatus(Integer bidStatus) {
        this.bidStatus = bidStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }
}