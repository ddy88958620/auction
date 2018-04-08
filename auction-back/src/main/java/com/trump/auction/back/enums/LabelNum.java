package com.trump.auction.back.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author:limingwu
 * @Description:
 * @Date: Create in 18:33 2018/3/21
 * @Modified By:
 */
public enum LabelNum {

    Notice(0, "启用"),
    Verification(1, "禁用");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    LabelNum(Integer code, String desc){
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, LabelNum> codeToEnums = new HashMap<>();

    static {
        for (LabelNum e : values()) {
            codeToEnums.put(e.code, e);
        }
    }

    public static LabelNum of(Integer code) {
        LabelNum e = codeToEnums.get(code);
        if (e == null) {
            throw new IllegalArgumentException("No such enum code: " + code);
        }
        return e;
    }

    public static Map<Integer, String> getAllType() {
        Map<Integer, String> ALL_EXPRESSION = new LinkedHashMap<Integer, String>();
        for (LabelNum labelNum :values()){
            ALL_EXPRESSION.put(labelNum.getCode(),labelNum.getDesc());
        }
        return ALL_EXPRESSION;
    }

}
