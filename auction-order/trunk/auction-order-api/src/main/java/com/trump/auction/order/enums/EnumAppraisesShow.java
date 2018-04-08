package com.trump.auction.order.enums;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 晒单是否显示枚举
 * Created by wangjian on 2017/12/20.
 */
public enum  EnumAppraisesShow implements BaseEnum<Integer> {
    ALL(-999, "全部"),
    NOTSHOW(2, "不显示"),
    SHOW(1, "显示");
   

    private Integer value;
    private String text;

    EnumAppraisesShow(Integer value, String text) {
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
        for (EnumAppraisesShow enumOrderStatus : values()) {
            ALL_EXPRESSION.put(enumOrderStatus.getValue(), enumOrderStatus.getText());
        }
        return ALL_EXPRESSION;
    }
}
