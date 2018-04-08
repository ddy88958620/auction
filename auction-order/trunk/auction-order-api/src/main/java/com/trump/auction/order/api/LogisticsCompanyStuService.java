package com.trump.auction.order.api;


import com.trump.auction.order.model.LogisticsCompanyModel;

import java.util.List;

/**
 * 物流公司相关服务
 * @author zhanping
 */
public interface LogisticsCompanyStuService {

    /**
     * 查询所有物流公司
     * @return
     */
    List<LogisticsCompanyModel> findLogisticsCompanyList();

    /**
     * 根据物流公司ID查询物流公司
     * @param id 物流公司ID
     * @return
     */
    LogisticsCompanyModel findLogisticsCompanyItemById(Integer id);

    /**
     * 新增物流公司信息
     * @param obj
     * @return
     */
    int insertLogisticsCompanyItem(LogisticsCompanyModel obj);

    /**
     * 根据物流公司ID跟新物流公司信息
     * @param obj
     * @return
     */
    int updateLogisticsCompanyItemById(LogisticsCompanyModel obj);

    /**
     * 根据物流公司ID删除物流公司信息
     * @param id
     * @return
     */
    int deleteLogisticsCompanyItemById(Integer id);
}
