package com.trump.auction.pals.api.constant;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 第三方枚举
 * 
 * @author wl
 * @version :1.0
 * 
 */
public enum EnumThirdPartyAsk {
	/**
     * 第三方 
     */
	TYPE_FUYOU("FUYOU", "富友"), 
	TYPE_YIMATONG("YIMATONG", "益码通"),
	TYPE_LIANLIAN("LIANLIAN", "连连"),
	TYPE_LKL("LKL", "拉卡拉"),
	TYPE_ALIPAY("ALIPAY", "支付宝"),
	TYPE_WECHAT("WECHAT", "微信"),
	ACT_RENEWAL("RENEWAL", "续期"),
	ACT_REPAY("REPAY", "还款"),
	ACT_GET_TOKEN("GET_TOKEN", "银行卡TOKEN"),
	
	ACT_VALIDATE_TOKEN("VALIDATE_TOKEN", "银行卡验证TOKEN"),
	ACT_SIGN_GRANT("SIGN_GRANT", "连连签约"),
	ACT_GRANT("GRANT", "连连请求扣款"),
	ACT_LIANLIAN_CARD("ACT_LIANLIAN_CARD", "连连主动付款"),
	ACT_DEBIT("DEBIT", "连连扣款"),
	ACT_LKL_REPAY("ACT_LKL_REPAY", "拉卡拉还款"),
	ACT_LKL_REPAY_STAGE("ACT_LKL_REPAY_STAGE", "拉卡拉分期还款"),
	ACT_LKL_REPAY_CONTINUE("ACT_LKL_REPAY_CONTINUE", "拉卡拉续费"),
	ACT_LKL_REPAY_FEE("ACT_LKL_REPAY_FEE", "拉卡拉扣款服务费"),
	ACT_LKL_REPAY_REFUND("ACT_LKL_REPAY_REFUND", "拉卡拉退款"),
	
	ACT_REPAY_DEBIT("REPAY_DEBIT", "连连分期扣款"),
	ACT_CONTINUE_DEBIT("CONTINUE_DEBIT", "连连续期扣款"),
	ACT_CONTINUE_LIANLIAN_CARD("ACT_CONTINUE_LIANLIAN_CARD", "连连主动续期扣款"),
	ACT_BANK_CARD_BIN("BANK_CARD_BIN", "银行卡绑定"),
	ACT_GRANT_FEE("GRANT_FEE", "连连签约服务费"),
	ACT_DEBIT_FEE("DEBIT_FEE", "连连扣款服务费"),
	ACT_EBJ_ALIPAY("EBJ_ALIPAY", "益码通扣款"),
	ACT_EBJ_ALIPAY_CONTINUE("EBJ_ALIPAY_CONTINUE", "益码通续期");
			
			
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

	EnumThirdPartyAsk(String type, String name) {
		this.type = type;
		this.name = name;
	}

	public static String getTypeName(String type) {
		String name = null;
		for (EnumThirdPartyAsk couponEnum : values()) {
			if (type.equals(couponEnum.getType())) {
				name = couponEnum.getName();
				break;
			}
		}
		return name;
	}

	public static Map<String, String> getAllType() {
		Map<String, String> ALL_EXPRESSION = new LinkedHashMap<String, String>();
		for (EnumThirdPartyAsk couponEnum : values()) {
			ALL_EXPRESSION.put(couponEnum.getType(), couponEnum.getName());
		}
		return ALL_EXPRESSION;
	}

}
