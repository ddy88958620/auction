package com.trump.auction.goods.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: zhangqingqiang.
 * @date: 2017/11/9 0009.
 * @Description: 返回结果枚举.
 */
public enum ResultEnum {
    SAVE_SUCCESS("200","保存成功"),
    SAVE_FAIL("-1","保存失败"),
    SAVE_ERROR("-1","保存异常"),
    NULL_PRODUCT("-1","请选择商品"),
    PRODUCT_EXIST("-1","商品已存在"),
    DELETE_PRODUCT_SUCCESS("200","删除商品成功"),
    DELETE_PRODUCT_FAIL("-1","删除商品失败"),
    UPDATE_PRODUCT_SUCCESS("200","修改商品信息成功"),
    UPDATE_PRODUCT_FAIL("-1","修改商品信息失败")
    ;

    @Getter
    private String code;

    @Getter
    private String desc;

    @Override
    public String toString() {
        return code;
    }

    ResultEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<String, ResultEnum> codeToEnums = new HashMap<>();

    static {
        for (ResultEnum e : values()) {
            codeToEnums.put(e.code, e);
        }
    }

    public static ResultEnum of(String code) {
        ResultEnum e = codeToEnums.get(code);
        if (e == null) {
            throw new IllegalArgumentException("No such enum code: " + code);
        }
        return e;
    }
}
