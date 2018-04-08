package com.trump.auction.activity.api;

import com.trump.auction.activity.model.ActivityShareModel;

/**
 * @description: 分享活动
 * @author: zhangqingqiang
 * @date: 2018-03-20 15:08
 **/
public interface ActivityShareStubService {

    /**
     * 获取活动信息
     * @param activityId 活动id非主键
     * @return 一条活动信息
     */
    ActivityShareModel getActivityInfo(String activityId);

    /**
     * 根据不同入口获取活动信息
     * @param entrance 活动入口
     * @return
     */
    ActivityShareModel getActivityByEntrance(Integer entrance);
}