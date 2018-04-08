package com.trump.auction.order.enums;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 订单状态枚举
 * Created by wangjian on 2017/12/20.
 */
public enum EnumLogisticsStatus implements BaseEnum<Integer> {
    ALL(-999,"全部"),
    UNDISPATCH(0,"待发货"),
    DISPATCHED(1,"已发货"),
    DISPATCH_FALSE(2,"已签收");

    private Integer value;
    private String text;

    EnumLogisticsStatus(Integer value, String text) {
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
        for (EnumLogisticsStatus logisticsStatus : values()) {
            ALL_EXPRESSION.put(logisticsStatus.getValue(), logisticsStatus.getText());
        }
        return ALL_EXPRESSION;
    }
}
