package com.trump.auction.back.sys.dao.read;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.trump.auction.back.sys.model.RoleModule;

@Repository
public interface RoleModuleReadDao {
	public List<RoleModule> findAll(HashMap<String, Object> params);
	public List<String> findModuleByRoleIds(@Param("roleIds")String roleIds);

}
