package com.trump.auction.cust.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cf.common.util.page.Paging;
import com.cf.common.utils.ServiceResult;
import com.trump.auction.cust.api.UserProductCollectStubService;
import com.trump.auction.cust.model.UserProductCollectModel;
import com.trump.auction.cust.service.UserProductCollectService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wangYaMin on 2017/12/22.
 */
@Service(version = "1.0.0")
public class UserProductCollectStubServiceImpl implements UserProductCollectStubService {

    @Autowired
    private UserProductCollectService userProductCollectService;

    @Override
    public Paging<UserProductCollectModel> findUserProductCollectPage(UserProductCollectModel userProductCollect, Integer pageNum, Integer pageSize) {
        return userProductCollectService.findUserProductCollectPage(userProductCollect,pageNum,pageSize);
    }

    @Override
    public ServiceResult saveUserProductCollect(UserProductCollectModel obj) {
        return userProductCollectService.saveUserProductCollect(obj);
    }

    @Override
    public ServiceResult updateUserProductCollect(String status, Integer id) {
        return userProductCollectService.updateUserProductCollect(status,id);
    }

    @Override
    public boolean checkUserProductCollect(Integer userId, Integer productId, Integer periodsId) {
        return userProductCollectService.checkUserProductCollect(userId,productId,periodsId);
    }

    @Override
    public ServiceResult cancelUserProductCollect(Integer userId, Integer productId, Integer periodsId) {
        return userProductCollectService.cancelUserProductCollect(userId, productId, periodsId);
    }


}
