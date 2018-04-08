package com.trump.auction.web.controller;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cf.common.util.http.WebClient;
import com.cf.common.utils.JSONUtil;
import com.trump.auction.web.util.HttpHelper;
import com.trump.auction.web.util.ThreadPoolMd;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import redis.clients.jedis.JedisCluster;

/**
 * Created by Administrator on 2017/12/18.
 */
@Controller
@Slf4j
public class MdController extends BaseController {


    @Autowired
    JedisCluster jedisCluster;


    private static Logger logger = LoggerFactory.getLogger(MdController.class);


    @RequestMapping(method = RequestMethod.POST, value = "appMdts")
    public void appMdts(HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        try {
	

            final String ipAddr = HttpHelper.getIpAddr(request);
//            final String appVersion = request.getParameter("appVersion") != null ? request.getParameter("appVersion") : request.getParameter("appVerrsion");
//            final String appVersion = request.getParameter("app_version");
            final String appName = request.getHeader("app_name");
//            final String product = (clientType != null && clientType.equals("android") ? "aAPP" : "iAPP") + "|" + appVersion;
//            final String deviceId = request.getParameter("deviceId");
            final String appType = request.getHeader("app_type");
//            final String deviceName = request.getParameter("deviceName");
            final String userIds = getUserIdFromRedis(request);
//            final String userAgent = deviceId + "|" + deviceName;
            final String userAgent = request.getHeader("userAgent");
            String data = request.getParameter("data");
            log.info("mdApps 埋点data data:{}",data);
            Map<String, Object> datas = new HashMap<String, Object>();
            JSONArray dataArray = JSONArray.fromObject(data);
            if (dataArray == null) {
                return;
            }
            Map[] dataMap = new Map[dataArray.size()];
            for (int i = 0; i < dataArray.size(); i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                Object o = dataArray.get(i);
                JSONObject datajson = (JSONObject) dataArray.get(i);
                Object clickElement = datajson.get("clickElement");
                Object outTime = datajson.get("outTime");
                Object clickTime = datajson.get("clickTime");
                Object inTime = datajson.get("inTime");
                Object location = datajson.get("location");
                map.put("ip", ipAddr);
                map.put("product", appType);
                map.put("APPName", appName);
                map.put("location", location);
                map.put("userId", userIds);
                map.put("clickTime", clickTime);
                map.put("clickElement", clickElement);
                map.put("inTime", inTime);
                map.put("outTime", outTime);
                map.put("userAgent", userAgent);
                dataMap[i] = map;
                log.info("mdApps 埋点点击元素 clickElement:{}",clickElement);
                log.info("mdApps location location:{}",location);
            }
            datas.put("data", dataMap);
            final String paramsJosn = JSONUtil.beanToJson(datas).replaceAll("\"", "\'");
            ThreadPoolMd.getInstance().run(new Runnable() {
                public void run() {
                    HashMap<String, Object> params = new HashMap<String, Object>();
                    params.put("soketOut", 1000);
                    params.put("connOut", 1000);
                    String result = WebClient.getInstance().postJsonData(jedisCluster.get("MD_URL"), paramsJosn, params);
//                    String result = WebClient.getInstance().postJsonData("http://106.14.241.104:8000/index.zwj2", paramsJosn, params);
//                    logger.info("md result"+result);
//                    System.out.print(result);
                }
            });

        }catch (Exception e){
            json.put("code", -1);
            json.put("message", "Success");
            json.put("data","{}");
            JSONUtil.toObjectJson(response, json.toString());
            logger.error("methord:==mdts error"+e);
        }finally {
            json.put("code", 0);
            json.put("message", "Success");
            json.put("data","{}");
            JSONUtil.toObjectJson(response, json.toString());
        }

    }




    /**
     * H5页面，列表数据埋点
     *
     * @param request
     *            request
     * @param response
     *            response
     */
    @RequestMapping(method = RequestMethod.POST, value = "h5Mdts")
    public void loanMarketMdts(HttpServletRequest request, HttpServletResponse response) {

        JSONObject json = new JSONObject();
        try {
            final String userId = getUserIdFromRedis(request);
            final String ipAddr = HttpHelper.getIpAddr(request);
            final String clickElement = request.getParameter("clickElement");
            final String appName = "qezww";

            final String inTime = request.getParameter("inTime");
            final String outTime = request.getParameter("outTime");
            final String location = request.getParameter("location");

            final String deviceId = request.getParameter("deviceId");
            final String deviceName = request.getParameter("deviceName");
            final String clientType = request.getParameter("appType");
            final String appVersion = request.getParameter("appVersion");
            final String userAgent = deviceId + "|" + deviceName;
            final String appType = clientType+ "|" +appVersion;
                    ThreadPoolMd.getInstance().run(new Runnable() {
                    public void run() {
                        Map<String, Object> datas = new HashMap<String, Object>();
                        Map<String, Object> map = new HashMap<String, Object>();
                        String clickTime = System.currentTimeMillis() + "";
                        map.put("ip", ipAddr);
                        map.put("product", appType);
                        map.put("APPName", appName);
//                        if(clickElement.equals("e_my_helpCenter_item")){
//                            map.put("location", string2Unicode(location));
//                        }else{
//                            map.put("location", location);
//                        }
                        map.put("location", location);
                        map.put("userId", userId);
                        map.put("clickTime", clickTime.substring(0, 10));
                        map.put("clickElement", clickElement);
                        map.put("inTime", inTime);
                        map.put("outTime", outTime);
                        map.put("userAgent", userAgent);
                        Map[] dataMap = new Map[1];
                        dataMap[0] = map;
                        datas.put("data", dataMap);
                        String paramsJosn = JSONUtil.beanToJson(datas);
                        paramsJosn = paramsJosn.replaceAll("\"", "\'");
                        HashMap<String, Object> params = new HashMap<String, Object>();
                        params.put("soketOut", 1000);
                        params.put("connOut", 1000);
                                            String result = WebClient.getInstance().postJsonData(jedisCluster.get("MD_URL"), paramsJosn, params);
//                        String result = WebClient.getInstance().postJsonData("http://106.14.241.104:8000/index.zwj2", paramsJosn, params);
                    }
                });

        }catch (Exception e){
            json.put("code", -1);
            json.put("message", "Success");
            json.put("data","{}");
            JSONUtil.toObjectJson(response, json.toString());
            logger.error("methord:==h5mdts error"+e);
        }finally {
            json.put("code", 0);
            json.put("message", "Success");
            json.put("data","{}");
            JSONUtil.toObjectJson(response, json.toString());
        }
    }

}
