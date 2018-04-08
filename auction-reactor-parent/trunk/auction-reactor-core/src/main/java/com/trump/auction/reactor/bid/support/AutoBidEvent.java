package com.trump.auction.reactor.bid.support;

import com.trump.auction.reactor.api.model.AuctionContext;
import lombok.*;

import java.math.BigDecimal;

/**
 * 自动出价事件
 *
 * @author Owen
 * @since 2018/1/10
 */
@ToString
@EqualsAndHashCode(of = {"auctionNo"})
@NoArgsConstructor
public class AutoBidEvent implements BidEvent {

    /**
     * 竞拍编号
     */
    @Getter @Setter private String auctionNo;

    /**
     * 期望最后出价
     */
    @Getter @Setter private BigDecimal expectLastPrice;

    public AutoBidEvent(AuctionContext context) {
        this.auctionNo = context.getAuctionNo();
        this.expectLastPrice = context.getLastPrice();
    }
}
