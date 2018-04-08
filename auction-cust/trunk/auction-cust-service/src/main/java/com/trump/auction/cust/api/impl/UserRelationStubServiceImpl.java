package com.trump.auction.cust.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cf.common.utils.ServiceResult;
import com.trump.auction.cust.api.UserRelationStubService;
import com.trump.auction.cust.model.UserRelationModel;
import com.trump.auction.cust.service.UserRelationService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description: 用户关系
 * @author: zhangqingqiang
 * @date: 2018-03-31 14:38
 **/
@Service(version = "1.0.0")
public class UserRelationStubServiceImpl implements UserRelationStubService {
    @Autowired
    private UserRelationService userRelationService;

    @Override
    public ServiceResult saveRelation(UserRelationModel userRelationModel) {
        return userRelationService.saveRelation(userRelationModel);
    }

    @Override
    public UserRelationModel selectPid(Integer userId) {
        return userRelationService. selectPid(userId);
    }
}
