package com.trump.auction.web.service.impl;

import com.trump.auction.cust.api.NotificationDeviceStubService;
import com.trump.auction.cust.model.NotificationDeviceModel;
import com.trump.auction.web.service.NotificationDeviceService;
import com.trump.auction.web.util.HandleResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: zhanping
 */
@Service
public class NotificationDeviceServiceImpl implements NotificationDeviceService {
    @Autowired
    private NotificationDeviceStubService notificationDeviceStubService;

    @Override
    public HandleResult save(NotificationDeviceModel obj) {
        HandleResult handleResult = new HandleResult(false);
        if (obj.getDeviceId() == null || StringUtils.isBlank(obj.getDeviceId())) {
            return handleResult.setCode(1).setMsg("deviceId为空");
        }
        if (obj.getDeviceTokenUmeng() == null || StringUtils.isBlank(obj.getDeviceTokenUmeng())) {
            return handleResult.setCode(1).setMsg("deviceToken为空");
        }
        if (obj.getDeviceType() == null) {
            return handleResult.setCode(1).setMsg("设备类型为空或无效");
        }
        int count = notificationDeviceStubService.save(obj);
        if (count == 1){
            handleResult.setResult(true).setCode(0);
        }
        return handleResult;
    }
}
