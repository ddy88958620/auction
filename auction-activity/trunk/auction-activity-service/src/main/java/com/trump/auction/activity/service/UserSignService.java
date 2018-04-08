package com.trump.auction.activity.service;

import com.cf.common.utils.ServiceResult;
import com.trump.auction.activity.model.UserSignModel;

/**
 * 签到
 * @author wangbo 2017/12/21.
 */
public interface UserSignService {
    /**
     * 新增用户签到信息
     * @param userId 用户id
     * @return 影响的行数
     */
    int insertUserSign(Integer userId);

    /**
     * 更新用户签到信息
     * @param userSignModel 用户签到信息
     * @return 影响的行数
     */
    int updateUserSign(UserSignModel userSignModel);

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

    /**
     * 用户签到（表中无记录，新增；表中有记录，更新），并发放积分
     * @param userId 用户id
     * @return ServiceResult
     * @throws Exception 发放积分失败，抛出异常，回滚数据
     */
    ServiceResult userSign(Integer userId) throws Exception;

}
