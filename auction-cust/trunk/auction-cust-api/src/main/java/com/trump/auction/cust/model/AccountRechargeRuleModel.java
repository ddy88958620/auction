package com.trump.auction.cust.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class AccountRechargeRuleModel implements Serializable{
    private static final long serialVersionUID = -1;
    private Integer id;

    private Integer ruleUser;

    private String ruleTitle;

    private Integer ruleStatus;

    private Date ruleStartTime;

    private Date ruleEndTime;

    private String remark;

    private Date createTime;

    private Date updateTime;

    private List<AccountRechargeRuleDetailModel> details;

}