package com.trump.auction.order.enums;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 支付状态枚举
 * Created by wangjian on 2017/12/20.
 */
public enum  EnumPayStatus implements BaseEnum<Integer> {
    ALL(-999, "全部"),
    PAYING(0, "支付中"),
    PAYSUC(2, "支付成功"),
    PAYFAIL(1, "支付失败");

    private Integer value;
    private String text;

    EnumPayStatus(Integer value, String text) {
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
        for (EnumPayStatus enumOrderStatus : values()) {
            ALL_EXPRESSION.put(enumOrderStatus.getValue(), enumOrderStatus.getText());
        }
        return ALL_EXPRESSION;
    }
}
