package com.trump.auction.back.sys.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cf.common.util.encrypt.AESUtil;
import com.cf.common.util.encrypt.MD5coding;
import com.trump.auction.back.constant.SysConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trump.auction.back.sys.model.SysUser;
import com.trump.auction.back.sys.service.SysUserService;
import com.trump.auction.back.util.sys.AppPropertyConfigurer;

/**
 * 
 * 类描述：后台登录类 <br>
 * 创建人：fanyinchuan<br>
 * 创建时间：2016-6-28 下午02:57:46 <br>
 * 
 * @version
 * 
 */
@Controller
public class LoginController extends BaseController implements InitializingBean {
	Logger logger = LoggerFactory.getLogger(getClass());
	public final static String SMS_REGISTER_PREFIX = "newphonecode_";// Redis注册key前缀
	private final static String DEFAULT_SMS_CODE = "0000";
	public final static int BACK_USER_TIME = 60 * 60 * 6;
	@Autowired
	private SysUserService sysUserService;
	private boolean disableMobileCode;

	@Override
	public void afterPropertiesSet() throws Exception {
		String value = AppPropertyConfigurer.getPropertyAsString("config.login.mobile-code.disable");
		if (org.springframework.util.StringUtils.hasText(value)) {
			this.disableMobileCode = Boolean.valueOf(value);
		} else {
			this.disableMobileCode = false;
		}
	}

	@RequestMapping(value = { "/login", "/index", "/" }, method = RequestMethod.GET)
	public String toLogin(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			String message = request.getParameter(MESSAGE);
			Map<String, String> cookieMap = ReadCookieMap(request);
			String backSessionId = cookieMap.get("backSessionId");
			String userId = jedisCluster.get(SysConstant.SYS_USER + backSessionId);
			SysUser backUser = null;
			model.addAttribute("website", jedisCluster.hgetAll(SysConstant.WEBSITE));
			if (StringUtils.isNotBlank(userId)) {
				backUser = sysUserService.findById(Integer.valueOf(userId));
			}
			if (backUser != null) {
				return "redirect:indexBack";
			}
			if (!StringUtils.isBlank(message)) {
				model.addAttribute(MESSAGE, message);
			}
		} catch (Exception e) {
			logger.error("back login error ", e);
		}
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
		String errMsg = null;
		String userPhone = request.getParameter("userMobile");
		String key = SMS_REGISTER_PREFIX + userPhone;
		try {
			String smsCode = request.getParameter("smsCode");

			// dev & test env disable SMS code validation
			String code = disableMobileCode ? DEFAULT_SMS_CODE : jedisCluster.get(key);
			if (code != null) {
				if (code.equals(smsCode)) {
					Integer userId = sysUserService.findLogin(userPhone, MD5coding.getInstance().encrypt(AESUtil.getInstance().encrypt(request.getParameter("userPassword").toString(), "")));
					if (userId != null) {
						Cookie cookie = new Cookie("backSessionId", request.getSession().getId());
						response.addCookie(cookie);
						jedisCluster.setex(SysConstant.SYS_USER + request.getSession().getId(), BACK_USER_TIME, userId.toString());
						jedisCluster.del(key);
					} else {
						errMsg = "该用户或密码错误！";
					}
				} else {
					errMsg = "短信验证码错误！";
				}
			} else {
				errMsg = "验证码失效或不存在！";
			}
		} catch (Exception e) {
			jedisCluster.del(key);
			errMsg = "服务器异常，稍后重试！";
			logger.error("post login error userPhone:{}", userPhone, e);
		}
		if (errMsg != null) {
			model.addAttribute("website", jedisCluster.hgetAll(SysConstant.WEBSITE));
			model.addAttribute(MESSAGE, errMsg);
			return "login";
		} else {
			return "redirect:indexBack";
		}
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> cookieMap = ReadCookieMap(request);
		String backSessionId = cookieMap.get("backSessionId");
		jedisCluster.del(SysConstant.SYS_USER + backSessionId);
		return "redirect:login";
	}
}
