package com.trump.auction.reactor.api;

import com.trump.auction.reactor.api.model.AuctionContext;

/**
 * {@link AuctionContext} 工厂.
 *
 * @author Owen
 * @since 2018/1/9
 */
public interface AuctionContextFactory {

    /**
     * Create {@link AuctionContext}
     * @param auctionNo auction NO.
     * @return {@link AuctionContext} instance
     */
    AuctionContext create(String auctionNo);
}
