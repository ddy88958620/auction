package com.trump.auction.common.util.http;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtil {

	public static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	public static HttpUtil instance;
	public static final String CHARSET = "UTF-8";
	HostnameVerifier hv = new HostnameVerifier() {
		public boolean verify(String urlHostName, SSLSession session) {
			return true;
		}
	};

	private static void trustAllHttpsCertificates() throws Exception {
		javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
		javax.net.ssl.TrustManager tm = new miTM();
		trustAllCerts[0] = tm;
		javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, null);
		javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	}

	static class miTM implements javax.net.ssl.TrustManager, javax.net.ssl.X509TrustManager {
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public boolean isServerTrusted(java.security.cert.X509Certificate[] certs) {
			return true;
		}

		public boolean isClientTrusted(java.security.cert.X509Certificate[] certs) {
			return true;
		}

		public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) throws java.security.cert.CertificateException {
			return;
		}

		public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) throws java.security.cert.CertificateException {
			return;
		}
	}

	private HttpUtil() {
	}

	public static HttpUtil getInstance() {
		if (instance == null) {
			synchronized (HttpUtil.class) {
				if (instance == null)
					instance = new HttpUtil();
			}
		}
		return instance;
	}

	public String doPost(String url, String params) {
		// logger.info("请求参数:" + params.toString());
		String result = null;
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpPost httpPost = new HttpPost(url);
			StringEntity stringEntity = new StringEntity(params.toString(), "utf-8");
			httpPost.setHeader("Content-Type", "text/xml;charset=utf-8");

			httpPost.setEntity(stringEntity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "utf-8"));
			String resultStr = reader.readLine();
			while (null != resultStr) {
				result = resultStr;
				resultStr = reader.readLine();
			}
		} catch (Exception e) {
			logger.error("dopost error", e);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return result;
	}

	/**
	 * 扫描图片信息
	 * 
	 * @param urlStr
	 * @param textMap
	 * @param fileMap
	 * @param contentType
	 *            没有传入文件类型默认采用application/octet-stream
	 *            contentType非空采用filename匹配默认的图片类型
	 * @return 返回response数据
	 */
	@SuppressWarnings("rawtypes")
	public static String formUploadImage(String urlStr, Map<String, String> textMap, Map<String, String> fileMap, String contentType) {
		String res = "";
		HttpURLConnection conn = null;
		// boundary就是request头和上传文件内容的分隔符
		String BOUNDARY = "---------------------------123821742118716";
		try {
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(30000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("YhInfo-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
			OutputStream out = new DataOutputStream(conn.getOutputStream());
			// text
			if (textMap != null) {
				StringBuffer strBuf = new StringBuffer();
				Iterator iter = textMap.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					String inputName = (String) entry.getKey();
					String inputValue = (String) entry.getValue();
					if (inputValue == null) {
						continue;
					}
					strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
					strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
					strBuf.append(inputValue);
				}
				out.write(strBuf.toString().getBytes(CHARSET));
			}
			// file
			if (fileMap != null) {
				Iterator iter = fileMap.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					String inputName = (String) entry.getKey();
					String inputValue = (String) entry.getValue();
					if (inputValue == null) {
						continue;
					}
					File file = new File(inputValue);
					String filename = file.getName();

					// 没有传入文件类型，同时根据文件获取不到类型，默认采用application/octet-stream
					contentType = new MimetypesFileTypeMap().getContentType(file);
					// contentType非空采用filename匹配默认的图片类型
					if (!"".equals(contentType)) {
						if (filename.endsWith(".png")) {
							contentType = "image/png";
						} else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg") || filename.endsWith(".jpe")) {
							contentType = "image/jpeg";
						} else if (filename.endsWith(".gif")) {
							contentType = "image/gif";
						} else if (filename.endsWith(".ico")) {
							contentType = "image/image/x-icon";
						}
					}
					if (contentType == null || "".equals(contentType)) {
						contentType = "application/octet-stream";
					}
					StringBuffer strBuf = new StringBuffer();
					strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
					strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\"" + filename + "\"\r\n");
					strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
					out.write(strBuf.toString().getBytes(CHARSET));
					DataInputStream in = new DataInputStream(new FileInputStream(file));
					int bytes = 0;
					byte[] bufferOut = new byte[1024];
					while ((bytes = in.read(bufferOut)) != -1) {
						out.write(bufferOut, 0, bytes);
					}
					in.close();
				}
			}
			byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
			out.write(endData);
			out.flush();
			out.close();
			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {
				// 读取返回数据
				StringBuffer strBuf = new StringBuffer();
				BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), CHARSET));

				String line = null;
				while ((line = reader.readLine()) != null) {
					strBuf.append(line).append("\n");
				}
				res = strBuf.toString();
				reader.close();
				reader = null;
			} else {
				StringBuffer error = new StringBuffer();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), CHARSET));
				String line1 = null;
				while ((line1 = bufferedReader.readLine()) != null) {
					error.append(line1).append("\n");
				}
				res = error.toString();
				bufferedReader.close();
				bufferedReader = null;
			}
			logger.info("返回请求参数:" + responseCode + " msg=" + res);
		} catch (Exception e) {
			logger.error("发送POST请求出错。" + urlStr, e);
		} finally {
			if (conn != null) {
				conn.disconnect();
				conn = null;
			}
		}
		return res;
	}

	/**
	 * 调用 API
	 * 
	 * @return
	 */
	public String post(String apiURL, List<NameValuePair> params) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost method = new HttpPost(apiURL);
		String body = null;
		// logger.info("parameters:" + parameters);
		try {

			// 建立一个NameValuePair数组，用于存储欲传送的参数
			// method.addHeader("Content-type","application/json; charset=utf-8");
			// method.setHeader("Accept", "application/json");
			if (params != null) {
				// 设置字符集
				HttpEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
				// 设置参数实体
				method.setEntity(entity);
			}
			// method.setEntity(new SerializableEntity(parameters,
			// Charset.forName("UTF-8")));

			HttpResponse response = httpClient.execute(method);

			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed:" + response.getStatusLine());
			}

			// Read the response body
			body = EntityUtils.toString(response.getEntity());

		} catch (IOException e) {
			// 网络错误
			e.printStackTrace();
		}
		return body;
	}

	/**
	 * 调用 API
	 * 
	 * @return
	 */
	public String postJson(String apiURL, String params) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(apiURL);
		String body = null;
		// logger.info("parameters:" + parameters);
		try {

			// 建立一个NameValuePair数组，用于存储欲传送的参数
			httpPost.addHeader("Content-type", "application/json; charset=utf-8");
			httpPost.setHeader("Accept", "application/json");
			if (params != null) {
				// 设置字符集
				StringEntity stringEntity = new StringEntity(params, "utf-8");
				// 设置参数实体
				httpPost.setEntity(stringEntity);
			}
			// httpPost.setEntity(new SerializableEntity(parameters,
			// Charset.forName("UTF-8")));

			HttpResponse response = httpClient.execute(httpPost);

			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed:" + response.getStatusLine());
			}

			// Read the response body
			body = EntityUtils.toString(response.getEntity());

		} catch (IOException e) {
			// 网络错误
			e.printStackTrace();
		}
		return body;
	}

	/**
	 * post请求是否忽略证书信任
	 * 
	 * @param url
	 * @param ignore
	 *            true忽略证书信任
	 * @return
	 */
	public String doPost(String url, String params, boolean ignore) {
		String result = null;
		try {
			if (ignore) {
				trustAllHttpsCertificates();
				HttpsURLConnection.setDefaultHostnameVerifier(hv);
			}
			result = doPost(url, params);
		} catch (Exception e) {
			logger.error("doPost url:{},", url, e);
		}
		return result;
	}
	/**
	 * get请求是否忽略证书信任
	 * 
	 * @param url
	 * @param ignore
	 *            true忽略证书信任
	 * @return
	 */
	public String doGet(String url, boolean ignore) {
		String result = null;
		try {
			if (ignore) {
				trustAllHttpsCertificates();
				HttpsURLConnection.setDefaultHostnameVerifier(hv);
			}
			result = doGet(url);
		} catch (Exception e) {
			logger.error("doGet url:{},", url, e);
		}
		return result;
	}

	/**
	 * 调用 API
	 * 
	 * @return
	 */
	public String doGet(String apiURL) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(apiURL);
		String body = null;
		// logger.info("parameters:" + parameters);
		try {

			// 建立一个NameValuePair数组，用于存储欲传送的参数
			httpGet.addHeader("Content-type", "application/json; charset=utf-8");
			httpGet.setHeader("Accept", "application/json");
			/*
			 * if (params != null) { // 设置字符集 StringEntity stringEntity = new
			 * StringEntity(params, "utf-8"); // 设置参数实体
			 * httpGet.setEntity(stringEntity); }
			 */
			// httpPost.setEntity(new SerializableEntity(parameters,
			// Charset.forName("UTF-8")));

			HttpResponse response = httpClient.execute(httpGet);

			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed:" + response.getStatusLine());
			}

			// Read the response body
			body = EntityUtils.toString(response.getEntity());

		} catch (IOException e) {
			// 网络错误
			e.printStackTrace();
		}
		return body;
	}

}
