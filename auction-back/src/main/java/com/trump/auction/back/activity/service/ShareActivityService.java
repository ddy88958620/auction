package com.trump.auction.back.activity.service;

import com.cf.common.util.page.Paging;
import com.cf.common.utils.ServiceResult;
import com.trump.auction.activity.model.ActivityShareModel;
import com.trump.auction.back.activity.model.ActivityShare;
import com.trump.auction.back.product.vo.ParamVo;

import java.util.HashMap;

/**
 * @author: zhangqingqiang.
 * @date: 2018/3/21 0021.
 * @Description: 分享活动 .
 */
public interface ShareActivityService {
    Paging<ActivityShare> getActivityList(HashMap<String, Object> params);

    ServiceResult saveActivity(ActivityShare activity);

    /**
     * 获取活动信息
     * @param activityId 主键
     */
    ActivityShareModel getActivityInfo(Integer activityId);

    /**
     * 修改活动
     * @param activity
     * @return
     */
    ServiceResult updateActivity(ActivityShare activity);
}
