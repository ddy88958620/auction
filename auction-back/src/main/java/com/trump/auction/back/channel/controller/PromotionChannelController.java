package com.trump.auction.back.channel.controller;

import com.cf.common.id.IdGenerator;
import com.cf.common.util.page.Paging;
import com.cf.common.utils.JSONUtil;
import com.cf.common.utils.JsonResult;
import com.cf.common.utils.Status;
import com.trump.auction.back.channel.model.PromotionChannel;
import com.trump.auction.back.channel.service.PromotionChannelService;
import com.trump.auction.back.constant.SysConstant;
import com.trump.auction.back.sys.controller.BaseController;
import com.trump.auction.back.sys.model.Config;
import com.trump.auction.back.sys.model.SysUser;
import com.trump.auction.back.sys.service.ConfigService;
import com.trump.auction.order.api.AddressInfoStuService;
import com.trump.auction.order.model.AddressInfoModel;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 推广渠道管理
 * @author wangjian 2018-1-19
 */
@Slf4j
@Controller
@RequestMapping("channel/")
public class PromotionChannelController extends BaseController {

    @Autowired
    private PromotionChannelService channelService;

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private AddressInfoStuService addressInfoStuService;

    @Autowired
    private ConfigService configService;

    @RequestMapping(value = "findToJsp", method = RequestMethod.GET)
    public String findToJsp(HttpServletRequest request, Model model){
        model.addAttribute("parentId", request.getParameter("myId"));
        List<Config> configs = configService.findParams(SysConstant.WEBSITE);
        String str = null;
        for (Config item:configs
                ) {
            if (SysConstant.COOPERATION_MODE.equals(item.getSysKey())) {
                str = item.getSysValueBig();
                break;
            }
        }
        model.addAttribute("cooperationModeList", JSONUtil.jsonArrToMapList(str));
        return "channel/channelList";
    }

    /**
     * 查看按钮跳转
     * @param request
     * @param model
     */
    @RequestMapping(value = "preview", method = RequestMethod.GET)
    public String preview(HttpServletRequest request, Model model,String channelId){
        model.addAttribute("parentId", request.getParameter("myId"));
        List<Config> configs = configService.findParams(SysConstant.WEBSITE);
        String str = null;
        for (Config item:configs
                ) {
            if (SysConstant.COOPERATION_MODE.equals(item.getSysKey())) {
                str = item.getSysValueBig();
                break;
            }
        }
        model.addAttribute("cooperationModeList", JSONUtil.jsonArrToMapList(str));
        model.addAttribute("provinceList", addressInfoStuService.findAddressInfoListByParentId(null));
        model.addAttribute("channelInfo", channelService.findPromotionChannelOne(request.getParameter("channelId")).getData());
        return "channel/channelView";
    }

