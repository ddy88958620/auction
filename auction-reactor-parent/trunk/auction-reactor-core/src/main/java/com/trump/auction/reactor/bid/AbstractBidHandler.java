package com.trump.auction.reactor.bid;

import com.cf.common.util.mapping.BeanMapper;
import com.trump.auction.reactor.api.AuctionContextFactory;
import com.trump.auction.reactor.api.BidService;
import com.trump.auction.reactor.api.exception.BidException;
import com.trump.auction.reactor.api.model.AuctionContext;
import com.trump.auction.reactor.api.model.BidRequest;
import com.trump.auction.reactor.api.model.BidResponse;
import com.trump.auction.reactor.bid.rule.BidHitRule;
import com.trump.auction.reactor.bid.support.*;
import com.trump.auction.reactor.repository.BidRepository;
import com.trump.auction.reactor.util.lock.ZookeeperLockFactory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * {@link BidHandler} 基础实现
 *
 * @author Owen
 * @since 2018/1/16
 */
@Slf4j
public abstract class AbstractBidHandler implements BidHandler {

    @Autowired
    @Getter(value = AccessLevel.PROTECTED)
    protected BeanMapper beanMapper;

    @Autowired
    @Getter(value = AccessLevel.PROTECTED)
    protected AuctionContextFactory contextFactory;

    @Autowired
    @Getter(value = AccessLevel.PROTECTED)
    protected BidService bidService;

    @Autowired
    @Getter(value = AccessLevel.PROTECTED)
    protected List<BidHitRule> hitRules;

    @Autowired
    @Getter(value = AccessLevel.PROTECTED)
    protected BidConfig config;

    @Autowired
    @Getter(value = AccessLevel.PROTECTED)
    protected BidTimer<BidEvent> timer;

    @Autowired
    @Getter(value = AccessLevel.PROTECTED)
    protected BidRepository bidRepository;

    @Autowired
    @Getter(value = AccessLevel.PROTECTED)
    protected ZookeeperLockFactory lockFactory;

    @Autowired
    protected BidQueueHandler bidQueueHandler;

    @Autowired
    protected BidResponses bidResponses;

    protected BidResponse onError(BidRequest bidRequest, AuctionContext context, Throwable cause) {
        return bidResponses.failed(BidException.code(cause), bidRequest);
    }

    protected BidResponse onSuccess(BidRequest bidRequest, AuctionContext context) {
        BidResponse bidResponse = bidResponses.success(bidRequest, context);
        bidRepository.saveBidResult(bidResponse);

        return bidResponse;
    }
}
