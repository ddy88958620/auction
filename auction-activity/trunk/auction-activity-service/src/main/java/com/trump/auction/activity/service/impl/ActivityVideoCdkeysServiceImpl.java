package com.trump.auction.activity.service.impl;

import com.alibaba.fastjson.JSON;
import com.cf.common.util.mapping.BeanMapper;
import com.trump.auction.activity.dao.ActivityVideoCdkeysDao;
import com.trump.auction.activity.domain.ActivityVideoCdkeys;
import com.trump.auction.activity.model.ActivityVideoCdkeysModel;
import com.trump.auction.activity.service.ActivityVideoCdkeysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 视频会员兑换码业务相关
 * @author wangbo 2018/3/1.
 */
@Service
public class ActivityVideoCdkeysServiceImpl implements ActivityVideoCdkeysService {
    @Autowired
    private BeanMapper beanMapper;
    @Autowired
    private ActivityVideoCdkeysDao videoCdkeysDao;

    @Override
    public Map<String, String> insertVideoCdkeys(List<ActivityVideoCdkeysModel> cdkeysModelslist) {
        Map<String, String> map = new HashMap<String, String>();
        List<ActivityVideoCdkeys> cdkeysList = beanMapper.mapAsList(cdkeysModelslist, ActivityVideoCdkeys.class);
        List<String> list = videoCdkeysDao.selectVideoCdkeysByKey(cdkeysList);
        if (list == null || list.size() == 0) {
            videoCdkeysDao.insertVideoCdkeys(cdkeysList);
            map.put("code", "0");
        } else {
            map.put("code", "-1");
            map.put("msg", JSON.toJSONString(list));
        }
        return map;
    }
}
