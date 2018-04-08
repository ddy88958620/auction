package com.trump.auction.web.controller;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.common.util.mapping.BeanMapper;
import com.trump.auction.order.api.OrderAppraisesStubService;
import com.trump.auction.order.api.OrderInfoStubService;
import com.trump.auction.order.model.OrderAppraisesModel;
import com.trump.auction.order.model.OrderInfoModel;
import com.trump.auction.order.model.OrderInfoQuery;
import com.trump.auction.web.service.PersonalService;
import com.trump.auction.web.util.HandleResult;
import com.trump.auction.web.util.JsonView;
import com.trump.auction.web.util.RedisContants;
import com.trump.auction.web.util.SpringUtils;
import com.trump.auction.web.vo.OrderAppraisesVo;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisCluster;

/**
 * 个人中心
 * Created by songruihuan on 2017/12/20.
 */
@Slf4j
@Controller
public class PersonalController extends BaseController{
    Logger logger = LoggerFactory.getLogger(PersonalController.class);


    @Autowired
    private PersonalService personalService;

    @Autowired
    private OrderAppraisesStubService orderAppraisesStubService;

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private JedisCluster jedisCluster;

    @Value("${static.resources.domain}")
    private String staticResourcesDomain;

    @Value("${aliyun.oss.domain}")
    private String aliyunOssDomain;
    @Autowired
    private OrderInfoStubService orderInfoStubService;
    /**
     * 我的财产使用说明
     * @return
     */
    @RequestMapping("myProperty-illustration")
    public String myPropertyIllustration(HttpServletRequest request,Model model){
    	int coin = 30;
    	if(isHitReleaseVersion(request)){
    		coin = 100;
		}
    	model.addAttribute("coin",coin);
        model.addAttribute("staticResourcesDomain",staticResourcesDomain);
        return "myProperty-illustration";
    }

    /**
     * 我的开心币使用规则
     * @return
     */
    @RequestMapping("shoppingMoney-illustration")
    public String shoppingMoneyIllustration(HttpServletRequest request,Model model){
    	int coin = 30;
    	if(isHitReleaseVersion(request)){
    		coin = 100;
		}
    	model.addAttribute("coin",coin);
        model.addAttribute("staticResourcesDomain",staticResourcesDomain);
        return "shoppingMoney-illustration";
    }

    /**
     * 晒单评论
     * @return
     */
    @RequestMapping("order-commets")
    public String orderCommets(HttpServletRequest request, HttpServletResponse response, Model model,Integer id){
        OrderAppraisesModel orderAppraisesModel = orderAppraisesStubService.queryOrderAppraises(String.valueOf(id));
        OrderAppraisesVo orderAppraisesVo = beanMapper.map(orderAppraisesModel, OrderAppraisesVo.class);
        String[] arr = null;
        if(StringUtils.hasText(orderAppraisesVo.getAppraisesPic())){
            arr = orderAppraisesVo.getAppraisesPic().split(",");
            for(int i=0;i<=arr.length-1;i++){
                arr[i]=aliyunOssDomain +arr[i];
            }
        }
        orderAppraisesVo.setPicArr(arr);
        OrderInfoQuery orderInfoQuery = new OrderInfoQuery();
        orderInfoQuery.setOrderId(orderAppraisesVo.getOrderId());
        OrderInfoModel orderInfoModel = orderInfoStubService.findOneOrder(orderInfoQuery);
        orderAppraisesVo.setProductPic(aliyunOssDomain+orderInfoModel.getProductPic());
        orderAppraisesVo.setProductName(orderInfoModel.getProductName());
        model.addAttribute("orderAppraisesVo",orderAppraisesVo);
        model.addAttribute("staticResourcesDomain",staticResourcesDomain);
        return "order-commets";
    }

    @RequestMapping("getH5Url")
    public void getH5Url(HttpServletRequest request, HttpServletResponse response){
        HandleResult result = new HandleResult(0,"h5页面url返回成功");
        Map<String, String> keys = jedisCluster.hgetAll("WEBSITE");
        String urls = keys.get("h5_url");
        
        
        JSONObject jsonObject = new  JSONObject();
        
        try {
        	if(StringUtils.hasText(urls)){
            	jsonObject =  JSONObject.parseObject(urls);
                Iterator iter = jsonObject.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    entry.setValue(staticResourcesDomain+entry.getValue());
                }
            }
		} catch (Exception e) {
			log.error("getH5Url error: {}",e);
		}
        
