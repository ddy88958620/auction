package com.trump.auction.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.trump.auction.web.service.BindPhoneService;
import com.trump.auction.web.util.HandleResult;
import com.trump.auction.web.util.JsonView;
import com.trump.auction.web.util.SpringUtils;

/**
 * 绑定手机号
 * Created by songruihuan on 2017/12/21.
 */
@RequestMapping("bind/")
@Controller
public class BindPhoneController extends BaseController {
	
	@Autowired
	private BindPhoneService bindPhoneService;
	/**
	 * <p>
	 * Title: 
	 * </p>
	 * <p>
	 * Description: 绑定手机号
	 * </p>
	 * @param request
	 * @param response
	 * @param phone
	 */
	@RequestMapping("phone")
	public void bindPhone(HttpServletRequest request,HttpServletResponse response,String phone,String smsCode,String type) {
		String userId = getUserIdFromRedis(request);
		if(type == null){
			type = request.getParameter("type ");
		}
		HandleResult result = bindPhoneService.handleBindPhoneProcess(Integer.valueOf(userId), phone,smsCode,type);
		SpringUtils.renderJson(response,JsonView.build(result.getCode(), result.getMsg()));
	}

	/**
	 * 手机号码校验
	 */
	@RequestMapping("checkPhone")
	public void checkPhone(HttpServletRequest request,HttpServletResponse response,String phone,String smsCode) {
		String userId = getUserIdFromRedis(request);
		HandleResult result = bindPhoneService.handleCheckPhone(request,response,Integer.valueOf(userId), phone,smsCode);
		SpringUtils.renderJson(response,JsonView.build(result.getCode(), result.getMsg()));
	}
}
