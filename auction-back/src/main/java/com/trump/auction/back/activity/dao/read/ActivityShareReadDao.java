package com.trump.auction.back.activity.dao.read;


import com.trump.auction.back.activity.model.ActivityShare;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface ActivityShareReadDao {

    int insert(ActivityShare record);

    int insertSelective(ActivityShare record);

    ActivityShare selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ActivityShare record);

    int updateByPrimaryKey(ActivityShare record);

    List<ActivityShare> getActivityList(HashMap<String, Object> params);

    int saveActivity(ActivityShare activity);
    /**
     * 获取活动信息
     * @param activityId 活动id非主键
     */
    ActivityShare getActivityInfo(String activityId);
}