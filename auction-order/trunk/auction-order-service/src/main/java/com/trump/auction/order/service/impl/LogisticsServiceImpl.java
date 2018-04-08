package com.trump.auction.order.service.impl;

import com.cf.common.utils.ServiceResult;
import com.trump.auction.order.dao.LogisticsDao;
import com.trump.auction.order.domain.Logistics;
import com.trump.auction.order.enums.EnumLogisticsStatus;
import com.trump.auction.order.enums.EnumOrderStatus;
import com.trump.auction.order.model.OrderInfoModel;
import com.trump.auction.order.service.LogisticsService;
import com.trump.auction.order.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class LogisticsServiceImpl implements LogisticsService {

    @Autowired
    private LogisticsDao logisticsDao;

    @Autowired
    private OrderInfoService orderInfoService;

    @Override
    public Logistics selectByPrimaryKey(String orderId){
        long startTime = System.currentTimeMillis();
        log.info("selectByPrimaryKey invoke,StartTime:{},params:{}", startTime, orderId);

        if (null == orderId) {
            throw new IllegalArgumentException("selectByPrimaryKey param orderId is null!");
        }

        Logistics logistics = null;
        try {
            logistics = logisticsDao.selectByPrimaryKey(orderId);
        } catch (Exception e) {
            log.error("selectByPrimaryKey", e);
        }

        long endTime = System.currentTimeMillis();
        log.info("selectByPrimaryKey end,duration:{}", endTime - startTime);
        return logistics;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int insert(Logistics obj){
        long startTime = System.currentTimeMillis();
        log.info("insert invoke,StartTime:{},params:{}", startTime, obj);

        if (null == obj) {
            throw new IllegalArgumentException("insert param logisticsModel is null!");
        }

        int executeCount = 0;
        try {
            obj.setLogisticsStatus(EnumLogisticsStatus.UNDISPATCH.getValue());
            executeCount = logisticsDao.insert(obj);
        } catch (Exception e) {
            log.error("insert error:", e);
            throw new RuntimeException(e);
        }

        long endTime = System.currentTimeMillis();
        log.info("insert end,duration:{}", endTime - startTime);
        return executeCount;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int updateByPrimaryKeySelective(Logistics obj){
        long startTime = System.currentTimeMillis();
        log.info("updateByPrimaryKeySelective invoke,StartTime:{},params:{}", startTime, obj);

        if (null == obj) {
            throw new IllegalArgumentException("updateByPrimaryKeySelective param logisticsModel is null!");
        }

        int executeCount = 0;
        try {
            executeCount = logisticsDao.updateByPrimaryKeySelective(obj);

            //发货>>同步更新订单状态为：已发货
            if (executeCount > 0) {
                if (EnumLogisticsStatus.DISPATCHED.getValue().equals(obj.getLogisticsStatus())) {
                    OrderInfoModel orderInfoModel = new OrderInfoModel();
                    orderInfoModel.setOrderId(obj.getOrderId());
                    orderInfoModel.setOrderStatus(EnumOrderStatus.SHIPPED.getValue());
                    ServiceResult serviceResult = orderInfoService.updateOrderStatus(orderInfoModel);

                    //更新失败则回滚
                    if (!ServiceResult.SUCCESS.equals(serviceResult.getCode())) {
                        throw new RuntimeException();
                    }
                }
            }
        } catch (Exception e) {
            log.error("updateByPrimaryKeySelective error:", e);
            throw new RuntimeException(e);
        }

        long endTime = System.currentTimeMillis();
        log.info("updateByPrimaryKeySelective end,duration:{}", endTime - startTime);
        return executeCount;
    }
}
