package com.trump.auction.reactor.bid;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.trump.auction.reactor.api.exception.BidException;
import com.trump.auction.reactor.api.model.AuctionContext;
import com.trump.auction.reactor.api.model.Bid;
import com.trump.auction.reactor.api.model.BidRequest;
import com.trump.auction.reactor.api.model.BidResponse;
import com.trump.auction.reactor.api.model.BidType;
import com.trump.auction.reactor.bid.rule.BidHitRule;
import com.trump.auction.reactor.bid.support.AttemptHitEvent;
import com.trump.auction.reactor.bid.support.AutoBidEvent;
import com.trump.auction.reactor.util.lock.DistributedLock;

import lombok.extern.slf4j.Slf4j;

/**
 * 默认单次出价处理
 *
 * @author Owen
 * @since 2018/1/16
 */
@Slf4j
@Component
public class DefaultBidHandle extends AbstractBidHandler implements BidHandler {

	@Override
	public boolean support(BidRequest bidRequest) {
		return BidType.DEFAULT.equals(bidRequest.getBidType());
	}

	@Override
	public BidResponse handleRequest(BidRequest bidRequest) {
		DistributedLock lock = null;
		AuctionContext context = null;

		try {
			lock = lockFactory.getBidLock(bidRequest.getAuctionNo());
			lock.acquire();

			context = contextFactory.create(bidRequest.getAuctionNo());
			return handleRequest0(bidRequest, context);
		} catch (Throwable e) {
			return handleException(bidRequest, context, e);
		} finally {
			if (lock != null) {
				lock.release();
			}
		}
	}

	protected BidResponse handleException(BidRequest bidRequest, AuctionContext context, Throwable e) {
		if (BidException.support(e)) {
			log.warn("[bid error] code:{}, request:{}, context:{},", e.getMessage(), bidRequest, context);
		} else {
			log.error("[bid error] request:{}, context:{}, error:{}", bidRequest, context, e);
		}

		return onError(bidRequest, context, e);
	}

	protected BidResponse handleRequest0(BidRequest bidRequest, AuctionContext context) {
		Bid bid = getBid(bidRequest);

		// 检查竞拍状态
		checkBid(bid, context);

		// 出价
		context.onBid(bid);
		bidRepository.saveContext(context);
		// 判断竞拍成功的前置条件
		if (isHittable(context)) {
			AttemptHitEvent event = new AttemptHitEvent(context);
			timer.add(event, context.getBidCountDown(), TimeUnit.SECONDS);
		} else {
			AutoBidEvent event = new AutoBidEvent(context);
			timer.add(event, config.nextAutoBidDelayTime(context.getBidCountDown()));
		}
		return onSuccess(bidRequest, context);
	}

	private Bid getBid(BidRequest bidRequest) {
		return beanMapper.map(bidRequest, Bid.class);
	}

	protected void checkBid(Bid bid, AuctionContext context) {
		context.checkBiddable(bid.getBidder());

		// 判断是否有未完成的出价
		if (bidQueueHandler.remainBid(context.getAuctionNo(), bid.getBidder())) {
			throw BidException.bidderQueued();
		}
	}

	/**
	 * 判断是否满足拍中的前置条件
	 */
	private boolean isHittable(AuctionContext context) {
		boolean bidHit = false;
		for (BidHitRule hitRule : hitRules) {
			bidHit = hitRule.check(context);
			if (!bidHit) {
				break;
			}
		}
		return bidHit;
	}

}
