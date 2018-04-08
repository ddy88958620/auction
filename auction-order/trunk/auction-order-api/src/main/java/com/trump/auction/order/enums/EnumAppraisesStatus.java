package com.trump.auction.order.enums;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 晒单状态枚举
 * Created by wangjian on 2017/12/20.
 */
public enum  EnumAppraisesStatus implements BaseEnum<Integer> {
    ALL(-999, "全部"),
    CHECKED(2, "已审核"),
    UNCHECKED(1, "待审核");
   

    private Integer value;
    private String text;

    EnumAppraisesStatus(Integer value, String text) {
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
        for (EnumAppraisesStatus enumOrderStatus : values()) {
            ALL_EXPRESSION.put(enumOrderStatus.getValue(), enumOrderStatus.getText());
        }
        return ALL_EXPRESSION;
    }
}
