package com.trump.auction.pals.util.weChatPay;

import java.util.Map;
import java.util.SortedMap;
import java.util.UUID;

import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;
import com.trump.auction.pals.domain.weChat.SimpleMapConverter;

/**
 * <p>
 * Title: 
 * </p>
 * <p>
 * Description: 
 * </p>
 * 
 * @author yll
 * @date 2017年12月20日下午4:21:18
 */
public class PaymentHelper {

	public static String sign(SortedMap<String, String> paramMap, String key) {

		// 1.拼接参数对
		StringBuilder signTmp = new StringBuilder();

		for (String param : paramMap.keySet()) {
			signTmp.append(param).append("=").append(paramMap.get(param)).append("&");
		}

		// 2.拼接上key
		signTmp.append("key=").append(key);

		// 3.MD5加密
		String sign = EncryptUtil.encodedByMD5(signTmp.toString());
		return sign;
	}

	public static String generateNonce() {

		String nonce = UUID.randomUUID().toString();
		nonce = nonce.replaceAll("-", "");
		return nonce;
	}

	/**
	 * 将map对象转化为xml格式的字符串
	 * 
	 * @param map
	 * @return
	 */
	public static String mapToXml(Map<String, String> map) {

		StringBuilder builder = new StringBuilder("<xml>");
		for (String key : map.keySet()) {
			builder.append("<").append(key).append(">").append(map.get(key)).append("</").append(key).append(">");
		}
		builder.append("</xml>");
		return builder.toString();
	}

	/**
	 * 将返回的xml转化为map对象
	 * 
	 * @param xml
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, String> xmlToMap(String xml) {
		XStream xstream = new XStream();
		xstream.registerConverter(new SimpleMapConverter(xstream.getMapper()));
		xstream.alias("xml", Map.class);
		Map<String, String> result = (Map) xstream.fromXML(xml);
		return result;
	}

	public static String getWechatVersion(String userAgent) {
		return null;
	}
	
	public static void main(String[] args) {
		Map<String, String> map = xmlToMap("<xml><appid>wxc6009c62e5c06c2c</appid><body>body</body><device_info>WEB</device_info><mch_id>1232069402</mch_id><nonce_str>12345</nonce_str><notify_url>http://zwz.pagekite.me/patient/paynotify.html</notify_url><out_trade_no>1234567890</out_trade_no><sign>2589087B11BC4102E9F7BD84DFA608AA</sign><spbill_create_ip>127.0.0.1</spbill_create_ip><time_expire>20171115182348</time_expire><time_start>20171114182348</time_start><total_fee>10000</total_fee><trade_type>APP</trade_type></xml>");
		System.out.println(JSONObject.toJSONString(map));
	}

}
