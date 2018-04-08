package com.trump.auction.trade.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.trump.auction.reactor.api.model.Bidder;
import com.trump.auction.trade.dao.RobotInfoDao;
import com.trump.auction.trade.domain.AuctionInfo;
import com.trump.auction.trade.domain.RobotInfo;
import com.trump.auction.trade.service.RobotService;
import com.trump.auction.trade.util.Base64Utils;
import com.trump.auction.trade.util.ConstantUtil;
import com.trump.auction.trade.util.RobotUtil;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisCluster;

/**
 * 拍卖
 *
 * @author zhangliyan
 * @create 2018-01-02 18:04
 **/
@Service
@Slf4j
public class RobotServiceImpl implements RobotService {
	@Autowired
	private RobotInfoDao robotInfoDao;
	@Autowired
	private JedisCluster jedisCluster;

	@Override
	public Integer insertRobot(RobotInfo robotInfo) {
		int count = 0;
		robotInfo.setSDate(new Date());
		robotInfo.setSDate(new Date());
		count = robotInfoDao.insertRobot(robotInfo);
		return count;
	}

	@Override
	public int saveUpdateRobot(RobotInfo robotInfo) {
		robotInfo.setSDate(new Date());
		robotInfo.setSDate(new Date());
		return robotInfoDao.saveUpdateRobot(robotInfo);
	}

	@Override
	public int deleteRobot(String[] ids) {
		return robotInfoDao.deleteRobot(ids);
	}

	@Override
	public List<RobotInfo> findRobotInfo(Integer start, Integer end) {
		List<RobotInfo> robotInfos = robotInfoDao.findRobotInfo(start, end);
		return robotInfos;
	}

	@Override
	public String addRedisRotbot(AuctionInfo auctionInfo) {
		String robot = "";
		String count = jedisCluster.get(ConstantUtil.AUCTION_TRADE_ROBOT_COUNT);
		if (StringUtils.isBlank(count) || count.length() > 4) {
			count = "0";
		}
		log.info("addRedisRotbot count={}", count);
		int lastCount = RobotUtil.getRobotNumber(auctionInfo);
		List<RobotInfo> robotInfos = robotInfoDao.findRobotInfo(Integer.valueOf(count), lastCount);
		jedisCluster.set(ConstantUtil.AUCTION_TRADE_ROBOT_COUNT, String.valueOf(Integer.valueOf(count) + lastCount));
		jedisCluster.del(
				ConstantUtil.AUCTION_TRADE_ROBOT_TRADE + ConstantUtil.getRedisKey(String.valueOf(auctionInfo.getId())));
		for (RobotInfo rt : robotInfos) {
			jedisCluster.rpush(ConstantUtil.AUCTION_TRADE_ROBOT_TRADE
					+ ConstantUtil.getRedisKey(String.valueOf(auctionInfo.getId())), JSON.toJSONString(rt));
		}
		putBidCount(robotInfos.size(), String.valueOf(auctionInfo.getId()));
		robot = jedisCluster.lpop(
				ConstantUtil.AUCTION_TRADE_ROBOT_TRADE + ConstantUtil.getRedisKey(String.valueOf(auctionInfo.getId())));
		return robot;
	}

	private void putBidCount(Integer count, String auctionInfoId) {
		jedisCluster.set(ConstantUtil.AUCTION_TRADE_ROBOT_NEW_COUNT + ConstantUtil.getRedisKey(auctionInfoId),
				String.valueOf(count));
	}

	private Integer getBidCount(AuctionInfo auctionInfo) {
		String count = jedisCluster.get(ConstantUtil.AUCTION_TRADE_ROBOT_NEW_COUNT
				+ ConstantUtil.getRedisKey(String.valueOf(auctionInfo.getId())));
		if (StringUtils.isBlank(count) || count.length() > 4) {
			count = "0";
		}
		Integer number = Integer.valueOf(count);
		if (number < RobotUtil.minNumber) {
			number = RobotUtil.minNumber;
		}
		return number;
	}

