package com.trump.auction.account.enums;

import com.google.common.collect.Maps;

import java.util.Map;

public enum EnumRechargeOrderStatus implements EnumBase<Integer> {
    ORDER_UNDONE(1, "未完成的订单"),
    ORDER_DONE(2, "完成的订单");

    private final Integer key;
    private final String value;

    EnumRechargeOrderStatus(Integer key, String value) {
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
     * 获取所有订单状态
     */
    public static Map<Integer, String> getAllOrderStatus() {
        Map<Integer, String> allOrderStatus = Maps.newLinkedHashMap();
        allOrderStatus.put(ORDER_UNDONE.getKey(), ORDER_UNDONE.getValue());
        allOrderStatus.put(ORDER_DONE.getKey(), ORDER_DONE.getValue());
        return allOrderStatus;
    }
}
