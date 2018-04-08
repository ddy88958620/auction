package com.trump.auction.order.enums;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 订单状态枚举
 * Created by wangjian on 2017/12/20.
 */
public enum EnumPaymentType implements BaseEnum<Integer> {
    ALIPAY(1,"支付宝"),
    WECHAT(2,"微信");

    private Integer value;
    private String text;

    EnumPaymentType(Integer value, String text) {
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
        for (EnumPaymentType enumPaymentType : values()) {
            ALL_EXPRESSION.put(enumPaymentType.getValue(), enumPaymentType.getText());
        }
        return ALL_EXPRESSION;
    }
}
