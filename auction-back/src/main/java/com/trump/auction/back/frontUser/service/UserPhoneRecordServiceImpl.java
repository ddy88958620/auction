package com.trump.auction.back.frontUser.service;

import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.github.pagehelper.PageHelper;
import com.trump.auction.back.frontUser.dao.read.UserPhoneRecordDao;
import com.trump.auction.back.frontUser.model.UserPhoneRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @Author=hanliangliang
 * @Date=2018/03/21
 */
@Service
public class UserPhoneRecordServiceImpl implements  UserPhoneRecordService {

    @Autowired
    private UserPhoneRecordDao userPhoneRecordDao;
    @Override
    public Paging<UserPhoneRecord> getPhoneRecord(HashMap<String,Object> params) {
        PageHelper.startPage(Integer.valueOf(String.valueOf(params.get("page") == null ? 1 : params.get("page"))),
                Integer.valueOf(String.valueOf(params.get("limit") == null ? 10 : params.get("limit"))));
        return PageUtils.page(userPhoneRecordDao.selectPhoneRecordByUserId(params));
    }
}
