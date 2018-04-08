package com.trump.auction.back.sys.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.trump.auction.back.sys.dao.read.SysUserReadDao;
import com.trump.auction.back.sys.dao.read.UserRoleReadDao;
import com.trump.auction.back.sys.dao.write.SysUserDao;
import com.trump.auction.back.sys.dao.write.UserRoleDao;
import com.trump.auction.back.sys.model.SysUser;
import com.trump.auction.back.sys.model.ZTree;

@Service
public class SysUserServiceImpl implements SysUserService {
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private SysUserReadDao sysUserReadDao;
	@Autowired
	private UserRoleDao userRoleDao;
	@Autowired
	private UserRoleReadDao userRoleReadDao;

	@Override
	public List<SysUser> findAll(HashMap<String, Object> params) {
		params.put("adminIds", SysUser.ADMINISTRATOR_IDS);
		return sysUserReadDao.findAll(params);
	}

	@Override
	public SysUser findById(Integer id) {
		return sysUserReadDao.findById(id, SysUser.ADMINISTRATOR_IDS);
	}

	@Override
	public void insert(SysUser backUser) {
		sysUserDao.insert(backUser);
	}

	@Override
	public void updateById(SysUser backUser) {
		sysUserDao.updateById(backUser);
	}

	@Override
	public int deleteById(List<String> realId) {
		int count = sysUserDao.deleteById(realId, SysUser.ADMINISTRATOR_IDS);
		if (count > 0) {
			// 需要删除无限子用户
			for (String id : realId) {
				List<String> haveList = sysUserReadDao.findIdsByParentIds(id);
				// 如果有子用户则删除
				if (haveList != null && haveList.size() > 1) {
					count += sysUserDao.deleteById(haveList, SysUser.ADMINISTRATOR_IDS);
				}
			}
		}
		return count;
	}

	@Override
	public Paging<SysUser> findPage(HashMap<String, Object> params) {
		params.put("adminIds", SysUser.ADMINISTRATOR_IDS);
		PageHelper.startPage(Integer.valueOf(String.valueOf(params.get("page") == null ? 1 : params.get("page"))),
				Integer.valueOf(String.valueOf(params.get("limit") == null ? 10 : params.get("limit"))));
		return PageUtils.page(sysUserReadDao.findAll(params));

	}

	@Override
	public void addUserRole(Integer toUserId, String[] roleIds, boolean isAdmin) {
		userRoleDao.deleteUserRoleByUserId(toUserId, isAdmin ? null : roleIds);
		// 当删除时该角色集合确实为空
		if (roleIds != null && roleIds.length > 0) {
			userRoleDao.inserUserRoleList(toUserId, roleIds);
		}
	}

	@Override
	public void updatePwdById(SysUser backUser) {
		sysUserDao.updatePwdById(backUser);
	}

	@Override
	public String[] findRoleIdByUserId(Integer userId, String[] ids) {
		return userRoleReadDao.findRoleIdByUserId(userId, ids);
	}

	@Override
	public List<ZTree> findByParentId(String parentId) {
		return sysUserReadDao.findByParentId(parentId, SysUser.ADMINISTRATOR_IDS);
	}

	@Override
	public List<String> findHaveIds(String[] ids, String parentId) {
		List<String> realId = new ArrayList<>();
		if (SysUser.isAdmin(parentId)) {
			realId = Arrays.asList(ids);
		} else {
			List<String> haveList = sysUserReadDao.findIdsByParentIds(parentId);
			if (haveList != null && haveList.size()>1) {
				for (String delId : ids) {
					for (String haveId : haveList) {
						if (haveId.equals(delId)) {
							realId.add(haveId);
						}
					}
				}
			}
		}
		return realId;
	}
	@Override
	public List<String> findIdsByParentIds(Integer parentId) {
		return sysUserReadDao.findIdsByParentIds(String.valueOf(parentId));
	}
	@Override
	public Integer findLogin(String userMobile, String pwd) {
		return sysUserReadDao.findLogin(userMobile, pwd);
	}
}
