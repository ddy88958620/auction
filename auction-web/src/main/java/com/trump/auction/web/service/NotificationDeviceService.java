package com.trump.auction.web.service;

import com.trump.auction.cust.model.NotificationDeviceModel;
import com.trump.auction.web.util.HandleResult;

/**
 * Author: zhanping
 */
public interface NotificationDeviceService {
    /**
     * 根据设备id添加或者更新对应的推送设备
     * @param obj
     * @return
     */
    HandleResult save(NotificationDeviceModel obj);
}
