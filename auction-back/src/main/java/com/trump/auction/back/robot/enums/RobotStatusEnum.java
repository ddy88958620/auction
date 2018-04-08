package com.trump.auction.back.robot.enums;

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
public enum RobotStatusEnum {

    OPEN_STATUS("1","停用"),CLOSE_STATUS("2","启用");

    @Getter
    private String code;

    @Getter
    private String desc;

    RobotStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<String, RobotStatusEnum> codeToEnums = new HashMap<String, RobotStatusEnum>();

    static {
        for (RobotStatusEnum e : values()) {
            codeToEnums.put(e.code, e);
        }
    }

    public static RobotStatusEnum of(Integer code) {
        RobotStatusEnum e = codeToEnums.get(code);
        if (e == null) {
            throw new IllegalArgumentException("No such enum code: " + code);
        }
        return e;
    }

    public static Map<String, String> getAllType() {
        Map<String, String> ALL_EXPRESSION = new LinkedHashMap<String, String>();
        for (RobotStatusEnum merchantStatus : values()) {
            ALL_EXPRESSION.put(merchantStatus.getCode(), merchantStatus.getDesc());
        }
        return ALL_EXPRESSION;
    }
}
