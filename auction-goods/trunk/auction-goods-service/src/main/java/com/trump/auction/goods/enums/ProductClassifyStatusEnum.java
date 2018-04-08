package com.trump.auction.goods.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: liuxueshen.
 * @date 2017-12-22
 * @Description: 商品分类.
 */
public enum ProductClassifyStatusEnum {
    NORMAL(0,"启用"),
    DELETED(1,"删除"),
    DISABLED(2,"禁用");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    ProductClassifyStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, ProductClassifyStatusEnum> codeToEnums = new HashMap<>();

    static {
        for (ProductClassifyStatusEnum e : values()) {
            codeToEnums.put(e.code, e);
        }
    }

    public static ProductClassifyStatusEnum of(Integer code) {
        ProductClassifyStatusEnum e = codeToEnums.get(code);
        if (e == null) {
            throw new IllegalArgumentException("No such enum code: " + code);
        }
        return e;
    }
}
