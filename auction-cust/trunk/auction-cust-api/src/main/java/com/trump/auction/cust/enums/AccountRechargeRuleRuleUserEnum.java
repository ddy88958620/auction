package com.trump.auction.cust.enums;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author: zhanping
 */
public enum AccountRechargeRuleRuleUserEnum {
    //规则用户(1,全部用户，2，首充用户)
    ALL_USER(1, "全部用户"),
    FIRST_USER(2,"首充用户");
    private final Integer type;
    private final String name;

    public Integer getType() {       return type;   }

    public String getName() {        return name;   }

    AccountRechargeRuleRuleUserEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String getTypeName(Integer type) {
        String name = null;
        for (AccountRechargeRuleRuleUserEnum tmp : values()) {
            if (type.intValue() == tmp.getType().intValue()) {
                name = tmp.getName();
                break;
            }
        }
        return name;
    }

    public static Map<Integer, String> getAllType() {
        Map<Integer, String> map = new LinkedHashMap<Integer, String>();
        for (AccountRechargeRuleRuleUserEnum tmp : values()) {
            map.put(tmp.getType(), tmp.getName());
        }
        return map;
    }
}
