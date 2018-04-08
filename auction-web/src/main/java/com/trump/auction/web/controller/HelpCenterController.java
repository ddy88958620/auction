package com.trump.auction.web.controller;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.trump.auction.web.util.RedisContants;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisCluster;

/**
 * 帮助中心
 * Created by songruihuan on 2017/12/20.
 */
@Slf4j
@Controller
public class HelpCenterController extends BaseController {

    @Autowired
    private JedisCluster jedisCluster;

    @Value("${static.resources.domain}")
    private String staticResourcesDomain;

    /**
     * 帮助中心
     * @param model
     * @return
     */
    @RequestMapping("helpCenter")
    public String helpCenter(Model model){

        Map<String, String> keys = jedisCluster.hgetAll("WEBSITE");
        String helpType = keys.get("help_type");
        String str = jedisCluster.get(RedisContants.MESSAGES_CENTER);
        JSONArray titleArrayFinal = new JSONArray();
        try {
        	JSONArray titleArrayTemp = JSONArray.parseArray(helpType);//helpType
            JSONArray contentsArray = JSONArray.parseArray(str);//helpMsg
           
            for (Object titileObj : titleArrayTemp) {
                JSONObject  title = (JSONObject) titileObj;
                JSONArray contentArray = new JSONArray();
                Iterator it = contentsArray.iterator();
                while (it.hasNext()) {
                    JSONObject content =  (JSONObject) it.next();
                    if (content.getString("channelType").equals(title.getString("help_type"))) {
                        contentArray.add(content);
                    }
                }
                if (contentArray.size() > 0) {
                    title.put("contentList", contentArray);
                    titleArrayFinal.add(title);
                }
            }
		} catch (Exception e) {
			log.error("helpCenter error: {}",e);
		}
        

        model.addAttribute("helpTypeList", titleArrayFinal);
        model.addAttribute("staticResourcesDomain",staticResourcesDomain);

        return "helpCenter";
    }

    /**
     * 拍卖协议
     * @return
     */
    @RequestMapping("auctionAgreementUrl")
    public String auctionAgreementUrl(HttpServletRequest request,Model model){
    	int coin = 30;
    	if(isHitReleaseVersion(request)){
    		coin = 100;
		}
    	model.addAttribute("coin",coin);
        model.addAttribute("staticResourcesDomain",staticResourcesDomain);
        return "user_agreement";
    }

    
     
    /**
     * 关于我们
     * @param
     * @return
     */
    @RequestMapping("aboutUsUrl")
    public String aboutUsUrl(Model model){
       model.addAttribute("staticResourcesDomain",staticResourcesDomain);
        return "about-us";
    }
}
