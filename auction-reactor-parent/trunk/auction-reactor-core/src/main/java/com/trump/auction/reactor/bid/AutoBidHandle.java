package com.trump.auction.reactor.bid;

import com.trump.auction.reactor.api.BidderService;
import com.trump.auction.reactor.api.model.*;
import com.trump.auction.reactor.api.exception.BidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 自动出价处理
 *
 * @author Owen
 * @since 2018/1/16
 */
@Slf4j
@Component
public class AutoBidHandle extends DefaultBidHandle implements BidHandler {

    @Autowired
    private BidderService bidderService;

    @Override
    public boolean support(BidRequest bidRequest) {
        return BidType.AUTO.equals(bidRequest.getBidType());
    }

    @Override
    protected void checkBid(Bid bid, AuctionContext context) {
        // 判断竞拍是否结束
        if (context.isComplete()) {
            throw BidException.auctionCompleted();
        }

        // 判断最后出价是否符合预期
        if (bid.getExpectLastPrice() != null && !context.isLastPrice(bid.getExpectLastPrice())) {
            throw BidException.lastPriceExpired();
        }
    }

    protected BidResponse handleRequest0(BidRequest bidRequest, AuctionContext context) {
        if (bidRequest.getBidder() == null) {
            bidRequest.setBidder(bidderService.nextBidder(context.getLastBidder(), context.getAuctionNo()));
        }

        return super.handleRequest0(bidRequest, context);
    }
}
