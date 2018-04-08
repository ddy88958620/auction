package com.trump.auction.reactor.api.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.alibaba.fastjson.annotation.JSONField;
import com.trump.auction.reactor.api.exception.BidException;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 拍卖上下文对象
 *
 * @author Owen
 * @since 2018/1/9
 */
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuctionContext implements Serializable {

	/**
	 * 竞拍编号
	 */
	@Setter
	@Getter
	private String auctionNo;
	/**
	 * 有效出价数
	 */
	@Getter
	private int validBidCount;
	/**
	 * 总共出价数
	 */
	@Getter
	private int totalBidCount;
	/**
	 * 保底出价次数
	 */
	@Getter
	private int expectCount;
	/**
	 * 最近一次拍卖价
	 */
	@Getter
	private BigDecimal lastPrice;
	/**
	 * 一次出价加价价格
	 */
	@Getter
	private BigDecimal stepPrice;
	/**
	 * 最近一次出价人
	 */
	@Getter
	private Bidder lastBidder;
	/**
	 * 竞拍状态
	 */
	@Getter
	private AuctionStatus status;
	/**
	 * 出价倒计时
	 */
	@Getter
	private int bidCountDown;
	/**
	 * 自动出价连续次数
	 */
	@Getter
	private int autoBidDurationTimes;

	private AuctionContext(String auctionNo) {
		this.auctionNo = auctionNo;
	}

	public static AuctionContext create(String auctionNo) {
		return new AuctionContext(auctionNo);
	}

	public AuctionContext setValidBidCount(int validBidCount) {
		this.validBidCount = validBidCount;
		return this;
	}

	public AuctionContext setTotalBidCount(int totalBidCount) {
		this.totalBidCount = totalBidCount;
		return this;
	}

	public AuctionContext setExpectCount(int expectCount) {
		this.expectCount = expectCount;
		return this;
	}

	public AuctionContext setLastPrice(BigDecimal lastPrice) {
		this.lastPrice = lastPrice;
		return this;
	}

	public AuctionContext setStepPrice(BigDecimal stepPrice) {
		this.stepPrice = stepPrice;
		return this;
	}

	public AuctionContext setLastBidder(Bidder lastBidder) {
		this.lastBidder = lastBidder;
		return this;
	}

	public AuctionContext setBidCountDown(int bidCountDown) {
		this.bidCountDown = bidCountDown;
		return this;
	}

	public AuctionContext setStatus(AuctionStatus status) {
		this.status = status;
		return this;
	}

	public AuctionContext setAutoBidDurationTimes(int autoBidDurationTimes) {
		this.autoBidDurationTimes = autoBidDurationTimes;
		return this;
	}

	public boolean isLastPrice(BigDecimal price) {
		return this.lastPrice.compareTo(price) == 0;
	}

	public boolean isLastBidder(Bidder bidder) {
		return lastBidder != null && lastBidder.equals(bidder);
	}

	/**
	 * 提价
	 * <p>
	 * 在 {@link #lastPrice)} 基础上增加 {@link #stepPrice}
	 * </p>
	 * 
	 * @return 最新的出价
	 */
	private BigDecimal increasePrice() {
		this.lastPrice = this.lastPrice.add(this.stepPrice);
		return this.lastPrice;
	}

	/**
	 * 增加出价次数
	 * 
	 * @param invalidBid
	 *            是否无效出价标识
	 * @return 最新的总出价次数
	 */
	private int increaseCount(boolean invalidBid) {
		this.totalBidCount += 1;

		if (!invalidBid) {
			this.validBidCount += 1;
		}

		return this.totalBidCount;
	}

	public AuctionContext onBid(Bid bid) {
		this.increasePrice();
		this.increaseCount(bid.isInvalidBid());
		this.setLastBidder(bid.getBidder());
		if (Bidder.isAutoBidder(bid.getBidder())) {
			this.autoBidDurationTimes += 1;
		} else {
			this.autoBidDurationTimes = 0;
		}
		return this;
	}

	public AuctionContext onComplete() {
		this.status = AuctionStatus.COMPLETE;
		return this;
	}

	@JSONField(serialize = false)
	public boolean isComplete() {
		return AuctionStatus.COMPLETE.equals(this.status);
	}

	/**
	 * 判断是否可出价
	 */
	public void checkBiddable(Bidder bidder) {
		// 竞拍已结束
		if (this.isComplete()) {
			throw BidException.auctionCompleted();
		}

		// 已是最后出价人
		if (this.isLastBidder(bidder)) {
			throw BidException.lastBidder();
		}
	}

}
