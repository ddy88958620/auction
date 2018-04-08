package com.trump.auction.web.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;

/**
 * <p>
 * Title: http工具类
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author youlianlai
 * @date 2017年10月18日下午4:50:13
 */
public class HttpHelper {
	private static String X_Requested_With = "X-Requested-With";
	private static String XMLHttpRequest = "XMLHttpRequest";
	private static final int TIMEOUT_IN_MILLIONS = 30000;

	public static final String CHARSET = "UTF-8";

	public static String getIpAddr(HttpServletRequest request) {
		if (request == null) {
			request = ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest();
		}
		String ip = request.getHeader("X-Real-IP");
		if (StringUtils.hasText(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.hasText(ip) && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个IP值，第一个为真实IP。
			int index = ip.indexOf(',');
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		} else {
			return request.getRemoteAddr();
		}
	}
	
	
	public static String getRequestPath(HttpServletRequest request) {
		try {
			if (request == null) {
				request = ((ServletRequestAttributes) RequestContextHolder
						.getRequestAttributes()).getRequest();
			}
			return request.getScheme() + "://" + request.getServerName() + ":"
					+ request.getServerPort() + request.getContextPath();
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * <p>
	 * Title: post请求
	 * </p>
	 * 
	 * @param url
	 * @param json:
	 *            json格式
	 * @return
	 */
	public static String doPost(String url, JSONObject json) {
		DataOutputStream out = null;
		BufferedReader in = null;
		String result = "";
		if(json == null){
			return	result;
		}
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("charset", "utf-8");
			conn.setUseCaches(false);
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
			conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
			
			out = new DataOutputStream(conn.getOutputStream());
			out.writeBytes(json.toString());
			out.flush();
			out.close();

			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	public static boolean isAjaxRequest(HttpServletRequest request) {
		String header = request.getHeader(X_Requested_With);
		return StringUtils.hasText(header) && XMLHttpRequest.equals(header);
	}
	
	/**
	 * Get请求，获得返回数据
	 * 
	 * @param urlStr
	 * @return
	 * @throws Exception
	 */
	public static String doGet(String urlStr) {
		URL url = null;
		HttpURLConnection conn = null;
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		try {
			url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
			conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			if (conn.getResponseCode() == 200) {
				is = conn.getInputStream();
				baos = new ByteArrayOutputStream();
				int len = -1;
				byte[] buf = new byte[128];
				while ((len = is.read(buf)) != -1) {
					baos.write(buf, 0, len);
				}
				baos.flush();
				return baos.toString();
			} else {
				//log.error("urlStr: {}; responseCode: {}",urlStr,conn.getResponseCode()); 
			}
		} catch (Exception e) {
			//log.error("urlStr: {}; error: {}",urlStr,e);
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
			}
			try {
				if (baos != null)
					baos.close();
			} catch (IOException e) {
			}
			conn.disconnect();
		}
		return null;
	}
}
