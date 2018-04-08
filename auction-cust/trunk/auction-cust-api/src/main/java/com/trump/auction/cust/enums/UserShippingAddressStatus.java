package com.trump.auction.cust.enums;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author: zhanping
 */
public enum UserShippingAddressStatus {
    //状态(0：启用，1：禁用，2：删除)',
    ENABLED(0, "启用"),
    DISABLED(1,"禁用"),
    DELETE(2,"删除");
    private final Integer type;
    private final String name;

    public Integer getType() {       return type;   }

    public String getName() {        return name;   }

    UserShippingAddressStatus(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String getTypeName(Integer type) {
        String name = null;
        for (UserShippingAddressStatus tmp : values()) {
            if (type.intValue() == tmp.getType().intValue()) {
                name = tmp.getName();
                break;
            }
        }
        return name;
    }

    public static Map<Integer, String> getAllType() {
        Map<Integer, String> map = new LinkedHashMap<Integer, String>();
        for (UserShippingAddressStatus tmp : values()) {
            map.put(tmp.getType(), tmp.getName());
        }
        return map;
    }
}
