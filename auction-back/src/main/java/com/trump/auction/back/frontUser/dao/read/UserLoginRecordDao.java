package com.trump.auction.back.frontUser.dao.read;

import com.trump.auction.back.frontUser.model.UserLoginRecord;

import java.util.HashMap;
import java.util.List;

/**
 * @Author=hanliangliang
 * @Date=2018/03/19
 */
public interface UserLoginRecordDao {

    List<UserLoginRecord> selectRecordByUserId(HashMap<String, Object> params);

    List<UserLoginRecord> getLoginRecordAll();

    /**
     * 统计登陆次数
     *  @param id 用户id
     * @return 登陆次数
     */
    int countRecordByUserId(Integer id);
}
