package com.trump.auction.trade.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: zhangqingqiang.
 * @date: 2018/1/19 0019.
 * @Description: 商品图片.
 */
public enum PicTypeEnum {

    DETAIL("0", "详情"),
    PRODUCT("1", "商品"),
    //预览图片
    THUMBNAIL("2", "商品缩略图"),
    CERTIFICATE("3", "质保图片");

    @Getter
    private String code;

    @Getter
    private String desc;

    PicTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<String, PicTypeEnum> codeToEnums = new HashMap<>();

    static {
        for (PicTypeEnum e : values()) {
            codeToEnums.put(e.code, e);
        }
    }

    public static PicTypeEnum of(String code) {
        PicTypeEnum e = codeToEnums.get(code);
        if (e == null) {
            throw new IllegalArgumentException("No such enum code: " + code);
        }
        return e;
    }
}
