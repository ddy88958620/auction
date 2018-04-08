package com.trump.auction.cust.service;

import com.cf.common.utils.ServiceResult;

public interface UserSenstiveWordService {

    /**
     * 检验用户名称是否合法
     * @param nickName 用户名(昵称)
     * @return code 0:成功  500:错误
     * @return msg  提示信息
     * @return ext  处理后的用户名
     */
    ServiceResult checkNickName(String nickName);
}
