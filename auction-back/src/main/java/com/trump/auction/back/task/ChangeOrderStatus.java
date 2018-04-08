package com.trump.auction.back.task;

import com.cf.common.utils.DateUtil;
import com.cf.common.utils.ServiceResult;
import com.trump.auction.order.api.OrderInfoStubService;
import com.trump.auction.order.enums.EnumOrderStatus;
import com.trump.auction.order.model.OrderInfoModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @description: 更新订单状态
 * @author: wangjian
 * @date: 2017-12-26
 **/
@Slf4j
@Service
public class ChangeOrderStatus {

    @Autowired
    private OrderInfoStubService orderInfoStubService;


    /**
     * 每5分钟扫描五天内下单后三天未支付订单
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void cancelOrder() {

        try {
            //查出超时未支付订单
            List<String> orderIds = orderInfoStubService.queryUnpaidOrders(DateUtil.addDay(new Date(),-3),DateUtil.addDay(new Date(),-5),getDate(23,59,59));

            if (CollectionUtils.isEmpty(orderIds)){
                return;
            }

            for (String orderId:orderIds) {
                log.info("restore inventory.....orderId:{}",orderId);
                //更新状态
                OrderInfoModel orderInfo = new OrderInfoModel();
                orderInfo.setOrderStatus(EnumOrderStatus.CLOSE.getValue());
                orderInfo.setOrderId(orderId);
                orderInfo.setUserId(10000);
                orderInfo.setUserIp("定时取消订单还库存");

                ServiceResult updateResult = orderInfoStubService.updateOrderStatus(orderInfo);

                if (!"200".equals(updateResult.getCode())){
                    log.error("cancelOrder failed orderId:", orderId);
                    continue;
                }
                log.info("cancelOrder success orderId:", orderId);
            }
        } catch (Exception e) {
            log.error("restore inventory error.....message:{}",e);
        }
        log.info("restore inventory end...");
    }

    private static Date getDate(int hour,int minute,int second){
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DAY_OF_MONTH),
                hour, minute, second);
        return calendar1.getTime();
    }
}
