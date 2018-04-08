package com.trump.auction.back.robot.controller;

import com.cf.common.util.page.Paging;
import com.cf.common.utils.Status;

import com.trump.auction.back.robot.enums.RobotStatusEnum;
import com.trump.auction.back.robot.model.RobotInfo;
import com.trump.auction.back.robot.service.RobotService;
import com.trump.auction.back.sys.controller.BaseController;
import com.trump.auction.back.sys.model.SysUser;
import com.trump.auction.back.util.file.PropertiesUtils;
import com.trump.auction.trade.api.RobotStubService;
import com.trump.auction.trade.model.RobotInfoModel;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 机器人
 *
 * @author zhangliyan
 * @create 2018-01-08 10:57
 **/
@Slf4j
@Controller
@RequestMapping("robot/")
public class RobotController extends BaseController {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private RobotService robotService;
    @Autowired
    private RobotStubService robotStubService;


    /**
     * 菜单跳转页(订单列表)
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("findList")
    public String findList(HttpServletRequest request,  Model model) {
        model.addAttribute("parentId", request.getParameter("myId"));
        return "robot/robotList";
    }

    /**
     * 分页查询机器人
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "findListPage", method = RequestMethod.POST)
    public void findListPage(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = getParametersO(request);
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            Paging<RobotInfo> page = robotService.findRobotPage(params);
            json.put("data", page.getList());
            json.put("count", page.getTotal() + "");
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
        } catch (Exception e) {
            log.error("findListPage error:", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    /**
     * 添加机器人
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value="createRobot",method = RequestMethod.GET)
    public String createRobot(HttpServletRequest request,  Model model) {
        model.addAttribute("parentId", request.getParameter("myId"));
        model.addAttribute("parentName", request.getParameter("parentName"));
        model.addAttribute("params", RobotStatusEnum.getAllType());
        model.addAttribute("url", "/module/save");
        return "robot/createRobot";
    }
    /**
     * 保存机器人
     */
    @RequestMapping(value = "saveRobot", method = RequestMethod.POST)
    public void saveAuctionRule(HttpServletRequest request, HttpServletResponse response, RobotInfoModel robotInfoModel) {
        JSONObject json = new JSONObject();

        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            SysUser backUser = loginAdminUser(request);
            if (backUser == null) {
                json.put("code", "-1");
                json.put("msg", "当前用户未登陆，请登陆");
                renderJson(response, json);
            }
            Integer count = robotStubService.insertRobot(robotInfoModel);

            if(count == 1){
                code = Status.SUCCESS.getName();
                msg = Status.SUCCESS.getValue();
            }

        } catch (Exception e) {
            logger.error("saveAuctionRule error", e);
        }finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }
    /**
     * 修改机器人
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="editRobot",method = RequestMethod.GET)
    public String editRobot(HttpServletRequest request,  Model model) {
        String id = request.getParameter("id");
        Integer mId = Integer.parseInt(id);
        String msg = null;
        if (StringUtils.isNotBlank(id)) {
            RobotInfo robotInfo = robotService.findRobotById(mId);
            if (robotInfo != null) {
                model.addAttribute("robotInfo", robotInfo);
                model.addAttribute("status", RobotStatusEnum.getAllType());
            } else {
                msg = "该记录不存在";
            }
        }else{
            msg = "非法参数";
        }
        String imgUrl= PropertiesUtils.get("aliyun.oos.url");
        model.addAttribute(MESSAGE, msg);
        model.addAttribute("parentId", request.getParameter("myId"));
        model.addAttribute("parentName", request.getParameter("parentName"));
        model.addAttribute("imgUrl",imgUrl);
        return "robot/editRobot";
    }
    /**
     * 保存修改
     */
    @RequestMapping(value = "saveUpdateRobot", method = RequestMethod.POST)
    public void saveUpdateRobot(HttpServletRequest request,HttpServletResponse response, RobotInfoModel robotInfoModel){
        JSONObject json = new JSONObject();
        String code;
        String msg;
        try {
            SysUser backUser = loginAdminUser(request);
            if (backUser == null) {
                json.put("code", "-1");
                json.put("msg", "当前用户未登陆，请登陆");
                renderJson(response, json);
            }
            int count = robotStubService.saveUpdateRobot(robotInfoModel);
            if (count >0) {
                code = Status.SUCCESS.getName();
                msg = "成功修改:" + count + "条数据";
                json.put("code", code);
                json.put("msg", msg);
                renderJson(response, json);
            }
        } catch (Exception e) {
            log.error("save error post update:{}",robotInfoModel, e);
        }
    }

    /**
     * 根据id删除规则
     * @param request
     * @param response
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void delete(HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg =Status.ERROR.getValue();
        try {
            String[] ids = request.getParameterValues("ids[]");
            if (ids != null && ids.length > 0) {
                int count = robotStubService.deleteRobot(ids);
                code = Status.SUCCESS.getName();
                msg = "成功删除:" + count + "条数据";
            } else {
                msg = "请选择要删除的行";
            }

        } catch (Exception e) {
            log.error("save error post delete:{}", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }
}
