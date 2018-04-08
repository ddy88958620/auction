package com.trump.auction.back.sys.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.trump.auction.back.constant.SysConstant;
import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import redis.clients.jedis.JedisCluster;

import com.octo.captcha.service.image.ImageCaptchaService;
import com.trump.auction.back.sys.model.SysUser;
import com.trump.auction.back.sys.service.SysUserService;
import com.trump.auction.back.util.json.JSONUtil;
import com.trump.auction.back.util.sys.RequestUtils;

public class BaseController {

	public static final String MESSAGE = "message";
	public static final String LOGING_DEVICE_PREFIX = "device_";
	public static final int BACK_USER_EXIST_TIME = 259200;// 3*24*60*60
	private static final String ENCODING_PREFIX = "encoding";
	private static final String NOCACHE_PREFIX = "no-cache";
	private static final boolean NOCACHE_DEFAULT = true;
	private static Logger logger = Logger.getLogger(BaseController.class);

	@Autowired
	JedisCluster jedisCluster;

	/**
	 * 得到 session
	 * 
	 * @return
	 */
	protected HttpSession getSession(HttpServletRequest request) {
		return request.getSession();
	}

	/**
	 * 获取IP地址
	 * 
	 * @param request
	 * @return
	 */
	public String getIpAddr(HttpServletRequest request) {
		return RequestUtils.getIpAddr(request);
	}

	@Autowired
	private ImageCaptchaService captchaService;
	@Autowired
	SysUserService sysUserService;

	/**
	 * 得到session中的admin user对象
	 */
	public SysUser loginAdminUser(HttpServletRequest request) {
		if (request == null) {
			request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		}
		SysUser backUser = null;
		Map<String, String> cookieMap = ReadCookieMap(request);
		String backSessionId = cookieMap.get("backSessionId");
		String userId = jedisCluster.get(SysConstant.SYS_USER + backSessionId);
		if (StringUtils.isNotBlank(userId)) {
			backUser = sysUserService.findById(Integer.valueOf(userId));
		}
		return backUser;
	}

	/**
	 * 验证码
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public boolean validateSubmit(HttpServletRequest request, HttpServletResponse response) {
		try {
			String key = request.getSession(false).getId();
			String value = jedisCluster.get(key);
			jedisCluster.del(key);
			return request.getParameter("captcha").toLowerCase().equals(value);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 获得request中的参数
	 * 
	 * @param request
	 * @return string object类型的map
	 */
	public HashMap<String, Object> getParametersO(HttpServletRequest request) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		if (request == null) {
			request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		}
		Map req = request.getParameterMap();
		if ((req != null) && (!req.isEmpty())) {
			Map<String, Object> p = new HashMap<String, Object>();
			Collection keys = req.keySet();
			for (Iterator i = keys.iterator(); i.hasNext();) {
				String key = (String) i.next();
				Object value = req.get(key);
				Object v = null;
				if ((value.getClass().isArray()) && (((Object[]) value).length > 0)) {
					if (((Object[]) value).length > 1) {
						v = ((Object[]) value);
					} else {
						v = ((Object[]) value)[0];
					}
				} else {
					v = value;
				}
				if ((v != null) && ((v instanceof String)) && !"\"\"".equals(v)) {
					String s = ((String) v).trim();
					if (s.length() > 0) {
						p.put(key, s);
					}
				}
			}
			hashMap.putAll(p);
			// 读取cookie
			hashMap.putAll(ReadCookieMap(request));

		}
		return hashMap;
	}

	/**
	 * 得到页面传递的参数封装成map
	 */

	public Map<String, String> getParameters(HttpServletRequest request) {
		if (request == null) {
			request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		}
		Map<String, String> p = new HashMap<String, String>();
		Map req = request.getParameterMap();
		if ((req != null) && (!req.isEmpty())) {

			Collection keys = req.keySet();
			for (Iterator i = keys.iterator(); i.hasNext();) {
				String key = (String) i.next();
				Object value = req.get(key);
				Object v = null;
				if ((value.getClass().isArray()) && (((Object[]) value).length > 0)) {
					v = ((Object[]) value)[0];
				} else {
					v = value;
				}
				if ((v != null) && ((v instanceof String)) && !"\"\"".equals(v)) {
					String s = (String) v;
					if (s.length() > 0) {
						p.put(key, s);
					}
				}
			}
			// 读取cookie
			p.putAll(ReadCookieMap(request));
			return p;
		}
		return p;
	}

	/**
	 * 将cookie封装到Map里面
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> ReadCookieMap(HttpServletRequest request) {
		Map<String, String> cookieMap = new HashMap<String, String>();
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie.getValue());
			}
		}
		return cookieMap;
	}

	public void renderJson(HttpServletResponse response, final List object, final String... headers) {
		JSONArray json = JSONArray.fromObject(object);
		renderJson(response, json.toString(), headers);
	}

	/**
	 * 直接输出JSON. Java对象,将被转化为json字符串.
	 * 
	 */
	public void renderJson(HttpServletResponse response, final Object object, final String... headers) {
		String jsonString = JSONUtil.getInstance().beanToJson(object);
		renderJson(response, jsonString, headers);
	}

	/**
	 * 直接输出JSON格式
	 * 
	 * @param string
	 *            json字符串.
	 */
	public void renderJson(HttpServletResponse response, final String string, final String... headers) {
		render(response, "application/json", string, headers);
	}

	/**
	 * 直接输出内容的简便函数.
	 * 
	 */
	public void render(HttpServletResponse response, final String contentType, final String content, final String... headers) {
		try {
			// 分析headers参数
			String encoding = "utf-8";
			boolean noCache = NOCACHE_DEFAULT;
			for (String header : headers) {
				String headerName = StringUtils.substringBefore(header, ":");
				String headerValue = StringUtils.substringAfter(header, ":");

				if (StringUtils.equalsIgnoreCase(headerName, ENCODING_PREFIX)) {
					encoding = headerValue;
				} else if (StringUtils.equalsIgnoreCase(headerName, NOCACHE_PREFIX)) {
					noCache = Boolean.parseBoolean(headerValue);
				} else {
					throw new IllegalArgumentException(headerName + "不是一个合法的header类型");
				}
			}
			// 设置headers参数
			String fullContentType = contentType + ";charset=" + encoding;
			response.setContentType(fullContentType);
			if (noCache) {
				response.setHeader("Pragma", "No-cache");
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expires", 0);
			}

			response.getWriter().write(content);
			response.getWriter().flush();

		} catch (IOException e) {
			logger.error("render error", e);
		}
	}

}
