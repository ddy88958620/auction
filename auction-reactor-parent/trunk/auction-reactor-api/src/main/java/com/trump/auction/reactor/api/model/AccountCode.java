package com.trump.auction.reactor.api.model;

import java.util.EnumSet;
import java.util.Optional;

/**
 * 账户类别
 * <p>
 *     枚举定义的先后顺序代表了出价扣币的先后顺序
 * </p>
 * @author Owen
 * @since 2018/1/24
 */
public enum AccountCode {

    /**
     * 赠币
     */
    FREE("2", false),
    /**
     * 拍币
     */
    YIPPE("1", false);

    private String val;
    private boolean invalidBid;

    AccountCode(String code, boolean invalidBid) {
        this.val = code;
        this.invalidBid = invalidBid;
    }

    public final String val() {
        return this.val;
    }

    /**
     * 是否无效出价
     */
    public final boolean isInvalidBid() {
        return this.invalidBid;
    }

    public static EnumSet<AccountCode> ALL_OF = EnumSet.allOf(AccountCode.class);

    public static Optional<AccountCode> of(String val) {
        for (AccountCode accountCode : ALL_OF) {
            if (accountCode.val.equals(val)) {
                return Optional.of(accountCode);
            }
        }

        return Optional.empty();
    }


}
