package com.trump.auction.cust.api;

import com.cf.common.utils.ServiceResult;

/**
 * 用户昵称敏感词库相关服务
 * @author hanliangliang
 */
public interface UserSenstiveWordStubService {

    /**
     * 检验用户名称是否合法
     * @param nickName 用户昵称
     * @return ServiceResult
     * @return code 0:成功  500:错误,存在敏感词汇
     * @return msg  提示信息
     * @return ext  敏感词汇集合
     */
    ServiceResult checkNickName(String nickName);
}
