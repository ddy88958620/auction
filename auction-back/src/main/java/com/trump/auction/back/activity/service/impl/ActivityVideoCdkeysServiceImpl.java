package com.trump.auction.back.activity.service.impl;

import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.github.pagehelper.PageHelper;
import com.trump.auction.back.activity.dao.read.ActivityVideoCdkeysReadDao;
import com.trump.auction.back.activity.model.ActivityVideoCdkeys;
import com.trump.auction.back.activity.service.ActivityVideoCdkeysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 视频会员激活码相关
 * @author wangbo 2018/2/26.
 */
@Service
public class ActivityVideoCdkeysServiceImpl implements ActivityVideoCdkeysService {
    @Autowired
    private ActivityVideoCdkeysReadDao activityVideoCdkeysReadDao;

    @Override
    public Paging<ActivityVideoCdkeys> findVideoCdkeysList(ActivityVideoCdkeys activityVideoCdkeys) {
        PageHelper.startPage(activityVideoCdkeys.getPageNum()==null ? 0 : activityVideoCdkeys.getPageNum(),
                activityVideoCdkeys.getNumPerPage()==null ? 10: activityVideoCdkeys.getNumPerPage());
        return PageUtils.page(activityVideoCdkeysReadDao.selectVideoCdkeysList(activityVideoCdkeys));
    }
}
