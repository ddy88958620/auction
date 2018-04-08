package com.trump.auction.account.enums;

/**
 * Created by wangyichao on 2017-12-22 下午 03:42.
 */
public enum EnumBalanceType implements EnumBase<Integer> {
	BALANCE_IN(1, "收入"), BALANCE_OUT(2, "支出");
	private Integer key;
	private String value;

	EnumBalanceType(Integer key, String value) {
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
