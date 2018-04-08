package com.trump.auction.back.config.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Created by wangjian on 2018/01/05.
 * banner URL跳转类型
 */
public enum BannerJumpTypeEnum {

    APP(1,"APP"),H5(2,"H5");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    BannerJumpTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, BannerJumpTypeEnum> codeToEnums = new HashMap<Integer, BannerJumpTypeEnum>();

    static {
        for (BannerJumpTypeEnum e : values()) {
            codeToEnums.put(e.code, e);
        }
    }

    public static BannerJumpTypeEnum of(Integer code) {
        BannerJumpTypeEnum e = codeToEnums.get(code);
        if (e == null) {
            throw new IllegalArgumentException("No such enum code: " + code);
        }
        return e;
    }

    public static Map<Integer, String> getAllType() {
        Map<Integer, String> ALL_EXPRESSION = new LinkedHashMap<Integer, String>();
        for (BannerJumpTypeEnum merchantType : values()) {
            ALL_EXPRESSION.put(merchantType.getCode(), merchantType.getDesc());
        }
        return ALL_EXPRESSION;
    }
}
