package com.trump.auction.back.frontUser.service;

import com.cf.common.util.page.Paging;
import com.trump.auction.back.frontUser.model.UserPhoneRecord;

import java.util.HashMap;

/**
 * @Author=hanliangliang
 * @Date=2018/03/21
 */
public interface UserPhoneRecordService {

    /**根据用户id查询用户换绑记录*/
    Paging<UserPhoneRecord> getPhoneRecord(HashMap<String,Object> params);
}
