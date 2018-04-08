package com.trump.auction.back.activity.dao.read;

import com.trump.auction.back.activity.model.ActivityVideoCdkeys;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 视频会员激活码数据读取
 * @author wangbo 2018/2/26.
 */
@Repository
public interface ActivityVideoCdkeysReadDao {
    /**
     * 根据条件查询视频会员激活码列表
     * @param activityVideoCdkeys 查询条件
     * @return 视频会员激活码列表
     */
    List<ActivityVideoCdkeys> selectVideoCdkeysList(ActivityVideoCdkeys activityVideoCdkeys);
}
