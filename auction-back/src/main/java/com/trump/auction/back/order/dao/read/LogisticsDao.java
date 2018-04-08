package com.trump.auction.back.order.dao.read;

import com.trump.auction.back.order.model.Logistics;

import java.util.List;
import java.util.Map;

public interface LogisticsDao {

    /**
     * 根据订单号查询物流信息
     * @mbggenerated 2017-12-20
     */
    Logistics selectByPrimaryKey(String orderId);

    /**
     * 根据条件查询物流信息集合
     * @param logistics
     * @return
     */
    List<Logistics> findLogisticsInfoList(Map<String,Object> logistics);
}