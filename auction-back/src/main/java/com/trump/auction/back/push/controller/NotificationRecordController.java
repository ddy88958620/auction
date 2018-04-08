package com.trump.auction.back.push.controller;

import com.alibaba.fastjson.JSONObject;
import com.cf.common.util.page.Paging;
import com.trump.auction.back.push.enums.NotificationRecordNotiTypeEnum;
import com.trump.auction.back.push.enums.NotificationRecordSendStatusEnum;
import com.trump.auction.back.push.enums.NotificationRecordTimeTypeEnum;
import com.trump.auction.back.push.model.NotificationRecord;
import com.trump.auction.back.push.service.NotificationRecordService;
import com.trump.auction.back.sys.controller.BaseController;
import com.trump.auction.back.sys.model.SysUser;
import com.trump.auction.back.util.common.Status;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: zhanping
 */
@Controller
@RequestMapping(value = "notiRecord/")
public class NotificationRecordController extends BaseController{

    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private NotificationRecordService notificationRecordService;

    /**
     * 推送列表
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
        String myId = request.getParameter("myId");
        model.addAttribute("parentId", myId);
        Map<Integer, String> sendStatus = NotificationRecordSendStatusEnum.getAllType();
        Map<Integer, String> notiType = NotificationRecordNotiTypeEnum.getAllType();
        model.addAttribute("sendStatus",sendStatus);
        model.addAttribute("notiType",notiType);
        return "push/record/list";
    }

    /**
     * 推送列表
     * @param request
     * @param response
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public void listData(HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            HashMap<String, Object> params = getParametersO(request);
            Paging<NotificationRecord> page = notificationRecordService.list(params);
            json.put("data", page.getList());
            json.put("count", page.getTotal() + "");
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
        } catch (Exception e) {
            logger.error("listData error", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(HttpServletRequest request, HttpServletResponse response, Model model) {
        String myId = request.getParameter("myId");
        model.addAttribute("parentId", myId);
        Map<Integer, String> notiType = NotificationRecordNotiTypeEnum.getAllType();
        model.addAttribute("notiType",notiType);
        Map<Integer, String> timeType = NotificationRecordTimeTypeEnum.getAllType();
        model.addAttribute("timeType",timeType);
        return "push/record/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public void addData(HttpServletRequest request, HttpServletResponse response, NotificationRecord obj){
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            Map<String, String> parameters = getParameters(request);
            String sendTimeSel = parameters.get("sendTimeSel");
            if (obj.getTimeType() == 2 && StringUtils.isNotBlank(sendTimeSel)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                obj.setSendTime(sdf.parse(sendTimeSel));
            }
            obj.setStatus(0);
            obj.setCreateTime(new Date());
            obj.setSendStatus(NotificationRecordSendStatusEnum.ED.getType());
            SysUser sysUser = loginAdminUser(request);
            obj.setUserId(sysUser.getId());
            obj.setUserName(sysUser.getUserName());
            obj.setUserIp(getIpAddr(request));
            int count = notificationRecordService.add(obj);
            if (count == 1) {
                code = Status.SUCCESS.getName();
                msg = Status.SUCCESS.getValue();
            }
        } catch (Exception e) {
            logger.error("add error", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    @RequestMapping(value = "selectProduct", method = RequestMethod.GET)
    public String selectProduct(HttpServletRequest request, HttpServletResponse response, Model model) {
        String myId = request.getParameter("myId");
        model.addAttribute("parentId", myId);
        Map<Integer, String> notiType = NotificationRecordNotiTypeEnum.getAllType();
        model.addAttribute("notiType",notiType);
        Map<Integer, String> timeType = NotificationRecordTimeTypeEnum.getAllType();
        model.addAttribute("timeType",timeType);
        return "push/record/selectProduct";
    }

    @RequestMapping(value = "selectActivity", method = RequestMethod.GET)
    public String selectActivity(HttpServletRequest request, HttpServletResponse response, Model model) {
        String myId = request.getParameter("myId");
        model.addAttribute("parentId", myId);
        Map<Integer, String> notiType = NotificationRecordNotiTypeEnum.getAllType();
        model.addAttribute("notiType",notiType);
        Map<Integer, String> timeType = NotificationRecordTimeTypeEnum.getAllType();
        model.addAttribute("timeType",timeType);
        return "push/record/selectActivity";
    }

    @RequestMapping(value = "selectActivity", method = RequestMethod.POST)
    public void selectActivityData(HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            HashMap<String, Object> params = getParametersO(request);
            Paging<NotificationRecord> page = new Paging<>();
            json.put("data", page.getList());
            json.put("count", page.getTotal() + "");
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
        } catch (Exception e) {
            logger.error("listData error", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }


    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public String detail(HttpServletRequest request, HttpServletResponse response, Model model,Integer id) {
        String myId = request.getParameter("myId");
        model.addAttribute("parentId", myId);
        Map<Integer, String> notiType = NotificationRecordNotiTypeEnum.getAllType();
        model.addAttribute("notiType",notiType);
        Map<Integer, String> timeType = NotificationRecordTimeTypeEnum.getAllType();
        model.addAttribute("timeType",timeType);
        Map<Integer, String> sendStatus = NotificationRecordSendStatusEnum.getAllType();
        model.addAttribute("sendStatus",sendStatus);
        NotificationRecord record = notificationRecordService.findById(id);
        model.addAttribute("record",record);
        return "push/record/detail";
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public void delete(HttpServletRequest request, HttpServletResponse response, Integer id) {
        net.sf.json.JSONObject json = new net.sf.json.JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            int count = notificationRecordService.delete(id);
            if (count==1){
                code = Status.SUCCESS.getName();
                msg = Status.SUCCESS.getValue();
            }
        } catch (Exception e) {
            logger.error("save error post delete:{}", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

}
