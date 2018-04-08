package com.trump.auction.back.messagecenter.controller;

import com.cf.common.util.page.Paging;
import com.cf.common.utils.JSONUtil;
import com.cf.common.utils.JsonResult;
import com.cf.common.utils.Status;
import com.trump.auction.back.constant.SysConstant;
import com.trump.auction.back.messagecenter.model.MessageCenter;
import com.trump.auction.back.messagecenter.service.MessageCenterService;
import com.trump.auction.back.sys.controller.BaseController;
import com.trump.auction.back.sys.model.Config;
import com.trump.auction.back.sys.model.SysUser;
import com.trump.auction.back.sys.service.ConfigService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 消息中心管理
 * @author Created by wangjian on 2018/1/4.
 */
@Slf4j
@Controller
@RequestMapping("messageCenter/")
public class MessageCenterController extends BaseController {

    @Autowired
    private MessageCenterService messageCenterService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private JedisCluster jedisCluster;

    @RequestMapping(value = "findToJsp", method = RequestMethod.GET)
    public String findToJsp(HttpServletRequest request, Model model){
        model.addAttribute("parentId", request.getParameter("myId"));
        List<Config> configs = configService.findParams(SysConstant.WEBSITE);
        String str = null;
        for (Config item:configs
             ) {
            if (SysConstant.HELP_TYPE.equals(item.getSysKey())) {
                str = item.getSysValueBig();
                break;
            }
        }
        model.addAttribute("messageTypeList", JSONUtil.jsonArrToMapList(str));
        return "messageCenter/messageList";
    }

