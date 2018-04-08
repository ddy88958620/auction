package com.trump.auction.cust.service;

import com.cf.common.utils.ServiceResult;
import com.trump.auction.cust.domain.AccountRechargeRule;

/**
 * Author: zhanping
 */
public interface AccountRechargeRuleService {
    /**
     * 新增规则
     * @param obj
     * @return
     */
    AccountRechargeRule addRule(AccountRechargeRule obj);
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
    ServiceResult updateRuleById(AccountRechargeRule obj);
    /**
     * 根据id删除规则
     * @param id
     * @return
     */
    ServiceResult deleteRuleById(Integer id);
    /**
     * 查询开启的一条规则
     * @return
     */
    AccountRechargeRule findEnableRule();
    /**
     * 关闭所有已开启活动规则
     * @return
     */
    ServiceResult closeAllRuleStatus();

}
