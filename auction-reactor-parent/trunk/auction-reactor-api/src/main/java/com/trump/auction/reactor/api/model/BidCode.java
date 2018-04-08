package com.trump.auction.reactor.api.model;

/**
 * 出价响应码
 *
 * @author Owen
 * @since 2018/1/18
 */
public enum BidCode {

    /**
     * 出价成功
     */
    SUCCESS,
    /**
     * 竞拍已结束
     */
    AUCTION_COMPLETED,
    /**
     * 最后出价已更新
     */
    LAST_PRICE_EXPIRED,
    /**
     * 同上一出价人
     */
    LAST_BIDDER,
    /**
     * 已在出价队列中
     */
    BIDDER_QUEUED,
    /**
     * 默认错误
     */
    DEFAULT_ERROR
}
