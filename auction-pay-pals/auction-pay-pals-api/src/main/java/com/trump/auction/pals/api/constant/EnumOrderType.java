package com.trump.auction.pals.api.constant;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 商户号控制枚举
 * 
 * @author wl
 * @version :1.0
 * 
 */
public enum EnumOrderType {
	
	/**
     * 商户号 
     */
	LX("LX", "连连续期"), 
	LQ("LQ", "连连签约"),
	LR("LR", "连连主动支付获取验证码"),
	LC("LC", "连连主动续期获取验证码"),
	LH("LH", "连连代扣"),
	LT("LT", "连连绑卡请求"),
	LB("LB", "连连绑卡验证"),
	FH("FH", "富友主动支付"),
	FX("FX", "富友续期"),
	KH("KH", "拉卡拉代扣"),
	KR("KR", "拉卡拉主动还款"),
	KC("KC", "拉卡拉续期"),
	YH("YH", "益码通还款"),
	AH("AH", "支付宝订单"),
	WX("WX", "微信订单"),
	YX("YX", "益码通续期");
    
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

	EnumOrderType(String type, String name) {
		this.type = type;
		this.name = name;
	}

	public static String getTypeName(String type) {
		String name = null;
		for (EnumOrderType couponEnum : values()) {
			if (type.equals(couponEnum.getType())) {
				name = couponEnum.getName();
				break;
			}
		}
		return name;
	}

	public static Map<String, String> getAllType() {
		Map<String, String> ALL_EXPRESSION = new LinkedHashMap<String, String>();
		for (EnumOrderType couponEnum : values()) {
			ALL_EXPRESSION.put(couponEnum.getType(), couponEnum.getName());
		}
		return ALL_EXPRESSION;
	}

}
