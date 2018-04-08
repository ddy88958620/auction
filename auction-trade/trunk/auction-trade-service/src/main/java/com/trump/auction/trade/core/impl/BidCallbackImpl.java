package com.trump.auction.trade.core.impl;

import com.trump.auction.reactor.api.BidCallback;
import com.trump.auction.reactor.api.model.BidHitResponse;
import com.trump.auction.reactor.api.model.BidResponse;
import com.trump.auction.trade.service.BidManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BidCallbackImpl implements BidCallback{


    @Autowired
    private BidManagerService  bidManagerService;
    /**
     * 出价回调
     * @param bidResponse
     */
    @Override
    public void onBidden(BidResponse bidResponse) {

        bidManagerService.bidHandle(bidResponse);
    }
    /**
     * 出价成功回调
     * @param
     */
    @Override
    public void onBidHit(BidHitResponse bidHitResponse) {

        bidManagerService.bidFinish(bidHitResponse);
    }





}
