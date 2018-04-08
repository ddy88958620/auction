package com.trump.auction.back.frontUser.service;

import com.cf.common.util.page.Paging;
import com.cf.common.utils.ServiceResult;
import com.trump.auction.back.frontUser.model.UserInfo;
import com.trump.auction.cust.model.UserInfoModel;

import java.util.HashMap;
import java.util.List;


public interface UserInfoService {

	/**
	 * 分页查询用户列表信息
	 * @param params
	 * @return 用户列表信息
	 */

	Paging<UserInfo> selectUserInfo(HashMap<String, Object> params);

	/**
	 * 根据参数查询用户列表
	 * @param ids
	 * @return
	 */
	List<UserInfo>  findAll(String [] ids);

	/**
	 * 禁用用户状态
	 * @param id 用户id
	 * @return 受影响的行数
	 */
	int updateUserInfoWrite(String id);

	/**
	 * 查找用户信息
	 * @param id 用户id
	 * @return 用户信息
	 */
	UserInfo findUserInfoById(Integer id);

	/**
	 * 修改用户信息
	 * @param userInfo 用户信息
	 * @return ServiceResult
	 */
	ServiceResult updateUserInfoById(UserInfoModel userInfo);

	/**
	 * 根据用户手机号查询用户信息
	 * @param userPhone 用户手机号
	 * @return 用户信息
	 */
	UserInfo findUserInfoByUserPhone(String userPhone);
}
