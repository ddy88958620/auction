package com.trump.auction.trade.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: zhangqingqiang.
 * @date: 2018/1/19 0019.
 * @Description: 期数表类型.
 */
public enum AuctionInfoStatus {

    AUCTIONING(1,"正在拍"),FINISHING(2,"已完结"),PREPARING(3,"未开始");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    AuctionInfoStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, AuctionInfoStatus> codeToEnums = new HashMap<Integer, AuctionInfoStatus>();

    static {
        for (AuctionInfoStatus e : values()) {
            codeToEnums.put(e.code, e);
        }
    }

    public static AuctionInfoStatus of(Integer code) {
        AuctionInfoStatus e = codeToEnums.get(code);
        if (e == null) {
            throw new IllegalArgumentException("No such enums code: " + code);
        }
        return e;
    }
}
