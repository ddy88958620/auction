package com.trump.auction.activity.api;

import com.trump.auction.activity.model.ActivityVideoCdkeysModel;

import java.util.List;
import java.util.Map;

/**
 * 视频会员兑换码相关
 * @author wangbo 2018/3/1.
 */
public interface ActivityVideoCdkeysStubService {
    /**
     * 批量插入视频卡券
     * @param cdkeysModelslist 待插入的集合
     * @return
     */
    Map<String,String> addVideoCdkeys(List<ActivityVideoCdkeysModel> cdkeysModelslist);
}
