package com.trump.auction.activity.api.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.trump.auction.activity.api.ActivityShareStubService;
import com.trump.auction.activity.model.ActivityShareModel;
import com.trump.auction.activity.service.ActivityShareService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description: 分享活动
 * @author: zhangqingqiang
 * @date: 2018-03-20 15:08
 **/
@Service(version = "1.0.0")
public class ActivityShareStubServiceImpl implements ActivityShareStubService{

    @Autowired
    private ActivityShareService activityShareService;

    /**
     * 获取活动信息
     * @param activityId 活动id非主键
     * @return 一条活动信息
     */
    @Override
    public ActivityShareModel getActivityInfo(String activityId){
        return activityShareService.getActivityInfo(activityId);
    }

    /**
     * 根据不同入口获取活动信息
     * @param entrance 活动入口
     * @return
     */
    @Override
    public ActivityShareModel getActivityByEntrance(Integer entrance) {
        return activityShareService.getActivityByEntrance(entrance);
    }
}