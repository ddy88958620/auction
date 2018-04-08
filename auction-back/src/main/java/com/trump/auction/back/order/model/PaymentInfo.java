package com.trump.auction.back.order.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wangjian
 */
@Data
public class PaymentInfo {
    /**
     * 
     */
    private Integer id;

    /**
     * 预支付流水号
     */
    private String paymentId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 
     */
    private String userPhone;

    /**
     * 支付金额
     */
    private Long paymentAmount;

    /**
     * 付款时间
     */
    private Date buyPayTime;

    /**
     * 订单金额(分)
     */
    private Integer orderAmount;

    /**
     * 支付类型
     */
    private Integer paymentType;

    /**
     * 支付子类型
     */
    private Integer paymentSubtype;

    /**
     * 支付流水号(第三方)
     */
    private String serialNo;

    /**
     * 支付状态
     */
    private Integer paymentStatus;

    /**
     * 支付次数
     */
    private Integer paymentCount;

    /**
     * 备注
     */
    private String remark;

    /**
     * 支付备注
     */
    private String paymentRemark;

    /**
     * 支付标志
     */
    private Integer payflag;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 
     */
    private Integer couponId;

    /**
     * 
     */
    private BigDecimal couponMoney;
}