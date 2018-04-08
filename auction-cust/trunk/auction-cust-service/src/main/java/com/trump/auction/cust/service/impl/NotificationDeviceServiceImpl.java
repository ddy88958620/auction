package com.trump.auction.cust.service.impl;

import com.cf.common.util.mapping.BeanMapper;
import com.trump.auction.cust.dao.NotificationDeviceDao;
import com.trump.auction.cust.domain.NotificationDevice;
import com.trump.auction.cust.model.NotificationDeviceModel;
import com.trump.auction.cust.service.NotificationDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Author: zhanping
 */
@Service
public class NotificationDeviceServiceImpl implements NotificationDeviceService {

    @Autowired
    private BeanMapper beanMapper;
    @Autowired
    private NotificationDeviceDao notificationDeviceDao;

    @Override
    public int save(NotificationDeviceModel obj) {
        NotificationDevice device = beanMapper.map(obj, NotificationDevice.class);
        if (device.getDeviceId() == null || device.getDeviceTokenUmeng() == null){
            return 0;
        }
        NotificationDevice byDeviceId = notificationDeviceDao.findByDeviceId(device.getDeviceId());
        if (byDeviceId == null) {
            device.setCreateTime(new Date());
            device.setUpdateTime(new Date());
            int addCount = notificationDeviceDao.add(device);
            return addCount;
        }else {
            device.setUpdateTime(new Date());
            int updateCount = notificationDeviceDao.updateByDeviceId(device);
            return updateCount;
        }
    }
}
