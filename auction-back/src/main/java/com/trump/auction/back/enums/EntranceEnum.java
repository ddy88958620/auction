package com.trump.auction.back.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangqingqiang
 *         分享活动入口类型
 */
public enum EntranceEnum {

    INDEX(1, "首页"), PRODUCT_DETAIL(2, "商品详情"), MY_INFO(3, "个人中心");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    EntranceEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, EntranceEnum> codeToEnums = new HashMap<Integer, EntranceEnum>();

    static {
        for (EntranceEnum e : values()) {
            codeToEnums.put(e.code, e);
        }
    }

    public static EntranceEnum of(Integer code) {
        EntranceEnum e = codeToEnums.get(code);
        if (e == null) {
            throw new IllegalArgumentException("No such enums code: " + code);
        }
        return e;
    }

    public static Map<Integer, EntranceEnum> getMap() {
        return codeToEnums;
    }
}
