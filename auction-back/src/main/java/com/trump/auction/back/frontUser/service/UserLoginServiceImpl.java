package com.trump.auction.back.frontUser.service;

import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.github.pagehelper.PageHelper;
import com.trump.auction.back.frontUser.dao.read.UserLoginRecordDao;
import com.trump.auction.back.frontUser.model.UserLoginRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @Author=hanliangliang
 * @Date=2018/03/19
 */
@Service
public class UserLoginServiceImpl implements  UserLoginService {

    @Autowired
    private UserLoginRecordDao userLoginRecordDao;
    @Override
    public Paging<UserLoginRecord> getLoginRecord(HashMap<String, Object> params) {
        int page=Integer.valueOf(String.valueOf(params.get("page")));
        int size=Integer.valueOf(String.valueOf(params.get("limit")));
        PageHelper.startPage(page,size );
        Integer.valueOf(String.valueOf(params.get("limit") == null ? 10 : params.get("limit")));
        return PageUtils.page(userLoginRecordDao.selectRecordByUserId(params));
    }


    @Override
    public Paging<UserLoginRecord> getLoginRecordAll(HashMap<String, Object> params) {
        int page=Integer.valueOf(String.valueOf(params.get("page")));
        int size=Integer.valueOf(String.valueOf(params.get("limit")));
        PageHelper.startPage(page,size );
        Integer.valueOf(String.valueOf(params.get("limit") == null ? 10 : params.get("limit")));
        return PageUtils.page(userLoginRecordDao.getLoginRecordAll());
    }

    @Override
    public Integer countRecordByUserId(Integer id) {
        return userLoginRecordDao.countRecordByUserId(id);
    }
}
