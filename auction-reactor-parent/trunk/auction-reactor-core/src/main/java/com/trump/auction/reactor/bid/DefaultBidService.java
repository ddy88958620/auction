package com.trump.auction.reactor.bid;

import com.trump.auction.reactor.api.AuctionService;
import com.trump.auction.reactor.api.BidCallback;
import com.trump.auction.reactor.api.BidService;
import com.trump.auction.reactor.api.model.*;
import com.trump.auction.reactor.api.exception.BidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

/**
 * {@link BidService} 默认实现
 *
 * @author Owen
 * @since 2018/1/9
 */
@Slf4j
@Service
public class DefaultBidService implements BidService, InitializingBean, DisposableBean {

    private ExecutorService executor;

    @Autowired
    private List<BidCallback> bidCallbacks;

    @Autowired
    private List<BidHandler> bidHandlers;

    @Autowired
    private BidQueueHandler bidQueueHandler;

    @Autowired
    private AuctionService auctionService;

    @Value("${auction.bid.thread-count:16}")
    private int bidThreadCount;

    @Override
    public Future<BidResponse> bid(BidRequest bidRequest) {
        if (isShutdown()) {
            throw new RuntimeException("Bid executor has already shutdown.");
        }

        return executor.submit(() -> bid0(bidRequest));
    }

    @Override
    public BidResponse bid(BidRequest bidRequest, boolean sync) {
        if (sync) {
            return bid0(bidRequest);
        }

        Future<BidResponse> future = executor.submit(() -> bid0(bidRequest));

        try {
            return future.get();
        } catch (Throwable e) {
            throw BidException.defaultError(e);
        }
    }

    @Override
    public void bid(MultiBidRequest bidRequest) {
        log.info("[Multi bid], request:{}", bidRequest);
        bidQueueHandler.join(bidRequest);
    }

    private BidResponse bid0(BidRequest bidRequest) {
        Assert.notNull(bidRequest, "[bidRequest]");

        Optional<BidHandler> bidHandler = getSupportHandler(bidRequest);
        if (!bidHandler.isPresent()) {
            log.warn("[Bid request] unsupported request:{}", bidRequest);
            throw BidException.defaultError();
        }

        log.info("[Bid request], request:{}", bidRequest);

        BidResponse result = bidHandler.get().handleRequest(bidRequest);

        try {
            onBidden(result);
        } catch (Throwable e) {
            log.error("[on bidden] cause an error", e);
        }

        return result;
    }

    private Optional<BidHandler> getSupportHandler(BidRequest bidRequest) {
        for (BidHandler bidHandler : bidHandlers) {
            if (bidHandler.support(bidRequest)) {
                return Optional.of(bidHandler);
            }
        }

        return Optional.empty();
    }

    private boolean isShutdown() {
        return executor == null || executor.isShutdown();
    }

    @Override
    public void onBidden(BidResponse bidResult) {
        for (BidCallback bidCallback : bidCallbacks) {
            try {
                bidCallback.onBidden(bidResult);
            } catch (Throwable e) {
                log.error("[on bidden] cause an error", e);
            }
        }

    }

    @Override
    public void onBidHit(BidHitResponse bidHitResponse) {
        for (BidCallback bidCallback : bidCallbacks) {
            try {
                bidCallback.onBidHit(bidHitResponse);
            } catch (Throwable e) {
                log.error("[on bid hit] cause an error", e);
            }
        }

        auctionService.onComplete(bidHitResponse.getAuctionNo());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
//        int processors = Runtime.getRuntime().availableProcessors();
//        log.info("[processors] number is {}", processors);

        this.executor = Executors.newFixedThreadPool(bidThreadCount);
    }

    @Override
    public void destroy() throws Exception {
        if (isShutdown()) {
            return;
        }

        this.executor.shutdownNow();
    }
}
