package com.trump.auction.back.userRecharge.dao.read;

import com.trump.auction.back.userRecharge.model.AccountRechargeRule;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * Author: zhanping
 */
@Repository
public interface AccountRechargeRuleDao {
    /**
     * 查询所有规则列表
     * @return
     */
    List<AccountRechargeRule> findRules(HashMap<String, Object> params);
}