    /**
     * 展示消息信息列表
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "findList", method = RequestMethod.POST)
    public void findList(HttpServletRequest request, HttpServletResponse response, Model model){
        Map<String,Object> params = getParametersO(request);
        model.addAttribute("params", params);
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            if (null != params.get("channelType") && "-999".equals(params.get("channelType").toString())){
                params.remove("channelType");
            }
            JsonResult jsonResult = messageCenterService.findMessageCenterList(params);
            if (JsonResult.SUCCESS.equals(jsonResult.getCode())) {
                Paging<MessageCenter> page = (Paging<MessageCenter>) jsonResult.getData();
                json.put("data", page.getList());
                json.put("count", page.getTotal() + "");
                code = Status.SUCCESS.getName();
                msg = Status.SUCCESS.getValue();
            }
        } catch (Exception e) {
            log.error("findList error:", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    @RequestMapping(value = "updateToJsp", method = RequestMethod.GET)
    public String updateToJsp(HttpServletRequest request, Model model){
        model.addAttribute("parentId", request.getParameter("myId"));
        List<Config> configs = configService.findParams(SysConstant.WEBSITE);
        String str = null;
        for (Config item:configs
                ) {
            if (SysConstant.HELP_TYPE.equals(item.getSysKey())) {
                str = item.getSysValueBig();
                break;
            }
    }
        model.addAttribute("messageTypeList", JSONUtil.jsonArrToMapList(str));
        model.addAttribute("messageInfo", messageCenterService.findMessageCenterOne(Integer.valueOf(request.getParameter("id"))).getData());
        return "messageCenter/messageUpdate";
    }

    /**
     * 修改消息信息
     * @param request
     * @param response
     * @param messageCenter
     * @param model
     */
    @RequestMapping(value = "updateMessageCenter", method = RequestMethod.POST)
    public void updateMessageCenter(HttpServletRequest request, HttpServletResponse response, MessageCenter messageCenter, Model model){
        Map<String,Object> params = getParametersO(request);
        model.addAttribute("params", params);
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            SysUser user = loginAdminUser(request);
            messageCenter.setUpdateUserId(user.getId());
            JsonResult jsonResult = messageCenterService.updateMessageCenter(messageCenter);
            if (JsonResult.SUCCESS.equals(jsonResult.getCode())) {

                //修改成功后重推redis
                List<MessageCenter> list = messageCenterService.findMessageCenterAll();
                String content = JSONUtil.beanListToJson(list);
                jedisCluster.set("auction.back.messages.center", content);
                log.info("pushRedis content:{}", content);

                code = Status.SUCCESS.getName();
                msg = Status.SUCCESS.getValue();
            } else {
                log.error("updateMessageCenter error:", jsonResult.getMsg());
            }
        } catch (Exception e) {
            log.error("updateMessageCenter error:", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    @RequestMapping(value = "saveToJsp", method = RequestMethod.GET)
    public String saveToJsp(HttpServletRequest request, Model model){
        model.addAttribute("parentId", request.getParameter("myId"));
        List<Config> configs = configService.findParams(SysConstant.WEBSITE);
        String str = null;
        for (Config item:configs
                ) {
            if (SysConstant.HELP_TYPE.equals(item.getSysKey())) {
                str = item.getSysValueBig();
                break;
            }
        }
        model.addAttribute("messageTypeList", JSONUtil.jsonArrToMapList(str));
        return "messageCenter/messageAdd";
    }

    /**
     * 保存消息信息
     * @param request
     * @param response
     * @param messageCenter
     * @param model
     */
    @RequestMapping(value = "saveMessageCenter", method = RequestMethod.POST)
    public void saveMessageCenter(HttpServletRequest request, HttpServletResponse response, MessageCenter messageCenter, Model model){
        Map<String,Object> params = getParametersO(request);
        model.addAttribute("params", params);
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            SysUser user = loginAdminUser(request);
            messageCenter.setAddUserId(user.getId() + "");
            messageCenter.setAddIp(getIpAddr(request));
            JsonResult jsonResult = messageCenterService.saveMessageCenter(messageCenter);
            if (JsonResult.SUCCESS.equals(jsonResult.getCode())) {

                //保存成功后重推redis
                List<MessageCenter> list = messageCenterService.findMessageCenterAll();
                String content = JSONUtil.beanListToJson(list);
                jedisCluster.set("auction.back.messages.center", content);
                log.info("pushRedis content:{}", content);

                code = Status.SUCCESS.getName();
                msg = Status.SUCCESS.getValue();
            } else {
                log.error("saveMessageCenter error:", jsonResult.getMsg());
            }
        } catch (Exception e) {
            log.error("saveMessageCenter error:", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    /**
     * 批量删除消息信息
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "deleteMessageCenter", method = RequestMethod.POST)
    public void deleteMessageCenter(HttpServletRequest request, HttpServletResponse response, Model model){
        Map<String,Object> params = getParametersO(request);
        model.addAttribute("params", params);
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            String[] ids = request.getParameterValues("ids[]");
            JsonResult jsonResult = messageCenterService.deleteMessageCenter(ids);
            if (JsonResult.SUCCESS.equals(jsonResult.getCode())) {

                //删除成功后重推redis
                List<MessageCenter> list = messageCenterService.findMessageCenterAll();
                String content = JSONUtil.beanListToJson(list);
                jedisCluster.set("auction.back.messages.center", content);
                log.info("pushRedis content:{}", content);

                code = Status.SUCCESS.getName();
                msg = Status.SUCCESS.getValue();
            } else {
                log.error("deleteMessageCenter error:", jsonResult.getMsg());
            }
        } catch (Exception e) {
            log.error("deleteMessageCenter error:", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    /**
     * 推送至redis
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "pushRedis")
    public void pushRedis(HttpServletRequest request, HttpServletResponse response, Model model){
        Map<String,Object> params = getParametersO(request);
        model.addAttribute("params", params);
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            List<MessageCenter> list = messageCenterService.findMessageCenterAll();
            String content = JSONUtil.beanListToJson(list);
            jedisCluster.set("auction.back.messages.center", content);
            log.info("pushRedis content:{}", content);
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
        } catch (Exception e) {
            log.error("pushRedis error:", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }
}
