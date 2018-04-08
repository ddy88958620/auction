package com.trump.auction.back.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zhangqingqiang
 * 拍品类型
 */
public enum PoundageEnum {

    ONE(1,"1"),TEN(10,"10");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    PoundageEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, PoundageEnum> codeToEnums = new HashMap<Integer, PoundageEnum>();

    static {
        for (PoundageEnum e : values()) {
            codeToEnums.put(e.code, e);
        }
    }

    public static PoundageEnum of(Integer code) {
        PoundageEnum e = codeToEnums.get(code);
        if (e == null) {
            throw new IllegalArgumentException("No such enums code: " + code);
        }
        return e;
    }
    public static Map<Integer, String> getAllType() {
        Map<Integer, String> ALL_EXPRESSION = new LinkedHashMap<Integer, String>();
        for (PoundageEnum poundageEnum : values()) {
            ALL_EXPRESSION.put(poundageEnum.getCode(), poundageEnum.getDesc());
        }
        return ALL_EXPRESSION;
    }
}
