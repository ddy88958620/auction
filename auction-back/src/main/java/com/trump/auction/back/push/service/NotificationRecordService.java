package com.trump.auction.back.push.service;

import com.cf.common.util.page.Paging;
import com.trump.auction.back.push.model.NotificationRecord;

import java.util.HashMap;

/**
 * Author: zhanping
 */
public interface NotificationRecordService {

    /**
     * 分页查询用户列表信息
     * @param params
     * @return 用户列表信息
     */
    Paging<NotificationRecord> list(HashMap<String, Object> params);

    /**
     * 新建推送
     * @param obj
     * @return
     */
    int add(NotificationRecord obj) throws Exception;

    /**
     * 删除一条推送记录
     * @param id
     * @return
     */
    int delete(Integer id);

    /**
     * 根据id查询详情
     * @param id
     * @return
     */
    NotificationRecord findById(Integer id);
}
