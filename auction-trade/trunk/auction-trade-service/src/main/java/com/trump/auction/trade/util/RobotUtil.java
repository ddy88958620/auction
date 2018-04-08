package com.trump.auction.trade.util;

import java.util.Random;

import com.trump.auction.trade.domain.AuctionInfo;

public class RobotUtil {
	// 最少出价人数
	public static final int minNumber = 5;
	// 价格达到此比例时，特殊机器人出场
	public static final int specialRate = 60;
	// 拍品出价人数随机率min
	private static final int minRandomNumber = 5;
	// 拍品出价人数随机率max
	private static final int maxRandomNumber = 8;

	/**
	 * 需要生成的新机器人总数量
	 * 
	 * @param auctionInfo
	 * @return
	 */
	public static int getRobotNumber(AuctionInfo auctionInfo) {
		// 市场价格
		double productPrice = auctionInfo.getProductPrice().doubleValue();
		Random random = new Random();
		int randomNum = random.ints(minRandomNumber, maxRandomNumber + 1).iterator().nextInt();
		int number = (int) (productPrice * randomNum / 100);
		return number < minNumber ? minNumber : number;
	}

	/**
	 * 获取当前出价比例
	 * 
	 * @param auctionInfo
	 * @return
	 */
	public static int getPriceRate(AuctionInfo auctionInfo) {
		if (auctionInfo.getFloorPrice().intValue() <= 0) {
			return 100;
		}
		// (起拍价格+手续费+当前全部出价)*100/预设成交价
		Integer bidPercent = (auctionInfo.getStartPrice().intValue()
				+ auctionInfo.getTotalBidCount() * auctionInfo.getPoundage()
				+ (auctionInfo.getTotalBidCount() * auctionInfo.getIncreasePrice().intValue())) * 100
				/ auctionInfo.getFloorPrice().intValue();
		return bidPercent;
	}

	/**
	 * 当前价格区间对应需要的出价人数
	 * 
	 * @param auctionInfo
	 * @return
	 */
	public static int getOldRobotNumber(AuctionInfo auctionInfo, Integer count) {
		// 保底出价次数
		double rate = getPriceRate(auctionInfo);
		if (rate <= 0) {
			rate = 10;
		}
		// 价格区间百分比
		rate = Math.ceil(rate / 10) * 10;
		int number = (int) (count * rate / 100);
		return number < minNumber ? minNumber : number;
	}

	/**
	 * 计算新旧机器人谁出场 计算机器人随着价格应该出现的数量 最低同时参与出价人数为5
	 * 
	 * @return
	 */
	public static Boolean needOldRobot(AuctionInfo auctionInfo, Integer count) {
		int needCount = getOldRobotNumber(auctionInfo, count);
		// 当出价人数小于当前需要出价人数，就需要新机器人
		if (needCount < auctionInfo.getPersonCount()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 计算当前价格区间，是否需要特殊机器人出场
	 * 
	 * @return
	 */
	public static boolean needSpecialRobot(AuctionInfo auctionInfo) {
		return getPriceRate(auctionInfo) > specialRate;
	}
}
