package com.trump.auction.reactor.api.model;

import lombok.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

/**
 * 委托多次出价请求
 *
 * @author Owen
 * @since 2017/12/29
 */
@ToString
@EqualsAndHashCode(of = {"auctionNo", "bidder", "bizNo"})
public class MultiBidRequest implements Serializable {

    /**
     * 竞拍编号
     */
    @Getter @Setter private String auctionNo;
    /**
     * 竞拍用户
     */
    @Getter @Setter private Bidder bidder;
    /**
     * 业务流水
     */
    @Getter @Setter private String bizNo;
    /**
     * 消费明细
     */
    @Getter private Set<BidCostDetail> costDetails = new TreeSet<>();

    /**
     * 添加消费明细
     */
    public MultiBidRequest withCostDetail(@NonNull BidCostDetail costDetail) {
        costDetails.add(costDetail);
        return this;
    }

    public void setCostDetails(Set<BidCostDetail> costDetails) {
        asserts(!isEmpty(costDetails), "[costDetails]");

        this.costDetails.addAll(costDetails);
    }

    private void asserts(boolean truth, String message) {
        if (!truth) {
            throw new IllegalArgumentException(message);
        }
    }

    private <E> boolean isEmpty(Collection<E> collection) {
        return collection == null || collection.isEmpty();
    }

    public Integer getBidCount() {
        int count = 0;
        for (BidCostDetail costDetail : costDetails) {
            count += costDetail.getCount();
        }

        return count;
    }
}
