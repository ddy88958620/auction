package com.trump.auction.activity.dao;

import com.trump.auction.activity.domain.ActivityUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 活动用户相关
 * @author wangbo 2018/1/11.
 */
@Repository
public interface ActivityUserDao {
    /**
     * 插入一条活动用户记录
     * @param activityUser 活动用户信息
     * @return 受影响的行数
     */
    int insertActivityUser(ActivityUser activityUser);

    /**
     * 根据userId查询活动用户信息
     * @param userId 用户id
     * @return 活动用户信息
     */
    ActivityUser selectActivityUserByUserId(@Param("userId")Integer userId);

    /**
     * 更新用户的免费抽奖次数
     * @param userId 用户id
     * @param freeLotteryTimes 免费抽奖次数
     * @param freeLotteryTimesOld 更新前的免费抽奖次数
     * @return 受影响的行数
     */
    int updateFreeLotteryTimes(@Param("userId")Integer userId,@Param("freeLotteryTimes")int freeLotteryTimes,
                               @Param("freeLotteryTimesOld")int freeLotteryTimesOld);
}
