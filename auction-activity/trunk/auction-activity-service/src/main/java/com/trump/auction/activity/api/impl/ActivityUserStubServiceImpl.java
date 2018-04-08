package com.trump.auction.activity.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cf.common.utils.ServiceResult;
import com.trump.auction.activity.api.ActivityUserStubService;
import com.trump.auction.activity.model.ActivityUserModel;
import com.trump.auction.activity.service.ActivityUserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 活动用户相关服务
 * @author wangbo 2018/1/11.
 */
@Service(version = "1.0.0")
public class ActivityUserStubServiceImpl implements ActivityUserStubService {
    @Autowired
    private ActivityUserService activityUserService;

    @Override
    public ServiceResult initActivityUser(ActivityUserModel activityUserModel) {
        return activityUserService.initActivityUser(activityUserModel);
    }

    @Override
    public ActivityUserModel findActivityUserByUserId(Integer userId) {
        return activityUserService.findActivityUserByUserId(userId);
    }

    @Override
    public int findFreeLotteryTimes(Integer userId) {
        return activityUserService.findFreeLotteryTimes(userId);
    }

    @Override
    public ServiceResult updateFreeLotteryTimes(Integer userId, int freeLotteryTimes, int freeLotteryTimesOld) {
        return activityUserService.updateFreeLotteryTimes(userId, freeLotteryTimes, freeLotteryTimesOld);
    }
}
