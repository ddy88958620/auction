package com.trump.auction.back.sys.dao.write;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleModuleDao {
	public void deleteByRoleId(String[] ids);
	public int insertModuleRole(@Param("roleId") String roleId, @Param("ids") List<String> ids);

}
