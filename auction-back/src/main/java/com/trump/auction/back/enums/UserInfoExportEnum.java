package com.trump.auction.back.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author:limingwu
 * @Description:
 * @Date: Create in 9:54 2018/3/28
 * @Modified By:
 */
public enum UserInfoExportEnum {
    ID(0, "用户Id"),
    UserFrom(1,"用户渠道"),
    UserPhone(2, "手机号"),
    WxNickName(3, "关联微信"),
    QqNickName(4, "关联QQ"),
    RechargeMoney(5, "首冲金额"),
    Coin1(6, "拍币金额"),
    Coin2(7, "赠币金额"),
    Coin3(8, "购物币金额"),
    Coin4(9, "积分金额"),
    Status(10, "状态"),
    RechargeType(11, "充值状态"),
    ProvinceNameAndCityName(12, "收货地址"),
    AddTime(13, "注册时间"),
    TerminalSign(14, "终端标识");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    UserInfoExportEnum(Integer code, String desc){
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, UserInfoExportEnum> codeToEnums = new HashMap<>();

    static {
        for (UserInfoExportEnum e : values()) {
            codeToEnums.put(e.code, e);
        }
    }

    public static UserInfoExportEnum of(Integer code) {
        UserInfoExportEnum e = codeToEnums.get(code);
        if (e == null) {
            throw new IllegalArgumentException("No such enum code: " + code);
        }
        return e;
    }

    public static Map<Integer, String> getAllType() {
        Map<Integer, String> ALL_EXPRESSION = new LinkedHashMap<Integer, String>();
        for (UserInfoExportEnum userInfoExportEnum :values()){
            ALL_EXPRESSION.put(userInfoExportEnum.getCode(),userInfoExportEnum.getDesc());
        }
        return ALL_EXPRESSION;
    }

}
