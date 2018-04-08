package com.trump.auction.back.frontUser.dao.read;

import com.trump.auction.back.frontUser.model.UserPhoneRecord;

import java.util.HashMap;
import java.util.List;

/**
 * @Author=hanliangliang
 * @Date=2018/03/21
 */
public interface UserPhoneRecordDao {
    /**
     * 统计换绑信息
     * @return 登陆次数
     */
    List<UserPhoneRecord> selectPhoneRecordByUserId(HashMap<String, Object> params);
}
