package com.trump.auction.back.activity.service.impl;

import com.cf.common.id.IdGenerator;
import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.cf.common.utils.ServiceResult;
import com.github.pagehelper.PageHelper;
import com.trump.auction.activity.model.ActivityShareModel;
import com.trump.auction.back.activity.dao.read.ActivityShareReadDao;
import com.trump.auction.back.activity.model.ActivityShare;
import com.trump.auction.back.activity.service.ShareActivityService;
import com.trump.auction.back.product.vo.ParamVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @description: 分享活动
 * @author: zhangqingqiang
 * @date: 2018-03-21 13:20
 **/
@Service
public class ShareActivityServiceImpl implements ShareActivityService{
    @Autowired
    private ActivityShareReadDao activityShareReadDao;
    @Autowired
    private BeanMapper beanMapper;

    @Override
    public Paging<ActivityShare> getActivityList(HashMap<String,Object> params) {
        PageHelper.startPage(Integer.valueOf(params.get("page").toString()),Integer.valueOf(params.get("limit").toString()));
        return PageUtils.page(activityShareReadDao.getActivityList(params));
    }

    @Override
    public ServiceResult saveActivity(ActivityShare activity) {
        String code = "101";
        String msg = "操作失败";

        int count = activityShareReadDao.saveActivity(activity);
        if (count==1) {
            code = "200";
            msg = "操作成功";
        }
        return new ServiceResult(code,msg);
    }

    /**
     * 获取活动信息
     * @param activityId 主键
     * @return 一条活动信息
     */
    @Override
    public ActivityShareModel getActivityInfo(Integer activityId) {
        return beanMapper.map(activityShareReadDao.selectByPrimaryKey(activityId),ActivityShareModel.class);
    }

    @Override
    public ServiceResult updateActivity(ActivityShare activity) {
        String code = "101";
        String msg = "修改失败";

        int count = activityShareReadDao.updateByPrimaryKeySelective(activity);
        if (count==1) {
            code = "200";
            msg = "操作成功";
        }
        return new ServiceResult(code,msg);
    }
}
