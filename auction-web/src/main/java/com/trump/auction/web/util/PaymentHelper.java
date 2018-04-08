package com.trump.auction.web.util;

import java.util.Map;

import com.thoughtworks.xstream.XStream;

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


}
