package com.trump.auction.cust.enums;

/**
 * @author wangbo 2018/1/3.
 */
public enum UserRechargeTypeEnum {
    NOT_RECHARGE(1,"未充值"),
    FIRST_RECHARGE(2,"首充"),
    MULTIPLE_RECHARGE(3,"多次"),
    FIRST_RECHARGE_WAIT(4,"首充返币等待中"),
    FIRST_RECHARGE_SUCCESS(5,"首充返币成功"),
    FIRST_RECHARGE_OVER(6,"首充拍币成功");
    private final Integer type;
    private final String name;

    UserRechargeTypeEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
