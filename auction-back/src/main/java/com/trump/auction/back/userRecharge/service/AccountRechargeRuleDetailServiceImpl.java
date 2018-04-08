package com.trump.auction.back.userRecharge.service;


import com.trump.auction.back.userRecharge.dao.read.AccountRechargeRuleDetailDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: zhanping
 */
@Slf4j
@Service
public class AccountRechargeRuleDetailServiceImpl implements AccountRechargeRuleDetailService {

    @Autowired
    private AccountRechargeRuleDetailDao accountRechargeRuleDetailDao;

}