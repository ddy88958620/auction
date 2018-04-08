package com.trump.auction.back.order.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by 罗显 on 2017/12/26.
 */
public enum MerchantStatusEnum {

    OPEN_STATUS("0","启用"),CLOSE_STATUS("1","停用");

    @Getter
    private String code;

    @Getter
    private String desc;

    MerchantStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<String, MerchantStatusEnum> codeToEnums = new HashMap<String, MerchantStatusEnum>();

    static {
        for (MerchantStatusEnum e : values()) {
            codeToEnums.put(e.code, e);
        }
    }

    public static MerchantStatusEnum of(Integer code) {
        MerchantStatusEnum e = codeToEnums.get(code);
        if (e == null) {
            throw new IllegalArgumentException("No such enum code: " + code);
        }
        return e;
    }

    public static Map<String, String> getAllType() {
        Map<String, String> ALL_EXPRESSION = new LinkedHashMap<String, String>();
        for (MerchantStatusEnum merchantStatus : values()) {
            ALL_EXPRESSION.put(merchantStatus.getCode(), merchantStatus.getDesc());
        }
        return ALL_EXPRESSION;
    }
}
