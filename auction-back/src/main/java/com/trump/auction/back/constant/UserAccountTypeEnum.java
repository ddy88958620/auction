package com.trump.auction.back.constant;

import java.util.LinkedHashMap;
import java.util.Map;

public enum UserAccountTypeEnum {

    AUCTION_COIN(1, "拍币"),
    PRESENT_COIN(2, "赠币"),
    POINTS(3, "积分"),
    BUY_COIN(4, "开心币");

    private Integer value;
    private String text;

    private UserAccountTypeEnum(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getText() {
        return this.text;
    }

    public String toString() {
        return this.value.toString();
    }
    public static Map<Integer, String> getAllType() {
        Map<Integer, String> ALL_EXPRESSION = new LinkedHashMap();
        UserAccountTypeEnum[] var4;
        int var3 = (var4 = values()).length;

        for(int var2 = 0; var2 < var3; ++var2) {
            UserAccountTypeEnum status = var4[var2];
            ALL_EXPRESSION.put(status.getValue(), status.getText());
        }

        return ALL_EXPRESSION;
    }

}
