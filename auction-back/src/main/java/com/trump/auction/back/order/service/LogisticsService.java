package com.trump.auction.back.order.service;


import com.cf.common.util.page.Paging;
import com.trump.auction.back.order.model.Logistics;

import java.util.Map;

/**
 * 物流管理
 * @author Created by wangjian on 2017/12/25.
 */
public interface LogisticsService {

    /**
     * 分页查询物流列表
     * @param params
     * @return
     */
    Paging<Logistics> findLogisticsPage(Map<String, Object> params);

    /**
     * 发货
     * @param logistics
     * @return
     */
    boolean deliverGoods(Logistics logistics);
}
