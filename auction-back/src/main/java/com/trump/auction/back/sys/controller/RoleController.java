package com.trump.auction.back.sys.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cf.common.util.page.Paging;
import com.cf.common.utils.Status;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trump.auction.back.sys.model.Role;
import com.trump.auction.back.sys.model.SysLog;
import com.trump.auction.back.sys.model.SysUser;
import com.trump.auction.back.sys.model.ZTree;
import com.trump.auction.back.sys.service.ModuleService;
import com.trump.auction.back.sys.service.RoleService;
import com.trump.auction.back.sys.service.SysLogService;
import com.trump.auction.back.util.sys.TreeUtil;

@Controller
@RequestMapping("role/")
public class RoleController extends BaseController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RoleService roleService;
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private SysLogService sysLogService;

	@RequestMapping(value = "getRoleList", method = RequestMethod.GET)
	public String getRoleList(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("parentId", request.getParameter("myId"));
		SysUser sysUser = loginAdminUser(request);
		if (SysUser.isAdmin(sysUser.getId() + "")) {
			model.addAttribute("nowPageId", "0");
			model.addAttribute("parentName", "根目录");
		}
		return "role/roleList";
	}

	/**
	 * 根据某个树节点查询子节点，角色管理树
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "getRoleList", method = RequestMethod.POST)
	public void findList(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> params = this.getParametersO(request);
		JSONObject json = new JSONObject();
		String code = Status.ERROR.getName();
		String msg = Status.ERROR.getValue();
		try {
			SysUser backUser = loginAdminUser(request);
			params.put("userId", backUser.getId());

			if (!params.containsKey("roleId")) {
				// 得到UserId查询对应的权限
				ZTree zTree = new ZTree(0, "根节点");
				List<ZTree> list = roleService.findRoleTree(params);
				list = TreeUtil.getInstance().getZTree(zTree.getId(), list);
				// 超级管理员才能看到角色根节点,对应的普通角色不能在根目录下创建角色
				if (SysUser.isAdmin(backUser.getId() + "")) {
					zTree.setChildren(list);
					json.put("data", Arrays.asList(zTree));
				} else {
					json.put("data", list);
				}
			} else {
				params.put("parentId", params.get("roleId"));
				Paging<Role> page = roleService.findPage(params);
				json.put("data", page.getList());
				json.put("count", page.getTotal() + "");
			}
			code = Status.SUCCESS.getName();
			msg = Status.SUCCESS.getValue();
		} catch (Exception e) {
			logger.error("getRoleList error", e);
		} finally {
			json.put("code", code);
			json.put("msg", msg);
			renderJson(response, json);
		}
	}

	/**
	 * 跳转到授权页
	 *
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "showRoleModuleList", method = RequestMethod.GET)
	public String showRoleModuleList(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("url", "/role/showRoleModuleList");
		model.addAttribute("roleId", request.getParameter("roleId"));
		return "role/roleModuleTree";
	}

	/**
	 * 基于当前登录用户给某个角色授权<br>
	 * 先查出当前登录用户某个父节点的第一层子节点<br>
	 * 再查出某个角色同一个父节点的第一层子节点<br>
	 * 外层遍历当前菜单树,内层遍历选中用户的菜单树
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "showRoleModuleList", method = RequestMethod.POST)
	public void showRoleModuleList(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> params = this.getParametersO(request);
		List<ZTree> list = new ArrayList<>();
		try {
			SysUser backUser = this.loginAdminUser(request);
			params.put("userId", backUser.getId());
			if (params.get("parentId") == null) {
				params.put("parentId", 0);
			}
			list = moduleService.findRoleTree(params);
		} catch (Exception e) {
			logger.error("getRoleList error", e);
		} finally {
			renderJson(response, list);
		}
	}

	@RequestMapping(value = "saveRoleModule", method = RequestMethod.POST)
	public void saveRoleModule(HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		String code = Status.ERROR.getName();
		String msg = Status.ERROR.getValue();
		String roleId = request.getParameter("roleId");
		try {
			String[] ids = request.getParameterValues("ids[]");
			if (ids != null && ids.length > 0) {
				SysUser sysUser = loginAdminUser(request);
				List<String> list2 = roleService.findHaveIds(false, new String[]{roleId + ""}, sysUser.getId());
				if (list2 != null && list2.size() > 0) {
					List<String> list = roleService.findHaveModules(ids, sysUser.getId());
					if (list != null && list.size() > 0) {
						int count = roleService.addRoleModule(roleId, list);
						code = Status.SUCCESS.getName();
						msg = Status.SUCCESS.getValue();
						msg = "成功授权:" + count + "个菜单";
						sysLogService.insertLog(new SysLog(sysUser.getId(), "saveRoleModule", "roleId=" + roleId + ",save ids:" + StringUtils.join(ids, ",")));
					} else {
						msg = "参数非法";
					}
				} else {
					msg = "您仅能对自己的子角色进行授权！";
				}
			} else {
				msg = "请选择要授权的菜单";
			}
		} catch (Exception e) {
			logger.error("save error post roleId:{}", roleId, e);
		} finally {
			json.put("code", code);
			json.put("msg", msg);
			renderJson(response, json);
		}
	}

	/**
	 * 添加或者更新权限
	 *
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.GET)
	public String save(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("parentId", request.getParameter("myId"));
		model.addAttribute("parentName", request.getParameter("parentName"));
		model.addAttribute("url", "/role/save");
		return "role/saveUpdate";
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public void save(HttpServletRequest request, HttpServletResponse response, Role role) {
		JSONObject json = new JSONObject();
		String code = Status.ERROR.getName();
		String msg = Status.ERROR.getValue();
		try {
			SysUser sysUser = loginAdminUser(request);
			List<String> list = roleService.findHaveIds(true, new String[]{role.getRoleSuper() + ""}, sysUser.getId());
			if (list != null && list.size() > 0) {
				role.setRoleAddip(getIpAddr(request));
				roleService.insert(role, sysUser.getId());
				code = Status.SUCCESS.getName();
				msg = Status.SUCCESS.getValue();
				sysLogService.insertLog(new SysLog(sysUser.getId(), "insertRole", role.toString()));
			} else {
				msg = "参数非法";
			}
		} catch (Exception e) {
			logger.error("save error post role:{}", role, e);
		} finally {
			json.put("code", code);
			json.put("msg", msg);
			renderJson(response, json);
		}
	}

	/**
	 * 添加或者更新权限
	 *
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String update(HttpServletRequest request, HttpServletResponse response, Model model) {
		String id = request.getParameter("id");
		String msg = null;
		try {
			if (StringUtils.isNotBlank(id)) {
				Role role = roleService.findById(Integer.valueOf(id));
				if (role != null) {
					Role parentRole = roleService.findById(role.getId());
					model.addAttribute("parentName", parentRole.getRoleName());
					model.addAttribute("parentId", parentRole.getRoleName());
					model.addAttribute("role", role);
					model.addAttribute("url", "/role/update");
				} else {
					msg = "该记录不存在";
				}
			} else {
				msg = "参数非法";
			}
		} catch (Exception e) {
			msg = "未知异常";
			logger.error("update error module:{}", id, e);
		}
		model.addAttribute(MESSAGE, msg);
		return "role/saveUpdate";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public void update(HttpServletRequest request, HttpServletResponse response, Role role) {
		JSONObject json = new JSONObject();
		String code = Status.ERROR.getName();
		String msg = Status.ERROR.getValue();
		try {
			SysUser sysUser = loginAdminUser(request);
			List<String> list = roleService.findHaveIds(false, new String[]{role.getId() + ""}, sysUser.getId());
			if (list != null && list.size() > 0) {
				roleService.updateById(role);
				code = Status.SUCCESS.getName();
				msg = Status.SUCCESS.getValue();
				sysLogService.insertLog(new SysLog(sysUser.getId(), "updateRole", role.toString()));
			} else {
				msg = "您仅能修改自己的子角色！";
			}
		} catch (Exception e) {
			logger.error("save error post role:{}", role, e);
		} finally {
			json.put("code", code);
			json.put("msg", msg);
			renderJson(response, json);
		}
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		String code = Status.ERROR.getName();
		String msg = Status.ERROR.getValue();
		try {
			String[] ids = request.getParameterValues("ids[]");
			if (ids != null && ids.length > 0) {
				SysUser sysUser = loginAdminUser(request);
				List<String> list = roleService.findHaveIds(false, ids, sysUser.getId());
				if (list != null && list.size() > 0) {
					int count = roleService.deleteById(ids);
					code = Status.SUCCESS.getName();
					msg = "成功删除:" + count + "条数据";
					sysLogService.insertLog(new SysLog(sysUser.getId(), "deleteRole", StringUtils.join(ids, ",")));
				} else {
					msg = "参数非法";
				}
			} else {
				msg = "请选择要删除的行";
			}
		} catch (Exception e) {
			logger.error("deleteModule error ", e);
		} finally {
			json.put("code", code);
			json.put("msg", msg);
			renderJson(response, json);
		}
	}

	@RequestMapping(value = "showUserListByRoleId", method = RequestMethod.POST)
	public void showUserListByRoleId(HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		String code = Status.ERROR.getName();
		String msg = Status.ERROR.getValue();
		try {
			String roleId = request.getParameter("id");
			if (StringUtils.isNotBlank(roleId)) {
				List<String> list = roleService.showUserListByRoleId(Integer.valueOf(roleId));
				if (list != null && list.size() > 0) {
					json.put("data", list);
					msg = Status.SUCCESS.getValue();
					code = Status.SUCCESS.getName();
				} else {
					msg = "暂无数据";
				}
			} else {
				msg = "请选择要查看的角色";
			}
		} catch (Exception e) {
			logger.error("showUserListByRoleId error ", e);
		} finally {
			json.put("code", code);
			json.put("msg", msg);
			renderJson(response, json);
		}
	}
}
