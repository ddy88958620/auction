package com.trump.auction.order.enums;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 订单状态枚举
 * Created by wangjian on 2017/12/20.
 */
public enum EnumOrderType implements BaseEnum<Integer> {
    ALL(-999,"全部"),
    AUCTION(1, "拍卖"),
    DIFFERENTIAL(2, "差价购");

    private Integer value;
    private String text;

    EnumOrderType(Integer value, String text) {
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
        for (EnumOrderType enumOrderType : values()) {
            ALL_EXPRESSION.put(enumOrderType.getValue(), enumOrderType.getText());
        }
        return ALL_EXPRESSION;
    }
}
