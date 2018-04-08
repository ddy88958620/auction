package com.trump.auction.reactor.mock;

import com.trump.auction.reactor.api.BidCallback;
import com.trump.auction.reactor.api.model.BidHitResponse;
import com.trump.auction.reactor.api.model.BidResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 竞拍结果通知回调
 *
 * @author Owen
 * @since 2018/1/10
 */
@Slf4j
@Service
public class MockBidNotifyCallback implements BidCallback {

    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public void onBidden(BidResponse bidResult) {
        log.info("[bid {}] result = {}", resultString(bidResult), bidResult);
    }

    private String resultString(BidResponse bidResult) {
        return bidResult.isSuccess() ? "success" : "failed";
    }

    @Override
    public synchronized void onBidHit(BidHitResponse bidHitResponse) {
        log.info("[bid hit] result = {}", bidHitResponse);
        if (counter.incrementAndGet() > 100) {
            System.exit(-1);
        }

    }
}
