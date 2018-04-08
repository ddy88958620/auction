package com.trump.auction.pals.api.constant;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 交易详情状态枚举
 * 
 * @author 2017年7月6日
 */
public enum EnumPayStatus {
	STATUS_WAIT(0, "交易中"),
	STATUS_FAILED(1, "交易失败"),
	STATUS_SUC(2, "交易成功");

	/*** 值 */
	private final Integer type;
	/*** 名称 */
	private final String name;

	public Integer getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	EnumPayStatus(Integer type, String name) {
		this.type = type;
		this.name = name;
	}

	public static String getTypeName(Integer type) {
		String name = null;
		for (EnumPayStatus couponEnum : values()) {
			if (type.equals(couponEnum.getType())) {
				name = couponEnum.getName();
				break;
			}
		}
		return name;
	}

	public static Map<Integer, String> getAllType() {
		Map<Integer, String> ALL_EXPRESSION = new LinkedHashMap<Integer, String>();
		for (EnumPayStatus couponEnum : values()) {
			ALL_EXPRESSION.put(couponEnum.getType(), couponEnum.getName());
		}
		return ALL_EXPRESSION;
	}
}
