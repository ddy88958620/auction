package com.trump.auction.order.enums;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 订单标志枚举
 * Created by wangjian on 2017/12/20.
 */
public enum EnumPaymentFlag implements BaseEnum<Integer> {
    PAY(1,"支付"),
    RECHARGE(2,"充值");

    private Integer value;
    private String text;

    EnumPaymentFlag(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    public static Map<Integer, String> getAllType() {
        Map<Integer, String> ALL_EXPRESSION = new LinkedHashMap<Integer, String>();
        for (EnumPaymentFlag enumPaymentType : values()) {
            ALL_EXPRESSION.put(enumPaymentType.getValue(), enumPaymentType.getText());
        }
        return ALL_EXPRESSION;
    }
}
