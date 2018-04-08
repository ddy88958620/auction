package com.trump.auction.reactor;

import com.trump.auction.reactor.api.AuctionService;
import com.trump.auction.reactor.api.BidderService;
import com.trump.auction.reactor.api.model.*;
import com.trump.auction.reactor.api.BidService;
import com.trump.auction.reactor.ext.repository.BidCostRepository;
import com.trump.auction.reactor.ext.service.BidExtService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Autowired
	private AuctionService auctionService;

	@Autowired
	StringRedisTemplate redisTemplate;

	@Autowired
	private BidService bidService;

	@Autowired
	private BidderService bidderService;

	@Autowired
	private BidCostRepository bidCostRepository;

	@Autowired
	private BidExtService bidExtService;

	ExecutorService executor = Executors.newFixedThreadPool(10);

	AtomicInteger id = new AtomicInteger(new Random().nextInt(10) + 1);

	final AtomicLong bizNoGen = new AtomicLong(1);

	int bidderCountPerAuction = 2;

	int auctionCount = 1;

	@Test
	public void contextLoads() throws Exception {
		String auctionNo = String.valueOf(nextAuctionNo());

		auctionService.onStart(auctionNo);

		for (int i = 0; i < auctionCount; i++) {
			mockBid(auctionNo);
		}

		TimeUnit.DAYS.sleep(Integer.MAX_VALUE);
	}

	private Long nextAuctionNo() {
		return redisTemplate.opsForValue().increment("auction.reactor.next-auction-no", 1);
	}

	private void mockBid(String auctionNo) {
		int count = 5;
		for (int i = 1; i <= bidderCountPerAuction; i++) {
			int bidCount = count * i;
			MultiBidRequest bidRequest = getBidRequest(auctionNo, getBidder(), bidCount);
			executor.submit(() -> {
				try {
					Thread.sleep(new Random().nextInt(9) + 1);
				} catch (InterruptedException e) {
				}

				bidService.bid(bidRequest);
			});
		}

		for (int i = 0; i < 20; i++) {
//			executor.submit(() -> bidService.bid(getBidRequest(auctionNo, getBidder())));
		}

	}

	private BidRequest getBidRequest(String actionNo, Bidder bidder) {
		BidRequest bidRequest = new BidRequest();
		bidRequest.setAuctionNo(actionNo);
		bidRequest.setBizNo(String.valueOf(bizNoGen.incrementAndGet()));
		bidRequest.setBidder(bidder);
		bidRequest.setBidType(BidType.DEFAULT);
		bidRequest.setAccountCode(new Random().nextBoolean() ? AccountCode.FREE : AccountCode.YIPPE);
		return bidRequest;
	}

	private MultiBidRequest getBidRequest(String actionNo, Bidder bidder, int bidCount) {
		MultiBidRequest bidRequest = new MultiBidRequest();
		bidRequest.setAuctionNo(actionNo);
		bidRequest.setBizNo(String.valueOf(bizNoGen.incrementAndGet()));
		bidRequest.setBidder(bidder);

		bidRequest.withCostDetail(new BidCostDetail(AccountCode.YIPPE, bidCount / 2))
				.withCostDetail(new BidCostDetail(AccountCode.FREE, bidCount - (bidCount / 2)));
		return bidRequest;
	}

	private Bidder getBidder() {
		int userId = id.incrementAndGet();

		Bidder bidder = bidderService.nextBidder();
		bidder.setUserId(String.valueOf(userId));
		bidder.setSubId("xxx");
		bidder.setName("用户" + userId);

		return bidder;
	}

	@Test
	public void testBidCost() {
		System.out.println(bidCostRepository.get("20013", Bidder.create("10", "xxx")));
		System.out.println(bidCostRepository.get("20013", Bidder.create("10", "xxx"), AccountCode.YIPPE));
		System.out.println(bidCostRepository.get("20013", Bidder.create("10", "xxx"), AccountCode.FREE));


		System.out.println(bidCostRepository.get("20013", Bidder.create("11", "xxx")));
		System.out.println(bidCostRepository.get("20013", Bidder.create("13", "xxx")));
		System.out.println(bidCostRepository.get("20013", Bidder.create("13", "xxx"), AccountCode.YIPPE));
	}

	@Test
	public void testQueryBidResult() {
		for (int i = 0; i < 10000; i++) {
			System.out.println(bidExtService.getLatest("1", 3));
		}

		System.out.println(bidExtService.getCost("1", "4"));
	}


	public void testLua() {
//		redisTemplate.exec()
	}
}
