package com.trump.auction.cust.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.utils.ServiceResult;
import com.trump.auction.cust.api.AccountRechargeRuleStubService;
import com.trump.auction.cust.domain.AccountRechargeRule;
import com.trump.auction.cust.model.AccountRechargeRuleModel;
import com.trump.auction.cust.service.AccountRechargeRuleService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: zhanping
 */
@Service(version = "1.0.0")
public class AccountRechargeRuleStubServiceImpl implements AccountRechargeRuleStubService {

    @Autowired
    private AccountRechargeRuleService accountRechargeRuleService;
    @Autowired
    private BeanMapper beanMapper;
    /**
     * 新增规则
     * @param obj
     * @return
     */
    public AccountRechargeRuleModel addRule(AccountRechargeRuleModel obj){
        AccountRechargeRule rule = accountRechargeRuleService.addRule(beanMapper.map(obj, AccountRechargeRule.class));
        return beanMapper.map(rule,AccountRechargeRuleModel.class);
    }
    /**
     * 根据id查询规则
     * @param id
     * @return
     */
    public AccountRechargeRuleModel findRuleById(Integer id){
        return beanMapper.map(accountRechargeRuleService.findRuleById(id),AccountRechargeRuleModel.class);
    }
    /**
     * 根据id更新规则
     * @param obj
     * @return
     */
    public ServiceResult updateRuleById(AccountRechargeRuleModel obj){
        return accountRechargeRuleService.updateRuleById(beanMapper.map(obj,AccountRechargeRule.class));
    }
    /**
     * 根据id删除规则
     * @param id
     * @return
     */
    public ServiceResult deleteRuleById(Integer id){
        return accountRechargeRuleService.deleteRuleById(id);
    }
    /**
     * 查询开启的一条规则
     * @return
     */
    public AccountRechargeRuleModel findEnableRule() {
        return beanMapper.map(accountRechargeRuleService.findEnableRule(),AccountRechargeRuleModel.class);
    }
    /**
     * 关闭所有已开启活动规则
     * @return
     */
    public ServiceResult closeAllRuleStatus() {
        return accountRechargeRuleService.closeAllRuleStatus();
    }
}
