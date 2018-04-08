package com.trump.auction.back.userRecharge.model;

import lombok.Data;

import java.util.Date;

/**
 * Created by wangYaMin on 2018/1/10.
 */
@Data
public class AccountRechargeOrder {
    private Integer id;
    private Integer userId;
    private String userName;
    private String userPhone;
    private Integer outMoney;
    private Integer intoCoin;
    private Integer rechargeType;
    private String rechargeTypeName;
    private Integer tradeStatus;
    private Integer payStatus;
    private String payRemark;
    private String resultJson;
    private String orderNo;
    private String outTradeNo;
    private Integer orderStatus;
    private Date createTime;
    private Date updateTime;
}
