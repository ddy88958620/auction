package com.trump.auction.account.service;

/**
 * 用户充值相关的定时任务
 */
public interface AccountRechargeTaskService {

    /**
     * 查询处理未完成的微信、支付宝充值订单
     */
    void resolveAccountRechargeOrderTask();

}
