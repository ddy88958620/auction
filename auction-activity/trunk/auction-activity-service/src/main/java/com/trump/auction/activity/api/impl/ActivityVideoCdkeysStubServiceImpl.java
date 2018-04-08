package com.trump.auction.activity.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.trump.auction.activity.api.ActivityVideoCdkeysStubService;
import com.trump.auction.activity.model.ActivityVideoCdkeysModel;
import com.trump.auction.activity.service.ActivityVideoCdkeysService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 视频会员兑换码相关
 * @author wangbo 2018/3/1.
 */
@Service(version = "1.0.0")
public class ActivityVideoCdkeysStubServiceImpl implements ActivityVideoCdkeysStubService {
    @Autowired
    private ActivityVideoCdkeysService activityVideoCdkeysService;

    @Override
    public Map<String, String> addVideoCdkeys(List<ActivityVideoCdkeysModel> cdkeysModelslist) {
        return activityVideoCdkeysService.insertVideoCdkeys(cdkeysModelslist);
    }
}
