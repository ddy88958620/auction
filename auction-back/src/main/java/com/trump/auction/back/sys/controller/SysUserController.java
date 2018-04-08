package com.trump.auction.back.sys.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cf.common.util.encrypt.AESUtil;
import com.cf.common.util.encrypt.MD5coding;
import com.cf.common.util.page.Paging;
import com.cf.common.utils.Status;
import com.trump.auction.back.sys.model.Role;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trump.auction.back.sys.model.SysLog;
import com.trump.auction.back.sys.model.SysUser;
import com.trump.auction.back.sys.model.ZTree;
import com.trump.auction.back.sys.service.RoleService;
import com.trump.auction.back.sys.service.SysLogService;
import com.trump.auction.back.sys.service.SysUserService;

@Controller
@RequestMapping("user/")
public class SysUserController extends BaseController {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysLogService sysLogService;
    @Autowired
    private RoleService roleService;

    /**
     * 跳转到全用户列表
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getUserPage", method = RequestMethod.GET)
    public String getUserPage(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("parentId", request.getParameter("myId"));
        return "user/userList";
    }

    /**
     * 查询全用户
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "getUserPage", method = RequestMethod.POST)
    public void findList(HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            HashMap<String, Object> params = getParametersO(request);
            Paging<SysUser> page = sysUserService.findPage(params);
            json.put("data", page.getList());
            json.put("count", page.getTotal() + "");
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
        } catch (Exception e) {
            logger.error("getUserPage error", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    @RequestMapping(value = "save", method = RequestMethod.GET)
    public String saveUpdateUser(HttpServletRequest request, HttpServletResponse response, Model model) {
        String parentUserId = request.getParameter("parentUserId");
        String parentUserName = request.getParameter("parentUserName");
        if (StringUtils.isBlank(parentUserId)) {
            // 如果没有选择父节点则当前登录用户是父节点
            SysUser loginUser = loginAdminUser(request);
            parentUserId = loginUser.getId() + "";
            SysUser sysUser = sysUserService.findById(loginUser.getId());
            parentUserName = sysUser.getUserAccount();
        }
        model.addAttribute("parentUserId", parentUserId);
        model.addAttribute("parentUserName", parentUserName);
        model.addAttribute("url", "/user/save");
        return "user/saveUpdate";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public void save(HttpServletRequest request, HttpServletResponse response, SysUser sysUser) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            Integer userId = loginAdminUser(request).getId();
            // 如果未传入父节点或者父节点等于当前登录用户,则登录人作为父节点
            if (sysUser.getParentId() == null || sysUser.getParentId().intValue() == 0 || sysUser.getParentId().intValue() == userId.intValue()) {
                // 如果未传入父节点,则子节点的父节点的只能是当前登录用户
                if (SysUser.isAdmin(userId + "")) {
                    // 如果当前操作人是超级管理员则父节点是0
                    sysUser.setParentId(SysUser.PARENT_ID_ADMIN);
                } else {
                    sysUser.setParentId(userId);
                }
            } else {
                // 否则需要查询传入的父节点ID是否在当前登录人的无线子角色内
                List<String> realId = sysUserService.findHaveIds(new String[]{sysUser.getParentId() + ""}, userId + "");
                if (realId == null || realId.size() == 0) {
                    msg = "参数非法";
                    return;
                }
            }
            sysUser.setAddIp(this.getIpAddr(request));
            sysUser.setUserPassword(MD5coding.getInstance().encrypt(AESUtil.getInstance().encrypt(sysUser.getUserPassword(), "")));
            sysUserService.insert(sysUser);
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
            sysLogService.insertLog(new SysLog(userId, "saveUser", sysUser.toString()));
        } catch (Exception e) {
            String tmp = e.getLocalizedMessage();
            if (tmp.contains("UK_user_account")) {
                msg = "用户名重复！";
            } else if (tmp.contains("uk_user_mobile")) {
                msg = "手机号码重复！";
            } else if (tmp.contains("uk_email")) {
                msg = "邮箱重复！";
            }
            logger.error("save error post sysUser:{}", sysUser, e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    @RequestMapping(value = "update", method = RequestMethod.GET)
    public String update(HttpServletRequest request, HttpServletResponse response, Model model) {
        String msg = null;
        String userId = request.getParameter("id");
        try {
            if (StringUtils.isNotBlank(userId)) {
                SysUser sysUser = sysUserService.findById(Integer.valueOf(userId));
                if (sysUser != null) {
                    model.addAttribute("sysUser", sysUser);
                    model.addAttribute("url", "/user/update");
                } else {
                    msg = "该记录不存在";
                }
            } else {
                msg = "参数非法";
            }
        } catch (Exception e) {
            msg = "未知异常";
            logger.error("update error userId:{}", userId, e);
        }
        model.addAttribute(MESSAGE, msg);
        return "user/saveUpdate";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public void update(HttpServletRequest request, HttpServletResponse response, SysUser sysUser) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            if (StringUtils.isNotBlank(sysUser.getUserPassword())) {
                sysUser.setUserPassword(MD5coding.getInstance().encrypt(AESUtil.getInstance().encrypt(sysUser.getUserPassword(), "")));
            }
            SysUser backUser = this.loginAdminUser(request);
            if (SysUser.isAdmin(sysUser.getId() + "")) {
                msg = "超级管理员不能被编辑";
            } else if (sysUser.getId().intValue() == backUser.getId().intValue()) {
                msg = "不能编辑自己的信息";
            } else {
                List<String> realId = sysUserService.findHaveIds(new String[]{sysUser.getId() + ""}, backUser.getId() + "");
                if (realId != null && realId.size() > 0) {
                    sysUserService.updateById(sysUser);
                    code = Status.SUCCESS.getName();
                    msg = Status.SUCCESS.getValue();
                    sysLogService.insertLog(new SysLog(loginAdminUser(request).getId(), "updateUser", sysUser.toString()));
                } else {
                    msg = "参数非法";
                }
            }
        } catch (Exception e) {
            logger.error("save error post sysUser:{}", sysUser, e);
            String tmp = e.getLocalizedMessage();
            if (tmp.contains("UK_user_account")) {
                msg = "用户名重复！";
            } else if (tmp.contains("uk_user_mobile")) {
                msg = "手机号码重复！";
            } else if (tmp.contains("uk_email")) {
                msg = "邮箱重复！";
            }
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    /**
     * 删除用户,超级管理员谁都能删,普通用户只能删除自己的子元素及孙子元素(向下无线递归)
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public void delete(HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            String[] ids = request.getParameterValues("ids[]");
            if (ids != null && ids.length > 0) {
                Integer userId = loginAdminUser(request).getId();
                // 该用户拥有的下级(无限级)用户
                List<String> realId = sysUserService.findHaveIds(ids, userId + "");
                if (realId != null && realId.size() > 0) {
                    int count = sysUserService.deleteById(realId);
                    code = Status.SUCCESS.getName();
                    msg = "成功删除:" + count + "个用户及子用户";
                    sysLogService.insertLog(new SysLog(userId, "deleteUser", StringUtils.join(realId, ",")));
                } else {
                    msg = "参数非法";
                }
            } else {
                msg = "请选择要删除的行";
            }
        } catch (Exception e) {
            logger.error("deleteUser error", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    /**
     * 基于当前登录用户给某个用户授权角色<br>
     * 先查出当前登录用户所拥有的权限<br>
     * 再查出将要授权的用户的角色中已经有的权限(即使该用户的权限不是自己授权的也要选中,只不过自己删除该授权)<br>
     * 遍历取交集<br>
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "showUserRole", method = RequestMethod.POST)
    public void showUserRole(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> params = this.getParametersO(request);
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        String id = params.get("id") + "";
        try {
            SysUser backUser = this.loginAdminUser(request);
            // 查询当前登录人的角色
            params.put("userId", backUser.getId());
            List<Role> userAll = roleService.findRoleList(params);
            List<Role> haveList = new ArrayList<>();
            // 如果勾选的用户是超级管理员,则haveList等于当前用户拥有的角色(目前超级管理员不会被查出来)
            if (SysUser.isAdmin(params.get("id") + "")) {
                haveList.addAll(userAll);
            } else {
                params.put("userId", id);
                haveList = roleService.findRoleList(params);
            }
            for (Role role : userAll) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", role.getId());
                jsonObject.put("name", role.getRoleName());
                jsonObject.put("tip", role.getRoleSummary());
                // 如果选中用户等于当前登录用户,则全选
                if (id.equals(backUser.getId() + "")) {
                    jsonObject.put("checked", true);
                } else {
                    for (Role role1 : haveList) {
                        if (role.getId().intValue() == role1.getId().intValue()) {
                            jsonObject.put("checked", true);
                        }
                    }
                }
                jsonArray.add(jsonObject);
            }
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
        } catch (Exception e) {
            logger.error("showUserRole error", e);
        } finally {
            json.put("id", id);
            json.put("data", jsonArray);
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    /**
     * 给用户授权角色<br>
     * 1.当前是超级管理员则所有角色均可用于授权,否则需要查询当前登录人的角色<br>
     * 2.当前是超级管理员则直接删掉要授权用户的所有角色,插入最新的角色,否则只能删除授权人是本人的信息<br>
     * <br>
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "saveUserRole", method = RequestMethod.POST)
    public void saveUserRole(HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            SysUser sysUser = this.loginAdminUser(request);
            Integer loginUserId = sysUser.getId();
            String[] roleIds = request.getParameterValues("roleIds[]");
            String userId = request.getParameter("id");
            // 当前登录用户对该角色没有操作权限
            List<String> list = sysUserService.findHaveIds(new String[]{userId}, loginUserId + "");
            boolean isAdmin = false;
            if (list != null && list.size() > 0) {
                if (SysUser.isAdmin(sysUser.getId() + "")){
                    isAdmin = true;
                }
                // 要授予的角色不为空的时候要查询当前登录用户在该范围内有哪些角色;要授予的角色为空的时候直接删除该用户的所有权限
                if (roleIds != null && roleIds.length > 0 && !isAdmin) {
                    // 如果不是超级管理员则传入的角色ID要作为条件结合当前登录用户的id查出当前登录者拥有的权限
                    roleIds = sysUserService.findRoleIdByUserId(loginUserId, roleIds);
                }
                sysUserService.addUserRole(Integer.valueOf(userId), roleIds,isAdmin);
                code = Status.SUCCESS.getName();
                msg = Status.SUCCESS.getValue();
                sysLogService.insertLog(new SysLog(loginUserId, "saveUserRole", StringUtils.join(roleIds, ",")));
            } else {
                msg = "当前登录用户对该角色没有操作权限";
            }
        } catch (Exception e) {
            logger.error("saveUserRole error", e);
        } finally {
            json.put("data", jsonArray);
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    /**
     * 跳转到子用户列表
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "findUserByParentId", method = RequestMethod.GET)
    public String findUserByParentId(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("parentId", request.getParameter("myId"));
        // 如果没有选择父节点则当前登录用户是父节点
        SysUser loginUser = loginAdminUser(request);
        model.addAttribute("parentUserId", loginUser.getId());
        model.addAttribute("parentUserName", loginUser.getUserAccount());
        return "user/sonUserList";
    }

    /**
     * 根据用户ID该用户的子用户
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "findUserByParentId", method = RequestMethod.POST)
    public void findUserByParentId(HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        String type = request.getParameter("type");
        String parentId = request.getParameter("parentId");
        List<ZTree> list = new ArrayList<>();
        try {
            SysUser sysUser = this.loginAdminUser(request);
            // 如果父节点不存在则是首次加载,以当前登录用户为根节点
            if (parentId == null) {
                // 如果当前是超级管理员则查询超级管理员的子用户,否则查询当前登录用户子用户
                if (SysUser.isAdmin(sysUser.getId() + "")) {
                    parentId = SysUser.PARENT_ID_ADMIN + "";
                } else {
                    parentId = sysUser.getId() + "";
                }
            }
            if ("tree".equals(type)) {
                list = sysUserService.findByParentId(parentId);
            } else {
                HashMap<String, Object> params = new HashMap<>();
                params.put("parentId", parentId);
                Paging<SysUser> page = sysUserService.findPage(params);
                json.put("data", page.getList());
                json.put("count", page.getTotal() + "");
                code = Status.SUCCESS.getName();
                msg = Status.SUCCESS.getValue();
            }
        } catch (Exception e) {
            logger.error("findUserByParentId error", e);
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

    @RequestMapping(value = "updateUserPassword", method = RequestMethod.POST)
    public void updateUserPassword(HttpServletRequest request, HttpServletResponse response, SysUser sysUser) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            String new1userPassWord = request.getParameter("new1UserPassword");
            String new2userPassWord = request.getParameter("new2UserPassword");
            SysUser backUsers = this.loginAdminUser(request);
            SysUser backUser = sysUserService.findById(backUsers.getId());
            if (MD5coding.getInstance().encrypt(AESUtil.getInstance().encrypt(sysUser.getUserPassword(), "")).equals(backUser.getUserPassword())) {
                if (new1userPassWord.equals(new2userPassWord)) {
                    sysUser.setUserPassword(MD5coding.getInstance().encrypt(AESUtil.getInstance().encrypt(new2userPassWord, "")));
                    sysUser.setId(backUser.getId());
                    sysUserService.updatePwdById(sysUser);
                    code = Status.SUCCESS.getName();
                    msg = Status.SUCCESS.getValue();
                    sysLogService.insertLog(new SysLog(loginAdminUser(request).getId(), "updateUserPassWord", sysUser.toString()));
                } else {
                    msg = "新密码两次输入不一致！";
                }
            } else {
                msg = "旧密码输入错误！";
            }
        } catch (Exception e) {
            logger.error("updateUserPassword post sysUser:{}", sysUser, e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }
}
