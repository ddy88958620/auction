package com.trump.auction.back.push.enums;

import java.util.LinkedHashMap;
import java.util.Map;


public enum NotificationRecordTimeTypeEnum {
    //发送时间类型:  1: 立即发送  2:定时发送
    NOW(1, "立即发送"),
    TIMING(2,"定时发送");
    private final Integer type;
    private final String name;

    public Integer getType() {       return type;   }

    public String getName() {        return name;   }

    NotificationRecordTimeTypeEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String getTypeName(Integer type) {
        String name = null;
        for (NotificationRecordTimeTypeEnum tmp : values()) {
            if (type.intValue() == tmp.getType().intValue()) {
                name = tmp.getName();
                break;
            }
        }
        return name;
    }

    public static Map<Integer, String> getAllType() {
        Map<Integer, String> map = new LinkedHashMap<Integer, String>();
        for (NotificationRecordTimeTypeEnum tmp : values()) {
            map.put(tmp.getType(), tmp.getName());
        }
        return map;
    }
}
