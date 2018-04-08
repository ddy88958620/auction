package com.trump.auction.order.api;


import com.trump.auction.order.model.LogisticsModel;

/**
 * 物流信息相关服务
 * @author zhanping
 */
public interface LogisticsStuService {

    /**
     * 根据订单ID查询物流信息
     * @param orderId orderId
     * @return
     */
    LogisticsModel findLogisticsItemById(String orderId);

    /**
     * 新增物流信息
     * @param obj
     * @return
     */
    int insertLogisticsItem(LogisticsModel obj);

    /**
     * 根据订单ID更新物流信息
     * @param obj
     * @return
     */
    int updateLogisticsItemById(LogisticsModel obj);
}
