package com.trump.auction.account.api;
/**
 * Created by wangyichao on 2018-02-02 下午 01:38.
 * 用户充值相关的定时任务
 */
public interface AccountRechargeTaskStubService {
    /**
     * 查询处理未完成的微信、支付宝充值订单
     */
    void resolveAccountRechargeOrderTask();

}
