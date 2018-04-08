package com.trump.auction.back.util.sys;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cf.common.util.mapping.BeanMapper;
import com.trump.auction.back.constant.SysConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import redis.clients.jedis.JedisCluster;

import com.trump.auction.back.sys.model.Config;
import com.trump.auction.back.sys.model.SysUser;
import com.trump.auction.back.sys.service.ConfigService;
import com.trump.auction.back.sys.service.ModuleService;
import com.trump.auction.back.sys.service.SysUserService;

/**
 * 
 * 类描述：后台拦截器，包括session验证和权限信息 <br>
 * 创建人：fanyinchuan<br>
 * 创建时间：2016-6-27 下午05:38:34 <br>
 * 
 * @version
 * 
 */
public class SysContextInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory.getLogger(SysContextInterceptor.class);

	@Autowired
	SysUserService sysUserService;
	@Autowired
	ModuleService moduleService;
	@Autowired
	JedisCluster jedisCluster;
	@Autowired
	ConfigService configService;
	@Autowired
	private BeanMapper beanMapper;

	@SuppressWarnings("unused")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		try {
			String uri = getURI(request);
			// 不在验证的范围内
			if (exclude2(uri)) {
				return true;
			}
			SysUser user = null;
			// 正常状态

			Map<String, String> cookieMap = ReadCookieMap(request);
			String backSessionId = cookieMap.get("backSessionId");
			String userId = jedisCluster.get(SysConstant.SYS_USER + backSessionId);

			if (StringUtils.isNotBlank(userId)) {
				user = sysUserService.findById(Integer.valueOf(userId));
			}

			boolean allow = true;
			String value = jedisCluster.get(SysConstant.ALLOW_IP);
			if (value == null) {
				List<Config> list = configService.findParams("SYSTEM_IP");
				if (list != null && list.size() > 0) {
					value = list.get(0).getSysValueAuto();
					if (StringUtils.isNotBlank(value)) {
						jedisCluster.setex(SysConstant.ALLOW_IP, 60 * 10, value);
					}
				}
			}
			if (StringUtils.isNotBlank(value)) {
				String[] ips = value.split(",");
				List<String> list = Arrays.asList(ips);
				String ip = RequestUtils.getIpAddr(request);
				if (!list.contains(ip)) {
					allow = false;
				}
			}
			if (!"/err".equals(uri)) {
				if (!allow) {
					if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
						// 如果是ajax请求响应头会有，x-requested-with；
						response.setHeader("statusCode", "303");
					} else {
						response.sendRedirect("err");
					}
					jedisCluster.del(SysConstant.SYS_USER + backSessionId);

					return false;
				}
			} else {
				if (!allow) {
					return true;
				}
			}
			// 不在验证的范围内
			if (exclude(uri)) {
				return true;
			}

			// 用户为null跳转到登录页面
			if (user == null) {
				if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest"))// 如果是ajax请求响应头会有，x-requested-with；
				{
					response.setHeader("statusCode", "301");// 在响应头设置session状态
					return false;
				}
				response.sendRedirect(getLoginUrl(request));
				return false;
			}
			if (user.getStatus().intValue() != SysUser.STATUS_USE.intValue()) {
				request.setAttribute("message", "用户已被删除");
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return false;
			}
			if (!SysUser.isAdmin(String.valueOf(user.getId()))) {


				HashMap<String, Object> params = new HashMap<String, Object>();
				params.put("id", userId);
				params.put("moduleUrl", uri);
				int count = moduleService.findModuleByUrl(params);
				if (count > 0) {
					return true;
				} else {
					response.setHeader("statusCode", HttpServletResponse.SC_FORBIDDEN + "");
					response.sendError(HttpServletResponse.SC_FORBIDDEN);
					return false;
				}

			} else {
				return true;
			}
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			logger.error("admin interrupt error", e);
			return false;
		}

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

	private String getLoginUrl(HttpServletRequest request) {
		StringBuilder buff = new StringBuilder();
		if (loginUrl.startsWith("/")) {
			String ctx = request.getContextPath();
			if (!StringUtils.isBlank(ctx)) {
				buff.append(ctx);
			}
		}
		buff.append(loginUrl);
		if (!StringUtils.isBlank(processUrl)) {
			buff.append("?").append("processUrl").append("=").append(getProcessUrl(request));
		}
		return buff.toString();
	}

	private String getProcessUrl(HttpServletRequest request) {
		StringBuilder buff = new StringBuilder();
		if (loginUrl.startsWith("/")) {
			String ctx = request.getContextPath();
			if (!StringUtils.isBlank(ctx)) {
				buff.append(ctx);
			}
		}
		buff.append(processUrl);
		return buff.toString();
	}

	private boolean exclude2(String uri) {
		if (noIp != null) {
			for (String exc : noIp) {
				if (exc.equals(uri)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断该请求是否是允许不经过session验证的路径
	 * 
	 * @param uri
	 * @return
	 */
	private boolean exclude(String uri) {
		if (excludeUrls != null) {
			for (String exc : excludeUrls) {
				if (exc.equals(uri)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 获得第二个路径分隔符的位置
	 * 
	 * @param request
	 * @throws IllegalStateException
	 *             访问路径错误，没有二(三)个'/'
	 */
	private static String getURI(HttpServletRequest request) throws IllegalStateException {
		UrlPathHelper helper = new UrlPathHelper();
		String uri = helper.getOriginatingRequestUri(request);
		String ctxPath = helper.getOriginatingContextPath(request);
		int count = 0;
		if (StringUtils.isNotBlank(ctxPath)) {
			count = ctxPath.length();
		}
		return uri.substring(count);
	}

	private String[] excludeUrls;
	/**
	 * 不用经过IP验证的请求
	 */
	private List<String> noIp;

	public void setNoIp(List<String> noIp) {
		this.noIp = noIp;
	}

	private String loginUrl;
	private String processUrl;
	private String outCall;

	public String getOutCall() {
		return outCall;
	}

	public void setOutCall(String outCall) {
		this.outCall = outCall;
	}

	public String[] getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(String[] excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getProcessUrl() {
		return processUrl;
	}

	public void setProcessUrl(String processUrl) {
		this.processUrl = processUrl;
	}

	/**
	 * 将cookie封装到Map里面
	 * 
	 * @param request
	 * @return
	 */
	private static Map<String, String> ReadCookieMap(HttpServletRequest request) {
		Map<String, String> cookieMap = new HashMap<String, String>();
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie.getValue());
			}
		}
		return cookieMap;
	}
}