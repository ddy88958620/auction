package com.trump.auction.cust.api;

import com.cf.common.utils.ServiceResult;
import com.trump.auction.cust.model.AccountRechargeRuleModel;

/**
 * Author: zhanping
 */
public interface AccountRechargeRuleStubService {
    /**
     * 新增规则
     * @param obj
     * @return
     */
    AccountRechargeRuleModel addRule(AccountRechargeRuleModel obj);
    /**
     * 根据id查询规则
     * @param id
     * @return
     */
    AccountRechargeRuleModel findRuleById(Integer id);
    /**
     * 根据id更新规则
     * @param obj
     * @return
     */
    ServiceResult updateRuleById(AccountRechargeRuleModel obj);
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
    AccountRechargeRuleModel findEnableRule();
    /**
     * 关闭所有已开启活动规则
     * @return
     */
    ServiceResult closeAllRuleStatus();
}
