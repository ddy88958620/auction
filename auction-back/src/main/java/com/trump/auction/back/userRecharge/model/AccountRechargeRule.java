package com.trump.auction.back.userRecharge.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AccountRechargeRule {
    private Integer id;

    private Integer ruleUser;

    private String ruleTitle;

    private Integer ruleStatus;

    private Date ruleStartTime;

    private Date ruleEndTime;

    private String remark;

    private Date createTime;

    private Date updateTime;

    private List<AccountRechargeRuleDetail> details;

}