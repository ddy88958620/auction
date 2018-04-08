package com.trump.auction.trade.core.impl;

import com.trump.auction.reactor.api.AuctionCallback;
import com.trump.auction.trade.domain.AuctionInfo;
import com.trump.auction.trade.service.AuctionBidService;
import com.trump.auction.trade.service.AuctionInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AuctionCallbackImpl implements AuctionCallback {

    @Autowired
    private AuctionInfoService auctionInfoService;
    @Override
    public List<String> getAll() {
        List<String> ids=auctionInfoService.findIds(1);
        return  ids;
    }


}
