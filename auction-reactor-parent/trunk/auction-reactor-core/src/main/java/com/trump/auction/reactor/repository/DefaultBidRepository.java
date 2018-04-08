package com.trump.auction.reactor.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.trump.auction.reactor.api.model.AuctionContext;
import com.trump.auction.reactor.api.model.BidRequest;
import com.trump.auction.reactor.api.model.BidResponse;
import com.trump.auction.reactor.api.model.BidStatistics;
import com.trump.auction.reactor.api.model.Bidder;
import com.trump.auction.reactor.api.model.MultiBid;
import com.trump.auction.reactor.common.repository.AbstractRedisRepository;

/**
 * {@link BidRepository} redis 实现
 *
 * @author Owen
 * @since 2018/1/15
 */
@Component
public class DefaultBidRepository extends AbstractRedisRepository implements BidRepository {

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Override
	public Map<String, MultiBid> getWaitBid(String auctionNo) {
		Map<Object, Object> result = redisTemplate.opsForHash().entries(waitBidHashKey(auctionNo));
		return convertToBean(result, MultiBid.class);
	}

	@Override
	public boolean hasWaitBid(String auctionNo) {
		return redisTemplate.opsForHash().size(waitBidHashKey(auctionNo)) > 0;
	}

	@Override
	public boolean isWaitBid(String auctionNo, Bidder bidder) {
		return redisTemplate.opsForHash().hasKey(waitBidHashKey(auctionNo), hashEntryKey(bidder));
	}

	@Override
	public void putWaitBid(MultiBid multiBid) {
		redisTemplate.opsForHash().put(waitBidHashKey(multiBid.getAuctionNo()), hashEntryKey(multiBid),
				hashEntryVal(multiBid));
	}

	@Override
	public void putWaitBid(String auctionNo, List<MultiBid> bids) {
		if (CollectionUtils.isEmpty(bids)) {
			return;
		}

		Map<String, MultiBid> bidMap = new HashMap<>();
		for (MultiBid multiBid : bids) {
			bidMap.put(hashEntryKey(multiBid), multiBid);
		}

		putWaitBid(auctionNo, bidMap);
	}

	@Override
	public void putWaitBid(String auctionNo, Map<String, MultiBid> bidMap) {
		if (CollectionUtils.isEmpty(bidMap)) {
			return;
		}

		Map<String, String> result = new HashMap<>();
		for (Map.Entry<String, MultiBid> bidEntry : bidMap.entrySet()) {
			MultiBid multiBid = bidEntry.getValue();
			if (multiBid.remainBidCount()) {
				result.put(hashEntryKey(multiBid), hashEntryVal(multiBid));
			}
		}

		if (result.isEmpty()) {
			return;
		}

		redisTemplate.opsForHash().putAll(waitBidHashKey(auctionNo), result);
	}

	@Override
	public void removeWaitBid(String auctionNo, List<MultiBid> bids) {
		if (CollectionUtils.isEmpty(bids)) {
			return;
		}

		redisTemplate.opsForHash().delete(waitBidHashKey(auctionNo),
				Lists.transform(bids, (bid) -> hashEntryKey(bid)).toArray());
	}

	@Override
	public void pushQueue(String auctionNo, List<BidRequest> bids) {
		if (CollectionUtils.isEmpty(bids)) {
			return;
		}

		redisTemplate.opsForList().leftPushAll(bidQueueKey(auctionNo), Lists.transform(bids, (bid) -> value(bid)));
	}

	@Override
	public void pushQueue(String auctionNo, BidRequest bid) {
		Assert.notNull(bid, "[bid]");

		redisTemplate.opsForList().leftPush(bidQueueKey(auctionNo), value(bid));
	}

	@Override
	public Optional<BidRequest> popQueue(String auctionNo) {
		String result = redisTemplate.opsForList().rightPop(bidQueueKey(auctionNo));
		if (StringUtils.isEmpty(result)) {
			return Optional.empty();
		}

		return Optional.of(convertToBean(result, BidRequest.class));
	}

	@Override
	public void rightPushQueue(String auctionNo, BidRequest bidRequest) {
		redisTemplate.opsForList().rightPush(bidQueueKey(auctionNo), value(bidRequest));
	}

	@Override
	public Optional<BidRequest> peekQueue(String auctionNo) {
		List<String> result = redisTemplate.opsForList().range(bidQueueKey(auctionNo), -1, -1);
		if (CollectionUtils.isEmpty(result)) {
			return Optional.empty();
		}

		return Optional.of(convertToBean(result.get(0), BidRequest.class));
	}

	@Override
	public boolean isQueueEmpty(String auctionNo) {
		return redisTemplate.opsForList().size(bidQueueKey(auctionNo)) <= 0;
	}

	@Override
	public List<BidRequest> getBidQueue(String auctionNo) {
		List<String> results = redisTemplate.opsForList().range(bidQueueKey(auctionNo), 0, -1);
		if (CollectionUtils.isEmpty(results)) {
			return Collections.EMPTY_LIST;
		}

		return Lists.transform(results, (bidRequest) -> convertToBean(bidRequest, BidRequest.class));
	}

