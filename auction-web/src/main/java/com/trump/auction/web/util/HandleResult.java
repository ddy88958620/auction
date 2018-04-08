package com.trump.auction.web.util;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

public class HandleResult {

	private Integer code;
	private boolean result = false;
	private String msg = "网络异常";
	private Object data =  new Object();
		
	public  HandleResult(Integer code) {
		this.code = code;
	}
	
	public  HandleResult(boolean result) {
		setResult(result);
	}
	public  HandleResult(Integer code,String msg) {
		this.code = code;
		this.msg = msg;
	}
	public  HandleResult(Integer code,String msg,Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	public  HandleResult(boolean result,String msg) {
		this.result = result;
		this.msg = msg;
	}
	public  HandleResult(boolean result,String msg,Object data) {
		this.result = result;
		this.msg = msg;
		this.data = data;
	}

	public Integer getCode() {
		return code;
	}

	public HandleResult setCode(Integer code) {
		this.code = code;
		return this;
	}

	public boolean isResult() {
		return result;
	}

	public HandleResult setResult(boolean result) {
		this.result = result;
		if(result){
			return this.setCode(0).setMsg("success");
		}
		if(!result){
			return this.setCode(1).setMsg("网络异常");
		}
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public HandleResult setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public Object getData() {
		return data;
	}

	public HandleResult setData(Object data) {
		this.data = data;
		return this;
	}
	
	public <E> HandleResult setDataPage(Integer pageNum,Integer pages,List<E> list) {
		JSONObject json = new JSONObject();
		json.put("pages", pages);
		json.put("pageNum", pageNum);
		json.put("list", list);
		this.data = json;
		return this;
	}
	

	
	
	
}
