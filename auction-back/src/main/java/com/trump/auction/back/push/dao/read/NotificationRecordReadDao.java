package com.trump.auction.back.push.dao.read;

import com.trump.auction.back.push.model.NotificationDevice;
import com.trump.auction.back.push.model.NotificationRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * Author: zhanping
 */
@Repository
public interface NotificationRecordReadDao {
    /**
     * 分页查询推送列表
     * @param params
     * @return
     */
    List<NotificationRecord> list(HashMap<String, Object> params);
    /**
     * 根据id查询推送详情
     * @param id
     * @return
     */
    NotificationRecord findById(@Param("id") Integer id);
}
