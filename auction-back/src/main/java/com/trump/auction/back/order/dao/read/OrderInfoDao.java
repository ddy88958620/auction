package com.trump.auction.back.order.dao.read;

import com.trump.auction.back.order.model.OrderInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderInfoDao {
    /**
     * 根据订单号查询订单详细信息
     * @mbggenerated 2017-12-20
     */
    OrderInfo selectByPrimaryKey(@Param("order_id") String id);

    /**
     * 根据条件查询订单信息集合
     * @param orderInfo
     * @return
     */
    List<OrderInfo> findOrderInfoList(Map<String,Object> orderInfo);

    /**
     * 根据用户ID查询订单数量
     * @param userId
     * @return
     */
    int countOrder(Integer userId);
}