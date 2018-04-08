package com.trump.auction.back.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author:limingwu
 * @Description: 用户充值Enum
 * @Date: Create in 11:24 2018/3/20
 * @Modified By:
 */
public enum UserInfoRechargeEnum {
    NON_RECHARGE(1, "未充值"),
    RECHARGE(2, "充值");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    UserInfoRechargeEnum(Integer code, String desc){
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, UserInfoRechargeEnum> codeToEnums = new HashMap<>();

    static {
        for (UserInfoRechargeEnum e : values()) {
            codeToEnums.put(e.code, e);
        }
    }

    public static UserInfoRechargeEnum of(Integer code) {
        UserInfoRechargeEnum e = codeToEnums.get(code);
        if (e == null) {
            throw new IllegalArgumentException("No such enum code: " + code);
        }
        return e;
    }

    public static Map<Integer, String> getAllType() {
        Map<Integer, String> ALL_EXPRESSION = new LinkedHashMap<Integer, String>();
        for (UserInfoRechargeEnum userInfoRechargeEnum :values()){
            ALL_EXPRESSION.put(userInfoRechargeEnum.getCode(),userInfoRechargeEnum.getDesc());
        }
        return ALL_EXPRESSION;
    }
}
