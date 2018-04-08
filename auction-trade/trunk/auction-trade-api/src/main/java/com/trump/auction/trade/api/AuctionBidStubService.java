package com.trump.auction.trade.api;

import com.trump.auction.trade.model.BidParam;
import com.trump.auction.trade.model.BidResult;
import com.trump.auction.trade.model.UserBidModel;

/**
 * 拍品出价接口
 */
public interface AuctionBidStubService {

     BidResult bidOperation(BidParam bidParam);

     BidResult    upADetailOrderStatus(Integer userId,Integer auctioId);

     UserBidModel userBidInfo(Integer UserId,Integer auctioId);

     /**
      *收藏
      * @param auctionId
      * @return
      */
     BidResult   updCollectCount(Integer auctionId);
     /**
      *取消收藏
      * @param auctionId
      * @return
      */
     BidResult   CancelCollectCount(Integer auctionId);


     /**
      *围观
      * @param auctionId
      * @return
      */
     BidResult   updPageViewCount(Integer auctionId);
}
