package com.trump.auction.account.enums;

/**
 * Created by wangyichao on 2018-01-25 下午 01:06.
 */
public enum EnumDiffBuyType implements EnumBase<Integer> {
    CREATE_ORDER(1, "创建订单"),
    DIFF_BUY(2, "差价购买"),
    CANCEL_ORDER(3, "取消订单")
    ;
    private Integer key;
    private String value;

    EnumDiffBuyType(Integer key, String value) {
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
