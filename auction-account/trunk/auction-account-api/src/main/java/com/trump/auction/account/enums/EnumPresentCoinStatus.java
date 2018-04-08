package com.trump.auction.account.enums;

/**
 * Created by wangyichao on 2017-12-22 上午 09:53.
 * 账户记录状态字段
 */
public enum EnumPresentCoinStatus implements EnumBase<Integer> {
	/*开心币*/
	UNUSED(1, "未使用"), USED(2, "已使用");

	private final Integer key;
	private final String value;

	EnumPresentCoinStatus(Integer key, String value) {
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
