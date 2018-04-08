package com.trump.auction.cust.enums;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author: zhanping
 */
public enum UserShippingAddressType {
    DEFAULT(0, "默认地址"),
    OTHER(1,"其他地址");
    private final Integer type;
    private final String name;

    public Integer getType() {       return type;   }

    public String getName() {        return name;   }

    UserShippingAddressType(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String getTypeName(Integer type) {
        String name = null;
        for (UserShippingAddressType tmp : values()) {
            if (type.intValue() == tmp.getType().intValue()) {
                name = tmp.getName();
                break;
            }
        }
        return name;
    }

    public static Map<Integer, String> getAllType() {
        Map<Integer, String> map = new LinkedHashMap<Integer, String>();
        for (UserShippingAddressType tmp : values()) {
            map.put(tmp.getType(), tmp.getName());
        }
        return map;
    }
}
