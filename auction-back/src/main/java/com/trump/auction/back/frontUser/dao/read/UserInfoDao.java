package com.trump.auction.back.frontUser.dao.read;

import com.trump.auction.back.frontUser.model.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;


@Repository
public interface UserInfoDao {

	/**
	 * 查询用户列表信息
	 * @param params
	 * @return 用户列表信息
	 */
	List<UserInfo> selectUserInfo(HashMap<String, Object> params);

	/**
	 * 根据参数查询用户信息列表
	 * @param ids
	 * @return
	 */
	List<UserInfo> findAll(String [] ids);

	/**
	 * 根据用户ID查询单条用户信息
	 * @param id
	 * @return 用户单条信息
	 */
	UserInfo selectOneUserInfo(@Param("id") Integer id);

	/**
	 * 根据用户ID查询单条用户信息
	 * @param id 用户信息
	 * @return 用户单条信息
	 */
	UserInfo selectUserInfoById(@Param("id") Integer id);

	/**
	 * 根据用户手机号查询用户信息
	 * @param userPhone 用户手机号
	 * @return 用户信息
	 */
	UserInfo selectUserInfoByUserPhone(@Param("userPhone")String userPhone);
}
