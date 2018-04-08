package com.trump.auction.back.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangqingqiang
 * 拍品类型
 */
public enum AuctionProductEnum {

    AUCTIONING(1,"开拍中"),PREPARING(2,"准备中"),TIMING(3,"定时"),OFFSHELF(4,"下架"),DELETE(6,"删除");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    AuctionProductEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, AuctionProductEnum> codeToEnums = new HashMap<Integer, AuctionProductEnum>();

    static {
        for (AuctionProductEnum e : values()) {
            codeToEnums.put(e.code, e);
        }
    }

    public static AuctionProductEnum of(Integer code) {
        AuctionProductEnum e = codeToEnums.get(code);
        if (e == null) {
            throw new IllegalArgumentException("No such enums code: " + code);
        }
        return e;
    }
}
