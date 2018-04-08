package com.trump.auction.cust.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cf.common.utils.ServiceResult;
import com.trump.auction.cust.api.UserSenstiveWordStubService;
import com.trump.auction.cust.service.UserSenstiveWordService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author=hanliangliang
 * @Date=2018/03/08
 */
@Service(version = "1.0.0")
public class UserSenstiveWordStubServiceImpl implements UserSenstiveWordStubService {

    @Autowired
    private UserSenstiveWordService userSenstiveWordService;

    @Override
    public ServiceResult checkNickName(String nickName) {
        return userSenstiveWordService.checkNickName(nickName);
    }
}
