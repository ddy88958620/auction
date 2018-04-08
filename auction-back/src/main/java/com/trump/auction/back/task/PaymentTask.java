package com.trump.auction.back.task;

import com.cf.common.utils.DateUtil;
import com.trump.auction.order.api.PaymentStubService;
import com.trump.auction.order.enums.EnumPayStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 定时处理待支付订单
 * @author: wangjian
 * @date: 2018-01-31
 **/
@Slf4j
@Service
public class PaymentTask {
    @Autowired
    private PaymentStubService paymentStubService;

    /**
     * 每5分钟查询支付的订单
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void changePaymentStatus(){
        log.info("changePaymentStatus begin");

        try {
            //用线程异步调用服务处理
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Date endDate = new Date();
                    Map<String,Object> map = new HashMap(4);
                    map.put("beginCreateDate", DateUtil.addHour(endDate, -2));
                    map.put("endCreateDate", endDate);
                    map.put("paymentStatus", EnumPayStatus.PAYING.getValue());
                    //payflag：1表示竞拍支付
                    map.put("payflag", 1);
                    paymentStubService.queryUnpaidInfoAndUpdate(map);
                }
            });
            thread.start();
        } catch (Exception e) {
            log.error("changePaymentStatus error:", e);
        }

        log.info("changePaymentStatus end");
    }
}
