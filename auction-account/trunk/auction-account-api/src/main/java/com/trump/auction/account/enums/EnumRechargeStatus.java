package com.trump.auction.account.enums;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by wangyichao on 2017-11-07 上午 10:32.
 * 充值状态和订单状态相关枚举
 */
public enum EnumRechargeStatus implements EnumBase<Integer> {
	RECHARGE_ING(1, "充值中"),
	RECHARGE_SUCCESS(2, "充值成功"),
	RECHARGE_FAILED(3, "充值失败");

	private final Integer key;
	private final String value;

	EnumRechargeStatus(Integer key, String value) {
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


	/**
	 * 获取所有支付状态
	 */
	public static Map<Integer, String> getAllTradeStatus() {
		Map<Integer, String> allTradeStatus = Maps.newLinkedHashMap();
		allTradeStatus.put(RECHARGE_ING.getKey(), RECHARGE_ING.getValue());
		allTradeStatus.put(RECHARGE_SUCCESS.getKey(), RECHARGE_SUCCESS.getValue());
		allTradeStatus.put(RECHARGE_FAILED.getKey(), RECHARGE_FAILED.getValue());
		return allTradeStatus;
	}

}
