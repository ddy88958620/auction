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
public enum AuctionRuleStatusEnum {

    OPEN_STATUS("1","启用"),CLOSE_STATUS("2","禁用");

    @Getter
    private String code;

    @Getter
    private String desc;

    AuctionRuleStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<String, AuctionRuleStatusEnum> codeToEnums = new HashMap<String, AuctionRuleStatusEnum>();

    static {
        for (AuctionRuleStatusEnum e : values()) {
            codeToEnums.put(e.code, e);
        }
    }

    public static AuctionRuleStatusEnum of(Integer code) {
        AuctionRuleStatusEnum e = codeToEnums.get(code);
        if (e == null) {
            throw new IllegalArgumentException("No such enum code: " + code);
        }
        return e;
    }

    public static Map<String, String> getAllType() {
        Map<String, String> ALL_EXPRESSION = new LinkedHashMap<String, String>();
        for (AuctionRuleStatusEnum merchantStatus : values()) {
            ALL_EXPRESSION.put(merchantStatus.getCode(), merchantStatus.getDesc());
        }
        return ALL_EXPRESSION;
    }
}
