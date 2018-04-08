package com.trump.auction.goods.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 罗显 on 2017/12/22.
 */
public enum InventoryTypeEnum {
    BACKSTAGEUPDATE(1,"后台修改"),SUBTRACTSTOCK(2,"出库"),ADDSTOCK(3,"入库");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    InventoryTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, InventoryTypeEnum> codeToEnums = new HashMap<>();

    static {
        for (InventoryTypeEnum e : values()) {
            codeToEnums.put(e.code, e);
        }
    }

    public static InventoryTypeEnum of(Integer code) {
        InventoryTypeEnum e = codeToEnums.get(code);
        if (e == null) {
            throw new IllegalArgumentException("No such enum code: " + code);
        }
        return e;
    }

}
