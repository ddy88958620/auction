package com.trump.auction.trade.core.impl;

import com.trump.auction.reactor.api.AuctionLifeCycle;
import com.trump.auction.reactor.api.BidService;
import com.trump.auction.reactor.api.model.BidRequest;
import com.trump.auction.reactor.api.model.BidType;
import com.trump.auction.trade.domain.AuctionInfo;
import com.trump.auction.trade.service.AuctionInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuctionLifeCycleImpl implements AuctionLifeCycle{
    @Autowired
    private BidService bidService;
    @Autowired
    private AuctionInfoService auctionInfoService;
    @Override
    public void onStart(String auctionId) {
        AuctionInfo auctionInfo=auctionInfoService.selectByPrimaryKey(Integer.valueOf(auctionId));
        BidRequest bidRequest=getBidRequest(auctionInfo);
        bidService.bid(bidRequest);
    }

    @Override
    public void onComplete(String s) {

    }

    /**
     * 封装调用参数我（单一出价）
     * @param
     * @return
     */
    private static BidRequest getBidRequest(AuctionInfo auctionInfo){
        //用户信息
        BidRequest  bidRequest=new BidRequest();
        bidRequest.setAuctionNo(String.valueOf(auctionInfo.getId()));
        bidRequest.setBidder(null);
        bidRequest.setBidType(BidType.AUTO);
        bidRequest.setBizNo(null);
        return  bidRequest;
    }
}
