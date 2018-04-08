package com.trump.auction.back.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zhangliyan
 * 下一期开拍倒计时
 */
public enum ShelvesDelayTimeEnum {

    NUM60(60,"60s"),NUM80(80,"80s"),NUM100(100,"100s"),NUM120(120,"120s"),NUM140(140,"140s");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    ShelvesDelayTimeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, ShelvesDelayTimeEnum> codeToEnums = new HashMap<Integer, ShelvesDelayTimeEnum>();

    static {
        for (ShelvesDelayTimeEnum e : values()) {
            codeToEnums.put(e.code, e);
        }
    }
    public static ShelvesDelayTimeEnum of(Integer code) {
        ShelvesDelayTimeEnum e = codeToEnums.get(code);
        if (e == null) {
            throw new IllegalArgumentException("No such enum code: " + code);
        }
        return e;
    }

    public static Map<Integer, String> getAllType() {
        Map<Integer, String> ALL_EXPRESSION = new LinkedHashMap<Integer, String>();
        for (ShelvesDelayTimeEnum shelvesDelayTimeEnum : values()) {
            ALL_EXPRESSION.put(shelvesDelayTimeEnum.getCode(), shelvesDelayTimeEnum.getDesc());
        }
        return ALL_EXPRESSION;
    }
}
