package com.trump.auction.cust.util;



import com.trump.auction.cust.constant.CustConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.util.Map;

@Component
public class WebsiteUtil {



    @Autowired
    private JedisCluster jedisCluster;


    private static WebsiteUtil instance;

    private WebsiteUtil() {
    }



    public String getAppTitle(String appName) {
        if (StringUtils.isBlank(appName)) {
            Map<String, String> map = jedisCluster.hgetAll(CustConstant.WEBSITE);
            appName = map.get("app_name");
        }
        Map<String, String> map = jedisCluster.hgetAll(appName);
        return map.get("APP_TITLE_" + appName);
    }

    public String getCompanyTitleShort() {

        Map<String, String> map = jedisCluster.hgetAll(CustConstant.WEBSITE);

        String appName = map.get("app_name");
        map = jedisCluster.hgetAll(appName);
        return map.get("COMPANY_TITLE_SHORT_" + appName);
    }


    /**
     * 获取APP的配置参数
     *
     * @param appName
     * @param param
     * @return
     */
    public String getAppConfig(String appName, String param) {
        if (org.apache.commons.lang3.StringUtils.isBlank(appName)) {
            Map<String, String> map = jedisCluster.hgetAll(CustConstant.WEBSITE);
            appName = map.get("app_name");
        }
        Map<String, String> map = jedisCluster.hgetAll(appName);
//		如果appName不为空并且得到的值为空，获取系统默认appName的值
        if ((null == map || map.isEmpty()) && org.apache.commons.lang3.StringUtils.isNotBlank(appName)) {
            return getAppConfig(null, param);
        } else if (null != map) {
            return map.get(param + "_" + appName);
        } else {
            return "";
        }
    }

    /**
     * 获取APP的配置参数 针对营销
     *
     * @param appName
     * @param param
     * @return
     */
    public String getAppConfigMarket(String appName, String param) {
        if (org.apache.commons.lang3.StringUtils.isBlank(appName)) {
            Map<String, String> map = jedisCluster.hgetAll(CustConstant.WEBSITE);
            appName = map.get("app_name");
        }
        Map<String, String> map = jedisCluster.hgetAll(appName);
//		如果appName不为空并且得到的值为空，获取系统默认appName的值
        if ((null == map || map.isEmpty()) && org.apache.commons.lang3.StringUtils.isNotBlank(appName)) {
            return getAppConfigMarket(null, param);
        } else if (null != map) {
            return map.get(param + "_" + appName+"_"+"market");
        } else {
            return "";
        }
    }


}
