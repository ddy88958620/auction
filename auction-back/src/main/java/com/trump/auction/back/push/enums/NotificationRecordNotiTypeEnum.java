package com.trump.auction.back.push.enums;

import java.util.LinkedHashMap;
import java.util.Map;


public enum NotificationRecordNotiTypeEnum {
    //消息类型:  1: 链接  2:活动 3:拍品
    URL(1, "链接"),
    ACTIVITY(2,"活动"),
    PRODUCT(3,"拍品");
    private final Integer type;
    private final String name;

    public Integer getType() {       return type;   }

    public String getName() {        return name;   }

    NotificationRecordNotiTypeEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String getTypeName(Integer type) {
        String name = null;
        for (NotificationRecordNotiTypeEnum tmp : values()) {
            if (type.intValue() == tmp.getType().intValue()) {
                name = tmp.getName();
                break;
            }
        }
        return name;
    }

    public static Map<Integer, String> getAllType() {
        Map<Integer, String> map = new LinkedHashMap<Integer, String>();
        for (NotificationRecordNotiTypeEnum tmp : values()) {
            map.put(tmp.getType(), tmp.getName());
        }
        return map;
    }
}
