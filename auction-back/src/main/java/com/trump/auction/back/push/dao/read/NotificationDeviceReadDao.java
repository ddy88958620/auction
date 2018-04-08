package com.trump.auction.back.push.dao.read;

import com.trump.auction.back.push.model.NotificationDevice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface NotificationDeviceReadDao {

    List<NotificationDevice> list(HashMap<String, Object> params);

    /**
     * 查询当前推送设备数量
     * @return
     */
    int count ();


}