package com.trump.auction.trade.service.impl.bid.initiali;

import com.trump.auction.reactor.api.AuctionService;
import com.trump.auction.trade.domain.AuctionInfo;
import com.trump.auction.trade.service.AuctionInfoService;
import com.trump.auction.trade.util.ConstantUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
@Slf4j
@Component
public class BidInitiali implements CommandLineRunner {
    @Autowired
    private AuctionInfoService auctionInfoService;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AuctionService auctionService;
    @Override
    public void run(String... strings) throws Exception {
        log.info("BidInitiali RobotBidTimes BID task ");
        List<AuctionInfo> auctionInfoList=auctionInfoService.findAuctionInfoStatus(ConstantUtil.AUCTION_TRADE_STATUS_ONE);
        for(AuctionInfo auctionInfo:auctionInfoList){
            if(null!=auctionInfo.getStartTime() && auctionInfo.getStartTime().getTime()<=System.currentTimeMillis()){
                auctionService.onStart(String.valueOf(auctionInfo.getId()));
            }
        }
    }




}
