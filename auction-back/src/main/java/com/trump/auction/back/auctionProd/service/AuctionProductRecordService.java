package com.trump.auction.back.auctionProd.service;

import com.trump.auction.back.auctionProd.model.AuctionProductRecord;

/**
 * @author: zhangqingqiang.
 * @date: 2018/1/25 0025.
 * @Description: 拍品快照 .
 */
public interface AuctionProductRecordService {
    AuctionProductRecord getRecordByAuctionId(Integer auctionId);

    AuctionProductRecord queryRecordByAuctionNo(Integer auctionNo);
}
