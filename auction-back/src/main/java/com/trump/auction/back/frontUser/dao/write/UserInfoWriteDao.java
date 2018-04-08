package com.trump.auction.back.frontUser.dao.write;

import com.trump.auction.back.frontUser.model.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoWriteDao {

	/**
	 * 禁用用户状态
	 * @param id 用户id
	 * @return 受影响的行数
	 */
	int updateUserInfoWrite(@Param("id") String id);

	/**
	 * 修改用户信息
	 * @param userInfo 用户信息
	 * @return
	 */
	int updateUserInfoById(UserInfo userInfo);
}
