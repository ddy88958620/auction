package com.trump.auction.back.userRecharge.service;

import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.github.pagehelper.PageHelper;
import com.trump.auction.back.userRecharge.dao.read.AccountRechargeRuleDao;
import com.trump.auction.back.userRecharge.dao.read.AccountRechargeRuleDetailDao;
import com.trump.auction.back.userRecharge.model.AccountRechargeRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Author: zhanping
 */
@Slf4j
@Service
public class AccountRechargeRuleServiceImpl implements AccountRechargeRuleService {

    @Autowired
    private AccountRechargeRuleDao accountRechargeRuleDao;
    @Autowired
    private AccountRechargeRuleDetailDao accountRechargeRuleDetailDao;

    @Override
    public Paging<AccountRechargeRule> findRules(HashMap<String, Object> params) {
        PageHelper.startPage(Integer.valueOf(String.valueOf(params.get("page") == null ? 1 : params.get("page"))),
                Integer.valueOf(String.valueOf(params.get("limit") == null ? 10 : params.get("limit"))));
        return PageUtils.page(accountRechargeRuleDao.findRules(params));
    }
}