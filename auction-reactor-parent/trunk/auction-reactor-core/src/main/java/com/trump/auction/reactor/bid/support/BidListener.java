package com.trump.auction.reactor.bid.support;

/**
 * 出价事件监听器
 *
 * @author Owen
 * @since 2018/1/11
 */
public interface BidListener<E extends BidEvent> {

    void onEvent(E event);

}
