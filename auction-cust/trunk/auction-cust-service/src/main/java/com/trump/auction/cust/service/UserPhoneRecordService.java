package com.trump.auction.cust.service;

import com.trump.auction.cust.domain.UserPhoneRecord;

import java.util.List;

/**
 * @Author=hanliangliang
 * @Date=2018/03/15
 */
public interface UserPhoneRecordService {
    /**
     * 新增用户手机换绑记录
     * @param userPhone 手机号码
     * @param userLastPhone 上一次手机号码
     * @param id 用户id
     * @return 成功返回1，失败返回0
     */
    int insertUserPhoneRecord(String  userPhone, String userLastPhone,Integer id);

    /**
     * 查询换绑所有记录
     * @return
     */
    List<UserPhoneRecord> selectAll();

    /**
     * 根据用户id查询换绑记录
     * @param id 用户id
     * @return
     */
    List<UserPhoneRecord> selectRecordById(Integer id);
}
