package com.trump.auction.reactor.bid.support;

import com.trump.auction.reactor.api.model.AuctionContext;
import com.trump.auction.reactor.util.schedule.CircleCapableTimingWheel;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Junit test case of {@link BidTimer}
 *
 * @author Owen
 * @since 2018/1/23
 */
public class TestBidTimer {

    @Test
    public void test() {
        BidTimer<BidEvent> timer = new BidTimer<>();

        CircleCapableTimingWheel<BidEvent> wheel = new CircleCapableTimingWheel<>(timer.getTickDuration(), timer.getElapsedPerWheel() / timer.getTickDuration(), TimeUnit.MILLISECONDS);

        timer.setTimingWheel(wheel);
        timer.getTimingWheel().addExpirationListener(System.out :: println);
        timer.getTimingWheel().start();

        timer.add(DelegateBidEvent.create("123456"), 7, TimeUnit.SECONDS);
        AuctionContext context = AuctionContext.create("123456");
		AutoBidEvent autoEvent = new AutoBidEvent(context);
		 timer.add(autoEvent, 8, TimeUnit.SECONDS);
		 timer.add(autoEvent, 9, TimeUnit.SECONDS);
		 timer.add(autoEvent, 10, TimeUnit.SECONDS);
		
		 timer.add(DelegateBidEvent.create("123456"), 8, TimeUnit.SECONDS);
		 timer.add(DelegateBidEvent.create("123456"), 8, TimeUnit.SECONDS);
    }
}
