package com.trump.auction.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.trump.auction.web.service.IndexService;
import com.trump.auction.web.util.HandleResult;
import com.trump.auction.web.util.JsonView;
import com.trump.auction.web.util.SpringUtils;

import redis.clients.jedis.JedisCluster;

/**
 * 主页index
 * Created by songruihuan on 2017/12/20.
 */
@Controller
public class IndexController extends BaseController {
    Logger logger = LoggerFactory.getLogger(IndexController.class);
    
    @Autowired
    JedisCluster jedisCluster;

    @Autowired
    private IndexService indexService;

    /**
     * <p>
     * Title: 
     * </p>
     * <p>
     * Description: 
     * </p>
     * @param request
     * @param response
     * @param pageNum
     * @param pageSize
     * @param type
     */
    @RequestMapping("index/common")
    public void indexCommon(HttpServletRequest request, HttpServletResponse response){
        HandleResult result = indexService.getIndexCommonData();
        SpringUtils.renderJson(response, JsonView.build(result.getCode(),result.getMsg(),
        		result.getData()));
    }
    
    /**
     * <p>
     * Title: 
     * </p>
     * <p>
     * Description: 
     * </p>
     * @param request
     * @param response
     * @param pageNum
     * @param pageSize
     * @param type
     */
    @RequestMapping("index/label")
    public void indexPage(HttpServletRequest request, HttpServletResponse response,Integer pageNum,Integer pageSize,Integer type){
    	String userId = super.getUserIdFromRedis(request);
        HandleResult result = indexService.getIndexData(userId,pageNum,pageSize,type);
        SpringUtils.renderJson(response, JsonView.build(result.getCode(),result.getMsg(),
        		result.getData()));
    }

}
