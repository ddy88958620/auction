package com.trump.auction.account.enums;

/**
 * Created by wangyichao on 2017-12-19 下午 06:10.
 * 账户相关交易名称
 */
public enum EnumTransactionTag implements EnumBase<Integer> {
	TRANSACTION_REGISTER(1, "注册赠送"),
	TRANSACTION_WX_RECHARGE(2, "微信充值"),
	TRANSACTION_ALI_RECHARGE(3, "支付宝充值"),
	TRANSACTION_RECHARGE_PRESENT(4, "充值赠送"),
	TRANSACTION_PAY(5, "竞拍消费"),
	TRANSACTION_PAY_BACK(6, "订单拍币返回"),
	TRANSACTION_DIFF_BUY(7, "差价购买"),
	TRANSACTION_GET_POINTS(8, "签到获取积分"),
	TRANSACTION_POINTS_EXCHANGE_PRESENT(9, "积分兑换赠币"),
	TRANSACTION_SIGN_GAIN_POINTS(10, "签到送积分"),
	//返币枚举
	TRANSACTION_BACK_COIN(11, "返还拍币"),
	TRANSACTION_BACK_PRESENT(12, "返赠币"),
	TRANSACTION_BACK_POINTS(13, "返积分"),
	TRANSACTION_BACK_BUY(14, "返开心币"),

	TRANSACTION_LOTTERY_COST(15, "大转盘消耗积分"),
	TRANSACTION_LOTTERY_PRIZE(16, "大转盘积分抽奖"),
    TRANSACTION_DIFF_BUY_REMAIN(17, "差价开心币找零"),
	TRANSACTION_SHARE_BACK_COIN(18, "晒单返拍币"),
	TRANSACTION_SHARE_BACK_PRESENT_COIN(19, "晒单返赠币"),
	TRANSACTION_SHARE_BACK_POINTS(20, "晒单返积分"),
	TRANSACTION_SHARE_POINTS(21, "分享返赠币")
	;


	private final Integer key;
	private final String value;

	EnumTransactionTag(Integer key, String value) {
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

	public static String getEnumTransactionTagByKey(Integer key){
		for(EnumTransactionTag ett : EnumTransactionTag.values()){
			if(key.intValue() == ett.getKey()){
				return ett.getValue();
			}
		}
		return null;
	}

}
