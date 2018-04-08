package com.trump.auction.goods.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: zhangqingqiang.
 * @date 2017-12-22
 * @Description: 订单状态枚举.
 */
public enum ProductManageStatusEnum {
    PRODUCT_ON(1,"上架"),
    PRODUCT_OFF(0,"下架");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    ProductManageStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, ProductManageStatusEnum> codeToEnums = new HashMap<>();

    static {
        for (ProductManageStatusEnum e : values()) {
            codeToEnums.put(e.code, e);
        }
    }

    public static ProductManageStatusEnum of(Integer code) {
        ProductManageStatusEnum e = codeToEnums.get(code);
        if (e == null) {
            throw new IllegalArgumentException("No such enum code: " + code);
        }
        return e;
    }
}
