package com.trump.auction.back.sys.dao.read;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.trump.auction.back.sys.model.Role;
import com.trump.auction.back.sys.model.ZTree;

@Repository
public interface RoleReadDao {
	public List<Role> findAdminAll(HashMap<String, Object> params);
	public List<Role> findUserAll(HashMap<String, Object> params);
	public List<ZTree> findAdminRoleTree(HashMap<String, Object> params);

	public List<ZTree> findUserRoleTree(HashMap<String, Object> params);

	public Role findById(Integer id);

	public List<String> findIdsByParentId(Integer id);

	public List<String> showUserListByRoleId(Integer roleId);
	public List<ZTree> getTreeByRoleId(@Param("roleId") Integer roleId, @Param("parentId") Integer parentId);

}
