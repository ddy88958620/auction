package com.trump.auction.back.activity.service;

import com.cf.common.util.page.Paging;
import com.trump.auction.back.activity.model.ActivityVideoCdkeys;

/**
 * 视频会员激活码相关
 * @author wangbo 2018/2/26.
 */
public interface ActivityVideoCdkeysService {
    /**
     * 根据条件分页查询视频会员激活码列表
     * @param activityVideoCdkeys 查询条件
     * @return 视频会员激活码列表
     */
    Paging<ActivityVideoCdkeys> findVideoCdkeysList(ActivityVideoCdkeys activityVideoCdkeys);
}
