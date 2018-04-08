package com.trump.auction.trade.dao;

import org.springframework.stereotype.Repository;

import com.trump.auction.trade.domain.AuctionBidetail;

/**
 * @author Administrator
 */
@Repository
public interface AuctionBidetailDao {


   int   saveAuctionBidetail(AuctionBidetail auctionBidetail);

 }