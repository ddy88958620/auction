package com.trump.auction.trade.dao;


import org.springframework.stereotype.Repository;

import com.trump.auction.trade.domain.AuctionDetail;

/**
 *  竞拍订单信息Dao
 * @author Administrator
 */
@Repository
public interface AuctionDetailDao {
    
    int  saveAuctionDetail(AuctionDetail auctionDetail);

}