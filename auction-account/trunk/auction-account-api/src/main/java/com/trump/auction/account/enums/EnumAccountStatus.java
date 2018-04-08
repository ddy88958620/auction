package com.trump.auction.account.enums;

/**
 * 用户账户启用状态
 */
public enum EnumAccountStatus implements EnumBase<Integer> {
	ENABLED(1, "可用"), DISABLED(2, "不可用");

	/*** 值 */
	private final Integer key;
	/*** 名称 */
	private final String value;

	EnumAccountStatus(Integer key, String value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public Integer getKey() {
		return key;
	}

	@Override
	public String getValue() {
		return value;
	}

}