package com.trump.auction.reactor.bid;

import com.trump.auction.reactor.api.AuctionLifeCycle;
import com.trump.auction.reactor.api.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * {@link AuctionService} 默认实现
 *
 * @author Owen
 * @since 2018/1/22
 */
@Component
public class DefaultAuctionService implements AuctionService {

    @Autowired
    private List<AuctionLifeCycle> lifeCycles;

    @Override
    public void onStart(String auctionNo) {
        for (AuctionLifeCycle lifeCycle : lifeCycles) {
            lifeCycle.onStart(auctionNo);
        }
    }

    @Override
    public void onComplete(String auctionNo) {
        for (AuctionLifeCycle lifeCycle : lifeCycles) {
            lifeCycle.onComplete(auctionNo);
        }
    }
}
