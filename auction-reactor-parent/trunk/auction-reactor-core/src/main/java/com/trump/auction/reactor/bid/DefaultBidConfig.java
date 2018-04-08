package com.trump.auction.reactor.bid;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * {@link BidConfig} 默认实现
 *
 * @author Owen
 * @since 2018/1/11
 */
@Component
public class DefaultBidConfig implements BidConfig, InitializingBean {

	@Value("${auction.auto-bid.min-time-before-cd-end:2000}")
	private int minAutoBidTime;

	@Value("${auction.delegate-bid.delay-time-range}")
	private float delayTimeRange;

	@Value("${auction.delegate-bid.min-delay-time:1000}")
	private long minDelayTime;

	@Value("${auction.auto-bid.times-to-hit:3}")
	private int autoBidTimesToHit;
	private int minDeBidTime = 3500;

	@Override
	public long nextAutoBidDelayTime(int countDown) {
		Assert.isTrue(countDown > 0, "[countDown]");
		long countDownMs = secondsToMs(countDown);

		if (countDownMs <= minAutoBidTime) {
			return countDownMs;
		}

		return countDownMs - minAutoBidTime;
	}

	private static int secondsToMs(int duration) {
		return (int) TimeUnit.MILLISECONDS.convert(duration, TimeUnit.SECONDS);
	}

	@Override
	public long nextDelegateBidDelayTime(int countDown) {
		Assert.isTrue(countDown > 0, "[countDown]");
		long countDownMs = secondsToMs(countDown);

		if (countDownMs <= minDeBidTime) {
			return countDownMs;
		}

		return countDownMs - minDeBidTime;
	}

	@Override
	public int autoBidTimesToHit() {
		return autoBidTimesToHit;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.isTrue(minAutoBidTime > 0, "[minAutoBidTime]");
		Assert.isTrue(autoBidTimesToHit > 0, "[autoBidTimesToHit]");
		Assert.isTrue(minDelayTime > 0, "[minDelayTime]");
	}
}
