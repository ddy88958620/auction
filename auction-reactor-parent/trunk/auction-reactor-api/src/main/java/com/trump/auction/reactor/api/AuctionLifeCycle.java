package com.trump.auction.reactor.api;

/**
 * 竞拍回调
 *
 * @author Owen
 * @since 2018/1/22
 */
public interface AuctionLifeCycle {

    /**
     * 竞拍开始
     * @param auctionNo 竞拍编号
     */
    void onStart(String auctionNo);

    /**
     * 竞拍完成
     * @param auctionNo 竞拍编号
     */
    void onComplete(String auctionNo);
}
