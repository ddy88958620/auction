package com.trump.auction.back.appraises.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by wanglei on 2017/12/26.
 */

public enum AppraiseStatusEnum {

	NOT_AUDITED("1","待审核"),
	AUDITED("2","已审核");

    @Getter
    private String code;

    @Getter
    private String desc;

    AppraiseStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<String, AppraiseStatusEnum> codeToEnums = new HashMap<String, AppraiseStatusEnum>();

    static {
        for (AppraiseStatusEnum e : values()) {
            codeToEnums.put(e.code, e);
        }
    }

    public static AppraiseStatusEnum of(Integer code) {
        AppraiseStatusEnum e = codeToEnums.get(code);
        if (e == null) {
            throw new IllegalArgumentException("No such enum code: " + code);
        }
        return e;
    }

    public static Map<String, String> getAllType() {
        Map<String, String> ALL_EXPRESSION = new LinkedHashMap<String, String>();
        for (AppraiseStatusEnum merchantStatus : values()) {
            ALL_EXPRESSION.put(merchantStatus.getCode(), merchantStatus.getDesc());
        }
        return ALL_EXPRESSION;
    }
}
