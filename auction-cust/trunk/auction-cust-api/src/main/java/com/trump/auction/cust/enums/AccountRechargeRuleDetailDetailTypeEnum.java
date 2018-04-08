package com.trump.auction.cust.enums;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author: zhanping
 */
public enum AccountRechargeRuleDetailDetailTypeEnum {
    //送币方式(1,不送，2.百分百，3.固定金额)
    DO_NOT_SEND(1, "不返币"),
    ONE_HUNDRED_PERCENT(2,"百分比返币"),
    FIXED_AMOUNT(3,"固定金额返币");
    private final Integer type;
    private final String name;

    public Integer getType() {       return type;   }

    public String getName() {        return name;   }

    AccountRechargeRuleDetailDetailTypeEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String getTypeName(Integer type) {
        String name = null;
        for (AccountRechargeRuleDetailDetailTypeEnum tmp : values()) {
            if (type.intValue() == tmp.getType().intValue()) {
                name = tmp.getName();
                break;
            }
        }
        return name;
    }

    public static Map<Integer, String> getAllType() {
        Map<Integer, String> map = new LinkedHashMap<Integer, String>();
        for (AccountRechargeRuleDetailDetailTypeEnum tmp : values()) {
            map.put(tmp.getType(), tmp.getName());
        }
        return map;
    }
}
