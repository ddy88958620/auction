package com.trump.auction.goods.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: zhangqingqiang.
 * @date 2017-12-22
 * @Description: 订单状态枚举.
 */
public enum ProductStatusEnum {
    NORMAL(0,"正常"),
    DELETED(999,"删除");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    ProductStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, ProductStatusEnum> codeToEnums = new HashMap<>();

    static {
        for (ProductStatusEnum e : values()) {
            codeToEnums.put(e.code, e);
        }
    }

    public static ProductStatusEnum of(Integer code) {
        ProductStatusEnum e = codeToEnums.get(code);
        if (e == null) {
            throw new IllegalArgumentException("No such enum code: " + code);
        }
        return e;
    }
}
