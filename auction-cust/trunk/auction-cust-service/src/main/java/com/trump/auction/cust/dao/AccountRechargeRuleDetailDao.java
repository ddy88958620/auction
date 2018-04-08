package com.trump.auction.cust.dao;

import com.trump.auction.cust.domain.AccountRechargeRuleDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author: zhanping
 */
@Repository
public interface AccountRechargeRuleDetailDao {
    /**
     * 新增规则详情
     * @param obj
     * @return
     */
    int addRuleDetail(AccountRechargeRuleDetail obj);

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
    int updateRuleDetailById(AccountRechargeRuleDetail obj);

    /**
     * 根据id删除规则详情
     * @param id
     * @return
     */
    int deleteRuleDetailById(Integer id);

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
    int deleteRuleDetailByRuleId(Integer id);
}
