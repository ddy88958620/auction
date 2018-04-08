package com.trump.auction.account.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.trump.auction.account.api.AccountRechargeTaskStubService;
import com.trump.auction.account.service.AccountRechargeTaskService;

@Service(version = "1.0.0")
public class AccountRechargeTaskStubServiceImpl implements AccountRechargeTaskStubService {
    private AccountRechargeTaskService accountRechargeTaskService;

    AccountRechargeTaskStubServiceImpl(AccountRechargeTaskService accountRechargeTaskService) {
        this.accountRechargeTaskService = accountRechargeTaskService;
    }

    @Override
    public void resolveAccountRechargeOrderTask() {
        accountRechargeTaskService.resolveAccountRechargeOrderTask();
    }
}
