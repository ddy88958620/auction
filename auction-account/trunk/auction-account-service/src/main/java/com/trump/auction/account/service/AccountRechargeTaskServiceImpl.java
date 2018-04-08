package com.trump.auction.account.service;

import com.alibaba.fastjson.JSONObject;
import com.trump.auction.account.dao.AccountRechargeOrderDao;
import com.trump.auction.account.domain.AccountRechargeOrder;
import com.trump.auction.account.enums.EnumRechargeOrderStatus;
import com.trump.auction.account.enums.EnumRechargeStatus;
import com.trump.auction.account.enums.EnumTransactionTag;
import com.trump.auction.pals.api.AlipayStubService;
import com.trump.auction.pals.api.WeChatPayStubService;
import com.trump.auction.pals.api.model.alipay.AliPayQueryResponse;
import com.trump.auction.pals.api.model.alipay.AlipayQueryRequest;
import com.trump.auction.pals.api.model.wechat.WeChatPayQueryRequest;
import com.trump.auction.pals.api.model.wechat.WeChatPayQueryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AccountRechargeTaskServiceImpl implements AccountRechargeTaskService {
    private AccountRechargeOrderDao accountRechargeOrderDao;
    private WeChatPayStubService weChatPayStubService;
    private AlipayStubService alipayStubService;
    private AccountInfoService accountInfoService;

    AccountRechargeTaskServiceImpl(AccountRechargeOrderDao accountRechargeOrderDao, WeChatPayStubService weChatPayStubService, AlipayStubService alipayStubService, AccountInfoService accountInfoService) {
        this.accountRechargeOrderDao = accountRechargeOrderDao;
        this.weChatPayStubService = weChatPayStubService;
        this.alipayStubService = alipayStubService;
        this.accountInfoService = accountInfoService;
    }

    @Override
    public void resolveAccountRechargeOrderTask() {
        //查询tradeStatus=1 orderStatus=1的订单
        List<AccountRechargeOrder> list = accountRechargeOrderDao.getUnfinishedRechargeOrder(EnumRechargeStatus.RECHARGE_ING.getKey(), EnumRechargeOrderStatus.ORDER_UNDONE.getKey());
        for(AccountRechargeOrder order : list) {
            int rechargeType = order.getRechargeType();
            String outTradeNo = order.getOutTradeNo();
            if(rechargeType == EnumTransactionTag.TRANSACTION_WX_RECHARGE.getKey()) {//微信
                WeChatPayQueryRequest weChatPayQueryRequest = new WeChatPayQueryRequest();
                weChatPayQueryRequest.setBatchNo(outTradeNo);
                WeChatPayQueryResponse weChatPayQueryResponse = weChatPayStubService.queryWeChatPay(weChatPayQueryRequest);
                boolean success = weChatPayQueryResponse.isSuccessed();
                String resultJson = JSONObject.toJSONString(weChatPayQueryResponse);
                updateAccountRechargeOrder(outTradeNo, success, resultJson);
            } else {
                if(rechargeType == EnumTransactionTag.TRANSACTION_ALI_RECHARGE.getKey()) {//支付宝
                    AlipayQueryRequest alipayQueryRequest = new AlipayQueryRequest();
                    alipayQueryRequest.setBatchNo(outTradeNo);
                    AliPayQueryResponse aliPayQueryResponse = alipayStubService.toAlipayQuery(alipayQueryRequest);
                    boolean success = aliPayQueryResponse.isSuccessed();
                    String resultJson = JSONObject.toJSONString(aliPayQueryResponse);
                    updateAccountRechargeOrder(outTradeNo, success, resultJson);
                }
            }
        }

    }

    private void updateAccountRechargeOrder(String outTradeNo, boolean success, String msg) {
        try {
            if(success) {
                accountInfoService.rechargeUserAccount(Boolean.TRUE, outTradeNo, msg);
            } else {
                accountInfoService.rechargeUserAccount(Boolean.FALSE, outTradeNo, msg);
            }
        } catch(Exception e) {
            log.error("充值订单-定时任务出错: outTradeNo:{}\n{}", outTradeNo, e);
        }
    }
}
