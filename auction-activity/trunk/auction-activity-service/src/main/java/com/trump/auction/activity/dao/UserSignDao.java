package com.trump.auction.activity.dao;

import com.trump.auction.activity.domain.UserSign;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author wangbo 2017/12/21.
 */
@Repository
public interface UserSignDao {
    /**
     * 新增用户签到信息
     * @param userSign 用户签到信息
     * @return 影响的行数
     */
    int insertUserSign(UserSign userSign);

    /**
     * 更新用户签到信息
     * @param userSign 用户签到信息
     * @return 影响的行数
     */
    int updateUserSign(UserSign userSign);

    /**
     * 根据用户id查询签到信息
     * @param userId 用户id
     * @return 签到信息
     */
    UserSign selectUserSignByUserId(@Param("userId") Integer userId);

    /**
     * 检查当天是否已签到
     * @param userId 用户id
     * @param todayDate 当天的日期
     * @return 记录行数（0：未签到；1：已签到）
     */
    int checkIsSigned(@Param("userId") Integer userId, @Param("todayDate") String todayDate);
}
