package com.trump.auction.activity.service.impl;

import com.cf.common.util.mapping.BeanMapper;
import com.trump.auction.activity.dao.ActivityShareDao;
import com.trump.auction.activity.model.ActivityShareModel;
import com.trump.auction.activity.service.ActivityShareService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: 分享活动
 * @author: zhangqingqiang
 * @date: 2018-03-20 15:08
 **/
@Service
@Slf4j
public class ActivityShareServiceImpl implements ActivityShareService {

    @Autowired
    private ActivityShareDao activityShareDao;
    @Autowired
    private BeanMapper beanMapper;

    /**
     * 获取活动信息
     * @param activityId 活动id(非主键)
     * @return 一条活动信息
     */
    @Override
    public ActivityShareModel getActivityInfo(String activityId) {
        return beanMapper.map(activityShareDao.getActivityInfo(activityId),ActivityShareModel.class);
    }

    /**
     * 根据不同入口获取活动信息
     * @param entrance 活动入口
     * @return
     */
    @Override
    public ActivityShareModel getActivityByEntrance(Integer entrance) {
        return beanMapper.map(activityShareDao.getActivityByEntrance(entrance),ActivityShareModel.class);
    }
}
