package com.trump.auction.order.enums;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 订单状态枚举
 * Created by wangjian on 2017/12/20.
 */
public enum  EnumOrderStatus implements BaseEnum<Integer> {
    ALL(-999, "全部"),
    UNPAID(1, "待支付尾款"),
    PAID(2, "已支付尾款"),
    LIUPAI(3, "已流拍(内部拍回)"),
    UNSHIPPED(4,"待配货"),
    SHIPPED(5,"已发货"),
    RECEIVED(6,"已签收"),
    COMPLETE(7, "已完成"),
    CLOSE(8, "已关闭");

    private Integer value;
    private String text;

    EnumOrderStatus(Integer value, String text) {
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
        for (EnumOrderStatus enumOrderStatus : values()) {
            ALL_EXPRESSION.put(enumOrderStatus.getValue(), enumOrderStatus.getText());
        }
        return ALL_EXPRESSION;
    }
}
