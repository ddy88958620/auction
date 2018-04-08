package com.trump.auction.back.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.trump.auction.back.constant.ModuleViewEnum;
import com.trump.auction.back.sys.dao.read.ModuleReadDao;
import com.trump.auction.back.sys.dao.write.ModuleDao;
import com.trump.auction.back.sys.model.Module;
import com.trump.auction.back.sys.model.ZTree;
import com.trump.auction.back.sys.model.SysUser;
import com.trump.auction.back.util.sys.TreeUtil;

@Service
public class ModuleServiceImpl implements ModuleService {
	@Autowired
	private ModuleDao moduleDao;
	@Autowired
	private RoleService roleService;
	@Autowired
	private ModuleReadDao moduleReadDao;

	@Override
	public List<Module> findAllModule(HashMap<String, Object> params) {
		params.put("moduleView", ModuleViewEnum.SHOW.getType());
		List<Module> list = new ArrayList<Module>();
		if (params.containsKey("userId") && SysUser.isAdmin(String.valueOf(params.get("userId")))) {
			list = moduleReadDao.findAdminAll(params);
		} else {
			if (params.get("parentId") != null) {
				list = moduleReadDao.findUserAll(params);
			}
		}
		return list;
	}

	@Override
	public List<ZTree> findModuleTree(HashMap<String, Object> params) {
		if (params.containsKey("userId") && SysUser.isAdmin(String.valueOf(params.get("userId")))) {
			return moduleReadDao.findAdminTree(params);
		} else {
			return moduleReadDao.findUserTree(params);
		}
	}

	@Override
	public List<ZTree> findRoleTree(HashMap<String, Object> params) {
		List<ZTree> userAll = findModuleTree(params);
		List<ZTree> haveList = roleService.getTreeByRoleId(Integer.valueOf(params.get("roleId").toString()),Integer.valueOf(params.get("parentId").toString()));

		return TreeUtil.getInstance().createCheckedTree(userAll, haveList, Integer.valueOf(params.get("parentId").toString()));
	}

	@Override
	public Paging<Module> findPage(HashMap<String, Object> params) {
		PageHelper.startPage(Integer.valueOf(String.valueOf(params.get("page") == null ? 1 : params.get("page"))),
				Integer.valueOf(String.valueOf(params.get("limit") == null ? 10 : params.get("limit"))));
		Paging<Module> pageConfig = new Paging<Module>();
		if (params.containsKey("userId") && SysUser.isAdmin(String.valueOf(params.get("userId")))) {
			pageConfig = PageUtils.page(moduleReadDao.findAdminAll(params));
		} else {
			pageConfig = PageUtils.page(moduleReadDao.findUserAll(params));
		}
		return pageConfig;

	}

	@Override
	public Module findById(Integer id) {
		return moduleReadDao.findById(id);
	}

	@Override
	public void updateById(Module HtCd) {
		moduleDao.updateById(HtCd);
	}

	@Override
	public int deleteById(String[] ids) {
		return moduleDao.deleteById(ids);
	}

	@Override
	public void insert(Module HtCd) {
		moduleDao.insert(HtCd);
	}

	@Override
	public int findModuleByUrl(HashMap<String, Object> params) {
		return moduleReadDao.findModuleByUrl(params);
	}

	@Override
	public List<ZTree> findAllTreeByParentId(String parentId) {
		return moduleReadDao.findAllTreeByParentId(parentId);
	}

	@Override
	public List<ZTree> findUserAllTreeByParentId(String moduleParentId, String userId) {
		return moduleReadDao.findUserAllTreeByParentId(moduleParentId, userId);
	}

}
