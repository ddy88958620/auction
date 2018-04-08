package com.trump.auction.back.sendSms.controller;

import com.cf.common.util.page.Paging;
import com.cf.common.utils.JsonResult;
import com.trump.auction.account.dto.AccountDto;
import com.trump.auction.back.channel.model.PromotionChannel;
import com.trump.auction.back.enums.SendTypeEnum;
import com.trump.auction.back.sendSms.model.SendSmsRecord;
import com.trump.auction.back.sendSms.model.SendSmsTemplate;
import com.trump.auction.back.sendSms.service.SendSmsRecordService;
import com.trump.auction.back.sendSms.service.SendSmsTemplateService;
import com.trump.auction.back.sys.controller.BaseController;
import com.trump.auction.back.sys.model.SysUser;
import com.trump.auction.back.util.common.Status;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:limingwu
 * @Description: 发送短信
 * @Date: Create in 9:58 2018/3/16
 */
@Controller
@RequestMapping("sms/")
public class SendSmsController extends BaseController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private SendSmsTemplateService sendSmsTemplateService;

    @Autowired
    private SendSmsRecordService sendSmsRecordService;


    /**
     * 跳转到群发短信界面
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "groupSendSms", method = RequestMethod.GET)
    public String findGroupSendSms(HttpServletRequest request, HttpServletResponse response, Model model){
        Map<String,Object> params = getParametersO(request);
        String myId = request.getParameter("myId");
        String ids = request.getParameter("ids");
        String [] idsArray = ids.split(",");
        model.addAttribute("count",idsArray.length);
        model.addAttribute("ids",ids);
        model.addAttribute("parentId", myId);
        return "frontUser/groupSmsAdd";
    }

    /**
     * 选择短信模板
     */
    @RequestMapping(value = "choose", method = RequestMethod.GET)
    public String chooseTemplate(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("parentId", request.getParameter("myId"));
        model.addAttribute("parentName",request.getParameter("parentName"));
        return "frontUser/templateList";
    }

    /**
     * 发送
     */
    @RequestMapping(value = "send", method = RequestMethod.POST)
    public void sned(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("templateId");
        String ids =request.getParameter("ids");
        int count = Integer.parseInt(request.getParameter("count"));
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            SysUser user = loginAdminUser(request);
            SendSmsRecord sendSmsRecord = new SendSmsRecord();
            sendSmsRecord.setPhone(ids);
            sendSmsRecord.setSmsTemplateId(Integer.parseInt(id));
            sendSmsRecord.setPublisher(user.getUserName());
            sendSmsRecord.setCount(count);
            int countRecord = sendSmsRecordService.saveSendSmsRecord(sendSmsRecord);
            if(countRecord>0){
                code = Status.SUCCESS.getName();
                msg = Status.SUCCESS.getValue();
            }
        } catch (Exception e) {
            logger.error("getTemplateList error", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    /**
     * 查询短信模板信息列表
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "getTemplateList", method = RequestMethod.POST)
    public void templateList(HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            HashMap<String, Object> params = getParametersO(request);
            Paging<SendSmsTemplate> page = sendSmsTemplateService.selectSendSmsTemplateInfo(params);
            json.put("data", page.getList());
            json.put("count", page.getTotal() + "");
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
        } catch (Exception e) {
            logger.error("getTemplateList error", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    /**
     * 选中短信模板
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "selected", method = RequestMethod.POST)
    public void selectTemplate(HttpServletRequest request, HttpServletResponse response, Model model) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            SendSmsTemplate sendSmsTemplate = new SendSmsTemplate();
            sendSmsTemplate.setId(id);
            sendSmsTemplate = sendSmsTemplateService.findByParameter(sendSmsTemplate);
            // 发送短信的条数
            double sendNum=1;
            if(sendSmsTemplate.getContent().length()>70){
                sendNum = Math.ceil(sendSmsTemplate.getContent().length()/67.0);
            }else{
                sendNum = 1;
            }
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
            json.put("sendSmsTemplate", sendSmsTemplate);
            json.put("num",sendSmsTemplate.getContent().length());
            json.put("sendNum",sendNum);
            model.addAttribute("sendSmsTemplate",sendSmsTemplate);
        } catch (Exception e) {
            logger.error("selectTemplate error", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }


    /**
     * 跳转到群发短信记录页面
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "groupSendSmsRecord", method = RequestMethod.GET)
    public String findGroupSendSmsRecord(HttpServletRequest request, HttpServletResponse response, Model model){
        String myId = request.getParameter("myId");
        model.addAttribute("parentId", myId);
        model.addAttribute("sendTypeList", SendTypeEnum.getAllType());
        return "frontUser/sendSmsRecord";
    }

    /**
     * 查询短信记录列表
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "sendSmsRecordList", method = RequestMethod.POST)
    public void sendSmsRecordList(HttpServletRequest request, HttpServletResponse response,Model model) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            HashMap<String, Object> params = getParametersO(request);
            Paging<SendSmsRecord> page = sendSmsRecordService.selectSendSmsRecordInfo(params);
            json.put("data", page.getList());
            json.put("count", page.getTotal() + "");
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
        } catch (Exception e) {
            logger.error("sendSmsRecordList error", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    /**
     * 查看短信记录信息
     */
    @RequestMapping(value = "seeSmsRecord", method = RequestMethod.GET)
    public String seeSmsRecord(HttpServletRequest request, HttpServletResponse response,Model model){
        String id = request.getParameter("id");
        try{
            SendSmsRecord sendSmsRecord = new SendSmsRecord();
            sendSmsRecord.setId(Integer.parseInt(id));
            sendSmsRecord  =  sendSmsRecordService.findByParameter(sendSmsRecord);
            model.addAttribute("sendSmsRecord",sendSmsRecord);
            model.addAttribute("sendTypeList", SendTypeEnum.getAllType());
        } catch (Exception e) {
            logger.error("seeSmsRecord error:", e);
        }
        return "frontUser/sendSmsRecordView";
    }

    /**
     * 跳转短信模板页面
     */
    @RequestMapping(value = "chooseTemplate", method = RequestMethod.GET)
    public String chooseTemplateList(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("parentId", request.getParameter("myId"));
        model.addAttribute("parentName",request.getParameter("parentName"));
        return "frontUser/sendTemlateList";
    }

    /**
     * 新建模板
     */
    @RequestMapping(value = "createTemplate", method = RequestMethod.GET)
    public String createTemplate(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("parentId", request.getParameter("myId"));
        model.addAttribute("parentName",request.getParameter("parentName"));
        model.addAttribute("sendTypeList", SendTypeEnum.getAllType());
        return "frontUser/templateSmsAdd";
    }

    /**
     * 保存模板
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "saveSmsTemplate", method = RequestMethod.POST)
    public void saveSmsTemplate(HttpServletRequest request, HttpServletResponse response,SendSmsTemplate sendSmsTemplate,Model model) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            int count = sendSmsTemplateService.saveSendSmsTemplate(sendSmsTemplate);
            if(count>0){
                code = Status.SUCCESS.getName();
                msg = Status.SUCCESS.getValue();
            }
        } catch (Exception e) {
            logger.error("saveSmsTemplate error", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    /**
     * 查看模板信息
     */
    @RequestMapping(value = "seeSmsTemplate", method = RequestMethod.GET)
    public String seeSmsTemplate(HttpServletRequest request, HttpServletResponse response,Model model){
        String id = request.getParameter("id");
        try{
          SendSmsTemplate sendSmsTemplate = new SendSmsTemplate();
          sendSmsTemplate.setId(Integer.parseInt(id));
          sendSmsTemplate  =  sendSmsTemplateService.findByParameter(sendSmsTemplate);
          model.addAttribute("sendSmsTemplate",sendSmsTemplate);
          model.addAttribute("sendTypeList", SendTypeEnum.getAllType());
        } catch (Exception e) {
            logger.error("seeSmsTemplate error:", e);
        }
        return "frontUser/templateSmsView";
    }

    /**
     * 跳转修改模板信息页面
     */
    @RequestMapping(value = "updateSmsTemplate", method = RequestMethod.GET)
    public String updateSmsTemplate(HttpServletRequest request, HttpServletResponse response,Model model){
        String id = request.getParameter("id");
        try{
            SendSmsTemplate sendSmsTemplate = new SendSmsTemplate();
            sendSmsTemplate.setId(Integer.parseInt(id));
            sendSmsTemplate  =  sendSmsTemplateService.findByParameter(sendSmsTemplate);
            model.addAttribute("sendSmsTemplate",sendSmsTemplate);
            model.addAttribute("sendTypeList", SendTypeEnum.getAllType());
        } catch (Exception e) {
            logger.error("updateSmsTemplate error:", e);
        }
        return "frontUser/templateSmsUpdate";
    }

    /**
     * 修改模板信息
     */
    @RequestMapping(value = "updateTemplate", method = RequestMethod.POST)
    public void updateTemplate(HttpServletRequest request, HttpServletResponse response,SendSmsTemplate sendSmsTemplate,Model model){
        JSONObject json = new JSONObject();
        String code = com.cf.common.utils.Status.ERROR.getName();
        String msg = com.cf.common.utils.Status.ERROR.getValue();
        try {
            int count = sendSmsTemplateService.updateSendSmsTemplate(sendSmsTemplate);
            if(count>0){
                code = com.cf.common.utils.Status.SUCCESS.getName();
                msg = com.cf.common.utils.Status.SUCCESS.getValue();
            }
        }catch (Exception e){
            logger.error("updateTemplate error:", e);
        }finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    /**
     * 删除模板信息
     */
    @RequestMapping(value = "deleteTemplate", method = RequestMethod.POST)
    public void deleteTemplate(HttpServletRequest request, HttpServletResponse response,Model model){
        String id = request.getParameter("id");
        JSONObject json = new JSONObject();
        String code = com.cf.common.utils.Status.ERROR.getName();
        String msg = com.cf.common.utils.Status.ERROR.getValue();
        try {
            SendSmsTemplate sendSmsTemplate = new SendSmsTemplate();
            sendSmsTemplate.setId(Integer.parseInt(id));
            int count = sendSmsTemplateService.deleteSendSmsTemplate(sendSmsTemplate);
            if(count>0){
                code = com.cf.common.utils.Status.SUCCESS.getName();
                msg = com.cf.common.utils.Status.SUCCESS.getValue();
            }
        }catch (Exception e){
            logger.error("updateTemplate error:", e);
        }finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

}
