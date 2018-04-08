package com.trump.auction.back.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author:limingwu
 * @Description:
 * @Date: Create in 23:45 2018/3/16
 * @Modified By:
 */
public enum SendTypeEnum {

    Marketing(0, "广告营销类"),
    Notice(1, "通知类"),
    Verification(2, "验证码类");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    SendTypeEnum(Integer code, String desc){
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, SendTypeEnum> codeToEnums = new HashMap<>();

    static {
        for (SendTypeEnum e : values()) {
            codeToEnums.put(e.code, e);
        }
    }

    public static SendTypeEnum of(Integer code) {
        SendTypeEnum e = codeToEnums.get(code);
        if (e == null) {
            throw new IllegalArgumentException("No such enum code: " + code);
        }
        return e;
    }

    public static Map<Integer, String> getAllType() {
        Map<Integer, String> ALL_EXPRESSION = new LinkedHashMap<Integer, String>();
        for (SendTypeEnum sendTypeEnum :values()){
            ALL_EXPRESSION.put(sendTypeEnum.getCode(),sendTypeEnum.getDesc());
        }
        return ALL_EXPRESSION;
    }
}
