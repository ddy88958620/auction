package com.trump.auction.cust.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.utils.ServiceResult;
import com.trump.auction.cust.api.AccountRechargeRuleDetailStubService;
import com.trump.auction.cust.domain.AccountRechargeRuleDetail;
import com.trump.auction.cust.model.AccountRechargeRuleDetailModel;
import com.trump.auction.cust.service.AccountRechargeRuleDetailService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Author: zhanping
 */
@Service(version = "1.0.0")
public class AccountRechargeRuleDetailStubServiceImpl implements AccountRechargeRuleDetailStubService {

    @Autowired
    private AccountRechargeRuleDetailService accountRechargeRuleDetailService;
    @Autowired
    private BeanMapper beanMapper;
    /**
     * 新增规则详情
     * @param obj
     * @return
     */
    public ServiceResult addRuleDetail(AccountRechargeRuleDetailModel obj){
        return accountRechargeRuleDetailService.addRuleDetail(beanMapper.map(obj, AccountRechargeRuleDetail.class));
    }

    /**
     * 根据id查询规则详情
     * @param id
     * @return
     */
    public AccountRechargeRuleDetailModel findRuleDetailById(Integer id){
        AccountRechargeRuleDetail detail = accountRechargeRuleDetailService.findRuleDetailById(id);
        return beanMapper.map(detail,AccountRechargeRuleDetailModel.class);
    }

    /**
     * 根据id更新规则详情
     * @param obj
     * @return
     */
    public ServiceResult updateRuleDetailById(AccountRechargeRuleDetailModel obj){
        return accountRechargeRuleDetailService.updateRuleDetailById(beanMapper.map(obj, AccountRechargeRuleDetail.class));
    }

    /**
     * 根据id删除规则详情
     * @param id
     * @return
     */
    public ServiceResult deleteRuleDetailById(Integer id){
        return accountRechargeRuleDetailService.deleteRuleDetailById(id);
    }

    /**
     * 根据规则id查询所有规则详情列表
     * @param id
     * @return
     */
    public List<AccountRechargeRuleDetailModel> findRuleDetailByRuleId(Integer id){
        List<AccountRechargeRuleDetail> details = accountRechargeRuleDetailService.findRuleDetailByRuleId(id);
        return beanMapper.mapAsList(details,AccountRechargeRuleDetailModel.class);
    }

    /**
     * 根据规则id删除所有规则详情
     * @param id
     * @return
     */
    public ServiceResult deleteRuleDetailByRuleId(Integer id) {
        return accountRechargeRuleDetailService.deleteRuleDetailByRuleId(id);
    }
}
