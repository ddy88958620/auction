package com.trump.auction.cust.service;

import com.cf.common.utils.ServiceResult;
import com.trump.auction.cust.model.UserInfoModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserInfoService {

    /**
     * 根据用户id查询用户信息
     * @param userId 用户id
     * @return 用户信息
     */
    UserInfoModel findUserInfoById(Integer userId);

    /**
     * 根据ID查询用户wx(qq) openid  wx(qq)授权用户唯一标识、头像、手机号码、昵称
     * @param id
     * @return
     */
    UserInfoModel findUserIndexInfoById(Integer id);

    /**
     * 根据用户手机号查询用户信息
     * @param userPhone 用户手机号
     * @return 用户信息
     */
    UserInfoModel findUserInfoByUserPhone(String userPhone);

    /**
     * 保存用户信息
     * @param userInfoModel 用户信息
     * @return 用户id
     */
    UserInfoModel saveUserInfo(UserInfoModel userInfoModel);

    /**
     * 根据用户openId和第三方登录类型查询用户信息
     * @param openId 用户openId
     * @param type 第三方登录类型
     * @return 用户信息
     */
    UserInfoModel findUserInfoByOpenId(String openId,String type);

    /**
     * 更新用户的第三方信息
     * @param userInfoModel 用户信息
     * @return ServiceResult
     */
    ServiceResult updateThirdUserInfo(UserInfoModel userInfoModel);

    /**
     * 根据用户id，修改手机号
     * @param id
     * @return 成功返回1，失败返回0
     */
    int updateUserPhoneById(String  userPhone,Integer id);

    /**
     * 修改登录密码
     * @param loginPassword 登录密码
     * @param id 用户id
     * @return ServiceResult
     */
    ServiceResult updateLoginPasswordById(String loginPassword,Integer id);

    /**
     * 修改支付密码
     * @param payPassword 支付密码
     * @param id 用户id
     * @return ServiceResult
     */
    ServiceResult updatePayPasswordById(String payPassword,Integer id);

    /**
     * 修改用户充值类型及首充金额
     * @param rechargeType 充值类型
     * @param rechargeMoney 首充金额
     * @param id 用户id
     * @return ServiceResult
     */
    ServiceResult updateRechargeTypeById(Integer rechargeType,Integer rechargeMoney,Integer id);

    /**
     * 修改用户名及登录密码
     * @param realName 用户名
     * @param loginPassword 登录密码
     * @param id 用户id
     * @return ServiceResult
     */
    ServiceResult updateUserNameAndPwdById(String realName,String loginPassword,Integer id);

    /**
     * 修改登录类型
     * @param loginType 登录类型
     * @param id 用户id
     * @return ServiceResult
     */
    ServiceResult updateLoginTypeById(String loginType,Integer id);

    /**
     *  查询首充需要反币用户
     */
    List<UserInfoModel> findFirstRechargeList();

    /**
     * 修改地址信息
     * @param provinceName
     * @param cityName
     * @param id
     * @return
     */
    ServiceResult updateAddressById(String provinceName, String cityName, Integer id);

    /**
     * 修改用户头像/昵称
     * @param userInfoModel 用户信息
     * @return ServiceResult
     */
    ServiceResult updateUserInfo(UserInfoModel userInfoModel);

    /**
     * 获取用户登陆信息（设备号、设备名、应用市场等）
     * @param appInfo {"clientType":"xx","appVersion":"版本号","deviceId":"设备id","deviceName":"设备名","osVersion":"xx",
     *                   "appName":"app名称","appMarket":"应用市场名"}
     * @param userId
     * @return
     */
    ServiceResult updateAppInfo(String appInfo, Integer userId);

    /**
     * 修改用户信息
     * @param userInfoModel 用户信息
     * @return ServiceResult
     */
    ServiceResult updateUserInfoById(UserInfoModel userInfoModel);
}
