package com.trump.auction.back.config.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Created by wangjian on 2018/01/05.
 * banner URL跳转类型
 */
public enum BannerHasLoginEnum {

    NO(0,"否"),YES(1,"是");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    BannerHasLoginEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, BannerHasLoginEnum> codeToEnums = new HashMap<Integer, BannerHasLoginEnum>();

    static {
        for (BannerHasLoginEnum e : values()) {
            codeToEnums.put(e.code, e);
        }
    }

    public static BannerHasLoginEnum of(Integer code) {
        BannerHasLoginEnum e = codeToEnums.get(code);
        if (e == null) {
            throw new IllegalArgumentException("No such enum code: " + code);
        }
        return e;
    }

    public static Map<Integer, String> getAllType() {
        Map<Integer, String> ALL_EXPRESSION = new LinkedHashMap<Integer, String>();
        for (BannerHasLoginEnum merchantType : values()) {
            ALL_EXPRESSION.put(merchantType.getCode(), merchantType.getDesc());
        }
        return ALL_EXPRESSION;
    }
}
