package com.trump.auction.cust.enums;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by dingxp on 2017/12/22 0022.
 */
public enum UserTypeEnum {
    TYPE_USER(1, "普通用户"),
    TYPE_ROBOT(2,"机器人");
    private final Integer type;
    private final String name;

    public Integer getType() {       return type;   }

    public String getName() {        return name;   }

    UserTypeEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String getTypeName(Integer type) {
        String name = null;
        for (UserTypeEnum tmp : values()) {
            if (type.intValue() == tmp.getType().intValue()) {
                name = tmp.getName();
                break;
            }
        }
        return name;
    }

    public static Map<Integer, String> getAllType() {
        Map<Integer, String> map = new LinkedHashMap<Integer, String>();
        for (UserTypeEnum tmp : values()) {
            map.put(tmp.getType(), tmp.getName());
        }
        return map;
    }
}
