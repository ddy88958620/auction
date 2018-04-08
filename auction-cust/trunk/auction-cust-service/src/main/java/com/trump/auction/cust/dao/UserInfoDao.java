package com.trump.auction.cust.dao;

import com.trump.auction.cust.domain.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoDao {
	 
	/**
	 * 根据id查询用户信息
	 * @param userId 用户id
	 * @return 用户信息
	 */
	UserInfo selectUserInfoById(@Param("id")Integer userId);

	/**
	 * 根据ID查询用户wx(qq) openid  wx(qq)授权用户唯一标识、头像、手机号码、昵称
	 * @param id
	 * @return
	 */
	UserInfo selectUserIndexInfoById(@Param("id")Integer id);

    /**
     * 根据用户手机号查询用户信息
     * @param userPhone 用户手机号
     * @return 用户信息
     */
    UserInfo selectUserInfoByUserPhone(@Param("userPhone")String userPhone);

    /**
     * 保存用户信息
     * @param userInfo 用户信息
     * @return 影响记录条数
     */
    int insertUserInfo(UserInfo userInfo);

	/**
	 * 根据用户openId和第三方登录类型查询用户信息
	 * @param openId 用户openId
	 * @param type 第三方登录类型
	 * @return 用户信息
	 */
	UserInfo selectUserInfoByOpenId(@Param("openId")String openId,@Param("type")String type);

	/**
	 * 更新用户的第三方信息
	 * @param userInfo 用户信息
	 * @return ServiceResult
	 */
	int updateThirdUserInfo(UserInfo userInfo);

	/**
	 * 根据用户id，修改手机号
	 * @param id
	 * @return 成功返回1，失败返回0
	 */
	int updateUserPhoneById(@Param("userPhone")String  userPhone,@Param("id")Integer id);

	/**
	 * 修改登录密码
	 * @param loginPassword 登录密码
	 * @param id 用户id
	 * @return 成功返回1，失败返回0
	 */
	int updateLoginPasswordById(@Param("loginPassword")String loginPassword,@Param("id")Integer id);

	/**
	 * 修改支付密码
	 * @param payPassword 支付密码
	 * @param id 用户id
	 * @return 成功返回1，失败返回0
	 */
	int updatePayPasswordById(@Param("payPassword")String payPassword,@Param("id")Integer id);

	/**
	 * 修改用户充值类型及首充金额
	 * @param rechargeType 充值类型
	 * @param rechargeMoney 首充金额
	 * @param id 用户id
	 * @return 成功返回1，失败返回0
	 */
	int updateRechargeTypeById(@Param("rechargeType")Integer rechargeType,@Param("rechargeMoney")Integer rechargeMoney,@Param("id")Integer id);

    /**
     * 修改用户名及登录密码
     * @param realName 用户名
     * @param loginPassword 登录密码
     * @param id 用户id
     * @return 成功返回1，失败返回0
     */
	int updateUserNameAndPwdById(@Param("realName")String realName,@Param("loginPassword")String loginPassword,@Param("id")Integer id);

    /**
     * 修改登录类型
     * @param loginType 登录类型
     * @param id 用户id
     * @return 成功返回1，失败返回0
     */
	int updateLoginTypeById(@Param("loginType")String loginType,@Param("id")Integer id);

	/**
	 * 查询首充用户列表
	 */
	List<UserInfo> findFirstRechargeList();

	/**
	 * 修改地址信息
	 * @param provinceName
	 * @param cityName
	 * @param id
	 * @return
	 */
	int updateAddressById(@Param("provinceName")String provinceName,@Param("cityName")String cityName,@Param("id")Integer id);

	/**
	 * 修改用户头像/昵称
	 * @param userInfo 用户信息
	 * @return ServiceResult
	 */
    int updateUserInfo(UserInfo userInfo);

	/**
	 * 获取用户登陆信息（设备号、设备名、应用市场等）
	 * @param appInfo {"clientType":"xx","appVersion":"版本号","deviceId":"设备id","deviceName":"设备名","osVersion":"xx",
	 *                   "appName":"app名称","appMarket":"应用市场名"}
	 * @param userId
	 * @return 1成功 0失败
	 */
	int updateAppInfo(@Param("appInfo") String appInfo, @Param("userId")Integer userId);

	/**
	 * 修改用户信息
	 * @param userInfo 用户信息
	 * @return
	 */
	int updateUserInfoById(UserInfo userInfo);
}
