package com.trump.auction.back.sensitiveWord.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 竞拍规则状态
 *
 * @author zhangliyan
 * @create 2018-01-03 14:25
 **/
public enum SensitiveWordTypeEnum {

    NICKNAME(1,"昵称敏感词"),SUNBILL(2,"晒单敏感词");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    SensitiveWordTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, SensitiveWordTypeEnum> codeToEnums = new HashMap();

    static {
        for (SensitiveWordTypeEnum e : values()) {
            codeToEnums.put(e.code, e);
        }
    }

    public static SensitiveWordTypeEnum of(Integer code) {
        SensitiveWordTypeEnum e = codeToEnums.get(code);
        if (e == null) {
            throw new IllegalArgumentException("No such enum code: " + code);
        }
        return e;
    }

    public static Map<Integer, String> getAllType() {
        Map<Integer, String> ALL_EXPRESSION = new LinkedHashMap();
        for (SensitiveWordTypeEnum merchantStatus : values()) {
            ALL_EXPRESSION.put(merchantStatus.getCode(), merchantStatus.getDesc());
        }
        return ALL_EXPRESSION;
    }
}
