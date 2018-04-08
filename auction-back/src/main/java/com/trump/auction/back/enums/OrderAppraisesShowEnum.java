package com.trump.auction.back.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author:limingwu
 * @Description:
 * @Date: Create in 13:18 2018/3/22
 * @Modified By:
 */
public enum OrderAppraisesShowEnum {
    YES(1, "显示"),
    NO(2, "不显示");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    OrderAppraisesShowEnum(Integer code, String desc){
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, OrderAppraisesShowEnum> codeToEnums = new HashMap<>();

    static {
        for (OrderAppraisesShowEnum e : values()) {
            codeToEnums.put(e.code, e);
        }
    }

    public static OrderAppraisesShowEnum of(Integer code) {
        OrderAppraisesShowEnum e = codeToEnums.get(code);
        if (e == null) {
            throw new IllegalArgumentException("No such enum code: " + code);
        }
        return e;
    }

    public static Map<Integer, String> getAllType() {
        Map<Integer, String> ALL_EXPRESSION = new LinkedHashMap<Integer, String>();
        for (OrderAppraisesShowEnum orderAppraisesShowEnum :values()){
            ALL_EXPRESSION.put(orderAppraisesShowEnum.getCode(),orderAppraisesShowEnum.getDesc());
        }
        return ALL_EXPRESSION;
    }
}
