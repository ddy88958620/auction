package com.trump.auction.activity.dao;


import com.trump.auction.activity.domain.ActivityShare;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityShareDao {

    /**
     * 获取活动信息
     * @param activityId 活动id非主键
     */
    ActivityShare getActivityInfo(String activityId);

    /**
     * 根据不同入口获取活动信息
     * @param entrance 活动入口
     * @return
     */
    ActivityShare getActivityByEntrance(@Param("shareEntrance") Integer entrance);
}