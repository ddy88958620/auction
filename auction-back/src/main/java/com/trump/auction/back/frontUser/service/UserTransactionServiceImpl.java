package com.trump.auction.back.frontUser.service;


import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.github.pagehelper.PageHelper;
import com.trump.auction.back.constant.UserTransactionTypeEnum;
import com.trump.auction.back.frontUser.dao.read.UserTransactionInfoDao;
import com.trump.auction.back.frontUser.model.UserTransactionInfo;
import com.trump.auction.back.sys.model.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Service
public class UserTransactionServiceImpl  implements UserTransactionService  {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserTransactionInfoDao userTransactionInfoDao;

    /**
     * 分页查询交易订单
     * @param params
     * @return 交易订单信息
     */
    @Override
    public Paging<UserTransactionInfo> selectUserTransactionInfo(HashMap<String, Object> params){

        PageHelper.startPage(Integer.valueOf(String.valueOf(params.get("page") == null ? 1 : params.get("page"))),
                Integer.valueOf(String.valueOf(params.get("limit") == null ? 10 : params.get("limit"))));
        return PageUtils.page(userTransactionInfoDao.selectUserTransactionInfo(params));
    }

    @Override
    public Paging<UserTransactionInfo> getInfoRecord(HashMap<String, Object> params) {
        PageHelper.startPage(Integer.valueOf(String.valueOf(params.get("page") == null ? 1 : params.get("page"))),
                Integer.valueOf(String.valueOf(params.get("limit") == null ? 10 : params.get("limit"))));
        return PageUtils.page(userTransactionInfoDao.getInfoRecord(params));
    }

    @Override
    public int countAccountRecharge(Integer userId) {
        return userTransactionInfoDao.countAccountRecharge(userId);
    }

}
