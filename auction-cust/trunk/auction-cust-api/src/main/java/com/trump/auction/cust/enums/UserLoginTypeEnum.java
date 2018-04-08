package com.trump.auction.cust.enums;

/**
 * 登录类型枚举类
 * @author wangbo 2017/12/21.
 */
public enum UserLoginTypeEnum {
    LOGIN_TYPE_PHONE("Phone","手机号"),
    LOGIN_TYPE_WX("WX","微信"),
    LOGIN_TYPE_QQ("QQ","QQ"),
    LOGIN_TYPE_SMS("SMS","短信");
    private final String type;
    private final String name;

    UserLoginTypeEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
