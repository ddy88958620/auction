package com.trump.auction.trade.core.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trump.auction.reactor.api.AuctionContextFactory;
import com.trump.auction.reactor.api.model.AuctionContext;
import com.trump.auction.reactor.api.model.AuctionStatus;
import com.trump.auction.reactor.repository.BidRepository;
import com.trump.auction.trade.domain.AuctionInfo;
import com.trump.auction.trade.service.AuctionInfoService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuctionContextFactoryImpl implements AuctionContextFactory {
    @Autowired
    private BidRepository bidRepository;
    @Autowired
    private AuctionInfoService auctionInfoService;
    @Override
    public AuctionContext create(String auctionNo) {
        AuctionContext context = bidRepository.getContext(auctionNo);
        if (context != null ) {
            if(context.getBidCountDown()<5){
                context.setBidCountDown(10);
            }
            return context;
        }
        AuctionInfo auctionInfo = auctionInfoService.selectByPrimaryKey(Integer.valueOf(auctionNo));
        if (null == auctionInfo.getCountDown()) {
            auctionInfo.setCountDown(10);
        }
        if (null == auctionInfo.getStartPrice()) {
            auctionInfo.setStartPrice(new BigDecimal("0"));
        }
        if (null == auctionInfo.getIncreasePrice()) {
            auctionInfo.setIncreasePrice(new BigDecimal("0.1"));
        }
        if (null == auctionInfo.getTotalBidCount()) {
            auctionInfo.setTotalBidCount(0);
        }
        context = AuctionContext.create(auctionNo)
                .setLastBidder(null)
                .setBidCountDown(auctionInfo.getCountDown())
                .setExpectCount(auctionInfo.getFloorBidCount())
                .setLastPrice(auctionInfo.getStartPrice().add(auctionInfo.getIncreasePrice().multiply(new BigDecimal(auctionInfo.getTotalBidCount()))))
                .setStepPrice(auctionInfo.getIncreasePrice())
                .setTotalBidCount(auctionInfo.getTotalBidCount())
                .setValidBidCount(auctionInfo.getValidBidCount())
                .setStatus(2 == auctionInfo.getStatus() ? AuctionStatus.COMPLETE : AuctionStatus.ON);
        if(AuctionStatus.COMPLETE.equals(context.getStatus())){
            return context;
        }
        bidRepository.saveContextIfAbsent(context);
        return bidRepository.getContext(auctionNo);
    }
}
