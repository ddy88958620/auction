package com.trump.auction.order.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cf.common.utils.ServiceResult;
import com.trump.auction.order.api.AppraisesSenstiveWordStubService;
import com.trump.auction.order.service.AppraisesSenstiveWordService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author=hanliangliang
 * @Date=2018/03/08
 */
@Service(version = "1.0.0")
public class AppraisesSenstiveWordStubServiceImpl implements AppraisesSenstiveWordStubService {

    @Autowired
    private AppraisesSenstiveWordService appraisesSenstiveWordService;

    @Override
    public ServiceResult checkAppraises(String appraises) {
        return appraisesSenstiveWordService.checkAppraises(appraises);
    }
}
