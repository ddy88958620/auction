package com.trump.auction.reactor.api;

import com.trump.auction.reactor.api.model.Bidder;

/**
 * 出价人服务
 *
 * @author Owen
 * @since 2017/12/29
 */
public interface BidderService {

    Bidder nextBidder(Bidder lastBidder, String auctionNo);

    Bidder nextBidder(Bidder lastBidder);

    Bidder nextBidder();

}
