package com.trump.auction.activity.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cf.common.utils.ServiceResult;
import com.trump.auction.activity.api.UserSignStubService;
import com.trump.auction.activity.model.UserSignModel;
import com.trump.auction.activity.service.UserSignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangbo 2017/12/22.
 */
@Slf4j
@Service(version = "1.0.0")
public class UserSignStubServiceImpl implements UserSignStubService {
    @Autowired
    private UserSignService userSignService;

    @Override
    public ServiceResult userSign(Integer userId) {
        try {
            return userSignService.userSign(userId);
        } catch (Exception e) {
            log.error("user sign failed,userId:{}",userId,e);
            return new ServiceResult(ServiceResult.FAILED,"签到失败");
        }
    }

    @Override
    public UserSignModel findUserSignByUserId(Integer userId) {
        return userSignService.findUserSignByUserId(userId);
    }

    @Override
    public ServiceResult checkIsSigned(Integer userId) {
        return userSignService.checkIsSigned(userId);
    }

}
