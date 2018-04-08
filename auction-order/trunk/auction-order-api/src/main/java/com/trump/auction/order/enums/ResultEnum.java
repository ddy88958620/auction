package com.trump.auction.order.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: wangjian.
 * @date: 2017/12/20
 * @Description: 返回结果枚举.
 */
public enum ResultEnum {
    SAVE_SUCCESS("0000","保存成功"),
    SAVE_FAIL("0001","保存失败"),
    SAVE_ERROR("0002","保存异常"),
    PARAM_EXCEPTION("0003","参数异常"),
    STOCK_NOTENOUGH("0004","库存不足"),
    NULL_PRODUCT("0005","未查询到拍品信息"),
    NULL_ADDRESS("0007","未能获取地址信息"),
    UPDATE_ORDER_SUCCESS("0008","更新订单状态成功"),
    UPDATE_ORDER_FAIL("0009","更新订单状态失败"),
    REDUCE_STOCK_SUCCESS("000","扣库存成功"),
    REDUCE_STOCK_FAIL("0011","扣库存失败"),
    RESTORE_INVENTORY_FAIL("0012","还原库存失败"),
    UPDATE_ADDRESS_SUCCESS("0013","更新地址成功"),
    UPDATE_ADDRESS_FAIL("0014","更新地址失败"),
    ORDER_STATUS_NOTNULL("0015","订单状态不能为空"),
    ORDER_ID_NOTNULL("0016","订单号不能为空"),
    USER_ID_NOTNULL("0017","用户ID不能为空"),
    MERCHANT_ID_NOTNULL("0018","商家ID不能为空"),
    PRODUCT_ID_NOTNULL("0019","拍品ID不能为空"),
    PRODUCT_NUM_NOTNULL("0020","商品数量不能为空"),
    ORDER_TYPE_NOTNULL("0021","订单类型不能为空"),
    ADDRESS_ID_NOTNULL("0022","收货地址不能为空"),
    ORDER_AMOUNT_NOTNULL("0023","订单总金额不能为空"),
    PAID_MONEY_NOTNULL("0024","订单实付金额不能为空"),
    BID_TIMES_NOTNULL("0025","出价次数不能为空"),
    AUCTION_NO_NOTNULL("0026","拍品期数ID不能为空"),
    REDUCE_BUY_COIN_FAILED("0027","冻结用户购物币失败")
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
