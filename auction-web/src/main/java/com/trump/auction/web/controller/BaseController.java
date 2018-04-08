package com.trump.auction.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.trump.auction.web.util.RedisContants;

import redis.clients.jedis.JedisCluster;

/**
 * <p>
 * Title: 基础控制类
 * </p>
 * 
 * @author youlianlai
 * @date 2017年11月7日下午6:52:10
 */
public class BaseController {

	@Autowired
	private JedisCluster jedisCluster;
	
	public String getUserToken(HttpServletRequest request) {
		return request.getHeader("UserToken");
	}
	
	public String getUserIdFromRedis(HttpServletRequest request) {
		String userToken =this.getUserToken(request);
		return jedisCluster.get(RedisContants.LOGIN_KEY+userToken);
	}
	
	public boolean validateCaptchaKey(HttpServletRequest request) {
		String deviceId = request.getHeader("deviceId");
		String validateCode = request.getParameter("validateCode");
		if(StringUtils.hasText(deviceId) && StringUtils.hasText(validateCode)){
			String cacheCode = jedisCluster.get(deviceId);
			if(!StringUtils.hasText(cacheCode)){
				return false;
			}
			if(validateCode.equals(cacheCode)){
				return true;
			}
		}
		return false;
	}

	public String getClientType(HttpServletRequest request) {
		return request.getHeader("clientType");
	}
	
	public String getAppVersion(HttpServletRequest request) {
		return request.getHeader("appVersion");
	}
	
	public String getDeviceId(HttpServletRequest request) {
		return request.getHeader("deviceId");
	}
	
	public String getDeviceName(HttpServletRequest request) {
		return request.getHeader("deviceName");
	}
	
	public String getOsVersion(HttpServletRequest request) {
		return request.getHeader("osVersion");
	}
	
	public String getAppName(HttpServletRequest request) {
		return request.getHeader("appName");
	}
	
	public String getAppMarket(HttpServletRequest request) {
		return request.getHeader("appMarket");
	}
	
	 /**
     * 是否命中审核中的版本
     * @param appInfo
     * @return
     */
	public boolean isHitReleaseVersion(HttpServletRequest request){
        Integer appVersion = Integer.valueOf(getAppVersion(request).replace(".", ""));
        String appMarket = getAppMarket(request);
        try {
        	Map<String, String> systemMap = jedisCluster.hgetAll(RedisContants.APP_COIN_RULE);
            if (null==systemMap||systemMap.isEmpty()|| !StringUtils.hasText(systemMap.get(RedisContants.APP_COIN_RULE))){
            	return false;
            }
            String appCoinRule = systemMap.get(RedisContants.APP_COIN_RULE);
            JSONArray appCoinRuleArray = JSONArray.parseArray(appCoinRule);
           
            for (Object appCoinRuleObj : appCoinRuleArray) {
                JSONObject shelfObj = (JSONObject) appCoinRuleObj;
                int ruleVersion = Integer.parseInt(shelfObj.get("appVersion").toString().replace(".",""));
                if((appVersion == ruleVersion)&&(appMarket.equals(shelfObj.get("appMarket").toString()))){
                	return true;
                }
            }
		} catch (Exception e) {
		}
        return false;
    }
	
}
