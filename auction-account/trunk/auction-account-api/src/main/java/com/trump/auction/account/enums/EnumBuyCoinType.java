package com.trump.auction.account.enums;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by dingxp on 2017-12-26 0027.
 * 开心币类型
 */
public enum  EnumBuyCoinType {
    BUY_COIN_PRODUCT(1,"指定商品"),BUY_COIN_ALL(2,"全场商品");

    private final Integer type;
    private final String name;

    public Integer getType() {
        return type;
    }
    public String getName() {
        return name;
    }
    EnumBuyCoinType(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String getEnumBuyCoinTypeName(Integer type) {
        String name = null;
        for (EnumBuyCoinType enumBuyCoinType : values()) {
            if (type.intValue() == enumBuyCoinType.getType().intValue()) {
                name = enumBuyCoinType.getName();
                break;
            }
        }
        return name;
    }
    public static Map<Integer, String> getAllType() {
        Map<Integer, String> allType = new LinkedHashMap<Integer, String>();
        for (EnumBuyCoinType enumBuyCoinType : values()) {
            allType.put(enumBuyCoinType.type,enumBuyCoinType.name);
        }
        return allType;
    }
}
