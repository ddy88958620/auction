package com.trump.auction.cust.service.impl;

import com.cf.common.utils.ServiceResult;
import com.trump.auction.cust.dao.AccountRechargeRuleDao;
import com.trump.auction.cust.dao.AccountRechargeRuleDetailDao;
import com.trump.auction.cust.domain.AccountRechargeRule;
import com.trump.auction.cust.domain.AccountRechargeRuleDetail;
import com.trump.auction.cust.service.AccountRechargeRuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: zhanping
 */
@Slf4j
@Service
public class AccountRechargeRuleServiceImpl implements AccountRechargeRuleService{

    @Autowired
    private AccountRechargeRuleDao accountRechargeRuleDao;
    @Autowired
    private AccountRechargeRuleDetailDao accountRechargeRuleDetailDao;

    @Override
    public AccountRechargeRule addRule(AccountRechargeRule obj) {

        int count = accountRechargeRuleDao.addRule(obj);
        if (count != 1) {
            return null;
        }

        List<AccountRechargeRuleDetail> details = obj.getDetails();

        boolean flag = true;
        if (details != null && details.size()>0){
            for (AccountRechargeRuleDetail detail:details) {
                detail.setRuleId(obj.getId());
                int result = accountRechargeRuleDetailDao.addRuleDetail(detail);
                if (result != 1){
                    flag = false;
                    break;
                }
            }
        }
        if (flag){
            return obj;
        }
        return null;
    }

    @Override
    public AccountRechargeRule findRuleById(Integer id) {
        AccountRechargeRule rule = accountRechargeRuleDao.findRuleById(id);
        if (rule != null){
            List<AccountRechargeRuleDetail> details = accountRechargeRuleDetailDao.findRuleDetailByRuleId(rule.getId());
            rule.setDetails(details);
        }
        return rule;
    }

    @Override
    public ServiceResult updateRuleById(AccountRechargeRule obj) {
        int count = accountRechargeRuleDao.updateRuleById(obj);
        if (count==1) {
            return new ServiceResult(ServiceResult.SUCCESS,"操作成功");
        }
        return new ServiceResult(ServiceResult.FAILED,"操作失败");
    }

    @Override
    public ServiceResult deleteRuleById(Integer id) {
        int count = accountRechargeRuleDao.deleteRuleById(id);
        if (count==1) {
            return new ServiceResult(ServiceResult.SUCCESS,"操作成功");
        }
        return new ServiceResult(ServiceResult.FAILED,"操作失败");
    }

    @Override
    public AccountRechargeRule findEnableRule() {
        AccountRechargeRule rule = accountRechargeRuleDao.findEnableRule();
        if (rule != null){
            List<AccountRechargeRuleDetail> details = accountRechargeRuleDetailDao.findRuleDetailByRuleId(rule.getId());
            rule.setDetails(details);
        }
        return rule;
    }
    @Override
    public ServiceResult closeAllRuleStatus() {
        int count = accountRechargeRuleDao.closeAllRuleStatus();
        if (count>0) {
            return new ServiceResult(ServiceResult.SUCCESS,"操作成功");
        }
        return new ServiceResult(ServiceResult.FAILED,"操作失败");
    }
}
