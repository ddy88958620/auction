package com.trump.auction.web.controller;

import com.trump.auction.cust.api.UserInfoStubService;
import com.trump.auction.cust.api.UserSenstiveWordStubService;
import com.trump.auction.cust.model.UserInfoModel;
import com.trump.auction.web.service.LoginRegisterService;
import com.trump.auction.web.util.*;
import com.trump.auction.web.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * Title: 登陆注册控制类
 * </p>
 * 
 * @author yll
 * @date 2017年12月20日下午5:48:08
 */
@Controller
public class LoginRegisterController extends BaseController {

	@Autowired
	JedisCluster jedisCluster;
	
	@Autowired
	LoginRegisterService loginRegisterService;

	@Autowired
	UserInfoStubService userInfoStubService;

	@Autowired
	UserSenstiveWordStubService userSenstiveWordStubService;

	@Value("${static.resources.domain}")
	private String staticResourcesDomain;
	
	/**
	 * <p>
	 * Title: 手机号注册
	 * </p>
	 * @param request
	 * @param response
	 * @param register
	 */
	@RequestMapping("register")
	public void register(HttpServletRequest request,HttpServletResponse response,RegisterParam register) {

		HandleResult result = loginRegisterService.handleUserRegister(request,register);
		SpringUtils.renderJson(response, JsonView.build(result.getCode(), 
				result.getMsg(), result.getData())); 
	}
	
	/**
	 * <p>
	 * Title: 发送验证码
	 * </p>
	 * @param response
	 * @param userPhone
	 */
	@RequestMapping("sendSmsCode")
	public void sendSmsCode(HttpServletRequest request,HttpServletResponse response,String userPhone,Integer smsCodeType) {
		
		HandleResult result = loginRegisterService.handleSendSmsCode(request,userPhone,smsCodeType);
		SpringUtils.renderJson(response, JsonView.build(result.getCode(), 
				result.getMsg(), result.getData())); 
		
	}

	@RequestMapping("login")
    public void login(HttpServletRequest request, HttpServletResponse response, LoginInfo loginInfo) {
        HandleResult result = loginRegisterService.handleUserLogin(request,loginInfo);
        SpringUtils.renderJson(response, JsonView.build(result.getCode(),
                result.getMsg(), result.getData()));
    }

    @RequestMapping("thirdPartyLogin")
    public void thirdPartyLogin(HttpServletRequest request, HttpServletResponse response, ThirdPartyLoginInfo thirdPartyLoginInfo) {
        HandleResult result = loginRegisterService.handleUserThirdPartyLogin(request,thirdPartyLoginInfo);
        SpringUtils.renderJson(response, JsonView.build(result.getCode(),
                result.getMsg(), result.getData()));
    }

	/**
	 * <p>
	 * Title: 忘记密码
	 * </p>
	 * <p>
	 * Description: 
	 * </p>
	 * @param response
	 * @param phone
	 * @param validateCode
	 * @param smsCode
	 */
    @RequestMapping("forgetPwd")
    public void forgetPwd(HttpServletRequest request,HttpServletResponse response,ForgetPwdParam forgetPwdParam){
    	HandleResult result = loginRegisterService.handleForgetPwd(request,forgetPwdParam);
    	 SpringUtils.renderJson(response, JsonView.build(result.getCode(),
                 result.getMsg()));
	}
    
    /**
     * 重置密码
     */
    @RequestMapping("resetPwd")
    public void resetPwd(HttpServletRequest request,HttpServletResponse response,ResetPwdParam resetPwdParam ) {
    	HandleResult result = loginRegisterService.handleResetPwd(request,resetPwdParam);
   	 SpringUtils.renderJson(response, JsonView.build(result.getCode(),
                result.getMsg()));
	}
    
	@RequestMapping("quit")
	public void quit(HttpServletRequest request,HttpServletResponse response) {
		String userToken = super.getUserToken(request);
		if(StringUtils.hasText(userToken)){
			jedisCluster.del(RedisContants.LOGIN_KEY+userToken);
		}
		SpringUtils.renderJson(response, JsonView.build(0, "退出成功",new Object()));
	}

	/**
	 * 分享活动注册
	 * @param
	 * @return
	 */
	@RequestMapping("shareRegister")
	public String aboutUsUrl(Model model,String channelId){
		model.addAttribute("staticResourcesDomain",staticResourcesDomain);
		model.addAttribute("channelId",channelId);
//		return "share-register";
		return "activity-register";
	}

	/**
	 * <p>
	 * Title: 分享发送验证码
	 * </p>
	 * @param response
	 */
	@RequestMapping("sendShareCode")
	public void sendShareCode(HttpServletRequest request,HttpServletResponse response) {

		String userPhone = request.getParameter("userPhone");
		HandleResult result = loginRegisterService.handleSmsCode(request,userPhone,EnumSmsCodeType.Register.getType());
		SpringUtils.renderJson(response, JsonView.build(result.getCode(),
				result.getMsg(), result.getData()));

	}

	/**
	 * <p>
	 * Title: 验证手机号是否注册
	 * </p>
	 * @param response
	 */
	@RequestMapping("checkSmsCode")
	public void checkSmsCode(HttpServletRequest request,HttpServletResponse response) {
		HandleResult handleResult = new HandleResult(false);
		String userPhone = request.getParameter("userPhone");
		UserInfoModel user = userInfoStubService.findUserInfoByUserPhone(userPhone);
		if (null!=user) {
			handleResult.setCode(7).setMsg("该手机号已经注册过");
		}else{
			handleResult.setResult(true).setCode(0);
		}
		SpringUtils.renderJson(response, JsonView.build(handleResult.getCode(),
				handleResult.getMsg(), handleResult.getData()));

	}

	/**
	 * 推广用户注册
	 * @param request
	 * @param response
	 * @param register
	 */
	@RequestMapping("doRegister")
	public void doRegister(HttpServletRequest request,HttpServletResponse response,RegisterParam register) {
		HandleResult result = loginRegisterService.shareUserRegister(request,register);
		SpringUtils.renderJson(response, JsonView.build(result.getCode(),
				result.getMsg(), result.getData()));
	}

	/**
	 * 用户头像/昵称修改
	 * @param request
	 * @param response
	 * @param user
	 */
	@RequestMapping("updateUserInfo")
	public void updateUserInfo(HttpServletRequest request,HttpServletResponse response,UserInfoVo user) {

		String userId = super.getUserIdFromRedis(request);
		if (StringUtils.isEmpty(userId)){
			SpringUtils.renderJson(response, JsonView.build(1,
					"未能获取用户信息", "未能获取用户信息"));
			return;
		}

		if (StringUtils.isEmpty(user.getHeadImg())&&StringUtils.isEmpty(user.getNickName())){
			SpringUtils.renderJson(response, JsonView.build(1,
					"传入参数有误", "传入参数有误"));
			return;
		}

		user.setId(Integer.valueOf(userId));
		HandleResult result = loginRegisterService.updateUserInfo(user);
		SpringUtils.renderJson(response, JsonView.build(result.getCode(),
				result.getMsg(), result.getData()));
	}

	/**
	 * <p>
	 * Title: 短信登陆
	 * </p>
	 * @param response
	 */
	@RequestMapping("smsCodeLogin")
	public void smsCodeLogin(HttpServletRequest request, HttpServletResponse response, SmsLoginVo smsLoginVo) {
		HandleResult result = loginRegisterService.handleSmsCodeLogin(request,smsLoginVo);
		SpringUtils.renderJson(response, JsonView.build(result.getCode(), result.getMsg(), result.getData()));
	}
}
