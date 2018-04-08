package com.trump.auction.reactor.bid;

import com.trump.auction.reactor.api.model.BidResponse;
import com.trump.auction.reactor.api.model.Bidder;
import com.trump.auction.reactor.api.model.MultiBidRequest;

/**
 * 出价排队处理
 *
 * @author Owen
 * @since 2018/1/16
 */
public interface BidQueueHandler {

    /**
     * 加入出价排队
     * @param bidRequest 出价请求
     */
    void join(MultiBidRequest bidRequest);

    /**
     * 安排下一个出价排队
     * @param auctionNo 竞拍编号
     * @return <code>true</code>  表示本次排队处理成功
     */
    boolean nextRound(String auctionNo);

    /**
     * 执行下一个出价
     * @param auctionNo 竞拍编号
     */
    BidResponse nextBid(String auctionNo);

    /**
     * 判断出价人是否还有未完成的出价
     * @param auctionNo 竞拍编号
     * @param bidder 出价人
     * @return <code>true</code> 表示出价人还有未完成的出价
     */
    boolean remainBid(String auctionNo, Bidder bidder);
}
