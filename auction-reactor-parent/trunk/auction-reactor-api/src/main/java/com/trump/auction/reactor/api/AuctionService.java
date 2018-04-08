package com.trump.auction.reactor.api;

/**
 * 竞拍服务
 *
 * @author Owen
 * @since 2018/1/22
 */
public interface AuctionService {

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
