package com.trump.auction.trade.core.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.trump.auction.reactor.api.BidderService;
import com.trump.auction.reactor.api.model.Bidder;
import com.trump.auction.trade.domain.AuctionInfo;
import com.trump.auction.trade.domain.RobotInfo;
import com.trump.auction.trade.service.AuctionInfoService;
import com.trump.auction.trade.service.RobotService;
import com.trump.auction.trade.util.Base64Utils;
import com.trump.auction.trade.util.ConstantUtil;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisCluster;

@Slf4j
@Service
public class BidderServiceImpl implements BidderService {
	@Value("${auction.trade.sub.user.url}")
	private String subUserUrl;
	@Autowired
	private AuctionInfoService auctionInfoService;
	@Autowired
	private JedisCluster jedisCluster;
	@Autowired
	private RobotService robotService;

	@Override
	public Bidder nextBidder() {

		return null;
	}

	@Override
	public Bidder nextBidder(Bidder lastBidder, String auctionId) {
		Long s1 = System.currentTimeMillis();
		Bidder robot = getBidder(auctionId, lastBidder);
		Long s2 = System.currentTimeMillis();
		log.info("BidderServiceImpl  nextBidder times ms={}", (s2 - s1));
		return robot;
	}

	@Override
	public Bidder nextBidder(Bidder bidder) {
		return null;
	}

	/**
	 * 封装用户对象
	 * 
	 * @return
	 */
	private Bidder getBidder(String auctionId, Bidder lastBidder) {
		AuctionInfo auctionInfo = auctionInfoService.selectByPrimaryKey(Integer.valueOf(auctionId));
		Bidder bidder = new Bidder();
		try {
			RobotInfo robotInfo = robotService.findRobot(auctionInfo,lastBidder);
			bidder.setName(robotInfo.getName());
			bidder.setHeadImgUrl(robotInfo.getHeadImg().toString());
			bidder.setSubId(robotInfo.getId().toString());
			bidder.setUserId("1");
			bidder.setAddrArea(robotInfo.getAddress());
		} catch (Exception e) {
			log.error("BidderServiceImpl getBidder error auctionId={}", auctionId, e.getMessage());
			jedisCluster.del(ConstantUtil.AUCTION_TRADE_ROBOT_TRADE
					+ ConstantUtil.getRedisKey(String.valueOf(auctionInfo.getId())));
		}
		return bidder;
	}

}
