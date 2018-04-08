package com.trump.auction.reactor.bid.support;

import java.io.Serializable;

/**
 * 竞拍延时任务处理的源
 * @author Owen
 * @since 2018/1/10
 */
public interface BidEvent extends Serializable {

    /**
     * 竞拍编号
     */
    String getAuctionNo();
}
