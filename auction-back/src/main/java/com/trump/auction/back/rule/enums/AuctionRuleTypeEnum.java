package com.trump.auction.back.rule.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 竞拍规则状态
 *
 * @author zhangliyan
 * @create 2018-01-03 14:25
 **/
public enum AuctionRuleTypeEnum {

    OPEN_STATUS("1","可以购买"),CLOSE_STATUS("2","不可以购买");

    @Getter
    private String code;

    @Getter
    private String desc;

    AuctionRuleTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<String, AuctionRuleTypeEnum> codeToEnums = new HashMap<String, AuctionRuleTypeEnum>();

    static {
        for (AuctionRuleTypeEnum e : values()) {
            codeToEnums.put(e.code, e);
        }
    }

    public static AuctionRuleTypeEnum of(Integer code) {
        AuctionRuleTypeEnum e = codeToEnums.get(code);
        if (e == null) {
            throw new IllegalArgumentException("No such enum code: " + code);
        }
        return e;
    }

    public static Map<String, String> getAllType() {
        Map<String, String> ALL_EXPRESSION = new LinkedHashMap<String, String>();
        for (AuctionRuleTypeEnum merchantStatus : values()) {
            ALL_EXPRESSION.put(merchantStatus.getCode(), merchantStatus.getDesc());
        }
        return ALL_EXPRESSION;
    }
}
