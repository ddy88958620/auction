package com.trump.auction.reactor.bid.support;

import com.trump.auction.reactor.api.model.BidHitResponse;
import com.trump.auction.reactor.api.model.AuctionContext;
import com.trump.auction.reactor.api.AuctionContextFactory;
import com.trump.auction.reactor.bid.BidQueueHandler;
import com.trump.auction.reactor.repository.BidRepository;
import com.trump.auction.reactor.api.BidService;
import com.trump.auction.reactor.util.lock.DistributedLock;
import com.trump.auction.reactor.util.lock.ZookeeperLockFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 尝试拍中监听器
 *
 * @author Owen
 * @since 2018/1/11
 */
@Slf4j
@Component
public class AttemptHitListener<E extends BidEvent> extends AbstractListener<E> {

    @Autowired
    private AuctionContextFactory contextFactory;

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private BidService bidService;

    @Autowired
    private ZookeeperLockFactory lockFactory;

    @Autowired
    private BidQueueHandler bidQueueHandler;

    @Override
    public boolean support(E event) {
        return event instanceof AttemptHitEvent;
    }

    @Override
    protected void onEvent0(E event) {
        AttemptHitEvent hitEvent = AttemptHitEvent.class.cast(event);
        AuctionContext context = getContext(hitEvent);
        if (!isHit(hitEvent, context)) {
            return;
        }
        final DistributedLock lock = lockFactory.getBidLock(hitEvent.getAuctionNo());

        try {
            lock.acquire();

            // 触发一次委托出价，防止异常情况造成的尚有待处理的委托中的出价却触发了拍中操作
            bidQueueHandler.nextBid(hitEvent.getAuctionNo());

            context = getContext(hitEvent);
            if (!isHit(hitEvent, context)) {
                return;
            }

            // 竞拍完成
            context.onComplete();
            saveContext(context);

            bidService.onBidHit(bidHitResponse(context));
        } catch (Throwable e) {
            log.error("[hit listener] cause an error.", e);
        } finally {
            lock.release();
        }
    }

    private AuctionContext getContext(AttemptHitEvent hitEvent) {
        return contextFactory.create(hitEvent.getAuctionNo());
    }

    private BidHitResponse bidHitResponse(AuctionContext context) {
        BidHitResponse result = new BidHitResponse();
        result.setAuctionNo(context.getAuctionNo());
        result.setLastPrice(context.getLastPrice());
        result.setLastBidder(context.getLastBidder());
        return result;
    }

    private boolean isHit(AttemptHitEvent event, AuctionContext context) {
        // 判断竞拍是否结束
        if (context.isComplete()) {
            log.warn("[attempt hit] auction is complete, event = {}.", event);
            return false;
        }

        // 判断是否最后一次出价
        if (!isLastBid(event, context)) {
            log.warn("[attempt hit] last price changed, event = {}.", event);
            return false;
        }

        return true;
    }

    private boolean isLastBid(AttemptHitEvent theEvent, AuctionContext context) {
        if (!context.isLastPrice(theEvent.getLastPrice())) {
            return false;
        }

        if (!context.isLastBidder(theEvent.getLastBidder())) {
            return false;
        }

        return true;
    }

    private void saveContext(AuctionContext context) {
        bidRepository.saveContext(context);
    }
}
