package com.trump.auction.back.constant;




import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 用户类型
 * 
 * @author wangYaMin
 */
public enum UserInfoEnum {
	IS_ENABLED(1, "已启用"),
	HAS_BEEN_CANCELLED(2, "已注销");
	private Integer value;
	private String text;

	private UserInfoEnum(Integer value, String text) {
		this.value = value;
		this.text = text;
	}

	public Integer getValue() {
		return this.value;
	}

	public String getText() {
		return this.text;
	}

	public String toString() {
		return this.value.toString();
	}

	public static Map<Integer, String> getAllType() {
		Map<Integer, String> ALL_EXPRESSION = new LinkedHashMap();
		UserInfoEnum[] var4;
		int var3 = (var4 = values()).length;

		for(int var2 = 0; var2 < var3; ++var2) {
			UserInfoEnum status = var4[var2];
			ALL_EXPRESSION.put(status.getValue(), status.getText());
		}

		return ALL_EXPRESSION;
	}

}
