package com.trump.auction.web.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cf.common.util.encrypt.MD5coding;
import com.cf.common.utils.ServiceResult;
import com.trump.auction.account.api.AccountInfoStubService;
import com.trump.auction.account.enums.EnumAccountType;
import com.trump.auction.activity.api.ActivityShareStubService;
import com.trump.auction.activity.model.ActivityShareModel;
import com.trump.auction.cust.api.PromotionChannelStubService;
import com.trump.auction.cust.api.SendSmsStubService;
import com.trump.auction.cust.api.UserInfoStubService;
import com.trump.auction.cust.api.UserSenstiveWordStubService;
import com.trump.auction.cust.api.*;
import com.trump.auction.cust.enums.UserLoginTypeEnum;
import com.trump.auction.cust.enums.UserTypeEnum;
import com.trump.auction.cust.model.PromotionChannelModel;
import com.trump.auction.cust.model.UserInfoModel;
import com.trump.auction.cust.model.UserLoginRecordModel;
import com.trump.auction.cust.model.UserRelationModel;
import com.trump.auction.web.controller.BaseController;
import com.trump.auction.web.service.LoginRegisterService;
import com.trump.auction.web.util.*;
import com.trump.auction.web.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class LoginRegisterServiceImpl extends BaseController implements LoginRegisterService {

	@Autowired
	JedisCluster jedisCluster;

	@Autowired
	private UserInfoStubService userInfoStubService;

	@Autowired
	private SendSmsStubService sendSmsStubService;

	@Autowired
	private PromotionChannelStubService promotionChannelStubService;
	@Autowired
	private AccountInfoStubService accountInfoStubService;
	@Autowired
	UserSenstiveWordStubService userSenstiveWordStubService;
	@Autowired
	private ActivityShareStubService activityShareStubService;
	@Autowired
	private UserRelationStubService userRelationStubService;

	@Value("${aliyun.oss.domain}")
	private String aliyunOssDomain;
	@Autowired
	private UserLoginRecordStubService userLoginRecordStubService;
	@Override
	public HandleResult handleUserRegister(HttpServletRequest request, RegisterParam register) {

		HandleResult handleResult = new HandleResult(false);
		if (!checkRegisterParams(request, register, handleResult)) {
			return handleResult;
		}
		try {

			UserInfoModel user = userInfoStubService.findUserInfoByUserPhone(register.getUserPhone());
			if (user != null) {
				if(user.getStatus().equals("2")){
					return handleResult.setCode(1).setMsg("当前用户已被禁用，可联系客服申诉");
				}else{
					return handleResult.setCode(5).setMsg("该手机号已经注册过");
				}
			}

			UserInfoModel userInfoModel = this.createUserInfo(request, register);
			if (userInfoModel.getId() != null) {
				//jedisCluster.del("phone_sms_code_" + EnumSmsCodeType.Register.getText() + "_" + register.getUserPhone());
				JSONObject json = this.setLoginBackInfo(request, userInfoModel,
						UserLoginTypeEnum.LOGIN_TYPE_PHONE.getType());
				userInfoStubService.updateAddressById(register.getProvince(), register.getCity(), userInfoModel.getId());
				//登陆日志
				loginRecord(userInfoModel,UserLoginTypeEnum.LOGIN_TYPE_PHONE.getType());
				return handleResult.setResult(true).setCode(0).setData(json);
			}

		} catch (Exception e) {
			log.error("register-user-error: {}", e);
		}
		return handleResult.setCode(6).setMsg("注册失败");
	}

	/**
	 * <p>
	 * Title: 创建用户信息
	 * </p>
	 *
	 * @param request
	 * @param register
	 * @return
	 */
	private UserInfoModel createUserInfo(HttpServletRequest request, RegisterParam register) {
		UserInfoModel userInfoModel = new UserInfoModel();
		String channelName = request.getHeader("appMarket");
		PromotionChannelModel promotionChannelModel = promotionChannelStubService.findChannelByName(channelName);
		if(promotionChannelModel != null){
			userInfoModel.setUserFrom(String.valueOf(promotionChannelModel.getId()));
		}else{
			userInfoModel.setUserFrom("1");
		}
		userInfoModel.setAddIp(HttpHelper.getIpAddr(request));
		userInfoModel.setLoginPassword(MD5coding.getInstance().code(register.getPassword()));
		userInfoModel.setStatus("1");
		userInfoModel.setUserPhone(register.getUserPhone());
		userInfoModel.setUserType(UserTypeEnum.TYPE_USER.getType().toString());
		userInfoModel = userInfoStubService.saveUserInfo(userInfoModel);
		return userInfoModel;
	}

	/**
	 * <p>
	 * Title: 活动创建用户信息
	 * </p>
	 *
	 * @param request
	 * @param register
	 * @return
	 */
	private UserInfoModel createUser(HttpServletRequest request, RegisterParam register) {
		UserInfoModel userInfoModel = new UserInfoModel();
		String channelId = request.getParameter("channelId");

		//渠道注册
		if (!StringUtils.isEmpty(channelId)){
			PromotionChannelModel promotionChannelModel = promotionChannelStubService.findByChannelId(channelId);
			if(promotionChannelModel == null){
				log.error("can't find promotionchannel by channelId:{}",channelId);
				return userInfoModel;
			}
			userInfoModel.setUserFrom(String.valueOf(promotionChannelModel.getId()));
		}else {
			//分享注册
			//TODO
			userInfoModel.setUserFrom("2");
		}

		userInfoModel.setAddIp(HttpHelper.getIpAddr(request));
//		userInfoModel.setLoginPassword(MD5coding.getInstance().code(register.getPassword()));
		userInfoModel.setStatus("1");
		userInfoModel.setUserPhone(register.getUserPhone());
		userInfoModel.setUserType(UserTypeEnum.TYPE_USER.getType().toString());
		userInfoModel = userInfoStubService.saveUserInfo(userInfoModel);
		return userInfoModel;
	}

	/**
	 * <p>
	 * Title: check注册的参数
	 * </p>
	 *
	 * @param register
	 * @param handleResult
	 * @return
	 */
	private boolean checkRegisterParams(HttpServletRequest request, RegisterParam register, HandleResult handleResult) {
		if (!StringUtils.hasText(register.getUserPhone())) {
			handleResult.setCode(1).setMsg("手机号不能为空");
			return false;
		}
		if (!RegexUtils.checkMobile(register.getUserPhone())) {
			handleResult.setCode(2).setMsg("请填写正确的手机号");
			return false;
		}

		if (!StringUtils.hasText(register.getSmsCode())) {
			handleResult.setCode(5).setMsg("短信验证码不能为空");
			return false;
		}

		if (!StringUtils.hasText(register.getPassword())) {
			handleResult.setCode(2).setMsg("登录密码不能为空");
			return false;
		}
		if (!RegexUtils.checkPassword(register.getPassword())) {
			handleResult.setCode(2).setMsg("请填写6-16位的数字和字母");
			return false;
		}

		if(register.getProvince().equals("中国")){
			register.setProvince("中国");
			if(!StringUtils.hasText(register.getCity())){
				register.setCity(" ");
			}
		}

		String codeKey = "phone_sms_code_"+EnumSmsCodeType.Register.getText()+ "_" + register.getUserPhone();
		// check短信验证码
		String code = jedisCluster.get(codeKey);
		if (!register.getSmsCode().equals(code)) {
			handleResult.setCode(6).setMsg("短信验证码错误");
			return false;
		}
		jedisCluster.del(codeKey);
		return true;
	}

	@Override
	public HandleResult handleSendSmsCode(HttpServletRequest request, String userPhone,Integer smsCodeType) {
		HandleResult handleResult = new HandleResult(false);

		if (!checkSendSmsParams(request, userPhone,smsCodeType, handleResult)) {
			return handleResult;
		}

		long code = NumberUtil.randomNo(6);
		boolean result = false;
		try {
			result = sendSmsStubService.sendSmsByUserPhone(userPhone, "您的验证码为"+code+"，请于5分钟内使用，如非本人操作请忽略。");
		} catch (Exception e) {
			log.error("send sms error, phone-num: {}",userPhone);
		}
		//TODO
		//result = true;
		//code = 100000;
		if (result) {
			log.info("{}验证码发送成功;手机号：{} 验证码：{}",EnumSmsCodeType.getTypeText(smsCodeType), userPhone, code);
			this.setUpSmsCache(userPhone,smsCodeType, code);
			return handleResult.setResult(true).setCode(0);
		}
		return handleResult.setCode(4).setMsg("发送失败");
	}

	/**
	 * 发送验证码 不包含图形验证码
	 * @param request
	 * @param userPhone
	 * @param smsCodeType 1
	 * @return
	 */
	@Override
	public HandleResult handleSmsCode(HttpServletRequest request, String userPhone, Integer smsCodeType) {
		HandleResult handleResult = new HandleResult(false);

		if (!checkSmsCode(request, userPhone,smsCodeType, handleResult)) {
			return handleResult;
		}

		long code = NumberUtil.randomNo(6);
		boolean result = false;
		try {
			result = sendSmsStubService.sendSmsByUserPhone(userPhone, "您的验证码为"+code+"，请于5分钟内使用，如非本人操作请忽略。");
		} catch (Exception e) {
			log.error("send sms error, phone-num: {}",userPhone);
		}
		//TODO
//		result = true;
//		code = 100000;

		if (result) {
			log.info("{}验证码发送成功;手机号：{} 验证码：{}",EnumSmsCodeType.getTypeText(smsCodeType), userPhone, code);
			this.setUpSmsCache(userPhone,smsCodeType, code);
			return handleResult.setResult(true).setCode(0);
		}
		return handleResult.setCode(4).setMsg("发送失败");
	}

	/**
	 * <p>
	 * Title: 设置[手机号-验证码]缓存
	 * </p>
	 *
	 * @param userPhone
	 * @param code
	 */
	private void setUpSmsCache(String userPhone,Integer smsCodeType, long code) {
		jedisCluster.set("phone_sms_code_"+ EnumSmsCodeType.getTypeText(smsCodeType) + "_" + userPhone, String.valueOf(code));
		jedisCluster.expire("phone_sms_code_" +EnumSmsCodeType.getTypeText(smsCodeType)+"_" + userPhone, 60 * 5);
		jedisCluster.set("prev_"+ EnumSmsCodeType.getTypeText(smsCodeType) + "_" + userPhone, userPhone);
		jedisCluster.expire("prev_" + EnumSmsCodeType.getTypeText(smsCodeType) + "_" + userPhone, 60);
	}

	/**
	 * <p>
	 * Title: check发送验证码参数,发送次数限制等 包含图形验证码校验
	 * </p>
	 *
	 * @param userPhone
	 * @param handleResult
	 * @return
	 */
	private boolean checkSendSmsParams(HttpServletRequest request, String userPhone,Integer smsCodeType, HandleResult handleResult) {
		String validateCode = request.getParameter("validateCode");
		if (!StringUtils.hasText(validateCode)) {
			handleResult.setCode(3).setMsg("图形验证码不能为空");
			return false;
		}
		// check图形验证码
		if (!validateCaptchaKey(request)) {
			handleResult.setCode(4).setMsg("图形验证码错误");
			return false;
		}

		return checkSmsCode(request,userPhone,smsCodeType,handleResult);
	}

	/**
	 * <p>
	 * Title: check发送验证码参数,发送次数限制等 不包含图形验证码校验
	 * </p>
	 *
	 * @param userPhone
	 * @param handleResult
	 * @return
	 */
	private boolean checkSmsCode(HttpServletRequest request, String userPhone,Integer smsCodeType, HandleResult handleResult) {

		if (!StringUtils.hasText(userPhone)) {
			handleResult.setCode(1).setMsg("手机号不能为空");
			return false;
		}
		if (!RegexUtils.checkMobile(userPhone)) {
			handleResult.setCode(2).setMsg("请填写正确的手机号");
			return false;
		}
		String prePhone = jedisCluster.get("prev_"+EnumSmsCodeType.getTypeText(smsCodeType)+"_" + userPhone);
		if (StringUtils.hasText(prePhone)) {
			handleResult.setCode(2).setMsg("一分钟内只能发送一次");
			return false;
		}

		String numberStr = jedisCluster.get("send_msg_num_" + EnumSmsCodeType.getTypeText(smsCodeType)+"_" + userPhone);
		if (numberStr == null) {
			jedisCluster.set("send_msg_num_" + EnumSmsCodeType.getTypeText(smsCodeType)+ "_" + userPhone, "1");
			jedisCluster.expire("send_msg_num_" + EnumSmsCodeType.getTypeText(smsCodeType)+ "_" + userPhone, 60 * 60 * 24);
		}
		if (numberStr != null) {
			Integer num = Integer.valueOf(numberStr);
			if (num > 10) {
				handleResult.setCode(3).setMsg("今日验证码发送已达上限");
				return false;
			}
			jedisCluster.set("send_msg_num_" + EnumSmsCodeType.getTypeText(smsCodeType)+ "_" + userPhone, String.valueOf(Integer.valueOf(numberStr) + 1));
			jedisCluster.expire("send_msg_num_" + EnumSmsCodeType.getTypeText(smsCodeType)+ "_" + userPhone, 60 * 60 * 24);
		}
		return true;
	}

	public boolean validateCaptchaKey(HttpServletRequest request) {
		String deviceId = request.getHeader("deviceId");
		String validateCode = request.getParameter("validateCode");
		if (StringUtils.hasText(deviceId) && StringUtils.hasText(validateCode)) {
			String cacheCode = jedisCluster.get(deviceId);
			if (!StringUtils.hasText(cacheCode)) {
				jedisCluster.del(deviceId);
				return false;
			}
			if (validateCode.toLowerCase().equals(cacheCode.toLowerCase())) {
				jedisCluster.del(deviceId);
				return true;
			}
		}
		return false;
	}

	@Override
	public HandleResult handleUserLogin(HttpServletRequest request, LoginInfo loginInfo) {
		HandleResult handleResult = new HandleResult(false);
		if (!checkLoginParams(loginInfo, handleResult)) {
			return handleResult;
		}
		try {
			UserInfoModel user = userInfoStubService.findUserInfoByUserPhone(loginInfo.getUserPhone());
			if (null != user && null != user.getId()) {
				if(user.getStatus().equals("2")){
					return handleResult.setCode(1).setMsg("当前用户已被禁用，可联系客服申诉");
				}
				if (MD5coding.getInstance().code(loginInfo.getLoginPassword()).equals(user.getLoginPassword())) {
					//设置登录信息
					JSONObject json = setLoginBackInfo(request, user, UserLoginTypeEnum.LOGIN_TYPE_PHONE.getType());
					userInfoStubService.updateAddressById(loginInfo.getProvince(), loginInfo.getCity(), user.getId());
					//记录登陆日志
					loginRecord(user,UserLoginTypeEnum.LOGIN_TYPE_PHONE.getType());
					return handleResult.setResult(true).setCode(0).setData(json);
				}
			}
			return handleResult.setCode(1).setMsg("账户不存在或密码错误");
		} catch (Exception e) {
			log.error("handleUserLogin error: {}",e);
			return handleResult.setCode(1).setMsg("网络故障");
		}
	}

	/**
	 * 将用户登录信息存储到redis，返回userToken给APP
	 * @param user
	 *            用户id
	 * @return userToken
	 */
	private JSONObject setLoginBackInfo(HttpServletRequest request, UserInfoModel user, String loginType) {
		String uuid = UUidUtil.getUUid();
		JSONObject json = new JSONObject();
		json.put("userToken", uuid);

		json.put("userPhone", user.getUserPhone());
		if (UserLoginTypeEnum.LOGIN_TYPE_QQ.getType().equals(loginType)) {
			json.put("userName", Base64Utils.decodeStr(user.getQqNickName()));
			json.put("iconUrl", user.getQqHeadImg());
		}

		if (UserLoginTypeEnum.LOGIN_TYPE_WX.getType().equals(loginType)) {
			json.put("userName", Base64Utils.decodeStr(user.getWxNickName()));
			json.put("iconUrl", user.getWxHeadImg());
		}

		if (UserLoginTypeEnum.LOGIN_TYPE_PHONE.getType().equals(loginType)) {
			json.put("userName", user.getUserPhone().replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
			json.put("iconUrl", HttpHelper.getRequestPath(request) + "/img/default-head-img.png");
		}

		if (UserLoginTypeEnum.LOGIN_TYPE_SMS.getType().equals(loginType)) {
			json.put("userName", user.getUserPhone().replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
			json.put("iconUrl", HttpHelper.getRequestPath(request) + "/img/default-head-img.png");
		}

		if (null!=user.getNickName()&&!StringUtils.isEmpty(user.getNickName())){
			json.put("userName", Base64Utils.decodeStr(user.getNickName()));
		}

		if (null!=user.getHeadImg()&&!StringUtils.isEmpty(user.getHeadImg())){
			json.put("iconUrl", aliyunOssDomain +user.getHeadImg());
		}

		String userId = String.valueOf(user.getId());
		json.put("loginType", loginType);
		userInfoStubService.updateLoginTypeById(loginType,user.getId());

		//获取登录设备相关信息
		updateAppInfo(request,Integer.valueOf(userId));

		jedisCluster.set(RedisContants.LOGIN_CHECK_KEY+userId,uuid);
		jedisCluster.set(RedisContants.LOGIN_KEY + uuid, userId);
		return json;
	}

	private void updateAppInfo(HttpServletRequest request,Integer userId){
		JSONObject appJson = new JSONObject();

		String clientType = getClientType(request);
		String deviceId = getDeviceId(request);
		String deviceName = getDeviceName(request);
		String appVersion = getAppVersion(request);
		String osVersion = getOsVersion(request);
		String appName = getAppName(request);
		String appMarket = getAppMarket(request);

		appJson.put("clientType",clientType);
		appJson.put("deviceId",deviceId);
		appJson.put("deviceName",deviceName);
		appJson.put("appVersion",appVersion);
		appJson.put("osVersion",osVersion);
		appJson.put("appName",appName);
		appJson.put("appMarket",appMarket);

		if (null==appMarket||null==appVersion){
			return;
		}

		try {
			userInfoStubService.updateAppInfo(appJson.toString(),userId);
		} catch (Exception e) {
			log.error("updateAppInfo error:{}",e);
		}
	}
	/**
	 * check登录信息
	 *
	 * @param loginInfo
	 *            loginInfo
	 * @param handleResult
	 *            handleResult
	 * @return 结果
	 */
	private boolean checkLoginParams(LoginInfo loginInfo, HandleResult handleResult) {
		if (!StringUtils.hasText(loginInfo.getUserPhone())) {
			handleResult.setCode(1).setMsg("手机号不能为空");
			return false;
		}
		if (!RegexUtils.checkMobile(loginInfo.getUserPhone())) {
			handleResult.setCode(2).setMsg("请填写正确的手机号");
			return false;
		}
		if (!StringUtils.hasText(loginInfo.getLoginPassword())) {
			handleResult.setCode(3).setMsg("登录密码不能为空");
			return false;
		}
		if (!RegexUtils.checkPassword(loginInfo.getLoginPassword())) {
			handleResult.setCode(1).setMsg("请输入6-16位的包含字母数字的密码");
			return false;
		}
		if(loginInfo.getProvince().equals("中国")){
			loginInfo.setProvince("中国");
			if(!StringUtils.hasText(loginInfo.getCity())){
				loginInfo.setCity(" ");
			};
		}

		return true;
	}

	@Override
	public HandleResult handleUserThirdPartyLogin(HttpServletRequest request, ThirdPartyLoginInfo thirdPartyLoginInfo) {
		HandleResult handleResult = new HandleResult(false);
		if (!checkThirdPartyLoginParams(thirdPartyLoginInfo, handleResult)) {
			return handleResult;
		}
		try {
			UserInfoModel userInfoModel = userInfoStubService.findUserInfoByOpenId(thirdPartyLoginInfo.getOpenId(),
					thirdPartyLoginInfo.getType());
			if (null == userInfoModel) {
				// 用户账户不存在，创建用户账户
				UserInfoModel newUserModel = new UserInfoModel();
				setThirdPartyLoginInfoToModel(thirdPartyLoginInfo, newUserModel);
				newUserModel.setAddIp(HttpHelper.getIpAddr(request));
				newUserModel.setStatus("1");
				newUserModel.setUserType(UserTypeEnum.TYPE_USER.getType().toString());
				String channelName = request.getHeader("appMarket");
				PromotionChannelModel promotionChannelModel = promotionChannelStubService.findChannelByName(channelName);
				if(promotionChannelModel != null){
					newUserModel.setUserFrom(String.valueOf(promotionChannelModel.getId()));
				}else{
					newUserModel.setUserFrom("1");
				}

				UserInfoModel resultUser = userInfoStubService.saveUserInfo(newUserModel);
				if (null != resultUser && null != resultUser.getId()) {
					JSONObject json = setLoginBackInfo(request, resultUser, thirdPartyLoginInfo.getType());
					userInfoStubService.updateAddressById(thirdPartyLoginInfo.getProvince(), thirdPartyLoginInfo.getCity(), resultUser.getId());
					//记录登陆日志
					loginRecord(resultUser,thirdPartyLoginInfo.getType());
					return handleResult.setResult(true).setCode(0).setData(json);
				}
			} else {

				if(userInfoModel.getStatus().equals("2")){
					return handleResult.setCode(1).setMsg("当前用户已被禁用，可联系客服申诉");
				}

				// 用户账户已经创建过，更新昵称，头像
				UserInfoModel newUserModel = new UserInfoModel();
				newUserModel.setId(userInfoModel.getId());
				setThirdPartyLoginInfoToModel(thirdPartyLoginInfo, newUserModel);
				ServiceResult serviceResult = userInfoStubService.updateThirdUserInfo(newUserModel);
				if (serviceResult.isSuccessed()) {
					JSONObject json = setLoginBackInfo(request, userInfoModel, thirdPartyLoginInfo.getType());
					userInfoStubService.updateAddressById(thirdPartyLoginInfo.getProvince(), thirdPartyLoginInfo.getCity(), userInfoModel.getId());
					//记录登陆日志
					loginRecord(userInfoModel,thirdPartyLoginInfo.getType());
					return handleResult.setResult(true).setCode(0).setData(json);
				}
			}
		} catch (Exception e) {
			log.error("handleUserThirdPartyLogin error: {}",e);
		}
		return handleResult.setCode(1).setMsg("登录失败");
	}

	/**
	 * 根据第三方登录类型set数据到model的对应属性
	 *
	 * @param thirdPartyLoginInfo
	 *            第三方登录信息
	 * @param newUserModel
	 *            用户model
	 */
	private void setThirdPartyLoginInfoToModel(ThirdPartyLoginInfo thirdPartyLoginInfo, UserInfoModel newUserModel) {
		// 昵称里会有表情的情况，encode后再保存到库，取出时，decode
		String nickName = Base64Utils.encodeStr(thirdPartyLoginInfo.getName());
		if (UserLoginTypeEnum.LOGIN_TYPE_WX.getType().equals(thirdPartyLoginInfo.getType())) {
			newUserModel.setWxOpenId(thirdPartyLoginInfo.getOpenId());
			newUserModel.setWxNickName(nickName);
			newUserModel.setWxHeadImg(thirdPartyLoginInfo.getIconUrl());
		} else if (UserLoginTypeEnum.LOGIN_TYPE_QQ.getType().equals(thirdPartyLoginInfo.getType())) {
			newUserModel.setQqOpenId(thirdPartyLoginInfo.getOpenId());
			newUserModel.setQqNickName(nickName);
			newUserModel.setQqHeadImg(thirdPartyLoginInfo.getIconUrl());
		}
	}

	/**
	 * check第三方登录信息
	 *
	 * @param thirdPartyLoginInfo
	 *            thirdPartyLoginInfo
	 * @param handleResult
	 *            handleResult
	 * @return 结果
	 */
	private boolean checkThirdPartyLoginParams(ThirdPartyLoginInfo thirdPartyLoginInfo, HandleResult handleResult) {
		List<String> typelist = Arrays.asList(UserLoginTypeEnum.LOGIN_TYPE_QQ.getType(), UserLoginTypeEnum.LOGIN_TYPE_WX.getType());
		if(!typelist.contains(thirdPartyLoginInfo.getType())){
			handleResult.setCode(1).setMsg("请选择正确的登陆方式");
			return false;
		}
		if (!StringUtils.hasText(thirdPartyLoginInfo.getOpenId())) {
			handleResult.setCode(1).setMsg("openId不能为空");
			return false;
		}
		if (!StringUtils.hasText(thirdPartyLoginInfo.getName())) {
			handleResult.setCode(2).setMsg("昵称不能为空");
			return false;
		}
		if (!StringUtils.hasText(thirdPartyLoginInfo.getIconUrl())) {
			thirdPartyLoginInfo.setIconUrl("");
		}
		if (!StringUtils.hasText(thirdPartyLoginInfo.getType())) {
			handleResult.setCode(4).setMsg("登录类型不能为空");
			return false;
		}
		if(thirdPartyLoginInfo.getProvince().equals("中国")){
			thirdPartyLoginInfo.setProvince("中国");
			if(!StringUtils.hasText(thirdPartyLoginInfo.getCity())){
				thirdPartyLoginInfo.setCity(" ");
			}
		}
		return true;
	}

	@Override
	public HandleResult handleForgetPwd(HttpServletRequest request, ForgetPwdParam params) {
		HandleResult result = new HandleResult(false);

		if (!checkForgetPwdParams(request, result, params)) {
			return result;
		}
		try {
			UserInfoModel userInfoModel = userInfoStubService.findUserInfoByUserPhone(params.getUserPhone());
			if (userInfoModel == null) {
				return result.setCode(6).setMsg("手机号不存在");
			}
			jedisCluster.set(RedisContants.ForgetPwd + params.getUserPhone(), String.valueOf(userInfoModel.getId()));
			jedisCluster.expire(RedisContants.ForgetPwd + params.getUserPhone(), 60 * 5);
			return result.setResult(true).setCode(0);
		} catch (Exception e) {
			log.error("handleForgetPwd error: {}",e);
		}
		return result;

	}

	private boolean checkForgetPwdParams(HttpServletRequest request, HandleResult result, ForgetPwdParam params) {
		if (params == null) {
			result.setCode(1).setMsg("参数不能为空");
			return false;
		}

		if (!StringUtils.hasText(params.getUserPhone())) {
			result.setCode(2).setMsg("手机号不能为空");
			return false;
		}

		if (!RegexUtils.checkMobile(params.getUserPhone())) {
			result.setCode(3).setMsg("手机号格式不正确");
			return false;
		}
		if (!StringUtils.hasText(params.getSmsCode())) {
			result.setCode(5).setMsg("短信验证码不能为空");
			return false;
		}

		// check短信验证码
		String codeKey = "phone_sms_code_" + EnumSmsCodeType.ForgetPwd.getText() +"_" + params.getUserPhone();
		String code = jedisCluster.get(codeKey);
		if (!params.getSmsCode().equals(code)) {
			result.setCode(7).setMsg("短信验证码错误");
			return false;
		}
		jedisCluster.del(codeKey);
		return true;
	}

	@Override
	public HandleResult handleResetPwd(HttpServletRequest request, ResetPwdParam params) {
		HandleResult result = new HandleResult(false);

		if (params == null) {
			return result.setCode(1).setMsg("参数不能为空");
		}

		if (!StringUtils.hasText(params.getUserPhone())) {
			return result.setCode(1).setMsg("手机号不能为空");
		}

		String userId = jedisCluster.get(RedisContants.ForgetPwd + params.getUserPhone());
		if (userId == null) {
			return result.setCode(1).setMsg("请重新确认忘记密码操作");
		}

		if (!StringUtils.hasText(params.getFirstPwd())) {
			return result.setCode(1).setMsg("密码不能为空");
		}
		if (!RegexUtils.checkPassword(params.getFirstPwd())) {
			return result.setCode(1).setMsg("请输入6-16位的包含字母数字的密码");
		}
		if (!StringUtils.hasText(params.getSecondPwd())) {
			return result.setCode(1).setMsg("密码不能为空");
		}
		if (!RegexUtils.checkPassword(params.getSecondPwd())) {
			return result.setCode(1).setMsg("请输入6-16位的包含字母数字的密码");
		}
		if (!params.getFirstPwd().equals(params.getSecondPwd())) {
			return result.setCode(1).setMsg("两次密码不一致");
		}

		try {
			UserInfoModel userInfoModel = userInfoStubService.findUserInfoByUserPhone(params.getUserPhone());
			if (userInfoModel == null) {
				return result.setCode(6).setMsg("手机号不存在");
			}
			ServiceResult serviceResult = userInfoStubService
					.updateLoginPasswordById(MD5coding.getInstance().code(params.getFirstPwd()), userInfoModel.getId());
			if (serviceResult != null && ServiceResult.SUCCESS.equals(serviceResult.getCode())) {
				jedisCluster.del(RedisContants.ForgetPwd + params.getUserPhone());
				return result.setResult(true);
			}
		} catch (Exception e) {
			log.error("handleResetPwd error: {}",e);
		}
		return result.setCode(1).setMsg("重置密码失败");
	}

	/**
	 * 渠道推广用户注册
	 * @param request
	 * @param register
	 * @return
	 */
	@Override
	public HandleResult shareUserRegister(HttpServletRequest request, RegisterParam register) {
		HandleResult handleResult = new HandleResult(false);

		//参数验证
		if (!checkRegisterParams(request, register, handleResult)) {
			return handleResult;
		}
		try {
			UserInfoModel user = userInfoStubService.findUserInfoByUserPhone(register.getUserPhone());
			if (null!=user) {
				return handleResult.setCode(7).setMsg("该手机号已经注册过");
			}

			//创建用户
			UserInfoModel userInfoModel = createUser(request, register);
			if (null==userInfoModel||null==userInfoModel.getId()){
				return handleResult.setCode(1).setMsg("注册用户失败");
			}

			JSONObject json = this.setLoginBackInfo(request, userInfoModel,
					UserLoginTypeEnum.LOGIN_TYPE_PHONE.getType());
			userInfoStubService.updateAddressById("中国", null, userInfoModel.getId());

//            int coin = getRandomCoin(5,20);
//            //初始化创建账户
//            accountInfoStubService.initUserAccount(userInfoModel.getId(),userInfoModel.getUserPhone(),userInfoModel.getUserPhone(), EnumAccountType.PRESENT_COIN.getKey().intValue());
//            //返赠币
//            ServiceResult result = accountInfoStubService.backShareCoin(userInfoModel.getId(),coin,EnumAccountType.PRESENT_COIN.getKey().intValue());
//
//            if (!"200".equals(result.getCode())){
//				return handleResult.setCode(1).setMsg("返赠币有误，请联系管理人员");
//            }
//			json.put("coin",coin);
			return handleResult.setResult(true).setCode(0).setData(json);

		} catch (Exception e) {
			log.error("register-user-error: {}", e);
		}
		return handleResult.setCode(6).setMsg("注册失败");
	}

	/**
	 * 用户头像/昵称修改
	 * @param user
	 * @return
	 */
	@Override
	public HandleResult updateUserInfo(UserInfoVo user) {
		HandleResult handleResult = new HandleResult(false);

		try {
			//敏感词校验
			ServiceResult senseResult = userSenstiveWordStubService.checkNickName(user.getNickName());

			if (!StringUtils.isEmpty(user.getNickName())&&!StringUtils.isEmpty(senseResult.getExt())){
				return handleResult.setCode(1).setMsg("昵称涉及敏感词，请重新输入");
			}

			UserInfoModel userInfo = new UserInfoModel();
			userInfo.setId(user.getId());
			userInfo.setHeadImg(user.getHeadImg());
			if (null!=user&&null!=user.getNickName()){
				userInfo.setNickName(Base64Utils.encodeStr(user.getNickName()));
			}

			ServiceResult result = userInfoStubService.updateUserInfo(userInfo);

			if (ServiceResult.SUCCESS.equals(result.getCode())){
				return handleResult.setResult(true).setCode(0);
			}
		} catch (Exception e) {
			log.error("updateUserInfo error: {}",e);
			return handleResult.setCode(1).setMsg("操作失败");
		}
		return handleResult.setCode(1).setMsg("操作失败");
	}

	private int getRandomCoin(int min,int max){
		Random random = new Random();
		return random.nextInt(max)%(max-min+1) + min;
	}

	/**
	 * 短信登陆
	 * @param smsLoginVo  登陆信息
	 * @return
	 */
	@Override
	public HandleResult handleSmsCodeLogin(HttpServletRequest request, SmsLoginVo smsLoginVo) {
		HandleResult handleResult = new HandleResult(false);
		//参数验证
		if (!checkSmsCodeLogin(request, smsLoginVo, handleResult)) {
			return handleResult;
		}

		try {
			UserInfoModel user = userInfoStubService.findUserInfoByUserPhone(smsLoginVo.getUserPhone());
			if (null != user && null != user.getId()) {
				if(user.getStatus().equals("2")){
					return handleResult.setCode(1).setMsg("当前用户已被禁用，可联系客服申诉");
				}
				JSONObject json = setLoginBackInfo(request, user, UserLoginTypeEnum.LOGIN_TYPE_PHONE.getType());
				userInfoStubService.updateAddressById(smsLoginVo.getProvince(), smsLoginVo.getCity(), user.getId());
				//记录登陆日志
				loginRecord(user,UserLoginTypeEnum.LOGIN_TYPE_SMS.getType());
				return handleResult.setResult(true).setCode(0).setData(json);
			}
			return handleResult.setCode(1).setMsg("手机号不存在或验证码错误");
		} catch (Exception e) {
			log.error("handleUserLogin error: {}",e);
			return handleResult.setCode(1).setMsg("网络故障");
		}
	}

	/**
	 * 短信登陆校验
	 * @param params  登陆信息
	 * @return
	 */
	public boolean checkSmsCodeLogin(HttpServletRequest request, SmsLoginVo params,HandleResult result) {

		if (params == null) {
			result.setCode(1).setMsg("参数不能为空");
			return  false;
		}
		if (!StringUtils.hasText(params.getUserPhone())) {
			result.setCode(2).setMsg("手机号不能为空");
			return  false;
		}
		if (!RegexUtils.checkMobile(params.getUserPhone())) {
			result.setCode(3).setMsg("手机号格式不正确");
			return false;
		}
		if (!StringUtils.hasText(params.getSmsCode())) {
			result.setCode(5).setMsg("短信验证码不能为空");
			return  false;
		}
		if("中国".equals(params.getProvince())){
			params.setProvince("中国");
			if(!StringUtils.hasText(params.getCity())){
				params.setCity(" ");
			}
		}
		String codeKey = "phone_sms_code_"+EnumSmsCodeType.SmsLogin.getText()+ "_" + params.getUserPhone();
		String code=jedisCluster.get(codeKey);
		if (!params.getSmsCode().equals(code)) {
			result.setCode(7).setMsg("短信验证码错误");
			return false;
		}
		jedisCluster.del(codeKey);
		return true;
	}

	/**
	 * 分享用户注册
	 * @param request
	 * @param register
	 * @param sid 分享者id
	 * @return
	 */
	@Override
	public HandleResult doRegister(HttpServletRequest request, RegisterParam register,String sid) {
		HandleResult handleResult = new HandleResult(false);

		//验证手机号
		if (!checkPhone(register, handleResult)) {
			return handleResult;
		}
		try {
			UserInfoModel user = userInfoStubService.findUserInfoByUserPhone(register.getUserPhone());
			if (null!=user) {
				return handleResult.setCode(7).setMsg("该手机号已经注册过");
			}

			//创建用户
			UserInfoModel userInfoModel = createUser(request, register);
			if (null==userInfoModel||null==userInfoModel.getId()){
				return handleResult.setCode(1).setMsg("注册用户失败");
			}

			//保存用户关系
			UserRelationModel userRelationModel = new UserRelationModel();
			userRelationModel.setPid(Integer.valueOf(sid));
			userRelationModel.setSid(userInfoModel.getId());
			userRelationModel.setCreateTime(new Date());
			userRelationStubService.saveRelation(userRelationModel);

			JSONObject json = this.setLoginBackInfo(request, userInfoModel,
					UserLoginTypeEnum.LOGIN_TYPE_PHONE.getType());
			userInfoStubService.updateAddressById(register.getProvince(), register.getCity(), userInfoModel.getId());
			String activityId = request.getParameter("activityId");
			buidAccount(userInfoModel,activityId,json,sid);
			return handleResult.setResult(true).setCode(0).setData(json);

		} catch (Exception e) {
			log.error("register-user-error: {}", e);
		}
		return handleResult.setCode(6).setMsg("注册失败");
	}

	/**
	 * 创建账户信息
	 * @param activityId
	 */
	private void buidAccount(UserInfoModel userInfoModel,String activityId,JSONObject json,String sid){
		if (StringUtils.isEmpty(activityId)){
			return;
		}

//		ActivityShareModel activity = activityShareStubService.getActivityInfo(activityId);
//		if (null==activity){
//			return;
//		}

//		Integer sharerId = Integer.valueOf(sid);

//		UserInfoModel sharer = userInfoStubService.findUserInfoById(sharerId);
		int coin = getRandomCoin(1,20);
		//初始化创建账户
		accountInfoStubService.initUserAccount(userInfoModel.getId(),userInfoModel.getUserPhone(),userInfoModel.getUserPhone(), EnumAccountType.PRESENT_COIN.getKey().intValue());
		//返赠币
		ServiceResult result = accountInfoStubService.backShareCoin(userInfoModel.getId(),coin,EnumAccountType.PRESENT_COIN.getKey().intValue());

//		//送积分
//		if (null!=activity.getSharerPoints()){
//			//初始化创建账户
//			accountInfoStubService.initUserAccount(sharerId,sharer.getUserPhone(),sharer.getUserPhone(), EnumAccountType.POINTS.getKey().intValue());
//			accountInfoStubService.backShareCoin(sharerId,Integer.valueOf(activity.getSharerPoints()),EnumAccountType.POINTS.getKey().intValue());
//		}
//		//送赠币
//		if (null!=activity.getSharerCoin()){
//			accountInfoStubService.initUserAccount(sharerId,sharer.getUserPhone(),sharer.getUserPhone(), EnumAccountType.PRESENT_COIN.getKey().intValue());
//			accountInfoStubService.backShareCoin(sharerId,Integer.valueOf(activity.getSharerCoin()),EnumAccountType.PRESENT_COIN.getKey().intValue());
//		}
//
//		//送积分
//		if (null!=activity.getRegisterPoints()){
//			//初始化创建账户
//			accountInfoStubService.initUserAccount(userInfoModel.getId(),userInfoModel.getUserPhone(),userInfoModel.getUserPhone(), EnumAccountType.POINTS.getKey().intValue());
//			accountInfoStubService.backShareCoin(userInfoModel.getId(),Integer.valueOf(activity.getRegisterPoints()),EnumAccountType.POINTS.getKey().intValue());
//		}
//		//送赠币
//		if (null!=activity.getRegisterCoin()){
//			String [] arr = activity.getRegisterCoin().split("-");
//			Arrays.sort(arr);
//			int coin = getRandomCoin(Integer.valueOf(arr[0]),Integer.valueOf(arr[1]));
//			accountInfoStubService.initUserAccount(userInfoModel.getId(),userInfoModel.getUserPhone(),userInfoModel.getUserPhone(), EnumAccountType.PRESENT_COIN.getKey().intValue());
//			accountInfoStubService.backShareCoin(userInfoModel.getId(),coin,EnumAccountType.PRESENT_COIN.getKey().intValue());
			json.put("coin",coin);
//		}

	}

	private boolean checkPhone(RegisterParam register, HandleResult handleResult) {
		if (!StringUtils.hasText(register.getUserPhone())) {
			handleResult.setCode(1).setMsg("手机号不能为空");
			return false;
		}
		if (!RegexUtils.checkMobile(register.getUserPhone())) {
			handleResult.setCode(2).setMsg("请填写正确的手机号");
			return false;
		}
		return true;
	}

	//登陆日志
	public  boolean  loginRecord(UserInfoModel user ,String loginType){
		boolean result =false;
		try{
			user = userInfoStubService.findUserInfoById(user.getId());
			UserLoginRecordModel loginModel=new UserLoginRecordModel();
			loginModel.setUserId( (long)user.getId());
			loginModel.setLoginTime(new Date());
			loginModel.setLoginIp(user.getAddIp());
			String city=user.getCityName();
			if (org.apache.commons.lang3.StringUtils.isEmpty(city)){
				city="";
			}
			loginModel.setAddress(user.getProvinceName()+city);
			if (org.apache.commons.lang3.StringUtils.isNotEmpty(user.getAppInfo())){
				com.alibaba.fastjson.JSONObject appInfObj = JSONArray.parseObject(user.getAppInfo());
				loginModel.setLoginDevices(appInfObj.get("deviceName").toString());
			}
			loginModel.setAddTime(user.getAddTime());
			loginModel.setLoginType(loginType);
			userLoginRecordStubService.loginRecord(loginModel);
			result =true;
		}catch (Exception e){
			log.error("loginRecord error",e);
		}
		return  result;
	}
}
