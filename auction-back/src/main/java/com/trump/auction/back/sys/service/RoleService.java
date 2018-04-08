package com.trump.auction.back.sys.service;

import java.util.HashMap;
import java.util.List;

import com.cf.common.util.page.Paging;
import com.trump.auction.back.sys.model.Role;
import com.trump.auction.back.sys.model.ZTree;

public interface RoleService {
	public List<ZTree> findRoleTree(HashMap<String, Object> params);

	List<Role> findRoleList(HashMap<String, Object> params);

	Paging<Role> findPage(HashMap<String, Object> params);

	public Role findById(Integer id);

	public int deleteById(String[] ids);

	public void updateById(Role backRole);

	public void insert(Role backRole, Integer userId);

	List<String> showUserListByRoleId(Integer id);

	public List<ZTree> getTreeByRoleId(Integer roleId,Integer parentId);


	int addRoleModule(String roleId, List<String> ids);

	/**
	 * 查询当前登录人拥有的角色,及这些角色的向下无限级子角色,遍历去重复<br>
	 * 如果是修改和删除则不能删除当前登录人拥有的角色,即只返回当前登录人拥有的角色的向下无限级子角色
	 * 
	 * @param isAdd
	 *            true 查询当前登录人的直接角色和的向下无限级子角色与要操作的ids集合求交集,即为可操作的结果集合<br>
	 *            false 是修改/删除/授权时
	 * @param ids
	 * @param userId
	 * @return 插入/修改/删除时校验要插入的角色的父角色需要在此范围内
	 */
	List<String> findHaveIds(Boolean isAdd, String[] ids, Integer userId);


	/**
	 * 返回某个用户所有的菜单ID<br>
	 *     根据用户查询其所有角色及无限子角色,再根据角色查询菜单ID集合
	 * @param ids
	 * @param userId
	 * @return
	 */
	List<String> findHaveModules(String[] ids, Integer userId);
}
