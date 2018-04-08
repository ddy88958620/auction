package com.trump.auction.trade.service;


import com.trump.auction.reactor.api.model.BidHitResponse;
import com.trump.auction.reactor.api.model.BidResponse;

public interface BidManagerService {
    /**
     *拍品竞拍中处理
     * @param bidResponse
     */
      void   bidHandle(BidResponse bidResponse);

    /**
     *拍品竞拍完成处理
     * @param bidHitResponse
     */
      void   bidFinish(BidHitResponse bidHitResponse);



}
