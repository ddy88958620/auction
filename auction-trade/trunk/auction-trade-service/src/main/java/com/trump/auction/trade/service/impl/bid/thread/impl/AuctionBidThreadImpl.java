package com.trump.auction.trade.service.impl.bid.thread.impl;

import com.trump.auction.trade.service.impl.bid.thread.AuctionBidThread;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AuctionBidThreadImpl implements AuctionBidThread{


    @Override
    @Async
    public boolean saveBidInfos() {
        
        return false;
    }
}
