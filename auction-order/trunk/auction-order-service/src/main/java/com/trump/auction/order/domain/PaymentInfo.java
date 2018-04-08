package com.trump.auction.order.domain;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentInfo {
    private Integer id;

    private String paymentId;

    private Integer userId;

    private String orderId;

    private String userPhone;

    private BigDecimal paymentAmount;

    private Date buyPayTime;

    private BigDecimal orderAmount;

    private Integer paymentType;

    private Integer paymentSubtype;

    private String serialNo;

    private Integer paymentStatus;

    private Integer paymentCount;

    private String remark;

    private String paymentRemark;

    private Integer payflag;

    private Date updateTime;

    private Date createDate;

    private Integer buyCoinId;

    private BigDecimal buyCoinMoney;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId == null ? null : paymentId.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Date getBuyPayTime() {
        return buyPayTime;
    }

    public void setBuyPayTime(Date buyPayTime) {
        this.buyPayTime = buyPayTime;
    }

    public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public Integer getPaymentSubtype() {
        return paymentSubtype;
    }

    public void setPaymentSubtype(Integer paymentSubtype) {
        this.paymentSubtype = paymentSubtype;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo == null ? null : serialNo.trim();
    }

    public Integer getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Integer getPaymentCount() {
        return paymentCount;
    }

    public void setPaymentCount(Integer paymentCount) {
        this.paymentCount = paymentCount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getPaymentRemark() {
        return paymentRemark;
    }

    public void setPaymentRemark(String paymentRemark) {
        this.paymentRemark = paymentRemark == null ? null : paymentRemark.trim();
    }

    public Integer getPayflag() {
        return payflag;
    }

    public void setPayflag(Integer payflag) {
        this.payflag = payflag;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

	public Integer getBuyCoinId() {
		return buyCoinId;
	}

	public void setBuyCoinId(Integer buyCoinId) {
		this.buyCoinId = buyCoinId;
	}

	public BigDecimal getBuyCoinMoney() {
		return buyCoinMoney;
	}

	public void setBuyCoinMoney(BigDecimal buyCoinMoney) {
		this.buyCoinMoney = buyCoinMoney;
	}
}