        result.setData(jsonObject);
        SpringUtils.renderJson(response, JsonView.build(result.getCode(),result.getMsg(), result.getData()));
    }

    /**
     * 我的财产
     * accountType 1：拍币；2：赠币；3：积分；4：开心币
     * 获取收支明细列表: userId, accountType, createTime,pageNum, pageSize
     * @param request
     * @param response
     * @param accountType
     * @param pageNum
     * @param pageSize
     */
    @RequestMapping("getMyAccount")
    public void getMyAccount(HttpServletRequest request, HttpServletResponse response, String date,Integer accountType, Integer listType,Integer pageNum,Integer pageSize){
            String userId = getUserIdFromRedis(request);
            HandleResult result  = personalService.getMyAccount(Integer.valueOf(userId),accountType,listType,date,pageNum,pageSize,request);
            SpringUtils.renderJson(response, JsonView.build(result.getCode(),result.getMsg(), result.getData()));
    }

    /**
     * 我的页面
     * @param request
     * @param response
     */
    @RequestMapping("getMyPage")
    public void getMyPage(HttpServletRequest request, HttpServletResponse response){
            String userId = getUserIdFromRedis(request);
            HandleResult result = personalService.getAccountInfo(Integer.valueOf(userId),request);
            SpringUtils.renderJson(response, JsonView.build(result.getCode(),result.getMsg(), result.getData()));
    }
    /**
     * 我的开心币
     * @param request
     * @param response
     */
    @RequestMapping("getShopCoinsList")
    public void getShopCoinsList(HttpServletRequest request, HttpServletResponse response, Integer listType,Integer pageNum,Integer pageSize){
            String userId = getUserIdFromRedis(request);
            HandleResult result  = personalService.getShopCoinsList(Integer.valueOf(userId),listType,pageNum,pageSize,request);
            SpringUtils.renderJson(response, JsonView.build(result.getCode(),result.getMsg(), result.getData()));
    }

    /**
     * 积分列表
     * @param request
     * @param response
     * @param accountType =3
     */
    @RequestMapping("getMyPoints")
    public void getMyPoints(HttpServletRequest request, HttpServletResponse response, Integer accountType){
            String userId = getUserIdFromRedis(request);
            HandleResult result  = personalService.getPointsExchangeList(Integer.valueOf(userId),accountType);
            SpringUtils.renderJson(response, JsonView.build(result.getCode(),result.getMsg(), result.getData()));
    }

    /**
     * 根据主键获取一条账户记录
     * @param request
     * @param response
     * @param id
     */
    @RequestMapping("getUserAccountRecordById")
    public void getUserAccountRecordById(HttpServletRequest request, HttpServletResponse response, Integer id){
            HandleResult result  = personalService.getUserAccountRecordById(id);
            SpringUtils.renderJson(response, JsonView.build(result.getCode(),result.getMsg(), result.getData()));
    }

    /**
     * 积分兑换
     * @param request
     * @param response
     * @param presentCoin
     */
    @RequestMapping("exchangePoints")
    public void exchangePoints(HttpServletRequest request, HttpServletResponse response, Integer presentCoin){
            String userId = getUserIdFromRedis(request);
            HandleResult result  = personalService.exchangePoints(Integer.valueOf(userId),presentCoin);
            SpringUtils.renderJson(response, JsonView.build(result.getCode(),result.getMsg(), result.getData()));
    }

    /**
     * 我的晒单
     * @param request
     * @param response
     */
    @RequestMapping("getAppraises")
    public void getAppraises(HttpServletRequest request, HttpServletResponse response,Integer pageNum,Integer pageSize){
            String userId = getUserIdFromRedis(request);
            HandleResult result  = personalService.getAppraisesByUserId(userId,pageNum,pageSize,request);
            SpringUtils.renderJson(response, JsonView.build(result.getCode(),result.getMsg(), result.getData()));
    }

    /**
     * 晒单详情页
     * @param request
     * @param response
     */
    @RequestMapping("getAppraisesDetail")
    public void getAppraisesDetail(HttpServletRequest request, HttpServletResponse response, Integer id){
            HandleResult result  = personalService.queryOrderAppraises(String.valueOf(id),request);
            SpringUtils.renderJson(response, JsonView.build(result.getCode(),result.getMsg(), result.getData()));
    }

}
