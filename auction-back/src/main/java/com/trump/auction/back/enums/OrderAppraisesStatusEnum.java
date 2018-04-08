package com.trump.auction.back.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author:limingwu
 * @Description:
 * @Date: Create in 13:08 2018/3/22
 * @Modified By:
 */
public enum  OrderAppraisesStatusEnum {
    UN_CHECK(1, "待审核"),
    CHECK(2, "审核");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    OrderAppraisesStatusEnum(Integer code, String desc){
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, OrderAppraisesStatusEnum> codeToEnums = new HashMap<>();

    static {
        for (OrderAppraisesStatusEnum e : values()) {
            codeToEnums.put(e.code, e);
        }
    }

    public static OrderAppraisesStatusEnum of(Integer code) {
        OrderAppraisesStatusEnum e = codeToEnums.get(code);
        if (e == null) {
            throw new IllegalArgumentException("No such enum code: " + code);
        }
        return e;
    }

    public static Map<Integer, String> getAllType() {
        Map<Integer, String> ALL_EXPRESSION = new LinkedHashMap<Integer, String>();
        for (OrderAppraisesStatusEnum orderAppraisesStatusEnum :values()){
            ALL_EXPRESSION.put(orderAppraisesStatusEnum.getCode(),orderAppraisesStatusEnum.getDesc());
        }
        return ALL_EXPRESSION;
    }
}
