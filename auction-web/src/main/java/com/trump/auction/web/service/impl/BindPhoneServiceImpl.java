package com.trump.auction.web.service.impl;

import com.trump.auction.cust.api.UserInfoStubService;
import com.trump.auction.cust.model.UserInfoModel;
import com.trump.auction.web.service.BindPhoneService;
import com.trump.auction.web.util.EnumSmsCodeType;
import com.trump.auction.web.util.HandleResult;
import com.trump.auction.web.util.RegexUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by songruihuan on 2017/12/21.
 */
@Slf4j
@Service
public class BindPhoneServiceImpl implements BindPhoneService {

	@Autowired
	private UserInfoStubService userInfoStubService;

	@Autowired
	private JedisCluster jedisCluster;

	@Override
	@Transactional(rollbackFor=Exception.class)
	public HandleResult  handleBindPhoneProcess(Integer userId, String phone, String smsCode, String type) {
		HandleResult result = new HandleResult(false);
		
		if(!checkPhone(userId,phone,smsCode,type, result)){
			return result;
		}
		int res1 = 0;
		try {
			res1 = userInfoStubService.updateUserPhoneById(phone, userId);
		} catch (Exception e) {
			log.error("handleBindPhoneProcess error: {}",e);
		}
		if(res1 == 1){
			return result.setResult(true).setCode(0);
		}
		
		return result.setCode(1).setMsg("绑定失败");
	}

	private boolean checkPhone(Integer userId,String phone,String smsCode,String type, HandleResult result) {
		if(!StringUtils.hasText(phone)){
			result.setCode(1).setMsg("手机号不能为空");
			return false;
		}
		
		if(!RegexUtils.checkMobile(phone)){
			result.setCode(2).setMsg("请输入正确的手机号");
			return false;
		}
		
		if(!StringUtils.hasText(smsCode)){
			result.setCode(1).setMsg("短信验证码不能为空");
			return false;
		}
		String flag = EnumSmsCodeType.BindPhone.getText();
		/*if(type.equals("1")){
			flag = EnumSmsCodeType.BindPhone.getText();
		}else{
			flag = EnumSmsCodeType.CheckPhone.getText();
		}*/
		//check短信验证码
		String codeKey = "phone_sms_code_"+flag+"_"+phone;
		String code = jedisCluster.get(codeKey);
		if(!smsCode.equals(code)){
			result.setCode(6)
				.setMsg("短信验证码错误");
			return false;
		}
		jedisCluster.del(codeKey);
		try {
		UserInfoModel userInfoModel = userInfoStubService.findUserInfoByUserPhone(phone);
		if(userInfoModel != null){
			result.setCode(5).setMsg("该手机号已绑定过");
			return false;
		}
			UserInfoModel userInfo = userInfoStubService.findUserInfoById(userId);
			if(userInfo == null){
				 result.setCode(3).setMsg("用户不存在");
				 return false;
			}
			if(StringUtils.hasText(type) && "1".equals(type)) {
				String oldPhone = userInfo.getUserPhone();
				if (StringUtils.hasText(oldPhone)) {
					if (oldPhone.equals(phone)) {
						result.setCode(4).setMsg("该手机号已绑定过");
						return false;
					}
				}
			}
		} catch (Exception e) {
			log.error("bindPhone-checkPhone：{}",e);
			result.setCode(5).setMsg("网络故障");
			return false;
		}
		return true;
	}

	@Override
	public HandleResult handleCheckPhone(HttpServletRequest request,HttpServletResponse response,Integer userId,String phone,String smsCode) {
		HandleResult result = new HandleResult(false);
		if(!checkCode(userId,phone,smsCode,EnumSmsCodeType.CheckPhone.getType().toString(),result)){
			return result;
		}
		try {
			UserInfoModel userInfo = userInfoStubService.findUserInfoById(userId);
			if(userInfo.getUserPhone().equals(phone)){
				jedisCluster.set("rebind_phone_"+phone, phone);
				return result.setResult(true);
			}
			return result.setCode(2).setMsg("该手机号不是您的已绑定的手机号");
		} catch (Exception e) {
			 log.error("handleCheckPhone：{}",e);
			 return result.setCode(5).setMsg("网络故障");
		}
	}

	private boolean checkCode(Integer userId,String phone,String smsCode,String type, HandleResult result) {
		if(!StringUtils.hasText(phone)){
			result.setCode(1).setMsg("手机号不能为空");
			return false;
		}

		if(!RegexUtils.checkMobile(phone)){
			result.setCode(2).setMsg("请输入正确的手机号");
			return false;
		}

		if(!StringUtils.hasText(smsCode)){
			result.setCode(1).setMsg("短信验证码不能为空");
			return false;
		}
		String flag = EnumSmsCodeType.BindPhone.getText();
		if(type.equals("1")){
			flag = EnumSmsCodeType.BindPhone.getText();
		}else{
			flag = EnumSmsCodeType.CheckPhone.getText();
		}
		//check短信验证码
		String codeKey = "phone_sms_code_"+flag+"_"+phone;
		String code = jedisCluster.get(codeKey);
		if(!smsCode.equals(code)){
			result.setCode(6)
					.setMsg("短信验证码错误");
			return false;
		}
		jedisCluster.del(codeKey);
		try {
			UserInfoModel userInfo = userInfoStubService.findUserInfoById(userId);
			if(userInfo == null){
				result.setCode(3).setMsg("用户不存在");
				return false;
			}
		} catch (Exception e) {
			log.error("bindPhone-checkPhone：{}",e);
			result.setCode(5).setMsg("网络故障");
			return false;
		}
		return true;
	}
}
