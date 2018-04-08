package com.trump.auction.activity.enums;

import java.util.LinkedHashMap;
import java.util.Map;

public enum EnumLotteryPrizeType {

    TYPE_GOODS(100, "实物商品"),
    TYPE_VIRTUAL_COINS(200, "虚拟财产"),
    TYPE_VIDEO_CDKEYS(300, "视频会员"),

    TYPE_GOODS_SUB(101, "实物商品"),

    TYPE_POINTS(201, "积分"),
    TYPE_PRESENT_COIN(202, "赠币"),

    TYPE_CDKEY_IQIY_MONTH(301, "爱奇艺月卡");

    /*** 值 */
    private final Integer type;
    /*** 名称 */
    private final String name;

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    EnumLotteryPrizeType(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public static Map<Integer, String> getAllType() {
        Map<Integer, String> allType = new LinkedHashMap<Integer, String>();
        allType.put(100, "实物商品");
        allType.put(200, "虚拟财产");
        allType.put(300, "视频会员");
        return allType;
    }

    public static Map<Integer, String> getAllGoodsType() {
        Map<Integer, String> allGoodsType = new LinkedHashMap<Integer, String>();
        allGoodsType.put(101, "实物商品");
        return allGoodsType;
    }

    public static Map<Integer,String> getAllVirtualCoinsType(){
        Map<Integer,String> allVirtualCoinsType  = new LinkedHashMap<Integer, String>();
        allVirtualCoinsType.put(201, "积分");
        allVirtualCoinsType.put(202, "赠币");
        return allVirtualCoinsType;
    }

    public static Map<Integer,String> getAllVideoCdkeysType(){
        Map<Integer,String> allVideoCdkeysType  = new LinkedHashMap<Integer, String>();
        allVideoCdkeysType.put(301, "爱奇艺月卡");
        return allVideoCdkeysType;
    }
}