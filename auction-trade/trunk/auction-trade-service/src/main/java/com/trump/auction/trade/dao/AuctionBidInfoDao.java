package com.trump.auction.trade.dao;

import org.springframework.stereotype.Repository;

import com.trump.auction.trade.domain.AuctionBidInfo;

@Repository
public interface AuctionBidInfoDao {
	
     int insert(AuctionBidInfo record);
}