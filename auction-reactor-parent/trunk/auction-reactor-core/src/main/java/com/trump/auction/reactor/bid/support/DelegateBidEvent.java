package com.trump.auction.reactor.bid.support;

import com.trump.auction.reactor.api.model.AuctionContext;
import lombok.*;

/**
 * 委派出价事件
 *
 * @author Owen
 * @since 2018/1/10
 */
@ToString
@EqualsAndHashCode(of = {"auctionNo"})
public class DelegateBidEvent implements BidEvent {

    /**
     * 竞拍编号
     */
    @Getter @Setter private String auctionNo;
    /**
     * 竞拍倒计时
     */
    @Getter @Setter private int bidCountDown;

    private static final int DEFAULT_COUNT_DOWN = 10;

    public DelegateBidEvent(String auctionNo, int bidCountDown) {
        this.auctionNo = auctionNo;
        this.bidCountDown = bidCountDown;
    }

    public static DelegateBidEvent create(AuctionContext context) {
        return new DelegateBidEvent(context.getAuctionNo(), context.getBidCountDown());
    }

    public static DelegateBidEvent create(String auctionNo) {
        return new DelegateBidEvent(auctionNo, DEFAULT_COUNT_DOWN);
    }
}
