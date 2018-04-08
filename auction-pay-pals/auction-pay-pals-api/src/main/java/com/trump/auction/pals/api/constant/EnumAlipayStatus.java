package com.trump.auction.pals.api.constant;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 支付宝支付状态枚举类
 * 
 * @author wangjian
 * @version :1.0
 * 
 */
public enum EnumAlipayStatus {

    TRADE_FINISHED("TRADE_FINISHED", "交易完成"),
    TRADE_SUCCESS("TRADE_SUCCESS", "支付成功"),
    WAIT_BUYER_PAY("WAIT_BUYER_PAY", "交易创建"),
    TRADE_CLOSED("TRADE_CLOSED", "交易关闭");

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

	EnumAlipayStatus(String type, String name) {
		this.type = type;
		this.name = name;
	}

	public static String getTypeName(String type) {
		String name = null;
		for (EnumAlipayStatus couponEnum : values()) {
			if (type.equals(couponEnum.getType())) {
				name = couponEnum.getName();
				break;
			}
		}
		return name;
	}

	public static Map<String, String> getAllType() {
		Map<String, String> ALL_EXPRESSION = new LinkedHashMap<String, String>();
		for (EnumAlipayStatus couponEnum : values()) {
			ALL_EXPRESSION.put(couponEnum.getType(), couponEnum.getName());
		}
		return ALL_EXPRESSION;
	}

}
