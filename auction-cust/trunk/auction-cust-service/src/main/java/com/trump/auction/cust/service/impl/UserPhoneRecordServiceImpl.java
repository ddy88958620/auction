package com.trump.auction.cust.service.impl;

import com.trump.auction.cust.dao.UserPhoneRecordDao;
import com.trump.auction.cust.domain.UserPhoneRecord;
import com.trump.auction.cust.service.UserPhoneRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author=hanliangliang
 * @Date=2018/03/15
 */
@Service
public class UserPhoneRecordServiceImpl implements UserPhoneRecordService {

    @Autowired
    private UserPhoneRecordDao usertPhoneRecord;

    @Override
    public List<UserPhoneRecord> selectRecordById(Integer id) {
        return usertPhoneRecord.selectRecordById(id);
    }

    @Override
    public int insertUserPhoneRecord(String userPhone,String userLastPhone ,Integer id) {
        if (userPhone.equals(userLastPhone)){
            return 0;
        }
        UserPhoneRecord record= new UserPhoneRecord();
        record.setUserPhone(userPhone);
        record.setUserLastPhone(userLastPhone);
        record.setUserId(id);
        return usertPhoneRecord.insertUserPhoneRecord(record);
    }

    @Override
    public List<UserPhoneRecord> selectAll() {
        return usertPhoneRecord.selectAll();
    }
}
