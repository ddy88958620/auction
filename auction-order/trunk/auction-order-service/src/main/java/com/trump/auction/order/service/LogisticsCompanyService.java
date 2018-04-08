package com.trump.auction.order.service;


import com.trump.auction.order.domain.LogisticsCompany;

import java.util.List;

public interface LogisticsCompanyService {
    LogisticsCompany selectByPrimaryKey(Integer id);
    int deleteByPrimaryKey(Integer id);
    int insert(LogisticsCompany obj);
    int insertSelective(LogisticsCompany obj);
    int updateByPrimaryKeySelective(LogisticsCompany obj);
    int updateByPrimaryKey(LogisticsCompany obj);

    /**
     * 查询所有物流公司
     * @return
     */
    List<LogisticsCompany> findLogisticsCompanyList();
}
