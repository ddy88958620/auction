package com.trump.auction.back.userRecharge.service;

import com.cf.common.util.page.Paging;
import com.trump.auction.back.userRecharge.model.AccountRechargeOrder;
import java.util.HashMap;

/**
 * Created by wangYaMin on 2018/1/10.
 */
public interface AccountRechargeOrderService {

    /**
     * 查询用户拍币充值订单列表信息
     * @param params
     * @return 用户拍币充值订单信息
     */
    Paging<AccountRechargeOrder> selectAccountRechargeOrder(HashMap<String, Object> params);
}
