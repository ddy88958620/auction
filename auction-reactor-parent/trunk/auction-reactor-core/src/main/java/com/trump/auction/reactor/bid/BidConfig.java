package com.trump.auction.reactor.bid;

/**
 * 竞拍配置
 *
 * @author Owen
 * @since 2018/1/11
 */
public interface BidConfig {

    /**
     * 自动出价延时时间
     * <p>
     *     精确到毫秒
     * </p>
     * @param countDown 竞拍倒计时时间(秒)
     */
    long nextAutoBidDelayTime(int countDown);

    /**
     * 委派出价延时时间
     * <p>
     *     精确到毫秒
     * </p>
     * @param countDown 竞拍倒计时时间(秒)
     */
    long nextDelegateBidDelayTime(int countDown);

    /**
     * 连续 N 次自动出价可满足拍中条件
     * <p>
     *     前提是达到预设成交价
     * </p>
     * @return 满足拍中条件的连续自动出价次数
     */
    int autoBidTimesToHit();
}
