package com.trump.auction.trade.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.trump.auction.trade.api.AuctionBidStubService;
import com.trump.auction.trade.model.BidParam;
import com.trump.auction.trade.model.BidResult;
import com.trump.auction.trade.model.UserBidModel;
import com.trump.auction.trade.service.AuctionBidService;
import com.trump.auction.trade.service.AuctionBidSystemService;
import org.springframework.beans.factory.annotation.Autowired;
@Service(version = "1.0.0")
public class AuctionBidStubServiceImpl implements AuctionBidStubService {

    @Autowired
    private AuctionBidSystemService auctionBidSystemService;
    @Autowired
    private AuctionBidService bidService;

    @Override
    public BidResult bidOperation(BidParam bidParam) {
        return  auctionBidSystemService.bidOperation(bidParam);
    }

    @Override
    public BidResult upADetailOrderStatus(Integer userId,Integer auctioId) {
        return bidService.upADetailOrderStatus(userId,auctioId);
    }

    @Override
    public UserBidModel userBidInfo(Integer userId, Integer auctioId) {
        return bidService.userBidInfo(userId,auctioId);
    }

    @Override
    public BidResult updCollectCount(Integer auctionId) {
        return bidService.updCollectCount(auctionId);
    }

    @Override
    public BidResult CancelCollectCount(Integer auctionId) {
        return bidService.CancelCollectCount(auctionId);
    }

    @Override
    public BidResult updPageViewCount(Integer auctionId) {

        return bidService.updPageViewCount(auctionId);
    }

}
