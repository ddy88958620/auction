package com.trump.auction.back.sys.controller;

import java.util.ArrayList;
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

import com.trump.auction.back.sys.model.Module;
import com.trump.auction.back.sys.model.SysLog;
import com.trump.auction.back.sys.model.ZTree;
import com.trump.auction.back.sys.model.SysUser;
import com.trump.auction.back.sys.service.ModuleService;
import com.trump.auction.back.sys.service.SysLogService;
import com.trump.auction.back.sys.service.SysUserService;

@Controller
@RequestMapping("module/")
public class ModuleController extends BaseController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ModuleService moduleService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysLogService sysLogService;

	@RequestMapping(value = "findModuleList", method = RequestMethod.GET)
	public String findModuleList(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("parentId", request.getParameter("myId"));
		return "module/moduleList";
	}

	/**
	 * 根据某个树节点查询子节点，菜单管理树
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "findModuleList", method = RequestMethod.POST)
	public void findModuleList(HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		String code = Status.ERROR.getName();
		String msg = Status.ERROR.getValue();
		String type = request.getParameter("type");
		String parentId = request.getParameter("parentId");
		List<ZTree> list = new ArrayList<>();
		try {
			if (parentId == null) {
				parentId = "0";
			}
			// 得到UserId查询对应的权限
			HashMap<String, Object> params = new HashMap<>();
			SysUser backUser = loginAdminUser(request);
			if ("tree".equals(type)) {
				if (SysUser.isAdmin(backUser.getId() + "")) {
					list = moduleService.findAllTreeByParentId(parentId);
				} else {
					list = moduleService.findUserAllTreeByParentId(parentId, backUser.getId() + "");
				}
			} else {
				params.put("userId", backUser.getId());
				params.put("parentId", parentId);
				// 如果父节点不存在则是首次加载,查询根节点
				Paging<Module> page = moduleService.findPage(params);
				json.put("data", page.getList());
				json.put("count", page.getTotal() + "");
				code = Status.SUCCESS.getName();
				msg = Status.SUCCESS.getValue();
			}
		} catch (Exception e) {
			logger.error("findModuleList post error", e);
		} finally {
			if ("tree".equals(type)) {
				renderJson(response, list);
			} else {
				json.put("code", code);
				json.put("msg", msg);
				renderJson(response, json);
			}
		}
	}


	/**添加或者更新权限
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
		model.addAttribute("url", "/module/save");
		return "module/saveUpdate";
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public void save(HttpServletRequest request, HttpServletResponse response, Module module) {
		JSONObject json = new JSONObject();
		String code = Status.ERROR.getName();
		String msg = Status.ERROR.getValue();
		try {
			moduleService.insert(module);
			code = Status.SUCCESS.getName();
			msg = Status.SUCCESS.getValue();
			sysLogService.insertLog(new SysLog(loginAdminUser(request).getId(), "insertModule", module.toString()));

		} catch (Exception e) {
			logger.error("save error post module:{}", module, e);
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
				Module module = moduleService.findById(Integer.valueOf(id));
				if (module != null) {
					model.addAttribute("module", module);
					model.addAttribute("url", "/module/update");
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
		return "module/saveUpdate";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public void update(HttpServletRequest request, HttpServletResponse response, Module module) {
		JSONObject json = new JSONObject();
		String code = Status.ERROR.getName();
		String msg = Status.ERROR.getValue();
		try {
			sysLogService.insertLog(new SysLog(loginAdminUser(request).getId(), "updateModule", module.toString()));
			moduleService.updateById(module);
			code = Status.SUCCESS.getName();
			msg = Status.SUCCESS.getValue();
		} catch (Exception e) {
			logger.error("save error post module:{}", module, e);
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
				sysLogService.insertLog(new SysLog(loginAdminUser(request).getId(), "deleteModule", StringUtils.join(ids, ",")));
				int count = moduleService.deleteById(ids);
				code = Status.SUCCESS.getName();
				msg = "成功删除:" + count + "条数据";
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
}
