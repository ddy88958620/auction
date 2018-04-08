package com.trump.auction.activity.service;

import com.cf.common.utils.ServiceResult;
import com.trump.auction.activity.model.ActivityUserModel;

/**
 * 活动用户相关
 * @author wangbo 2018/1/11.
 */
public interface ActivityUserService {
    /**
     * 初始化活动用户
     * @param activityUserModel 活动用户信息
     * @return 受影响的行数
     */
    ServiceResult initActivityUser(ActivityUserModel activityUserModel);

    /**
     * 根据userId查询活动用户信息
     * @param userId 用户id
     * @return 活动用户信息
     */
    ActivityUserModel findActivityUserByUserId(Integer userId);

    /**
     * 查询用户剩余的免费抽奖次数
     * @param userId 用户id
     * @return 免费抽奖次数
     */
    int findFreeLotteryTimes(Integer userId);

    /**
     * 更新用户的免费抽奖次数
     * @param userId 用户id
     * @param freeLotteryTimes 免费抽奖次数
     * @param freeLotteryTimesOld 更新前的免费抽奖次数
     * @return ServiceResult
     */
    ServiceResult updateFreeLotteryTimes(Integer userId,int freeLotteryTimes,int freeLotteryTimesOld);
}
