package com.trump.auction.back.enums;

import com.trump.auction.back.rule.enums.AuctionRuleStatusEnum;
import lombok.Getter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author hanliangliang 2018-1-29
 * @Description: 渠道来源状态.
 */
public enum ChannelSourceEnum {
    OK(0,"正常"),DELETED(1,"删除");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    ChannelSourceEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, ChannelSourceEnum> codeToEnums = new HashMap<>();

    static {
        for (ChannelSourceEnum e : values()) {
            codeToEnums.put(e.code, e);
        }
    }

    public static ChannelSourceEnum of(Integer code) {
        ChannelSourceEnum e = codeToEnums.get(code);
        if (e == null) {
            throw new IllegalArgumentException("No such enum code: " + code);
        }
        return e;
    }

    public static Map<Integer, String> getAllType() {
        Map<Integer, String> ALL_EXPRESSION = new LinkedHashMap<Integer, String>();
        for (ChannelSourceEnum channelSourceEnum :values()){
            ALL_EXPRESSION.put(channelSourceEnum.getCode(),channelSourceEnum.getDesc());
        }
        return ALL_EXPRESSION;
    }
}
