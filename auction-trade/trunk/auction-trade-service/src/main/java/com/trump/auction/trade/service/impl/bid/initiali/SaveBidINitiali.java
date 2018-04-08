package com.trump.auction.trade.service.impl.bid.initiali;

import com.trump.auction.trade.service.AuctionInfoService;
import com.trump.auction.trade.service.BidManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SaveBidINitiali implements CommandLineRunner {
    private   static final  Long isBid=1L;

    @Autowired
    private AuctionInfoService auctionInfoService;
    @Autowired
    private BidManagerService managerService;
    @Override
    public void run(String... strings) throws Exception {
        log.info("SaveBidINitiali  BID task ");


    }

}
