package com.trump.auction.back.sys.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.trump.auction.back.constant.SysConstant;
import com.trump.auction.back.sys.model.SysLog;
import com.trump.auction.back.sys.service.SysLogService;
import com.trump.auction.back.util.common.Status;
import com.trump.auction.back.util.common.SysContant;
import com.trump.auction.back.util.sys.UploadUtils;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.trump.auction.back.sys.model.Module;
import com.trump.auction.back.sys.model.SysUser;
import com.trump.auction.back.sys.service.ModuleService;
import com.trump.auction.back.util.sys.SystemConfigStarter;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 类描述：首页类 <br>
 * 创建人：fanyinchuan<br>
 * 创建时间：2016-6-28 下午02:57:46 <br>
 */
@Controller
public class IndexController extends BaseController {
    private static Logger logger = Logger.getLogger(IndexController.class);
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private SysLogService sysLogService;
    @RequestMapping("indexBack")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            HashMap<String, Object> params = new HashMap<String, Object>();
            SysUser backUser = loginAdminUser(request);
            if (backUser == null) {
                return "login";
            }
            if (SysUser.isAdmin(backUser.getId() + "")) {
                model.addAttribute("isSuper", true);
            }
            model.addAttribute("loginUser", backUser);
            params.put("userId", backUser.getId());
            params.put("parentId", "0");
            // 获得顶级菜单
            List<Module> menuModuleList = moduleService.findAllModule(params);

            int moduleId = 0;
            if (menuModuleList != null && menuModuleList.size() > 0) {
                moduleId = menuModuleList.get(0).getId();
            }
            model.addAttribute("website", jedisCluster.hgetAll(SysConstant.WEBSITE));
            model.addAttribute("moduleId", moduleId);
            model.addAttribute("menuModuleList", menuModuleList);
            model.addAttribute("updateUserPwdUrl", "user/updateUserPassword");
        } catch (Exception e) {
            logger.error("back index error ", e);
        }
        return "index";
    }

    @RequestMapping("subMenu")
    public void subMenu(HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            HashMap<String, Object> params = this.getParametersO(request);
            SysUser backUser = loginAdminUser(request);
            params.put("userId", backUser.getId());
            params.put("parentId", params.get("myId"));
            // 获得某个顶级菜单的子菜单（二级菜单）
            List<Module> subMenu = moduleService.findAllModule(params);
            for (Module backModule : subMenu) {
                params.put("parentId", backModule.getId());
                // 获得某个二级菜单的子菜单（三级菜单）
                List<Module> thirdMenu = moduleService.findAllModule(params);
                backModule.setModuleList(thirdMenu);
            }
            json.put("data", subMenu);
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
        } catch (Exception e) {
            logger.error("subMenu error ", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    @RequestMapping("/rightSubList")
    public void rightSubList(HttpServletRequest request, HttpServletResponse response, Model model) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("parentId", request.getParameter("parentId"));
            SysUser backUser = loginAdminUser(request);
            params.put("userId", backUser.getId());
            // 获得当前登录用户的myId下的子权限
            List<Module> rightSubList = moduleService.findAllModule(params);
            model.addAttribute("rightSubList", rightSubList);
            json.put("data", rightSubList);
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
        } catch (Exception e) {
            logger.error("rightSubList error ", e);
            model.addAttribute(MESSAGE, "权限查询异常！");
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    /**
     * 更新系统缓存<br>
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("updateCache")
    public void updateCache(HttpServletRequest request, HttpServletResponse response, Model model) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            SystemConfigStarter.getInstance().init(null);
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
        } catch (Exception e) {
            logger.error("updateCache error ", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    @RequestMapping(value = "uploadFiles", method = RequestMethod.POST)
    public void uploadFiles(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            List<MultipartFile> files = multipartRequest.getFiles("file");
            String reqPath = null;
            String realFileName = null;
            String suffix = null;
            for (MultipartFile file : files) {
                realFileName = file.getOriginalFilename();
                /** 构建图片保存的目录 **/
                String filePathDir = UploadUtils.getRelatedPath();
                /** 得到图片保存目录的真实路径 **/
                String fileRealPathDir = UploadUtils.getRealPath();
                /** 获取文件的后缀 **/
                suffix = realFileName.substring(realFileName.lastIndexOf("."));
                // /**使用UUID生成文件名称**/
                String fileImageName = UUID.randomUUID().toString() + suffix;// 构建文件名称
                // String fileImageName = multipartFile.getOriginalFilename();
                /** 拼成完整的文件保存路径加文件 **/
                String fileName = fileRealPathDir + File.separator + fileImageName;

                String resultFilePath = filePathDir + "/" + fileImageName;
                File newFile = new File(fileName);
                try {
                    FileCopyUtils.copy(file.getBytes(), newFile);
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (StringUtils.isNotBlank(resultFilePath)) {
                    resultFilePath = resultFilePath.replaceAll("\\\\", "/");
                }
                reqPath = resultFilePath;
                code = Status.SUCCESS.getName();
                msg = Status.SUCCESS.getValue();
                json.put("data", "{\"src\":\"" + reqPath + "\"}");
                sysLogService.insertLog(new SysLog(loginAdminUser(request).getId(), "uploadFile", reqPath));

            }
        } catch (Exception e) {
            logger.error("uploadFiles error ", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

}