	@Override
	public boolean isInQueue(String auctionNo, Bidder bidder) {
		List<BidRequest> bidQueue = getBidQueue(auctionNo);
		if (CollectionUtils.isEmpty(bidQueue)) {
			return false;
		}

		for (BidRequest bidRequest : bidQueue) {
			if (bidRequest.getBidder().equals(bidder)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void saveContext(AuctionContext context) {
		redisTemplate.opsForValue().set(auctionContextKey(context.getAuctionNo()), value(context));
	}

	@Override
	public Boolean saveContextIfAbsent(AuctionContext context) {
		return redisTemplate.opsForValue().setIfAbsent(auctionContextKey(context.getAuctionNo()), value(context));
	}

	@Override
	public AuctionContext getContext(String auctionNo) {
		String json = redisTemplate.opsForValue().get(auctionContextKey(auctionNo));
		return StringUtils.hasText(json) ? JSON.parseObject(json, AuctionContext.class) : null;
	}

	@Override
	public void saveBidResult(BidResponse bidResp) {
		redisTemplate.opsForList().leftPush(bidResultQueueKey(bidResp.getAuctionNo()), value(bidResp));
	}

	@Override
	public List<BidResponse> getBidResult(String auctionNo, int count) {
		Assert.isTrue(count > 0, "[count]");

		List<String> result = redisTemplate.opsForList().range(bidResultQueueKey(auctionNo), -count, -1);
		if (CollectionUtils.isEmpty(result)) {
			return Collections.EMPTY_LIST;
		}

		Collections.reverse(result);

		return Lists.transform(result, (bidResult) -> convertToBean(bidResult, BidResponse.class));
	}

	@Override
	public void removeBidResult(String auctionNo, int count) {
		Assert.isTrue(count > 0, "[count]");

		redisTemplate.opsForList().trim(bidResultQueueKey(auctionNo), 0, -(count + 1));
	}

	private String hashEntryVal(Object value) {
		return value(value);
	}

	private String value(Object value) {
		return JSON.toJSONString(value);
	}

	private String waitBidHashKey(String auctionNo) {
		return makeKey(auctionNo, "multi-bid-hash");
	}

	private String bidQueueKey(String auctionNo) {
		return makeKey(auctionNo, "multi-bid-queue");
	}

	private String hashEntryKey(MultiBid multiBid) {
		return hashEntryKey(multiBid.getBidder());
	}

	private String hashEntryKey(Bidder bidder) {
		return bidder.getId();
	}

	private String auctionContextKey(String auctionNo) {
		return makeKey(auctionNo, "auction-context");
	}

	private String bidResultQueueKey(String auctionNo) {
		return makeKey(auctionNo, "bid-result-queue");
	}

	private String bidStatisticsKey(String auctionNo) {
		return makeKey(auctionNo, "bid-statistics");
	}

	@Override
	public boolean hasBidStatistics(String auctionNo,String userId) {
		Map<Object, Object> result = redisTemplate.opsForHash().entries(bidStatisticsKey(auctionNo));
		if (CollectionUtils.isEmpty(result)) {
			return false;
		}
		Map<String, BidStatistics> bidMap = convertToBean(result, BidStatistics.class);
		if (CollectionUtils.isEmpty(bidMap)) {
			return false;
		}
		List<BidStatistics> list = new ArrayList<>(bidMap.values());
		for (BidStatistics bidCost2 : list) {
			if (userId.equals(bidCost2.getUserId())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public BidStatistics getBidStatistics(String auctionNo, String userId) {
		Map<Object, Object> result = redisTemplate.opsForHash().entries(bidStatisticsKey(auctionNo));
		if (CollectionUtils.isEmpty(result)) {
			return null;
		}
		Map<String, BidStatistics> bidMap = convertToBean(result, BidStatistics.class);
		if (CollectionUtils.isEmpty(bidMap)) {
			return null;
		}
		List<BidStatistics> list = new ArrayList<>(bidMap.values());
		BidStatistics bid = null;
		for (BidStatistics bidCost2 : list) {
			if (userId.equals(bidCost2.getUserId())) {
				bid = bidCost2;
				break;
			}
		}
		return bid;
	}

	@Override
	public void putBidStatistics(String auctionNo, BidStatistics bid) {
		if (bid == null || bid.getUserId() == null || bid.getPCoin() <= 0 || bid.getZCoin() <= 0) {
			return;
		}
		List<BidStatistics> bids = null;
		Map<Object, Object> res = redisTemplate.opsForHash().entries(bidStatisticsKey(auctionNo));
		if (CollectionUtils.isEmpty(res)) {
			bids = new ArrayList<>();
			bids.add(bid);
		} else {
			Map<String, BidStatistics> bidMap = convertToBean(res, BidStatistics.class);
			bids = new ArrayList<>(bidMap.values());
			boolean hasbid = false;
			for (BidStatistics bidCost : bids) {
				if (bidCost.getUserId().equals(bid.getUserId())) {
					bidCost.setBidCount(bid.getBidCount());
					bidCost.setZCoin(bid.getZCoin());
					bidCost.setPCoin(bid.getPCoin());
					hasbid = true;
					break;
				}
			}
			if (!hasbid) {
				bids.add(bid);
			}
		}
		Map<String, String> result = new HashMap<>();
		for (BidStatistics bidCost : bids) {
			result.put(bidCost.getUserId(), hashEntryVal(bidCost));
		}
		if (result.isEmpty()) {
			return;
		}
		redisTemplate.opsForHash().putAll(bidStatisticsKey(auctionNo), result);
	}

}
