package com.trump.auction.back.userRecharge.service;


import com.cf.common.util.page.Paging;
import com.trump.auction.back.userRecharge.model.AccountRechargeRule;

import java.util.HashMap;
import java.util.List;

/**
 * Author: zhanping
 */
public interface AccountRechargeRuleService {
    /**
     * 查询所有规则列表
     * @return
     */
    Paging<AccountRechargeRule> findRules(HashMap<String, Object> params);
}
