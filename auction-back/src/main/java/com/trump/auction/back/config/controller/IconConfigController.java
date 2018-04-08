package com.trump.auction.back.config.controller;

import com.cf.common.utils.JSONUtil;
import com.cf.common.utils.Status;
import com.trump.auction.back.config.enums.BannerDisplayStatusEnum;
import com.trump.auction.back.config.enums.BannerHasLoginEnum;
import com.trump.auction.back.config.enums.BannerJumpTypeEnum;
import com.trump.auction.back.constant.SysConstant;
import com.trump.auction.back.sys.controller.BaseController;
import com.trump.auction.back.sys.model.Config;
import com.trump.auction.back.sys.model.SysLog;
import com.trump.auction.back.sys.service.ConfigService;
import com.trump.auction.back.sys.service.SysLogService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  icon按钮管理
 * @author Created by wangjian on 2018/01/09.
 */
@Slf4j
@Controller
@RequestMapping("iconConfig/")
public class IconConfigController extends BaseController {

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private ConfigService configService;

    @Autowired
    private SysLogService sysLogService;

    @RequestMapping("addIconToJsp")
    public String addIconToJsp(HttpServletRequest request, HttpServletResponse response, Model model){
        model.addAttribute("parentId", request.getParameter("myId"));
        model.addAttribute("iconJumpTypeList", BannerJumpTypeEnum.getAllType());
        model.addAttribute("iconDisplayStatusList", BannerDisplayStatusEnum.getAllType());
        model.addAttribute("iconHasLoginList", BannerHasLoginEnum.getAllType());

        //从sys_config表中读取现有的icon信息并解析到前台展示
        Config config = configService.findParamsBySysKey(SysConstant.AUCTION_BACK_INDEX_ICON);
        String str = config == null ? null : config.getSysValueBig();

        if (StringUtils.isNotBlank(str)) {
            Map<String, Object> banner = JSONUtil.parseJSON2Map(str);
            List<HashMap<Object,Object>> banners = JSONUtil.jsonArrToMapList(banner.get("icon").toString());

            for (int i = 0; i < banners.size(); i++) {
                HashMap<Object,Object> temporary = banners.get(i);
                model.addAttribute("icon" + (i + 1) , temporary);

                //如果是跳转原生页面的icon则解析其参数在前台展示
                if (BannerJumpTypeEnum.APP.getCode().equals(Integer.valueOf(temporary.get("jumpType").toString()))) {
                    List<HashMap<Object, Object>> params = JSONUtil.jsonArrToMapList(temporary.get("params").toString());
                    StringBuilder sb = new StringBuilder();

                    for (int j = 0; j < params.size(); j++) {
                        sb.append(params.get(j).get("key")).append(":").append(params.get(j).get("value"));
                        if (j != params.size() - 1) {
                            sb.append(",");
                        }
                    }
                    model.addAttribute("params" + (i + 1), sb.toString());
                }
            }
        }
        return "iconConfig/addIcon";
    }

    /**
     * 添加Icon信息并推送redis
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("addIcon")
    public void addIcon(HttpServletRequest request, HttpServletResponse response, Model model){
        long beginTime=System.currentTimeMillis();
        log.info("addIcon Start,BeginTime:"+beginTime);

        Map<String,Object> params = getParametersO(request); //获取request中的参数
        model.addAttribute("params",params);
        JSONObject json = new JSONObject();
        String code = Status.ERROR.getName();
        String msg = Status.ERROR.getValue();

        try {
            Map<String, Object> bannerMap = new HashMap<>(5);
            List<HashMap<Object, Object>> banners = new ArrayList<>();
            HashMap<Object, Object> banner = null;
            for (int i = 1; i < 6; i++) {
                try {
                    Object name = params.get("name" + i);
                    Object img = params.get("img" + i);
                    Object jump = params.get("jumpType" + i);
                    Object display = params.get("displayType" + i);
                    Object identifyIos = params.get("identifyIos" + i);
                    Object identifyAndroid = params.get("identifyAndroid" + i);
                    Object hasLogin = params.get("hasLogin" + i);
                    Object param = params.get("params" + i);
                    if ((null == name || StringUtils.isBlank(param.toString()))
                            ||(null == param || StringUtils.isBlank(param.toString()))
                            || (null == img || StringUtils.isBlank(img.toString()))
                            || (null == identifyIos || StringUtils.isBlank(identifyIos.toString()))
                            || (null == identifyAndroid || StringUtils.isBlank(identifyAndroid.toString()))) {
                        continue;
                    }
                    banner = new HashMap<>(7);
                    banner.put("name", name);
                    banner.put("img", img);
                    banner.put("jumpType", jump);
                    banner.put("displayType", display);
                    banner.put("identifyIos", identifyIos);
                    banner.put("identifyAndroid", identifyAndroid);
                    banner.put("hasLogin", hasLogin);
                    if (BannerJumpTypeEnum.H5.getCode().equals(Integer.valueOf(jump.toString()))) {
                        banner.put("url", param);
                    } else {
                        try {
                            List<Map<String, Object>> paramList = new ArrayList<>();
                            Map<String, Object> paramMap = null;
                            String[] temporary = param.toString().split(",");
                            for (int j = 0; j < temporary.length; j++) {
                                String[] myd = temporary[j].split(":");
                                paramMap = new HashMap<>(2);
                                paramMap.put("key", myd[0]);
                                paramMap.put("value", myd[1]);
                                paramList.add(paramMap);
                            }
                            banner.put("params", paramList);
                        } catch (Exception e) {
                            log.error("addIcon data assembly error:", e);
                            msg = "Icon:" + i + "参数数据结构错误，请核对后重新操作！";
                            return;
                        }
                    }
                    banners.add(banner);
                } catch (Exception e) {
                    log.error("addIcon data assembly error:", e);
                    msg = "Icon:" + i + "参数数据结构错误，请核对后重新操作！";
                    return;
                }
            }
            bannerMap.put("icon", JSONUtil.beanListToJson(banners));

            //数据处理完成后将数据落入sys_config库并推送redis
            String data = JSONUtil.beanToJson(bannerMap);
            log.info("icon=>{}", data);

            //如果是第一次落库则新增,否则更新
            if (null == configService.findParamsBySysKey(SysConstant.AUCTION_BACK_INDEX_ICON)) {
                Config config = new Config();
                config.setInputType("textdomain");
                config.setSysName("前台icon按钮配置信息");
                config.setSysType("index_icon");
                config.setRemark("前台icon按钮配置信息");
                config.setSysValueBig(data);
                config.setSysKey(SysConstant.AUCTION_BACK_INDEX_ICON);
                configService.saveConfig(config);
                sysLogService.insertLog(new SysLog(loginAdminUser(request).getId(), "updateConfig", config.toString()));
            } else {
                Config config = new Config();
                config.setSysValueBig(data);
                config.setSysKey(SysConstant.AUCTION_BACK_INDEX_ICON);
                configService.updateValueBySysKey(config);
                sysLogService.insertLog(new SysLog(loginAdminUser(request).getId(), "updateConfig", config.toString()));
            }

            jedisCluster.set(SysConstant.AUCTION_BACK_INDEX_ICON, data);
            code = Status.SUCCESS.getName();
            msg = Status.SUCCESS.getValue();
        } catch (Exception e) {
            log.error("addIcon error:", e);
        } finally {
            json.put("code", code);
            json.put("msg", msg);
            renderJson(response, json);

            long endTime=System.currentTimeMillis();
            log.info("addIcon End,Spend time:{}", (endTime-beginTime));
        }
    }
}
