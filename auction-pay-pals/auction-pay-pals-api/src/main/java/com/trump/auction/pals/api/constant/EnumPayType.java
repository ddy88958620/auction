package com.trump.auction.pals.api.constant;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 付款方式枚举
 *
 * @author wl
 * @version :1.0
 *
 */
public enum EnumPayType {

	LIANLIAN("LIANLIAN", "连连"),
	LAKALA("LAKALA", "拉卡拉"),
	YIMATONG("YIMATONG", "益码通"),
	ALIPAY("ALIPAY", "支付宝"),
	WECHAT("WECHAT", "微信"),
	FUYOU("FUYOU", "富友");

	/*** 值 */
	private final String type;
	/*** 名称 */
	private final String name;

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	EnumPayType(String type, String name) {
		this.type = type;
		this.name = name;
	}

	public static String getTypeName(String type) {
		String name = null;
		for (EnumPayType couponEnum : values()) {
			if (type.equals(couponEnum.getType())) {
				name = couponEnum.getName();
				break;
			}
		}
		return name;
	}

	public static Map<String, String> getAllType() {
		Map<String, String> ALL_EXPRESSION = new LinkedHashMap<String, String>();
		for (EnumPayType couponEnum : values()) {
			ALL_EXPRESSION.put(couponEnum.getType(), couponEnum.getName());
		}
		return ALL_EXPRESSION;
	}

}
