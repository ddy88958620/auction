package com.trump.auction.back.order.enums;

import lombok.Getter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by 罗显 on 2017/12/25.
 * 商家类型
 */
public enum  MerchantTypeEnum {

    THIRD_PARTY(0,"第三方"),CHANNEL(1,"渠道"),JD(2,"京东");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    MerchantTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, MerchantTypeEnum> codeToEnums = new HashMap<Integer, MerchantTypeEnum>();

    static {
        for (MerchantTypeEnum e : values()) {
            codeToEnums.put(e.code, e);
        }
    }

    public static MerchantTypeEnum of(Integer code) {
        MerchantTypeEnum e = codeToEnums.get(code);
        if (e == null) {
            throw new IllegalArgumentException("No such enum code: " + code);
        }
        return e;
    }

    public static Map<Integer, String> getAllType() {
        Map<Integer, String> ALL_EXPRESSION = new LinkedHashMap<Integer, String>();
        for (MerchantTypeEnum merchantType : values()) {
            ALL_EXPRESSION.put(merchantType.getCode(), merchantType.getDesc());
        }
        return ALL_EXPRESSION;
    }
}
