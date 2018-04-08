package com.trump.auction.cust.service;

import com.cf.common.utils.ServiceResult;
import com.trump.auction.cust.domain.AccountRechargeRuleDetail;

import java.util.List;

/**
 * Author: zhanping
 */
public interface AccountRechargeRuleDetailService {
    /**
     * 新增规则详情
     * @param obj
     * @return
     */
    ServiceResult addRuleDetail(AccountRechargeRuleDetail obj);

    /**
     * 根据id查询规则详情
     * @param id
     * @return
     */
    AccountRechargeRuleDetail findRuleDetailById(Integer id);

    /**
     * 根据id更新规则详情
     * @param obj
     * @return
     */
    ServiceResult updateRuleDetailById(AccountRechargeRuleDetail obj);

    /**
     * 根据id删除规则详情
     * @param id
     * @return
     */
    ServiceResult deleteRuleDetailById(Integer id);

    /**
     * 根据规则id查询所有规则详情列表
     * @param id
     * @return
     */
    List<AccountRechargeRuleDetail> findRuleDetailByRuleId(Integer id);

    /**
     * 根据规则id删除所有规则详情
     * @param id
     * @return
     */
    ServiceResult deleteRuleDetailByRuleId(Integer id);
}
