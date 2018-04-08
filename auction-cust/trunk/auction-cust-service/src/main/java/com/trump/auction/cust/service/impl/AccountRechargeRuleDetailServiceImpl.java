package com.trump.auction.cust.service.impl;

import com.cf.common.utils.ServiceResult;
import com.trump.auction.cust.dao.AccountRechargeRuleDao;
import com.trump.auction.cust.dao.AccountRechargeRuleDetailDao;
import com.trump.auction.cust.domain.AccountRechargeRuleDetail;
import com.trump.auction.cust.service.AccountRechargeRuleDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: zhanping
 */
@Slf4j
@Service
public class AccountRechargeRuleDetailServiceImpl implements AccountRechargeRuleDetailService {

    @Autowired
    private AccountRechargeRuleDetailDao accountRechargeRuleDetailDao;

    @Override
    public ServiceResult addRuleDetail(AccountRechargeRuleDetail obj) {
        int count = accountRechargeRuleDetailDao.addRuleDetail(obj);
        if (count==1) {
            return new ServiceResult(ServiceResult.SUCCESS,"操作成功");
        }
        return new ServiceResult(ServiceResult.FAILED,"操作失败");
    }

    @Override
    public AccountRechargeRuleDetail findRuleDetailById(Integer id) {
        return accountRechargeRuleDetailDao.findRuleDetailById(id);
    }

    @Override
    public ServiceResult updateRuleDetailById(AccountRechargeRuleDetail obj) {
        int count = accountRechargeRuleDetailDao.updateRuleDetailById(obj);
        if (count==1) {
            return new ServiceResult(ServiceResult.SUCCESS,"操作成功");
        }
        return new ServiceResult(ServiceResult.FAILED,"操作失败");
    }

    @Override
    public ServiceResult deleteRuleDetailById(Integer id) {
        int count = accountRechargeRuleDetailDao.deleteRuleDetailById(id);
        if (count==1) {
            return new ServiceResult(ServiceResult.SUCCESS,"操作成功");
        }
        return new ServiceResult(ServiceResult.FAILED,"操作失败");
    }

    @Override
    public List<AccountRechargeRuleDetail> findRuleDetailByRuleId(Integer id){
        return accountRechargeRuleDetailDao.findRuleDetailByRuleId(id);
    }

    @Override
    public ServiceResult deleteRuleDetailByRuleId(Integer id) {
        int count = accountRechargeRuleDetailDao.deleteRuleDetailByRuleId(id);
        if (count==1) {
            return new ServiceResult(ServiceResult.SUCCESS,"操作成功");
        }
        return new ServiceResult(ServiceResult.FAILED,"操作失败");
    }
}
