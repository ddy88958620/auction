package com.trump.auction.reactor.api.model;

import lombok.*;

import java.io.Serializable;

/**
 * 出价消费明细
 *
 * @author Owen
 * @since 2018/2/1
 */
@ToString
@EqualsAndHashCode(of = {"accountCode"})
public class BidCostDetail implements Serializable, Comparable<BidCostDetail> {

    @Getter private AccountCode accountCode;

    @Getter @Setter private int count;

    public BidCostDetail(@NonNull AccountCode accountCode, int count) {
        this.accountCode = accountCode;
        this.count = count;
    }

    public void setAccountCode(@NonNull AccountCode accountCode) {
        this.accountCode = accountCode;
    }

    @Override
    public int compareTo(BidCostDetail o) {
        return this.accountCode.compareTo(o.accountCode);
    }
}
