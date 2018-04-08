package com.trump.auction.back.sys.service;

import java.util.HashMap;
import java.util.List;

import com.cf.common.util.page.Paging;
import com.trump.auction.back.sys.model.SysUser;
import com.trump.auction.back.sys.model.ZTree;

public interface SysUserService {
	public Integer findLogin(String userMobile, String pwd);

	/**
	 * 传入要操作的用户主键集合,再查询id=parentId的用户的无限级子用户,返回交集,如果parentId是超级管理员则返回ids
	 *
	 * @param ids
	 * @return
	 */
	public List<String> findHaveIds(String[] ids, String parentId);

	public List<ZTree> findByParentId(String parentId);

	/**
	 * 查询某个用户的角色主键集合
	 *
	 * @param userId
	 * @return
	 */
	public String[] findRoleIdByUserId(Integer userId, String[] ids);

	public List<SysUser> findAll(HashMap<String, Object> params);

	public SysUser findById(Integer id);

	public int deleteById(List<String> realId);

	public void insert(SysUser backUser);

	public void updateById(SysUser backUser);

	Paging<SysUser> findPage(HashMap<String, Object> params);

	void addUserRole(Integer toUserId, String[] roleIds,boolean isAdmin);

	public void updatePwdById(SysUser backUser);

	/**
	 * 查询某个用户的无限子用户
	 *
	 * @param parentId
	 * @return
	 */
	public List<String> findIdsByParentIds(Integer parentId);

}
