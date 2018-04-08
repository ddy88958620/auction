package com.trump.auction.reactor.bid;

import com.trump.auction.reactor.api.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 委托单次出价
 *
 * @author Owen
 * @since 2018/1/16
 */
@Slf4j
@Component
public class DelegateBidHandle extends DefaultBidHandle implements BidHandler {

    @Override
    public boolean support(BidRequest bidRequest) {
        return BidType.DELEGATE.equals(bidRequest.getBidType());
    }

    @Override
    protected void checkBid(Bid bid, AuctionContext context) {
        context.checkBiddable(bid.getBidder());
    }
}
