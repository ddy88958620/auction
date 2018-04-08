package com.trump.auction.back.sys.dao.write;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.trump.auction.back.sys.model.UserRole;

@Repository
public interface UserRoleDao {
	public int deleteByRoleId(String[] ids);

	public void deleteUserRoleByUserId(@Param("userId") Integer toUserId, @Param("array") String[] roleIds);

	public void inserUserRoleList(@Param("userId") Integer userId, @Param("array") String[] roleIds);

	public void inserUserRole(UserRole backUserRole);
}
