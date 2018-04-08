package com.trump.auction.reactor.bid.rule;

import com.trump.auction.reactor.api.model.AuctionContext;
import com.trump.auction.reactor.api.model.Bidder;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * 有效出价次数规则
 *
 * @author Owen
 * @since 2018/1/10
 */
@Component
public class BidCountRule implements BidHitRule, Ordered {

    @Override
    public boolean check(AuctionContext context) {
        if (Bidder.isAutoBidder(context.getLastBidder())) {
            return context.getTotalBidCount() >= context.getExpectCount();
        }

        return context.getValidBidCount() >= context.getExpectCount();
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}
