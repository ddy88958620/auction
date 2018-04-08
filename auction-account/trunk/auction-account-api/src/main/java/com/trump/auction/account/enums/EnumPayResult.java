package com.trump.auction.account.enums;

/**
 * 拍币支付结果
 */
public enum EnumPayResult implements EnumBase<Integer> {
	BLOCKED(100, "操作太频繁，请稍后重试"),
	FAILED(101, "支付失败"),
	SUCCESS(200, "支付成功"),
	NOT_EXIST(300, "操作失败，账户不存在"),
	NOT_ENOUGH(301, "支付失败，余额不足"),
	RECORD_NOT_EXIST(400, "退款失败，交易记录不存在"),
	WRONG_MONEY(401, "退款失败，退款金额大于支付金额"),
	NET_EXCEPTION(500, "网络异常，请稍后重试"),
	SYSTEM_EXCEPTION(501, "系统异常，请稍后重试");

	private final Integer key;
	private final String value;

	EnumPayResult(Integer key, String value) {
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
