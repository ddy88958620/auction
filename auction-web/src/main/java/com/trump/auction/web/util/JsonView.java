package com.trump.auction.web.util;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;
/**
 * <p>
 * Title: 接口返回数据分装类
 * </p>
 * 
 * @author youlianlai
 * @date 2017年10月31日下午4:38:30
 */
@Data
public class JsonView {
	// 默认成功code
	private final static Integer def_success_code = 0;

	private Integer code;

	private String msg = "";

	private Object data = new Object();

	public static JsonView build(Integer code, String msg, Object data) {
		return new JsonView(code, msg, data);
	}

	public static JsonView success(Object data) {
		return new JsonView(data);
	}

	public static JsonView success() {
		return new JsonView(new Object());
	}

	public JsonView() {

	}

	public static JsonView build(Integer code, String msg) {
		return new JsonView(code, msg, new Object());
	}
	
	public JsonView(Integer code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public JsonView(Object data) {
		this.code = def_success_code;
		this.msg = "success";
		this.data = data;
	}
	
	public static  <E> JsonView buildPage(Integer pages,Integer pageNum, List<E> list) {
		JSONObject json = new JSONObject();
		json.put("pages", pages);
		json.put("pageNum", pageNum);
		json.put("list", list);
		return new JsonView(json);
	}
	
}
