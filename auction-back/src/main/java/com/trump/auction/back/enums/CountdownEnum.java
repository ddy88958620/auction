package com.trump.auction.back.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zhangliyan
 * 延时周期
 */
public enum CountdownEnum {

    FIVE(5,"5"),TEN(10,"10");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    CountdownEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, CountdownEnum> codeToEnums = new HashMap<Integer, CountdownEnum>();

    static {
        for (CountdownEnum e : values()) {
            codeToEnums.put(e.code, e);
        }
    }

    public static CountdownEnum of(Integer code) {
        CountdownEnum e = codeToEnums.get(code);
        if (e == null) {
            throw new IllegalArgumentException("No such enums code: " + code);
        }
        return e;
    }
    public static Map<Integer, String> getAllType() {
        Map<Integer, String> ALL_EXPRESSION = new LinkedHashMap<Integer, String>();
        for (CountdownEnum countdownEnum : values()) {
            ALL_EXPRESSION.put(countdownEnum.getCode(), countdownEnum.getDesc());
        }
        return ALL_EXPRESSION;
    }
}
