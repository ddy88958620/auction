package com.trump.auction.back.constant;

/**
 * Created by Administrator on 2018/1/10.
 */
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 用户拍币充值订单类型
 *
 * @author wangYaMin
 */
public enum  AccountRechargeOrderEnum{

    WECHAT_TOP_UP(1, "微信充值"), ALIPAY_RECHARGE(2, "支付宝充值");


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

    AccountRechargeOrderEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String getTypeName(Integer type) {
        String name = null;
        for (AccountRechargeOrderEnum tmp : values()) {
            if (type.intValue() == tmp.getType().intValue()) {
                name = tmp.getName();
                break;
            }
        }
        return name;
    }

    public static Map<Integer, String> getAllType() {
        Map<Integer, String> map = new LinkedHashMap<Integer, String>();
        for (AccountRechargeOrderEnum tmp : values()) {
            map.put(tmp.getType(), tmp.getName());
        }
        return map;
    }

}