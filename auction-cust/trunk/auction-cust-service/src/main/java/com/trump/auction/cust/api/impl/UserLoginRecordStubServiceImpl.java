package com.trump.auction.cust.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.utils.ServiceResult;
import com.trump.auction.cust.api.UserLoginRecordStubService;
import com.trump.auction.cust.model.UserLoginRecordModel;
import com.trump.auction.cust.service.UserLoginRecordService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author=hanliangliang
 * @Date=2018/03/15
 */
@Service(version = "1.0.0")
public class UserLoginRecordStubServiceImpl implements UserLoginRecordStubService {

    @Autowired
    private UserLoginRecordService userLoginRecordService;
    @Autowired
    private BeanMapper beanMapper;

    @Override
    public List<UserLoginRecordModel> selectRecordByUserId(Integer id) {
        return beanMapper.mapAsList(userLoginRecordService.selectRecordByUserId(id),UserLoginRecordModel.class);
    }

    @Override
    public ServiceResult loginRecord(UserLoginRecordModel userLoginRecordModel) {
        return userLoginRecordService.loginRecord(userLoginRecordModel);
    }

    @Override
    public Integer countRecordByUserId(Integer id) {
        return userLoginRecordService.countRecordByUserId(id);
    }
}
