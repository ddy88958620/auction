package com.trump.auction.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.trump.auction.activity.constant.ActivityConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.trump.auction.account.enums.EnumAccountType;
import com.trump.auction.cust.api.UserProductCollectStubService;
import com.trump.auction.trade.api.AuctionInfoStubService;
import com.trump.auction.trade.model.AuctionInfoModel;
import com.trump.auction.web.service.PersonalService;
import com.trump.auction.web.service.SignService;
import com.trump.auction.web.util.HandleResult;
import com.trump.auction.web.util.RedisContants;

import redis.clients.jedis.JedisCluster;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 签到
 * Created by songruihuan on 2017/12/20.
 */
@Controller
public class SignController extends  BaseController{
    Logger logger = LoggerFactory.getLogger(SignController.class);

    @Autowired
    private SignService signService;
    @Autowired
    private PersonalService personalService;
    @Autowired
    private JedisCluster jedisCluster;

    @Value("${aliyun.oss.domain}")
    private String aliyunOssDomain;

    @Value("${static.resources.domain}")
    private String staticResourcesDomain;
    @Autowired
    private UserProductCollectStubService userProductCollectStubService;
    
	@Autowired
	private AuctionInfoStubService auctionInfoStubService;
	
    /**
     * 签到规则
     * @return
     */
    @RequestMapping("sign-rule")
    public String signRule(Model model){
        model.addAttribute("staticResourcesDomain",staticResourcesDomain);
        return "sign-rule";
    }

    /**
     * 签到
     * @return
     */
    @RequestMapping("sign-in")
    public String signIn(HttpServletRequest request, HttpServletResponse response, Model model){

            String userId = getUserIdFromRedis(request);
            HandleResult result = signService.checkIsSigned(Integer.valueOf(userId));
            if(result.isResult()){//已签到
                model.addAttribute("signFlag",true);
            }else{//未签到
                model.addAttribute("signFlag",false);
                signService.userSign(Integer.valueOf(userId));//去签到
            }
            Map<String, String> signConfigMap = jedisCluster.hgetAll(ActivityConstant.USER_SIGN);
            int basePoints = Integer.parseInt(signConfigMap.get(ActivityConstant.BASE_POINTS));
            int extraPoints = Integer.parseInt(signConfigMap.get(ActivityConstant.EXTRA_POINTS));
            int signCycle = Integer.parseInt(signConfigMap.get(ActivityConstant.SIGN_CYCLE));

            Integer seriesSignDays = signService.findUserSignByUserId(Integer.valueOf(userId)).getSeriesSignDays();//连续签到天数

            if(seriesSignDays.intValue()==signCycle){
                model.addAttribute("todayPoints",basePoints+extraPoints);//今天签到积分数
            }else{
                model.addAttribute("todayPoints",basePoints);//今天签到积分数
            }
            //拍卖推荐
            String str = jedisCluster.get(RedisContants.RECOMMEND_AUCTION_PRODS);
            JSONArray array = null;
            if(null!=str){
                array =JSONArray.parseArray(str);
            }else{
                array = new JSONArray();
            }
            for(int i=0;i<array.size();i++){
                JSONObject jsonObject = (JSONObject) array.get(i);
                String productId = (String) jsonObject.get("auctionProdId");
                String auctionId = (String) jsonObject.get("auctionId");
                
                /*AuctionInfoModel auctionInfoModel = auctionInfoStubService.getAuctionInfoById(Integer.valueOf(auctionId));
                
				Integer status = auctionInfoModel.getStatus();
    			if(status.equals(1)){
    				jsonObject.put("bidPrice", auctionInfoModel.getIncreasePrice().multiply(
    						new BigDecimal(auctionInfoModel.getTotalBidCount())).add(auctionInfoModel.getStartPrice()));
    			}else{
    				jsonObject.put("bidPrice", auctionInfoModel.getFinalPrice());
    			}*/
                boolean flag = userProductCollectStubService.checkUserProductCollect(Integer.valueOf(userId),Integer.valueOf(productId),Integer.valueOf(auctionId));//商品id  期数id
                jsonObject.put("flag",flag);
            }


            model.addAttribute("signCycle",signCycle);//签到周期
            model.addAttribute("extraPoints",extraPoints);//签到额外分数
            model.addAttribute("basePoints",basePoints);//签到基础分数
            model.addAttribute("seriesSignDays",seriesSignDays);//连续签到天数
            model.addAttribute("points",personalService.getAuctionCoinByUserId(Integer.valueOf(userId), EnumAccountType.POINTS.getKey()));//当前积分数
            model.addAttribute("recommend",array);
            model.addAttribute("token",request.getHeader("userToken"));//request.getHeader("userToken"));//87381e8d4ec54f98a5926f1e48172007
            model.addAttribute("aliyunOssDomain",aliyunOssDomain);
            model.addAttribute("staticResourcesDomain",staticResourcesDomain);
            return "sign-in";
    }

}
