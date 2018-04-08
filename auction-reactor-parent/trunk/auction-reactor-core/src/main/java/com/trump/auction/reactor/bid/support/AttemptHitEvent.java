package com.trump.auction.reactor.bid.support;

import com.trump.auction.reactor.api.model.Bidder;
import com.trump.auction.reactor.api.model.AuctionContext;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 尝试拍中事件
 *
 * @author Owen
 * @since 2018/1/10
 */
@ToString
@NoArgsConstructor
public class AttemptHitEvent implements BidEvent {

    /**
     * 竞拍编号
     */
    @Getter @Setter private String auctionNo;

    /**
     * 期望最后出价
     */
    @Getter @Setter private BigDecimal lastPrice;

    /**
     * 期望最后出价人
     */
    @Getter @Setter private Bidder lastBidder;

    public AttemptHitEvent(AuctionContext context) {
        this.auctionNo = context.getAuctionNo();
        this.lastBidder = context.getLastBidder();
        this.lastPrice = context.getLastPrice();
    }
}
