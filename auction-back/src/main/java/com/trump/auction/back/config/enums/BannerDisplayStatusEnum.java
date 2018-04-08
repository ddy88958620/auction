package com.trump.auction.back.config.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Created by wangjian on 2018/01/05.
 * banner URL跳转类型
 */
public enum BannerDisplayStatusEnum {

    BLOCK(1,"显示"),NONE(2,"隐藏");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    BannerDisplayStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, BannerDisplayStatusEnum> codeToEnums = new HashMap<Integer, BannerDisplayStatusEnum>();

    static {
        for (BannerDisplayStatusEnum e : values()) {
            codeToEnums.put(e.code, e);
        }
    }

    public static BannerDisplayStatusEnum of(Integer code) {
        BannerDisplayStatusEnum e = codeToEnums.get(code);
        if (e == null) {
            throw new IllegalArgumentException("No such enum code: " + code);
        }
        return e;
    }

    public static Map<Integer, String> getAllType() {
        Map<Integer, String> ALL_EXPRESSION = new LinkedHashMap<Integer, String>();
        for (BannerDisplayStatusEnum merchantType : values()) {
            ALL_EXPRESSION.put(merchantType.getCode(), merchantType.getDesc());
        }
        return ALL_EXPRESSION;
    }
}
