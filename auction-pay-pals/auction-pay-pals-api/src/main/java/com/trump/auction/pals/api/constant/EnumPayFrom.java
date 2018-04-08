package com.trump.auction.pals.api.constant;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 付款来源枚举
 *
 * @author wl
 * @version :1.0
 *
 */
public enum EnumPayFrom {

	QBHK("QBHK", "钱包还款"),
	QBXQ("QBXQ", "钱包续期"),
	LQCZ("LQCZ", "零钱充值"),
	BTHK("BTHK", "白条还款"),
	JPCZ("JPCZ", "竞拍充值"),
	JPZF("JPZF", "竞拍支付");

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

	EnumPayFrom(String type, String name) {
		this.type = type;
		this.name = name;
	}

	public static String getTypeName(String type) {
		String name = null;
		for (EnumPayFrom couponEnum : values()) {
			if (type.equals(couponEnum.getType())) {
				name = couponEnum.getName();
				break;
			}
		}
		return name;
	}

	public static Map<String, String> getAllType() {
		Map<String, String> ALL_EXPRESSION = new LinkedHashMap<String, String>();
		for (EnumPayFrom couponEnum : values()) {
			ALL_EXPRESSION.put(couponEnum.getType(), couponEnum.getName());
		}
		return ALL_EXPRESSION;
	}

}
