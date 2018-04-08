package com.trump.auction.trade.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: .
 * @date: 2017/11/9 0009.
 * @Description: 拍品状态枚举.
 */
public enum AuctionProductStatusEnum {
    AUCTIONINT(1,"开拍中"),
    PREPARING(2,"准备中"),
    TIMING(3,"定时"),
    OFF(4,"下架"),
    DELETE(6,"删除")
    ;
    @Getter
    private Integer code;

    @Getter
    private String desc;

    AuctionProductStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
