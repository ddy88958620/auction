package com.trump.auction.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.trump.auction.web.util.RedisContants;

import redis.clients.jedis.JedisCluster;

/**
 * <p>
 * Title: 拦截器
 * </p>
 * 
 * @author youlianlai
 * @date 2017年10月31日下午12:40:13
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {
	public static final String login_key = "game.login_key_";
	@Autowired
	JedisCluster jedisCluster;
	
    @Value("${static.resources.domain}")
    private String staticResourcesDomain;

    @Value("${aliyun.oss.domain}")
    private String aliyunOssDomain;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		addPathToSession(request);
		
		String userToken = request.getHeader("userToken");
		if(userToken == null){
			response.setStatus(401);
			return false;
		}
		request.setAttribute("aliyunOssDomain", aliyunOssDomain);
		String  userId = jedisCluster.get(RedisContants.LOGIN_KEY+userToken);
		if(userId == null){
			response.setStatus(401);
			return false;
		}
		
		String cacheToken = jedisCluster.get(RedisContants.LOGIN_CHECK_KEY+userId);
		if(!userToken.equals(cacheToken)){
			response.setStatus(401);
			return false;
		}
		
		return true;
	}
	/**
     * 添加各种请求域名到session中
     *
     * @param request
     */
    private void addPathToSession(HttpServletRequest request) {
        if (request.getSession().getAttribute("baseUrl") == null) {
            String baseUrl = "http://" + request.getServerName() + ":" + request.getServerPort();
            request.getSession().setAttribute("baseUrl", baseUrl);
        }

        if (request.getSession().getAttribute("staticResourcesDomain") == null) {
            request.getSession().setAttribute("staticResourcesDomain", staticResourcesDomain);
        }

        if (request.getSession().getAttribute("aliyunOssDomain") == null) {
            request.getSession().setAttribute("aliyunOssDomain", aliyunOssDomain);
        }
    }
}
