package com.trump.auction.back.push.dao.write;

import com.trump.auction.back.push.model.NotificationRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Author: zhanping
 */
@Repository
public interface NotificationRecordDao {
    /**
     * 添加一条推送
     * @param obj
     * @return
     */
    int add(NotificationRecord obj);

    /**
     * 删除一条推送记录
     * @param id
     * @return
     */
    int delete(@Param("id") Integer id);
}
