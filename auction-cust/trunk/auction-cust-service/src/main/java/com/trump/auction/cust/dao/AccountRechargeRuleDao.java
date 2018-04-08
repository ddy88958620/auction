package com.trump.auction.cust.dao;

import com.trump.auction.cust.domain.AccountRechargeRule;
import org.springframework.stereotype.Repository;

/**
 * Author: zhanping
 */
@Repository
public interface AccountRechargeRuleDao {
    /**
     * 新增规则
     * @param obj
     * @return
     */
    int addRule(AccountRechargeRule obj);
    /**
     * 根据id查询规则
     * @param id
     * @return
     */
    AccountRechargeRule findRuleById(Integer id);
    /**
     * 根据id更新规则
     * @param obj
     * @return
     */
    int updateRuleById(AccountRechargeRule obj);
    /**
     * 根据id删除规则
     * @param id
     * @return
     */
    int deleteRuleById(Integer id);

    /**
     * 查询开启的一条规则
     * @return
     */
    AccountRechargeRule findEnableRule();

    /**
     * 关闭所有已开启活动规则
     * @return
     */
    int closeAllRuleStatus();
}
