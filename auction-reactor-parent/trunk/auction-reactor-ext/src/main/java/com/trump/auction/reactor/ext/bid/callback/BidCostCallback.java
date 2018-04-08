package com.trump.auction.reactor.ext.bid.callback;

import com.trump.auction.reactor.api.BidCallback;
import com.trump.auction.reactor.api.model.BidHitResponse;
import com.trump.auction.reactor.api.model.BidResponse;
import com.trump.auction.reactor.api.model.BidType;
import com.trump.auction.reactor.api.model.Bidder;
import com.trump.auction.reactor.ext.repository.BidCostRepository;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 出价消费累计计算
 * @author Owen
 * @since 2018/1/24
 */
@Component
public class BidCostCallback implements BidCallback, InitializingBean, DisposableBean, Ordered {

    @Autowired
    private BidCostRepository repository;

    private ExecutorService executor;

    @Override
    public void onBidden(BidResponse bidResult) {
        // 过滤出价失败的
        if (bidResult.isFailed()) {
            return;
        }

        // 过滤自动出价
        if (BidType.AUTO.equals(bidResult.getBidType())) {
            return;
        }

        if (Bidder.isAutoBidder(bidResult.getBidder())) {
            return;
        }

        executor.submit(() -> increaseCost(bidResult));
    }

    private void increaseCost(BidResponse bidResult) {
        repository.increase(bidResult.getAuctionNo(), bidResult.getBidder(), bidResult.getAccountCode());
    }

    @Override
    public void onBidHit(BidHitResponse bidHitResponse) {
        // empty method, do nothing
    }

    @Override
    public void destroy() throws Exception {
        if (executor == null || executor.isShutdown()) {
            return;
        }

        this.executor.shutdownNow();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.executor = Executors.newFixedThreadPool(1);
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}
