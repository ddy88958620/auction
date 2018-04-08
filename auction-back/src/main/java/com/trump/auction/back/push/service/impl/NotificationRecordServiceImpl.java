package com.trump.auction.back.push.service.impl;

import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.github.pagehelper.PageHelper;
import com.trump.auction.back.push.dao.read.NotificationDeviceReadDao;
import com.trump.auction.back.push.dao.read.NotificationRecordReadDao;
import com.trump.auction.back.push.dao.write.NotificationRecordDao;
import com.trump.auction.back.push.enums.NotificationRecordNotiTypeEnum;
import com.trump.auction.back.push.model.NotificationRecord;
import com.trump.auction.back.push.service.NotificationRecordService;
import com.trump.auction.back.push.utils.UmengPushUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: zhanping
 */
@Service
public class NotificationRecordServiceImpl implements NotificationRecordService {

    @Autowired
    private NotificationRecordReadDao notificationRecordReadDao;
    @Autowired
    private NotificationRecordDao notificationRecordDao;
    @Autowired
    private NotificationDeviceReadDao notificationDeviceReadDao;

    @Override
    public Paging<NotificationRecord> list(HashMap<String, Object> params) {
        PageHelper.startPage(Integer.valueOf(String.valueOf(params.get("page") == null ? 1 : params.get("page"))),
                Integer.valueOf(String.valueOf(params.get("limit") == null ? 10 : params.get("limit"))));
        return PageUtils.page(notificationRecordReadDao.list(params));
    }

    @Override
    public int add(NotificationRecord obj) throws Exception {

        /**
         * validate
         */
        Integer notiType = obj.getNotiType();
        switch (notiType){
            case 1: {
                String url = obj.getUrl();
                if (StringUtils.isBlank(url)){return 0;}
            }break;
            case 2: {
                Integer activityId = obj.getActivityId();
                if (activityId == null){return 0;}
            }break;
            case 3: {
                Integer getProductId = obj.getProductId();
                if (getProductId == null){return 0;}
            }break;
            default:{return 0;}
        }
        if (obj.getTimeType() == 2 && obj.getSendTime() == null){ return 0; }
        if (StringUtils.isBlank(obj.getTitle()) || StringUtils.isBlank(obj.getSubject())|| StringUtils.isBlank(obj.getContent())){return 0;}

        /**
         * 推送设备数量
         */
        obj.setDeviceCount(notificationDeviceReadDao.count());

        /**
         * 走友盟推
         */
        UmengPushUtil.sendBroadcast(obj);

        return  notificationRecordDao.add(obj);
    }

    @Override
    public int delete(Integer id) {
        return notificationRecordDao.delete(id);
    }

    @Override
    public NotificationRecord findById(Integer id) {
        return notificationRecordReadDao.findById(id);
    }
}
