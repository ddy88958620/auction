package com.trump.auction.account.enums;

/**
 * Created by wangyichao on 2017-12-20 下午 01:18.
 * 账户类型
 */
public enum EnumAccountType implements EnumBase<Integer> {
	AUCTION_COIN(1, "拍币"), PRESENT_COIN(2, "赠币"), POINTS(3, "积分"), BUY_COIN(4, "开心币");

	private final Integer key;
	private final String value;

	EnumAccountType(Integer key, String value) {
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
	public static String getUserAccountTypeName(Integer type) {
		String name = null;
		for (EnumAccountType userAccountType : values()) {
			if (type.intValue() == userAccountType.getKey().intValue()) {
				name = userAccountType.getValue();
				break;
			}
		}
		return name;
	}
}
