package com.trump.auction.account.enums;

/**
 * Created by dingxp on 2017-12-27 0027.
 * 开心币状态
 */
public enum EnumBuyCoinStatus {

    STATUS_UNUSED(1, "未使用"), STATUS_SUR_USED(2, "部分使用"), STATUS_ALL_USED(3, "已使用"), STATUS_OVER(4, "已过期"),
    STATUS_DELETE(-1, "已删除"),//开心币多次使用，当执行取消订单时，删除最新开心币记录
    BUY_COIN_USING(5, "待使用")
    ;

    private final Integer status;
    private final String value;

    public Integer getStatus() {
        return status;
    }
    public String getValue() {
        return value;
    }
    EnumBuyCoinStatus(Integer status, String value) {
        this.status = status;
        this.value = value;
    }

    public static String getEnumBuyCoinStatusValue(Integer status) {
        String name = null;
        for (EnumBuyCoinStatus enumBuyCoinStatus : values()) {
            if (status.intValue() == enumBuyCoinStatus.getStatus().intValue()) {
                name = enumBuyCoinStatus.getValue();
                break;
            }
        }
        return name;
    }
}
