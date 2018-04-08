package com.trump.auction.back.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author:limingwu
 * @Description:
 * @Date: Create in 13:12 2018/3/22
 * @Modified By:
 */
public enum  OrderAppraisesTypeEnum {

    USER(1, "用户"),
    SYSTEM(2, "系统");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    OrderAppraisesTypeEnum(Integer code, String desc){
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, OrderAppraisesTypeEnum> codeToEnums = new HashMap<>();

    static {
        for (OrderAppraisesTypeEnum e : values()) {
            codeToEnums.put(e.code, e);
        }
    }

    public static OrderAppraisesTypeEnum of(Integer code) {
        OrderAppraisesTypeEnum e = codeToEnums.get(code);
        if (e == null) {
            throw new IllegalArgumentException("No such enum code: " + code);
        }
        return e;
    }

    public static Map<Integer, String> getAllType() {
        Map<Integer, String> ALL_EXPRESSION = new LinkedHashMap<Integer, String>();
        for (OrderAppraisesTypeEnum orderAppraisesTypeEnum :values()){
            ALL_EXPRESSION.put(orderAppraisesTypeEnum.getCode(),orderAppraisesTypeEnum.getDesc());
        }
        return ALL_EXPRESSION;
    }
}
