package com.trump.auction.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.trump.auction.account.api.AccountInfoStubService;
import com.trump.auction.account.enums.EnumAccountType;
import com.trump.auction.activity.api.ActivityShareStubService;
import com.trump.auction.activity.model.ActivityShareModel;
import com.trump.auction.cust.api.UserInfoStubService;
import com.trump.auction.cust.model.UserInfoModel;
import com.trump.auction.web.service.LoginRegisterService;
import com.trump.auction.web.util.EnumSmsCodeType;
import com.trump.auction.web.util.HandleResult;
import com.trump.auction.web.util.JsonView;
import com.trump.auction.web.util.SpringUtils;
import com.trump.auction.web.vo.MD5coding;
import com.trump.auction.web.vo.RegisterParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @description: 分享朋友圈
 * @author: zhangqingqiang
 * @date: 2018-03-29 10:48
 **/
@Controller
@RequestMapping("share")
public class ShareController extends BaseController{
    @Value("${static.resources.domain}")
    private String staticResourcesDomain;
    @Value("${aliyun.oss.domain}")
    private String aliyunOssDomain;
    @Autowired
    LoginRegisterService loginRegisterService;
    @Autowired
    private ActivityShareStubService activityShareStubService;
    @Autowired
    private JedisCluster jedisCluster;
    @Autowired
    private AccountInfoStubService accountInfoStubService;
    @Autowired
    private UserInfoStubService userInfoStubService;

    public static final String AUCTION_SHARE_TIMES = "auction.share.times.";
    /**
     * 分享 step1
     * @param activityId 活动id非主键
     * @param sid 分享者id
     * @return
     */
    @RequestMapping("shareFirst")
    public String aboutUsUrl(Model model, String activityId,String sid){
        model.addAttribute("staticResourcesDomain",staticResourcesDomain);
        model.addAttribute("activityId",activityId);
        model.addAttribute("sid",sid);
        return "share-first";
    }

    /**
     * 分享用户短信注册
     * @param request
     * @param response
     * @param register
     * @param sid 分享者id
     */
    @RequestMapping("doRegister")
    public void doRegister(HttpServletRequest request, HttpServletResponse response, RegisterParam register,String sid) {
        HandleResult result = loginRegisterService.doRegister(request,register,sid);
        SpringUtils.renderJson(response, JsonView.build(result.getCode(),
                result.getMsg(), result.getData()));
    }

    /**
     * 获取分享活动
     * @param request
     * @param response
     * @param activityId
     */
    @RequestMapping("getActivity")
    public void getActivity(HttpServletRequest request, HttpServletResponse response, String activityId) {
        String userId = super.getUserIdFromRedis(request);
        if (StringUtils.isEmpty(userId)){
            SpringUtils.renderJson(response, JsonView.build(1,
                    "未能获取用户信息", "未能获取用户信息"));
            return;
        }

        HandleResult handleResult = new HandleResult(false);
        ActivityShareModel activity = activityShareStubService.getActivityInfo(activityId);
        if (null==activity) {
            handleResult.setCode(1).setMsg("未获取活动信息");
        }else{
            if (null!=activity.getPicUrl()){
                activity.setPicUrl(aliyunOssDomain+activity.getPicUrl());
            }
            activity.setActivityUrl(activity.getActivityUrl()+"?sid="+userId);
            JSONObject json = new JSONObject();
            json.put("activity",activity);
            handleResult.setResult(true).setCode(0).setData(json);
        }
        SpringUtils.renderJson(response, JsonView.build(handleResult.getCode(),
                handleResult.getMsg(), handleResult.getData()));
    }

    /**
     * 分享 h5
     * @param activityId 活动id非主键
     * @param sid 分享者id
     * @return
     */
    @RequestMapping("toShare")
    public String toShare(Model model, String activityId,String sid){
        model.addAttribute("staticResourcesDomain",staticResourcesDomain);
        model.addAttribute("activityId",activityId);
        model.addAttribute("sid",sid);
        return "share-h5";
    }

    /**
     * 分享成功返2赠币10积分
     * @param request
     * @param response
     */
    @RequestMapping("shareReward")
    public void shareReward(HttpServletRequest request, HttpServletResponse response) {
        HandleResult handleResult = new HandleResult(false);
        String userId = super.getUserIdFromRedis(request);
        if (StringUtils.isEmpty(userId)){
            SpringUtils.renderJson(response, JsonView.build(1,
                    "未能获取用户信息", "未能获取用户信息"));
            return;
        }
        String key = AUCTION_SHARE_TIMES+ userId;
        long ttl = jedisCluster.ttl(key);
        if (ttl > 0) {
            SpringUtils.renderJson(response, JsonView.build(1,
                    "分享奖励每天仅限1次", "分享奖励每天仅限1次"));
            return;
        }

        UserInfoModel userInfoModel = userInfoStubService.findUserInfoById(Integer.valueOf(userId));
        accountInfoStubService.initUserAccount(userInfoModel.getId(),userInfoModel.getUserPhone(),userInfoModel.getUserPhone(), EnumAccountType.PRESENT_COIN.getKey().intValue());
        accountInfoStubService.backShareCoin(userInfoModel.getId(),2,EnumAccountType.PRESENT_COIN.getKey().intValue());
        accountInfoStubService.initUserAccount(userInfoModel.getId(),userInfoModel.getUserPhone(),userInfoModel.getUserPhone(), EnumAccountType.POINTS.getKey().intValue());
        accountInfoStubService.backShareCoin(userInfoModel.getId(),10,EnumAccountType.POINTS.getKey().intValue());

        jedisCluster.set(AUCTION_SHARE_TIMES+ userId, String.valueOf(userId));
        setExpirTime(AUCTION_SHARE_TIMES+ userId);
//        jedisCluster.expire(AUCTION_SHARE_TIMES+ userId, 60 * 60*24);
        handleResult.setResult(true).setCode(0);

        SpringUtils.renderJson(response, JsonView.build(handleResult.getCode(),
                handleResult.getMsg(), handleResult.getData()));
    }

    private void setExpirTime(String key){
        Calendar curDate = Calendar.getInstance();
        Calendar nextDayDate = new GregorianCalendar(curDate.get(Calendar.YEAR), curDate.get(Calendar.MONTH), curDate.get(Calendar.DATE)+1, 0, 0, 0);
        Long sec = (nextDayDate.getTimeInMillis() - (new Date()).getTime())/1000;
        jedisCluster.expire(key, sec.intValue());
    }
}
