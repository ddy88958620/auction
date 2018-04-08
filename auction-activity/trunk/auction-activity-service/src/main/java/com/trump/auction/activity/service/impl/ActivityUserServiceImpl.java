package com.trump.auction.activity.service.impl;

import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.utils.DateUtil;
import com.cf.common.utils.ServiceResult;
import com.trump.auction.activity.constant.ActivityConstant;
import com.trump.auction.activity.dao.ActivityUserDao;
import com.trump.auction.activity.domain.ActivityUser;
import com.trump.auction.activity.model.ActivityUserModel;
import com.trump.auction.activity.service.ActivityUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 * 活动用户相关
 * @author wangbo 2018/1/11.
 */
@Service
@Slf4j
public class ActivityUserServiceImpl implements ActivityUserService{
    @Autowired
    private BeanMapper beanMapper;
    @Autowired
    private ActivityUserDao activityUserDao;
    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public ServiceResult initActivityUser(ActivityUserModel activityUserModel) {
        ActivityUser activityUser = activityUserDao.selectActivityUserByUserId(activityUserModel.getUserId());
        if (null==activityUser) {
            int count = activityUserDao.insertActivityUser(beanMapper.map(activityUserModel, ActivityUser.class));
            if (count==1) {
                return new ServiceResult(ServiceResult.SUCCESS,"操作成功");
            }
            return new ServiceResult(ServiceResult.FAILED,"操作失败");
        }
        return new ServiceResult(ServiceResult.SUCCESS,"操作成功");
    }

    @Override
    public ActivityUserModel findActivityUserByUserId(Integer userId) {
        return beanMapper.map(activityUserDao.selectActivityUserByUserId(userId),ActivityUserModel.class);
    }

    @Override
    public int findFreeLotteryTimes(Integer userId) {
        ActivityUser activityUser = activityUserDao.selectActivityUserByUserId(userId);
        Date now = new Date();
        int lotteryTimes = activityUser.getFreeLotteryTimes();
        try {
            // 没有抽过奖或者今天是第一次抽奖 - 做计算
            if (activityUser.getLastFreeTime() == null || DateUtil.daysBetween(activityUser.getLastFreeTime(), now) > 0) {
                Map<String, String> lotteryMap = jedisCluster.hgetAll(ActivityConstant.ACTIVITY_LOTTERY);
                int freeTimes = Integer.parseInt(lotteryMap.get(ActivityConstant.FREE_TIMES));
                // 更新免费抽奖次数
                int effectedRow = activityUserDao.updateFreeLotteryTimes(userId, freeTimes, activityUser.getFreeLotteryTimes());
                if (effectedRow > 0) {
                    //保险起见
                    activityUser = activityUserDao.selectActivityUserByUserId(userId);
                    lotteryTimes = activityUser.getFreeLotteryTimes();
                }
            }
        } catch (ParseException e) {
            log.error("findFreeLotteryTimes error", e);
        }
        return lotteryTimes;
    }

    @Override
    public ServiceResult updateFreeLotteryTimes(Integer userId, int freeLotteryTimes, int freeLotteryTimesOld) {
        int count = activityUserDao.updateFreeLotteryTimes(userId, freeLotteryTimes, freeLotteryTimesOld);
        if (count==1) {
            return new ServiceResult(ServiceResult.SUCCESS,"操作成功");
        }
        return new ServiceResult(ServiceResult.FAILED,"操作失败");
    }
}
