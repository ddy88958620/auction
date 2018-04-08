package com.trump.auction.cust.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AccountRechargeRuleDetailModel implements Serializable {
    private static final long serialVersionUID = -1;
    private Integer id;

    private Integer ruleId;

    private Integer rechargeMoney;

    private Integer detailType;

    private Integer presentCoin;

    private Integer points;

    private Integer status;

    private Date createTime;
}