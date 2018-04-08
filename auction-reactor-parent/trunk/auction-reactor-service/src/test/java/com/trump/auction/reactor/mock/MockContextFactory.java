package com.trump.auction.reactor.mock;

import com.trump.auction.reactor.api.model.AuctionContext;
import com.trump.auction.reactor.api.AuctionContextFactory;
import com.trump.auction.reactor.api.model.AuctionStatus;
import com.trump.auction.reactor.repository.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Mock auction context
 *
 * @author Owen
 * @since 2018/1/17
 */
@Service
public class MockContextFactory implements AuctionContextFactory {

    @Autowired
    private BidRepository bidRepository;

    @Override
    public AuctionContext create(String auctionNo) {
        AuctionContext context = bidRepository.getContext(auctionNo);
        if (context != null) {
            return context;
        }

        context = AuctionContext.create(auctionNo)
                .setLastBidder(null)
                .setBidCountDown(10)
                .setExpectCount(30)
                .setLastPrice(new BigDecimal("200"))
                .setStepPrice(new BigDecimal("2"))
                .setTotalBidCount(0)
                .setValidBidCount(0)
                .setStatus(AuctionStatus.ON);

        bidRepository.saveContextIfAbsent(context);
        return bidRepository.getContext(auctionNo);
    }
}
