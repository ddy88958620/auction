package com.trump.auction.back.util.common;

/**
 * 定义返回状态
 * */
public enum Status {

	/**
	 * 成功
	 */
	SUCCESS("0", "成功"),
	/**
	 * 系统错误
	 */
	ERROR("500", "未知异常");
	/**
	 * 名称
	 */
	private final String name;

	/**
	 * 值
	 */
	private final String value;

	Status(String name, String value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * 获取名称
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 获取值
	 * 
	 * @return
	 */
	public String getValue() {
		return value;
	}
}
