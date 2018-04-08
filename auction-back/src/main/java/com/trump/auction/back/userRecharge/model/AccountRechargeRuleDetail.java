package com.trump.auction.back.userRecharge.model;

import lombok.Data;

import java.util.Date;

@Data
public class AccountRechargeRuleDetail {
    private Integer id;

    private Integer ruleId;

    private Integer rechargeMoney;

    private Integer detailType;

    private Integer presentCoin;

    private Integer points;

    private Integer status;

    private Date createTime;

}