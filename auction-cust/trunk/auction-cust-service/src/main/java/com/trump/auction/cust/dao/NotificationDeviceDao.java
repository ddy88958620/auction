package com.trump.auction.cust.dao;

import com.trump.auction.cust.domain.NotificationDevice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationDeviceDao {
    /**
     * 根据设备id查找对应的推送设备
     * @param deviceId
     * @return
     */
    NotificationDevice findByDeviceId(@Param("deviceId") String deviceId);

    /**
     * 添加推送设备
     * @param obj
     * @return
     */
    int add(NotificationDevice obj);

    /**
     * 根据设备id更新对应的推送设备
     * @param obj
     * @return
     */
    int updateByDeviceId(NotificationDevice obj);
}