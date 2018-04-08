package com.trump.auction.back.order.service;


import com.cf.common.util.page.Paging;
import com.trump.auction.back.order.model.OrderInfo;

import java.util.Map;

/**
 * 订单管理
 * @author Created by wangjian on 2017/12/25.
 */
public interface OrderInfoService {

    /**
     * 分页查询订单列表
     * @param params
     * @return
     */
    Paging<OrderInfo> findOrderInfoPage(Map<String,Object> params);

    /**
     * 根据订单号查询订单详细信息
     * @param orderId
     * @return
     */
    OrderInfo findOrderInfoView(String orderId);

    /**
     * 根据用户ID查询订单数量
     * @param userId
     * @return
     */
    int countOrder(Integer userId);

}
