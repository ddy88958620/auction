package com.trump.auction.reactor.api.model;

import lombok.*;

import java.io.Serializable;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

/**
 * 多次出价
 *
 * @author Owen
 * @since 2018/1/12
 */
@ToString
@EqualsAndHashCode(of = {"auctionNo", "bidder", "bizNo"})
public class MultiBid implements Serializable {

    /**
     * 竞拍编号
     */
    @Getter @Setter private String auctionNo;
    /**
     * 竞拍用户
     */
    @Getter @Setter private Bidder bidder;
    /**
     * 出价次数
     */
    @Getter @Setter private int bidCount;
    /**
     * 剩余出价次数
     */
    @Getter @Setter private int leftBidCount;
    /**
     * 业务流水
     * <p>
     *     原样返回
     * </p>
     */
    @Getter @Setter private String bizNo;
    
    /**
     * 消费明细
     */
    @Getter private Set<BidCostDetail> costDetails = new TreeSet<>();

    @Getter private long timestamp;

    public MultiBid() {
        this.timestamp = System.currentTimeMillis();
    }

    // 已完成出价次数
    public int biddenCount() {
        return bidCount - leftBidCount;
    }

    // 判断是否还有剩余出价次数
    public boolean remainBidCount() {
        return leftBidCount > 0;
    }

    // 递减剩余出价次数并返回本次出价币种类型
    public AccountCode decreaseBidCount() {
        Optional<BidCostDetail> optional = nextBidCostDetail();

        if (!optional.isPresent()) {
            throw new IllegalStateException("[bid count spent]");
        }

        BidCostDetail costDetail = optional.get();
        costDetail.setCount(costDetail.getCount() - 1);

        leftBidCount -= 1;

        return costDetail.getAccountCode();
    }

    private Optional<BidCostDetail> nextBidCostDetail() {
        for (BidCostDetail costDetail : costDetails) {
            if (costDetail.getCount() <= 0) {
                continue;
            }

            return Optional.of(costDetail);
        }

        return Optional.empty();
    }

    public void setCostDetails(Set<BidCostDetail> costDetails) {
        this.costDetails.addAll(costDetails);
    }

}
