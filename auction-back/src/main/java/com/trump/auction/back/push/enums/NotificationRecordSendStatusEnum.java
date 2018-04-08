package com.trump.auction.back.push.enums;

import java.util.LinkedHashMap;
import java.util.Map;


public enum NotificationRecordSendStatusEnum {
    //推送状态:  1: 进行中  2:已完成
    ING(1, "进行中"),
    ED(2,"已完成");
    private final Integer type;
    private final String name;

    public Integer getType() {       return type;   }

    public String getName() {        return name;   }

    NotificationRecordSendStatusEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String getTypeName(Integer type) {
        String name = null;
        for (NotificationRecordSendStatusEnum tmp : values()) {
            if (type.intValue() == tmp.getType().intValue()) {
                name = tmp.getName();
                break;
            }
        }
        return name;
    }

    public static Map<Integer, String> getAllType() {
        Map<Integer, String> map = new LinkedHashMap<Integer, String>();
        for (NotificationRecordSendStatusEnum tmp : values()) {
            map.put(tmp.getType(), tmp.getName());
        }
        return map;
    }
}
