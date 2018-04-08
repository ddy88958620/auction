package com.trump.auction.activity.api;

import com.cf.common.utils.ServiceResult;
import com.trump.auction.activity.model.UserSignModel;

/**
 * 用户签到相关
 * @author wangbo 2017/12/21.
 */
public interface UserSignStubService {
    /**
     * 用户签到（表中无记录，新增；表中有记录，更新），并发放积分
     * @param userId 用户id
     * @return ServiceResult
     */
    ServiceResult userSign(Integer userId);

    /**
     * 根据用户id查询签到信息
     * @param userId 用户id
     * @return 签到信息
     */
    UserSignModel findUserSignByUserId(Integer userId);

    /**
     * 检查当天是否已签到
     * @param userId 用户id
     * @return ServiceResult
     */
    ServiceResult checkIsSigned(Integer userId);
}
