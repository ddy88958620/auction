package com.trump.auction.reactor.bid.rule;

import com.trump.auction.reactor.api.model.AuctionContext;
import com.trump.auction.reactor.api.model.Bidder;
import com.trump.auction.reactor.bid.BidConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * 自动出价连续次数规则
 *
 * @author Owen
 * @since 2018/1/10
 */
@Component
public class AutoBidTimesRule implements BidHitRule, Ordered {

    @Autowired
    private BidConfig bidConfig;

    @Override
    public boolean check(AuctionContext context) {
        if (Bidder.isAutoBidder(context.getLastBidder())) {
            return context.getAutoBidDurationTimes() >= bidConfig.autoBidTimesToHit();
        }

        return true;
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE + 1;
    }
}
