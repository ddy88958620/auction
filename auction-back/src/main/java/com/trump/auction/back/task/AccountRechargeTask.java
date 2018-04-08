package com.trump.auction.back.task;

import com.trump.auction.account.api.AccountInfoStubService;
import com.trump.auction.account.api.AccountRechargeTaskStubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 微信支付宝充值订单-定时任务
 */
@Component("accountRechargeTask")
public class AccountRechargeTask {
    @Autowired
    private AccountRechargeTaskStubService accountRechargeTaskStubService;
    @Autowired
    private AccountInfoStubService accountInfoStubService;

    /**
     * 查询处理未完成的微信、支付宝充值订单
     */
    public void resolveAccountRechargeOrderTask() {
        accountRechargeTaskStubService.resolveAccountRechargeOrderTask();
    }


    /**
     * 首充用户未拍中反币操作
     */
    public void returnBuyCoin(){
    	//accountInfoStubService.returnBuyCoin();
    }

}
