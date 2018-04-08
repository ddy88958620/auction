package com.trump.auction.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.cf.common.util.mapping.BeanMapper;
import com.trump.auction.cust.api.AccountRechargeRuleStubService;
import com.trump.auction.cust.api.UserInfoStubService;
import com.trump.auction.cust.model.AccountRechargeRuleDetailModel;
import com.trump.auction.cust.model.AccountRechargeRuleModel;
import com.trump.auction.cust.model.UserInfoModel;
import com.trump.auction.web.service.RechargeService;
import com.trump.auction.web.util.HandleResult;
import com.trump.auction.web.util.JsonView;
import com.trump.auction.web.util.SpringUtils;
import com.trump.auction.web.vo.AccountRechargeRuleVo;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisCluster;

/**
 * 充值
 * Created by songruihuan on 2017/12/20.
 */
@Slf4j
@Controller
@RequestMapping("recharge/")
public class RechargeController extends BaseController{
	
	@Autowired
	private RechargeService rechargeService;
	
	@Autowired
	private AccountRechargeRuleStubService accountRechargeRuleStubService;
	
	@Autowired
	private UserInfoStubService userInfoStubService;
	
	@Autowired
	private BeanMapper beanMapper;
	
	@Autowired
	private JedisCluster jedisCluster;
	
	
	@RequestMapping("rule")
	public void gradient(HttpServletRequest request,HttpServletResponse response) {
		List<AccountRechargeRuleDetailModel>  list = new ArrayList<AccountRechargeRuleDetailModel>();
		List<AccountRechargeRuleVo> vos = new ArrayList<>();
		JSONObject json  = new JSONObject();
		try {
			AccountRechargeRuleModel ruleModel = accountRechargeRuleStubService.findEnableRule();
			
			if(ruleModel != null){
				Integer ruleUser = ruleModel.getRuleUser();
				if(ruleUser.equals(2)){
					String userId = getUserIdFromRedis(request);
					if(userId != null){
						UserInfoModel userInfoModel = userInfoStubService.findUserInfoById(Integer.valueOf(userId));
						Integer rechargeType = userInfoModel.getRechargeType();
						if(rechargeType.equals(1) && ruleModel != null){
							list = ruleModel.getDetails();
							vos = beanMapper.mapAsList(list, AccountRechargeRuleVo.class);
						}
					}else{
						list = ruleModel.getDetails();
						vos = beanMapper.mapAsList(list, AccountRechargeRuleVo.class);
					}
				}else{
					list = ruleModel.getDetails();
					vos = beanMapper.mapAsList(list, AccountRechargeRuleVo.class);
				}
				
			}
			
			
			json.put("list", vos);
			String recharge_title = jedisCluster.get("recharge_title");
			recharge_title = recharge_title==null?"1.单笔充值达到指定金额即可获得对应额度的赠币奖励":recharge_title;
			String recharge_min_money = jedisCluster.get("recharge_min_money");
			recharge_min_money = recharge_min_money==null?"100":recharge_min_money;
			json.put("ruleDesc", recharge_title);
			Integer minMoney = Integer.valueOf(recharge_min_money)/100;
			json.put("minMoney", minMoney);
		} catch (Exception e) {
			log.error("recharge/rule error : {}",e);
		}
		SpringUtils.renderJson(response, JsonView.build(0, "success",json));
	}
	
	/**
	 * <p>
	 * Title: 预支付
	 * </p>
	 * <p>
	 * Description: 生成订单，请求第三方支付 生成repay_id
	 * </p>
	 * @param request
	 * @param response
	 * @param payType:支付方式：1微信，2支付宝
	 * @return
	 */
	@RequestMapping("prePay")
	public void  prePay(HttpServletRequest request,HttpServletResponse response,Integer money,String payType) {
		String userId = getUserIdFromRedis(request);
		HandleResult result = rechargeService.prePay(request,Integer.valueOf(userId), money, payType);
		SpringUtils.renderJson(response, JsonView.build(result.getCode(), result.getMsg(),result.getData()));
	}
	
	
}