	@Override
	public RobotInfo findRobot(AuctionInfo auctionInfo, Bidder lastBidder) {
		String robot = "";
		if (RobotUtil.needSpecialRobot(auctionInfo)) {
			robot = specialRobot(auctionInfo);
		} else if (RobotUtil.needOldRobot(auctionInfo, getBidCount(auctionInfo))) {
			robot = oldRobot(auctionInfo);
		} else {
			robot = newRobot(auctionInfo);
		}
		RobotInfo robotInfo = JSONObject.parseObject(robot, RobotInfo.class);
		if (StringUtils.isNotBlank(robotInfo.getName())) {
			robotInfo.setName(Base64Utils.decodeStr(robotInfo.getName()));
		}
		if (null != lastBidder && null != lastBidder.getName() && null != robotInfo
				&& (lastBidder.getName().equals(robotInfo.getName()))) {
			robotInfo = findRobot(auctionInfo, lastBidder);
		}
		return robotInfo;
	}

	/**
	 * 取出新的机器人
	 * 
	 * @param auctionInfo
	 * @return
	 */
	private String newRobot(AuctionInfo auctionInfo) {
		String robot = "";
		Long news = jedisCluster.llen(
				ConstantUtil.AUCTION_TRADE_ROBOT_TRADE + ConstantUtil.getRedisKey(String.valueOf(auctionInfo.getId())));
		if (0 == news) {
			return addRedisRotbot(auctionInfo);
		}
		List<String> robots = jedisCluster.lrange(
				ConstantUtil.AUCTION_TRADE_ROBOT_TRADE + ConstantUtil.getRedisKey(String.valueOf(auctionInfo.getId())),
				0, 0);
		robot = robots == null ? null : robots.get(0);
		if (StringUtils.isBlank(robot)) {
			robot = addRedisRotbot(auctionInfo);
		} else {
			jedisCluster.lrem(ConstantUtil.AUCTION_TRADE_ROBOT_TRADE
					+ ConstantUtil.getRedisKey(String.valueOf(auctionInfo.getId())), 0, robot);
		}
		jedisCluster.rpush(ConstantUtil.AUCTION_TRADE_ROBOT_TRADE_old
				+ ConstantUtil.getRedisKey(String.valueOf(auctionInfo.getId())), robot);
		return robot;
	}

	/**
	 * 取出老的机器人
	 * 
	 * @param auctionInfo
	 * @return
	 */
	private String oldRobot(AuctionInfo auctionInfo) {
		String robot = "";
		Long olds = jedisCluster.llen(ConstantUtil.AUCTION_TRADE_ROBOT_TRADE_old
				+ ConstantUtil.getRedisKey(String.valueOf(auctionInfo.getId())));
		if (0 == olds || olds < RobotUtil.getOldRobotNumber(auctionInfo, getBidCount(auctionInfo))) {
			return newRobot(auctionInfo);
		}
		Random random = new Random();
		int randomNumber = random.ints(0, olds.intValue()).iterator().nextInt();
		List<String> robots = jedisCluster.lrange(ConstantUtil.AUCTION_TRADE_ROBOT_TRADE_old
				+ ConstantUtil.getRedisKey(String.valueOf(auctionInfo.getId())), randomNumber, randomNumber);
		robot = robots == null ? null : robots.get(0);
		if (StringUtils.isBlank(robot)) {
			robot = addRedisRotbot(auctionInfo);
		}
		return robot;
	}

	/**
	 * 取出特殊的机器人
	 * 
	 * @param auctionInfo
	 * @return
	 */
	private String specialRobot(AuctionInfo auctionInfo) {
		String robot = "";
		Long olds = jedisCluster.llen(ConstantUtil.AUCTION_TRADE_ROBOT_TRADE_old
				+ ConstantUtil.getRedisKey(String.valueOf(auctionInfo.getId())));
		if (olds < RobotUtil.minNumber) {
			return newRobot(auctionInfo);
		}
		double rate = olds.intValue() * 0.7;
		int minRondom = 0;
		if (olds - (int) rate > RobotUtil.minNumber) {
			minRondom = (int) rate;
		} else if (olds - RobotUtil.minNumber > 0) {
			minRondom = olds.intValue() - RobotUtil.minNumber;
		}
		Random random = new Random();
		int randomNumber = random.ints(minRondom, olds.intValue()).iterator().nextInt();
		List<String> robots = jedisCluster.lrange(ConstantUtil.AUCTION_TRADE_ROBOT_TRADE_old
				+ ConstantUtil.getRedisKey(String.valueOf(auctionInfo.getId())), randomNumber, randomNumber);
		robot = robots == null ? null : robots.get(0);
		if (StringUtils.isBlank(robot)) {
			robot = addRedisRotbot(auctionInfo);
		}
		return robot;
	}

}
