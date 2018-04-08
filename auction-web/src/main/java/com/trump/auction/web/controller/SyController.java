package com.trump.auction.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.common.utils.JSONUtil;
import com.trump.auction.trade.enums.SysConstant;

import com.trump.auction.web.util.RedisContants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cf.common.utils.SpringUtils;
import com.cf.common.utils.Status;

import redis.clients.jedis.JedisCluster;

/**
 * 首页
 *
 * @author gaoyuhai 2016-12-10 上午11:29:15
 */
@Controller
public class SyController extends BaseController {

	private static Logger loger = LoggerFactory.getLogger(SyController.class);
	
	@Autowired
	private JedisCluster jedisCluster;


	/**
	 * 仅IOS调用
	 */
	@RequestMapping(value = "/credit-app/gotoUpdate")
	public void gotoUpdate(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		JSONObject jsonObject = new JSONObject();
		String code = Status.SUCCESS.getName();
		try {
			Integer appVersion = Integer.valueOf(request.getHeader("appVersion").replace(".", ""));
			String clientType = request.getHeader("clientType");
			String appName = StringUtils.isBlank(request.getHeader("appName"))?"kxjp":request.getHeader("appName");
			Map<String, String> systemMap = jedisCluster.hgetAll("SYSTEM");
			String[] array = null;
			String updateContent = "";
			int forceUpdateVersion = 0;
			int recentVersion = 0;
			if ("ios".equals(clientType)) {
				array = systemMap.get("SYSTEM_ITYPE").split("&");
				updateContent = systemMap.get("SYSTEM_ITYPE_DESC");
				forceUpdateVersion = Integer.valueOf(systemMap.get("SYSTEM_ITYPE_FORCE_" + appName).replace(".", ""));
				recentVersion = Integer.valueOf(array[0].replace(".", ""));
			} else if ("android".equals(clientType)) {
				// 这里的逻辑不会被调用
				array = systemMap.get("SYSTEM_ATYPE").split("&");
				updateContent = systemMap.get("SYSTEM_ATYPE_DESC");
				forceUpdateVersion = Integer.valueOf(systemMap.get("SYSTEM_ATYPE_FORCE_" + appName).replace(".", ""));
				recentVersion = Integer.valueOf(array[0].replace(".", ""));
			}
			// 默认是不更新
			String updateType = "3";
			if (appVersion < recentVersion) {
				// 如果当前版本小于最新版本,则必须弹出更新窗口,有取消按钮(即可不更新)
				updateType = "0";
				if (appVersion < forceUpdateVersion) {
					// 如果强制更新则返回1
					updateType = "1";
				}
			}
			updateType = "3";
			jsonObject.put("updateType", updateType);
			jsonObject.put("updateContent", updateContent);
		} catch (Exception e) {
			code = Status.ERROR.getName();
			loger.error("gotoUpdate error ", e);
		}
		jsonObject.put("code", code);
		SpringUtils.renderJson(response, jsonObject);

	}

    @RequestMapping("/healthCheck")
    public void index(HttpServletResponse response) {
        SpringUtils.renderJson(response, "success");
    }


	/**
	 * 获取上架登陆种类 0.全部 1.QQ 2.微信
	 */
	@RequestMapping("/shelfSpecies")
    public void landKind(HttpServletRequest request, HttpServletResponse response, Model model){
		JSONObject jsonObject = new JSONObject();
		String code = Status.SUCCESS.getName();
		jsonObject.put("way","0");
		try {
			// 测试值
			// Integer appVersion = Integer.valueOf("1.1.0".replace(".",""));
			// 获取App版本号
			 Integer appVersion = Integer.valueOf(request.getHeader("appVersion").replace(".", ""));
			// 从Redis获取已经配置好的值
			Map<String, String> systemMap = jedisCluster.hgetAll(RedisContants.APP_SHELF_SPECIES);
			String shelfSpecies = systemMap.get(RedisContants.APP_SHELF_SPECIES);
			JSONArray shelfSpeciesArray = JSONArray.parseArray(shelfSpecies);//shelfSpecies
			for (Object shelfSpeciesObj : shelfSpeciesArray) {
				JSONObject shelfObj = (JSONObject) shelfSpeciesObj;
				// 判断app版本
				if(appVersion == Integer.parseInt(shelfObj.get("version").toString().replace(".",""))){
					// 设置上架种类方式值
					jsonObject.put("way",shelfObj.get("kind").toString());
				}
			}
		}catch (Exception e){
			code = Status.ERROR.getName();
			loger.error("shelfSpecies error ", e);
		}finally {
			jsonObject.put("code",code);
			SpringUtils.renderJson(response, jsonObject);
		}
	}
}
