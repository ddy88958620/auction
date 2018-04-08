package com.trump.auction.back.frontUser.service;

import com.cf.common.util.page.Paging;
import com.trump.auction.back.frontUser.model.UserLoginRecord;

import java.util.HashMap;

/**
 * @Author=hanliangliang
 * @Date=2018/03/19
 */
public interface UserLoginService {

    /**根据用户id查询用户日志记录*/
    Paging<UserLoginRecord> getLoginRecord(HashMap<String, Object> params);

    /**用户所有日志记录*/
    Paging<UserLoginRecord> getLoginRecordAll(HashMap<String, Object> params);
    /**
     * 统计登陆次数
     *  @param id 用户id
     * @return 登陆次数
     */
    Integer countRecordByUserId(Integer id);
}
