package com.trump.auction.trade.service;

import com.trump.auction.reactor.api.model.BidRequest;
import com.trump.auction.reactor.api.model.BidResponse;
import com.trump.auction.trade.model.BidParam;
import com.trump.auction.trade.model.BidResult;
import com.trump.auction.trade.model.UserBidModel;

public interface AuctionBidService {


    /**
     * 用户信息
     */
    UserBidModel userBidInfo(Integer userId,Integer auctionId);

    /**
     * 修改订单状态
     */
    BidResult  upADetailOrderStatus(Integer userId,Integer auctioId);

    BidResult   updCollectCount(Integer auctionId);

    BidResult   updPageViewCount(Integer auctionId);

    /**
     *取消收藏
     * @param auctionId
     * @return
     */
    BidResult   CancelCollectCount(Integer auctionId);
}
