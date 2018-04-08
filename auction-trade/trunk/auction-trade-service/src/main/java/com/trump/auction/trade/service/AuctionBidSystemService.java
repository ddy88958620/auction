package com.trump.auction.trade.service;

import com.trump.auction.trade.domain.AuctionBidetail;
import com.trump.auction.trade.model.BidParam;
import com.trump.auction.trade.model.BidResult;

/**
 * 用户出价
 */
public interface AuctionBidSystemService {
    /**
     * 用户出价
     * @param bidParam 参数集合
     * @return
     */
      BidResult bidOperation(BidParam bidParam);


}
