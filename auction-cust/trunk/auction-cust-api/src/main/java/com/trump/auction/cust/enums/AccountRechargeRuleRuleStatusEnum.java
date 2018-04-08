package com.trump.auction.cust.enums;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author: zhanping
 */
public enum  AccountRechargeRuleRuleStatusEnum {
    //规则状态(0，关闭；1，开启)
    CLOSE(0, "关闭"),
    OPEN(1,"开启");
    private final Integer type;
    private final String name;

    public Integer getType() {       return type;   }

    public String getName() {        return name;   }

    AccountRechargeRuleRuleStatusEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String getTypeName(Integer type) {
        String name = null;
        for (AccountRechargeRuleRuleStatusEnum tmp : values()) {
            if (type.intValue() == tmp.getType().intValue()) {
                name = tmp.getName();
                break;
            }
        }
        return name;
    }

    public static Map<Integer, String> getAllType() {
        Map<Integer, String> map = new LinkedHashMap<Integer, String>();
        for (AccountRechargeRuleRuleStatusEnum tmp : values()) {
            map.put(tmp.getType(), tmp.getName());
        }
        return map;
    }
}
