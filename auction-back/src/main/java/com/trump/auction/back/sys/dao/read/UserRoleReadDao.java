package com.trump.auction.back.sys.dao.read;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleReadDao {

	/**
	 * 查询某个用户的角色主键集合
	 * 
	 * @param userId
	 * @return
	 */
	public String[] findRoleIdByUserId(@Param("userId") Integer userId, @Param("array") String[] ids);
}
