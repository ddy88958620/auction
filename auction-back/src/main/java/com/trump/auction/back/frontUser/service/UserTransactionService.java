package com.trump.auction.back.frontUser.service;

import com.cf.common.util.page.Paging;
import com.trump.auction.back.appraises.model.OrderAppraises;
import com.trump.auction.back.frontUser.model.UserTransactionInfo;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分页查询交易订单
 * @param params
 * @return 交易订单信息
 */

public interface UserTransactionService {


    Paging<UserTransactionInfo> selectUserTransactionInfo(HashMap<String, Object> params) throws Exception;

    Paging<UserTransactionInfo> getInfoRecord(HashMap<String, Object> params);


    /**
     * 根据用户id查询充值次数
     * @param userId
     */
    int countAccountRecharge(Integer userId);
}
