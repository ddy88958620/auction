package com.trump.auction.cust.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.trump.auction.cust.api.NotificationDeviceStubService;
import com.trump.auction.cust.model.NotificationDeviceModel;
import com.trump.auction.cust.service.NotificationDeviceService;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Author: zhanping
 */
@Service(version = "1.0.0")
public class NotificationDeviceStubServiceImpl implements NotificationDeviceStubService {

    @Autowired
    private NotificationDeviceService notificationDeviceService;

    @Override
    public int save(NotificationDeviceModel obj) {
        return notificationDeviceService.save(obj);
    }
}