    /**
     * 展示推广渠道列表
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
            JsonResult jsonResult = channelService.findPromotionChannelList(params);
            if (JsonResult.SUCCESS.equals(jsonResult.getCode())) {
                Paging<PromotionChannel> page = (Paging<PromotionChannel>) jsonResult.getData();
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
            if (SysConstant.COOPERATION_MODE.equals(item.getSysKey())) {
                str = item.getSysValueBig();
                break;
            }
        }
        model.addAttribute("cooperationModeList", JSONUtil.jsonArrToMapList(str));
        model.addAttribute("provinceList", addressInfoStuService.findAddressInfoListByParentId(null));
        model.addAttribute("channelInfo", channelService.findPromotionChannelOne(request.getParameter("id")).getData());
        return "channel/channelUpdate";
    }

    /**
     * 修改推广渠道信息
     * @param request
     * @param response
     * @param promotionChannel
     * @param model
     */
    @RequestMapping(value = "updateChannel", method = RequestMethod.POST)
    public void updateChannel(HttpServletRequest request, HttpServletResponse response, PromotionChannel promotionChannel, Model model){
        Map<String,Object> params = getParametersO(request);
        model.addAttribute("params", params);
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            SysUser user = loginAdminUser(request);
            promotionChannel.setUserId(user.getId());
            promotionChannel.setUserIp(getIpAddr(request));
            JsonResult jsonResult = channelService.updatePromotionChannel(promotionChannel);
            if (JsonResult.SUCCESS.equals(jsonResult.getCode())) {
                code = Status.SUCCESS.getName();
                msg = Status.SUCCESS.getValue();
            } else {
                log.error("updateChannel error:", jsonResult.getMsg());
            }
        } catch (Exception e) {
            log.error("updateChannel error:", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    @RequestMapping(value = "saveToJsp", method = RequestMethod.GET)
    public String saveToJsp(HttpServletRequest request, Model model){
        model.addAttribute("parentId", request.getParameter("myId"));
        model.addAttribute("channelId", idGenerator.next());
        List<Config> configs = configService.findParams(SysConstant.WEBSITE);
        String str = null;
        for (Config item:configs
                ) {
            if (SysConstant.COOPERATION_MODE.equals(item.getSysKey())) {
                str = item.getSysValueBig();
                break;
            }
        }
        model.addAttribute("cooperationModeList", JSONUtil.jsonArrToMapList(str));
        model.addAttribute("provinceList", addressInfoStuService.findAddressInfoListByParentId(null));
        return "channel/channelAdd";
    }

    /**
     * 保存消息信息
     * @param request
     * @param response
     * @param promotionChannel
     * @param model
     */
    @RequestMapping(value = "saveChannel", method = RequestMethod.POST)
    public void saveChannel(HttpServletRequest request, HttpServletResponse response, PromotionChannel promotionChannel, Model model){
        Map<String,Object> params = getParametersO(request);
        model.addAttribute("params", params);
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            SysUser user = loginAdminUser(request);
            promotionChannel.setUserId(user.getId());
            promotionChannel.setUserIp(getIpAddr(request));
            String channelSource=promotionChannel.getChannelSource();
            Object result=channelService.findChannelSource(channelSource).getData();
            if((result==null||result=="")){
                JsonResult jsonResult = channelService.savePromotionChannel(promotionChannel);
                if (JsonResult.SUCCESS.equals(jsonResult.getCode())) {
                    code = Status.SUCCESS.getName();
                    msg = Status.SUCCESS.getValue();
                } else {
                    log.error("saveChannel error:", jsonResult.getMsg());
                }
            }else {
                 code = Status.ERROR.getName();
                 msg = "渠道来源已经存在！";
            }
        } catch (Exception e) {
            log.error("saveChannel error:", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    /**
     * 批量删除推广渠道信息
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "deleteChannel", method = RequestMethod.POST)
    public void deleteChannel(HttpServletRequest request, HttpServletResponse response, Model model){
        Map<String,Object> params = getParametersO(request);
        model.addAttribute("params", params);
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            String[] ids = request.getParameterValues("ids[]");
            JsonResult jsonResult = channelService.deletePromotionChannel(ids);
            if (JsonResult.SUCCESS.equals(jsonResult.getCode())) {
                code = Status.SUCCESS.getName();
                msg = Status.SUCCESS.getValue();
            } else {
                log.error("deleteChannel error:", jsonResult.getMsg());
            }
        } catch (Exception e) {
            log.error("deleteChannel error:", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }

    @ResponseBody
    @RequestMapping("getAddressInfo")
    public String getAddressInfo(Integer parentId){
        return JSONUtil.beanListToJson(addressInfoStuService.findAddressInfoListByParentId(parentId));
    }

    @RequestMapping("getQrc")
    public void getQrc(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> params = this.getParametersO(request);
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        String qrcUrl = params.get("qrcUrl") + "";
        try {
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
        } catch (Exception e) {
            log.error("getQrc error", e);
        } finally {
            json.put("data", qrcUrl);
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }
}
