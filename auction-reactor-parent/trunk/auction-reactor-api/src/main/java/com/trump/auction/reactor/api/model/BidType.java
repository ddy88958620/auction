package com.trump.auction.reactor.api.model;

/**
 * 出价类型
 *
 * @author Owen
 * @since 2018/1/17
 */
public enum BidType {

    /**
     * 正常出价
     */
    DEFAULT,
    /**
     * 自动出价
     */
    AUTO,
    /**
     * 委托出价
     */
    DELEGATE
}
