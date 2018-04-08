package com.trump.auction.reactor.bid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.cf.common.util.mapping.BeanMapper;
import com.trump.auction.reactor.api.AuctionContextFactory;
import com.trump.auction.reactor.api.BidService;
import com.trump.auction.reactor.api.exception.BidException;
import com.trump.auction.reactor.api.model.AuctionContext;
import com.trump.auction.reactor.api.model.BidRequest;
import com.trump.auction.reactor.api.model.BidResponse;
import com.trump.auction.reactor.api.model.BidType;
import com.trump.auction.reactor.api.model.Bidder;
import com.trump.auction.reactor.api.model.MultiBid;
import com.trump.auction.reactor.api.model.MultiBidRequest;
import com.trump.auction.reactor.repository.BidRepository;
import com.trump.auction.reactor.util.lock.DistributedLock;
import com.trump.auction.reactor.util.lock.ZookeeperLockFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * {@link BidQueueHandler} 默认实现
 *
 * @author Owen
 * @since 2018/1/16
 */
@Slf4j
@Component
public class DefaultBidQueueHandler implements BidQueueHandler {

	@Autowired
	private BidRepository bidRepository;

	@Autowired
	private ZookeeperLockFactory lockFactory;

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private AuctionContextFactory contextFactory;

	@Autowired
	private BidService bidService;

	@Override
	public void join(MultiBidRequest bidRequest) {
		final DistributedLock lock = lockFactory.getBidLock(bidRequest.getAuctionNo());
		try {
			lock.acquire();
			join0(getMultiBid(bidRequest), contextFactory.create(bidRequest.getAuctionNo()));
		} finally {
			lock.release();
		}
	}

	protected void join0(MultiBid multiBid, AuctionContext context) throws BidException {
		final String auctionNo = multiBid.getAuctionNo();

		context.checkBiddable(multiBid.getBidder());

		if (remainBid(auctionNo, multiBid.getBidder())) {
			throw BidException.bidderQueued();
		}

		// 查看排在队首的出价信息
		Optional<BidRequest> queueHead = bidRepository.peekQueue(auctionNo);

		// 入队
		bidRepository.pushQueue(auctionNo, decreaseBidCount(multiBid));
		bidRepository.putWaitBid(multiBid);

		// 队首为空（即队列为空）直接出价
		if (!queueHead.isPresent()) {
			this.nextBid(auctionNo);
		}
	}

	private MultiBid getMultiBid(MultiBidRequest bidRequest) {
		MultiBid bid = beanMapper.map(bidRequest, MultiBid.class);
		bid.setLeftBidCount(bid.getBidCount());
		return bid;
	}

	@Override
	public boolean nextRound(String auctionNo) {
		final DistributedLock lock = lockFactory.getBidLock(auctionNo);

		try {
			lock.acquire();
			return nextRound0(auctionNo, contextFactory.create(auctionNo));
		} finally {
			lock.release();
		}
	}

	private void nextRoundIfNecessary(String auctionNo) {
		nextRound(auctionNo);
	}

	@Override
	public BidResponse nextBid(String auctionNo) {
//		final DistributedLock lock = lockFactory.getBidLock(auctionNo);
//
//		try {
//			lock.acquire();

			nextRoundIfNecessary(auctionNo);

			Optional<BidRequest> bidRequest = bidRepository.popQueue(auctionNo);

			if (!bidRequest.isPresent()) {
				return null;
			}

			BidResponse bidResult = bidService.bid(bidRequest.get(), true);

			// 出价失败重新入队
			if (bidResult.isFailed()) {
				log.info("[delegate bid] requeue, bidRequest:{}", bidRequest.get());
				bidRepository.rightPushQueue(auctionNo, bidRequest.get());
			}
			return bidResult;
//		} finally {
//			lock.release();
//		}
	}

	@Override
	public boolean remainBid(String auctionNo, Bidder bidder) {
		// 判断是否在委托出价中
		if (bidRepository.isWaitBid(auctionNo, bidder)) {
			log.warn("[bid queue] bidder is in queue, bidder:{}, auctionNo:{}", bidder, auctionNo);
			return true;
		}

		// 判断是否在委托出价中
		if (bidRepository.isInQueue(auctionNo, bidder)) {
			log.warn("[bid queue] bidder is in queue, bidder:{}, auctionNo:{}", bidder, auctionNo);
			return true;
		}

		return false;
	}

	protected boolean nextRound0(String auctionNo, AuctionContext context) {
		if (!bidRepository.isQueueEmpty(auctionNo)) {
			log.debug("[bid queue] not empty, auctionNo:{}", auctionNo);
			return false;
		}

		Map<String, MultiBid> bidMap = bidRepository.getWaitBid(auctionNo);

		if (CollectionUtils.isEmpty(bidMap)) {
			return false;
		}

		// 判断委托待出价人是否只剩下最后出价人
		// 若只剩最后出价人则无需安排本次排队
		if (onlyLastBidderRemain(context, bidMap)) {
			return false;
		}

		List<MultiBid> multiBids = getMultiBids(bidMap);

		List<BidRequest> bidQueue = multiBids.stream().filter((bid) -> bid.remainBidCount())
				.map((bid) -> decreaseBidCount(bid)).collect(Collectors.toList());
		// 打乱队列顺序(委托出价队列太死板，ABCABC...)
		if (bidQueue.size() > 2) {
			Random rd = new Random();
			for (BidRequest bid : bidQueue) {
				bid.setOrderRondom(rd.ints(0, 1000).iterator().nextInt());
			}
			Collections.sort(bidQueue, Comparator.comparingInt((bid) -> bid.getOrderRondom()));
		}
		// 判断排队第一位是否为最后出价人
		// 若为最后出价人则适当调整顺序
		if (context.isLastBidder(firstBidder(bidQueue))) {
			bidQueue.add(1, bidQueue.remove(0));
		}

		// 要删除的委托出价（出价次数已全部排完）
		List<MultiBid> removeList = multiBids.stream().filter((bid) -> !bid.remainBidCount())
				.collect(Collectors.toList());

		bidRepository.putWaitBid(auctionNo, multiBids);
		bidRepository.removeWaitBid(auctionNo, removeList);
		bidRepository.pushQueue(auctionNo, bidQueue);
		return true;
	}

	// 判断委托待出价人是否只剩下最后出价人
	private boolean onlyLastBidderRemain(AuctionContext context, Map<String, MultiBid> bidMap) {
		return bidMap != null && bidMap.size() == 1 && bidMap.containsKey(context.getLastBidder().getId());
	}

	private Bidder firstBidder(List<BidRequest> bidQueue) {
		return bidQueue.get(0).getBidder();
	}

	private BidRequest decreaseBidCount(MultiBid bid) {
		BidRequest bidRequest = beanMapper.map(bid, BidRequest.class);
		bidRequest.setBidType(BidType.DELEGATE);
		bidRequest.setAccountCode(bid.decreaseBidCount());

		bidRequest.setBidCycle(bid.getBidCount() + "-" + bid.biddenCount());

		return bidRequest;
	}

	private List<MultiBid> getMultiBids(Map<String, MultiBid> bidMap) {
		List<MultiBid> result = new ArrayList<>(bidMap.values());
		Collections.sort(result, Comparator.comparingLong((bid) -> bid.getTimestamp()));
		return result;
	}

}
