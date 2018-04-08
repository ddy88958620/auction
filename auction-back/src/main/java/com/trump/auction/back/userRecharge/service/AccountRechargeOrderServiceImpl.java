package com.trump.auction.back.userRecharge.service;

import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.github.pagehelper.PageHelper;
import com.trump.auction.back.userRecharge.dao.read.AccountRechargeOrderDao;
import com.trump.auction.back.userRecharge.model.AccountRechargeOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Created by Administrator on 2018/1/10.
 */
@Repository
@Service
public class AccountRechargeOrderServiceImpl implements AccountRechargeOrderService{

    @Autowired
    private AccountRechargeOrderDao accountRechargeOrderDao;
    @Override
    public Paging<AccountRechargeOrder> selectAccountRechargeOrder(HashMap<String, Object> params) {
        PageHelper.startPage(Integer.valueOf(String.valueOf(params.get("page") == null ? 1 : params.get("page"))),
                Integer.valueOf(String.valueOf(params.get("limit") == null ? 10 : params.get("limit"))));
        return PageUtils.page(accountRechargeOrderDao.selectAccountRechargeOrder(params));
    }
}
