package com.trump.auction.back.util.json;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultDefaultValueProcessor;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * 
 * JSON工具类
 */
public class JSONUtil {
	private static JSONUtil instance;

	private JSONUtil() {
	}

	public static JSONUtil getInstance() {
		if (instance == null) {
			synchronized (JSONUtil.class) {
				if (instance == null)
					instance = new JSONUtil();
			}
		}
		return instance;
	}

	final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * javabean转map
	 * 
	 * @param javaBean
	 * @return
	 */
	public Map<String, String> beanToMap(Object javaBean) {
		Map<String, String> result = new HashMap<String, String>();
		Method[] methods = javaBean.getClass().getDeclaredMethods();

		for (Method method : methods) {
			try {
				if (method.getName().startsWith("get")) {
					String field = method.getName();
					field = field.substring(field.indexOf("get") + 3);
					field = field.toLowerCase().charAt(0) + field.substring(1);

					Object value = method.invoke(javaBean, (Object[]) null);
					result.put(field, null == value ? "" : value.toString());
				}
			} catch (Exception e) {
			}
		}

		return result;
	}

	/**
	 * json对象转换成Map
	 * 
	 * @param jsonObject
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> jsonToMap(JSONObject jsonObject) {
		Map<String, String> result = new HashMap<String, String>();
		Iterator<String> iterator = jsonObject.keys();
		String key = null;
		String value = null;
		while (iterator.hasNext()) {
			key = iterator.next();
			value = jsonObject.getString(key);
			result.put(key, value);
		}
		return result;
	}

	/**
	 * json字符串转换成map
	 * 
	 * @param jsonStr
	 * @return
	 */
	public Map<String, Object> parseJSON2Map(String jsonStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 最外层解析
		JSONObject json = JSONObject.fromObject(jsonStr);
		for (Object k : json.keySet()) {
			Object v = json.get(k);
			// 如果内层还是数组的话，继续解析
			if (v instanceof JSONArray) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				Iterator<JSONObject> it = ((JSONArray) v).iterator();
				while (it.hasNext()) {
					JSONObject json2 = it.next();
					list.add(parseJSON2Map(json2.toString()));
				}
				map.put(k.toString(), list);
			} else {
				map.put(k.toString(), v);
			}
		}
		return map;
	}

	/**
	 * JSON 对象字符格式转换成java对象
	 * 
	 * @param jsonString
	 * @param beanCalss
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T jsonToBean(String jsonString, Class<T> beanClass) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		T bean = (T) JSONObject.toBean(jsonObject, beanClass);
		return bean;
	}

	public <T> T jsonToBeanNoProperty(String jsonString, Class<T> beanClass, String... format) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
		// false);
		if (format == null || format.length == 0) {
			objectMapper.setDateFormat(sdf);
		} else {
			objectMapper.setDateFormat(new SimpleDateFormat(format[0]));
		}
		T bean = (T) objectMapper.readValue(jsonString, beanClass);
		return bean;
	}

	/**
	 * java对象转换成json字符串
	 * 
	 * @param bean
	 * @return
	 */
	public String beanToJson(Object bean) {
		JSONObject json = JSONObject.fromObject(bean);
		return json.toString();
	}

	/**
	 * java对象转换成json字符串(Double、Integer类型,值为null时转换为"")
	 * 
	 * @param bean
	 * @return
	 */
	public String beanToJsonNull(Object bean) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerDefaultValueProcessor(Integer.class, new DefaultDefaultValueProcessor() {
			public Object getDefaultValue(Class type) {
				return null;
			}
		});
		jsonConfig.registerDefaultValueProcessor(Double.class, new DefaultDefaultValueProcessor() {
			public Object getDefaultValue(Class type) {
				return null;
			}
		});
		jsonConfig.registerDefaultValueProcessor(BigDecimal.class, new DefaultDefaultValueProcessor() {
			public Object getDefaultValue(Class type) {
				return null;
			}
		});
		jsonConfig.registerDefaultValueProcessor(Long.class, new DefaultDefaultValueProcessor() {
			public Object getDefaultValue(Class type) {
				return null;
			}
		});
		JSONObject json = JSONObject.fromObject(bean, jsonConfig);
		return json.toString();
	}

	/**
	 * java对象List集合转换成json字符串
	 * 
	 * @param beans
	 * @return
	 */

	@SuppressWarnings("rawtypes")
	public String beanListToJson(List beans) {
		StringBuffer rest = new StringBuffer();
		rest.append("[");
		int size = beans.size();
		for (int i = 0; i < size; i++) {
			rest.append(beanToJson(beans.get(i)) + ((i < size - 1) ? "," : ""));
		}
		rest.append("]");
		return rest.toString();
	}

	/**
	 * String -list to json
	 * 
	 * @param beans
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String beanListToJson1(List beans) {
		StringBuffer rest = new StringBuffer();
		rest.append("[");
		int size = beans.size();
		for (int i = 0; i < size; i++) {
			rest.append("\"" + beans.get(i) + "\"" + ((i < size - 1) ? "," : ""));
		}
		rest.append("]");
		return rest.toString();
	}

	/**
	 * String 转List<T>
	 * 
	 * @param <T>
	 * @param jsonArray
	 * @param beanClsss
	 * @return
	 */
	public <T> List<T> jsonArrayToBean(String jsonArray, Class<T> beanClsss) {
		JSONArray jsonArr = JSONArray.fromObject(jsonArray);
		List<T> beanList = new ArrayList<T>();
		for (int i = 0; i < jsonArr.size(); i++) {
			T bean = jsonToBean(String.valueOf(jsonArr.get(i)), beanClsss);
			beanList.add(bean);
		}
		return beanList;
	}

	/**
	 * 输出json
	 * 
	 * @param response
	 * @param object
	 */
	public void toObjectJson(HttpServletResponse response, String json) {
		try {
			response.setContentType("application/json; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.print(json);
			out.flush();
			out.close();
		} catch (Exception e) {

		}
	}

	/**
	 * jsonArray to list
	 * 
	 * @param jsonArray
	 * @return
	 */
	public List<HashMap<Object, Object>> jsonArrToMapList(String jsonArray) {
		List<HashMap<Object, Object>> mapList = new ArrayList<HashMap<Object, Object>>();
		if (StringUtils.isNotBlank(jsonArray)) {
			JSONArray jsonArr = JSONArray.fromObject(jsonArray);
			for (int i = 0; i < jsonArr.size(); i++) {
				HashMap<Object, Object> map = new HashMap<Object, Object>();
				JSONObject json = jsonArr.getJSONObject(i);
				for (Object k : json.keySet()) {
					Object v = json.get(k);
					map.put(k, v);
				}
				mapList.add(map);
			}
		}
		return mapList;
	}
}
