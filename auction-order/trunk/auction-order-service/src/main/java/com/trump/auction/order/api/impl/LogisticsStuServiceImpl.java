package com.trump.auction.order.api.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.cf.common.util.mapping.BeanMapper;
import com.trump.auction.order.api.LogisticsStuService;
import com.trump.auction.order.domain.Logistics;
import com.trump.auction.order.model.LogisticsModel;
import com.trump.auction.order.service.LogisticsService;

/**
 * 物流信息相关服务
 * @author zhanping
 */
@Service(version = "1.0.0")
public class LogisticsStuServiceImpl implements LogisticsStuService {

    @Autowired
    LogisticsService logisticsService;
    @Autowired
    private BeanMapper beanMapper;

    /**
     * 根据订单ID查询物流信息
     * @param orderId 订单ID
     * @return
     */
    public LogisticsModel findLogisticsItemById(String orderId){
        return beanMapper.map(logisticsService.selectByPrimaryKey(orderId),LogisticsModel.class);
    }

    /**
     * 新增物流信息
     * @param obj
     * @return
     */
    public int insertLogisticsItem(LogisticsModel obj){
        return logisticsService.insert(beanMapper.map(obj,Logistics.class));
    }

    /**
     * 根据订单ID更新物流信息
     * @param obj
     * @return
     */
    public int updateLogisticsItemById(LogisticsModel obj){
        return logisticsService.updateByPrimaryKeySelective(beanMapper.map(obj,Logistics.class));
    }
}
