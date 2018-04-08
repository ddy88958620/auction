package com.trump.auction.cust.service.impl;

import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.utils.ServiceResult;
import com.trump.auction.cust.dao.UserLoginRecordDao;
import com.trump.auction.cust.domain.UserLoginRecord;
import com.trump.auction.cust.model.UserLoginRecordModel;
import com.trump.auction.cust.service.UserLoginRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author=hanliangliang
 * @Date=2018/03/15
 */
@Service
public class UserLoginRecordServiceImpl implements UserLoginRecordService {

    @Autowired
    private UserLoginRecordDao userLoginRecordDao;

    @Autowired
    private BeanMapper beanMapper;

    @Override
    public List<UserLoginRecord> selectRecordByUserId(Integer id) {
        return userLoginRecordDao.selectRecordByUserId(id);
    }

    @Override
    public ServiceResult loginRecord(UserLoginRecordModel userLoginRecordModel) {
        UserLoginRecord userLoginRecord=beanMapper.map(userLoginRecordModel,UserLoginRecord.class);
        int count = userLoginRecordDao.insertLoginRecord(userLoginRecord);
        if (count==1) {
            return new ServiceResult(ServiceResult.SUCCESS,"操作成功");
        }
        return new ServiceResult(ServiceResult.FAILED,"操作失败");
    }

    @Override
    public Integer countRecordByUserId(Integer id) {
        return userLoginRecordDao.countRecordByUserId(id);
    }

}
