package com.trump.auction.reactor.bid.support;

import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.trump.auction.reactor.api.AuctionContextFactory;
import com.trump.auction.reactor.api.model.AuctionContext;
import com.trump.auction.reactor.api.model.BidResponse;
import com.trump.auction.reactor.api.model.MultiBid;
import com.trump.auction.reactor.bid.BidConfig;
import com.trump.auction.reactor.bid.BidQueueHandler;
import com.trump.auction.reactor.repository.BidRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 委拖出价监听器
 *
 * @author Owen
 * @since 2018/1/11
 */
@Slf4j
@Component
public class DelegateBidListener<E extends BidEvent> extends AbstractListener<E> {

	@Autowired
	private BidQueueHandler bidQueueHandler;

	@Autowired
	private BidTimer<BidEvent> timer;

	@Autowired
	private BidConfig bidConfig;
	@Autowired
	private AuctionContextFactory contextFactory;
	@Autowired
	private BidRepository bidRepository;

	@Override
	public boolean support(E event) {
		return event instanceof DelegateBidEvent;
	}

	@Override
	protected void onEvent0(E event) {
		final DelegateBidEvent bidEvent = DelegateBidEvent.class.cast(event);
		try {
			bidQueueHandler.nextBid(bidEvent.getAuctionNo());
		} finally {
			addNextTask(event, bidEvent);
		}
	}

	// 安排下一个委托出价任务
	private void addNextTask(E event, DelegateBidEvent bidEvent) {
		long delegateBidDelayTime = bidConfig.nextDelegateBidDelayTime(bidEvent.getBidCountDown());
		long autoBidTime = bidConfig.nextAutoBidDelayTime(bidEvent.getBidCountDown());
		Map<String, MultiBid> bidMap = bidRepository.getWaitBid(bidEvent.getAuctionNo());
		if (CollectionUtils.isEmpty(bidMap) || bidMap.values().size() <= 0) {
			timer.add(event, delegateBidDelayTime);
		} else if (bidMap.values().size() == 1) {
			timer.add(event, delegateBidDelayTime + autoBidTime);
		} else {
			Random random = new Random();
			int number = random.nextInt(100);
			if (number < 20) {
				timer.add(event, delegateBidDelayTime + autoBidTime);
			} else {
				timer.add(event, delegateBidDelayTime);
			}
		}
	}

}
