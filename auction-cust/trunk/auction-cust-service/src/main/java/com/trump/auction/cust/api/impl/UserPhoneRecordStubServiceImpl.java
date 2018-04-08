package com.trump.auction.cust.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cf.common.util.mapping.BeanMapper;
import com.trump.auction.cust.api.UserPhoneRecordStubService;
import com.trump.auction.cust.model.UserPhoneRecordModel;
import com.trump.auction.cust.service.UserPhoneRecordService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author=hanliangliang
 * @Date=2018/03/15
 */
@Service(version = "1.0.0")
public class UserPhoneRecordStubServiceImpl implements UserPhoneRecordStubService {

    @Autowired
    private UserPhoneRecordService userPhoneRecordService;
    @Autowired
    private BeanMapper beanMapper;
    @Override
    public int insertUserPhoneRecord(String userPhone, String userLastPhone,Integer id) {
        return userPhoneRecordService.insertUserPhoneRecord(userPhone,userLastPhone,id);
    }

    @Override
    public List<UserPhoneRecordModel> selectAll() {
        return beanMapper.mapAsList(userPhoneRecordService.selectAll(), UserPhoneRecordModel.class);
    }

    @Override
    public List<UserPhoneRecordModel> selectRecordById(Integer id) {
        return beanMapper.mapAsList(userPhoneRecordService.selectRecordById(id), UserPhoneRecordModel.class);
    }
}
