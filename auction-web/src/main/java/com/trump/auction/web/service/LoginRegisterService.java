package com.trump.auction.web.service;

import com.trump.auction.web.util.HandleResult;
import com.trump.auction.web.vo.*;

import javax.servlet.http.HttpServletRequest;

public interface LoginRegisterService {

	/**
	 * <p>
	 * Title: 处理用户注册
	 * </p>
	 * @param request
	 * @param register
	 * @return
	 */
	HandleResult handleUserRegister(HttpServletRequest request,RegisterParam register);

	/**
	 * <p>
	 * Title: 处理发送短信验证码
	 * </p>
	 * @param userPhone
	 * @return
	 */
	HandleResult handleSendSmsCode(HttpServletRequest request,String userPhone,Integer smsCodeType);

	/**
	 * <p>
	 * Title: 处理发送短信验证码不包含图形验证码
	 * </p>
	 * @param userPhone
	 * @param smsCodeType
	 * @return
	 */
	HandleResult handleSmsCode(HttpServletRequest request, String userPhone, Integer smsCodeType);

	/**
	 * 处理用户登录
	 * @param loginInfo 登录信息
	 * @return 登录结果
	 */
	HandleResult handleUserLogin(HttpServletRequest request,LoginInfo loginInfo);

	/**
	 * 处理用户第三方登录
	 * @param thirdPartyLoginInfo 第三方登录信息
	 * @return 登录结果
	 */
	HandleResult handleUserThirdPartyLogin(HttpServletRequest request,ThirdPartyLoginInfo thirdPartyLoginInfo);

	/**
	 * <p>
	 * Title: 处理忘记密码逻辑
	 * </p>
	 * @param forgetPwdParam 忘记密码参数
	 * @return
	 */
	HandleResult handleForgetPwd(HttpServletRequest request,ForgetPwdParam forgetPwdParam);

	/**
	 * <p>
	 * Title: 处理重置密码逻辑
	 * </p>
	 * @param request
	 * @param resetPwdParam
	 * @return
	 */
	HandleResult handleResetPwd(HttpServletRequest request, ResetPwdParam resetPwdParam);

	/**
	 * 推广用户注册
	 * @param request
	 * @param register
	 * @return
	 */
	HandleResult shareUserRegister(HttpServletRequest request, RegisterParam register);

	/**
	 * 用户头像/昵称修改
	 * @param user
	 * @return
	 */
	HandleResult updateUserInfo(UserInfoVo user);

	/**
	 * 用户短信登录
	 * @param smsLoginVo 短信登录信息
	 * @return 登录结果
	 */
	HandleResult handleSmsCodeLogin(HttpServletRequest request,SmsLoginVo smsLoginVo);

	/**
	 * 分享注册
	 * @param request
	 * @param register
	 * @return
	 */
	HandleResult doRegister(HttpServletRequest request, RegisterParam register,String sid);
}
