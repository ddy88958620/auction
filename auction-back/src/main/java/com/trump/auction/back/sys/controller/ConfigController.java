package com.trump.auction.back.sys.controller;

import com.cf.common.utils.Status;
import com.trump.auction.back.sys.model.ConfigVo;
import com.trump.auction.back.sys.model.SysLog;
import com.trump.auction.back.sys.service.ConfigService;
import com.trump.auction.back.sys.service.SysLogService;
import com.trump.auction.back.util.sys.SystemConfigStarter;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("config/")
public class ConfigController extends BaseController {
    @Autowired
    private ConfigService configService;
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SysLogService sysLogService;

    @RequestMapping(value = "update/{sysType}", method = RequestMethod.GET)
    public String update(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("sysType") String sysType) {
        model.addAttribute("sysType", sysType);
        model.addAttribute("list", configService.findParams(sysType));
        model.addAttribute("url", "/config/update/"+sysType);
        return "config/config";
    }

    @RequestMapping(value = "update/{sysType}", method = RequestMethod.POST)
    public void update(HttpServletRequest request, HttpServletResponse response, @PathVariable("sysType") String sysType, ConfigVo configVo) {
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();
        try {
            configService.updateValue(configVo.getList(), sysType);
            SystemConfigStarter.getInstance().init(null);
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
            sysLogService.insertLog(new SysLog(loginAdminUser(request).getId(), "updateConfig", configVo.getList().toString()));
        } catch (Exception e) {
            logger.error("updateconfig error post sysType:{}", sysType, e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);
        }
    }
}
