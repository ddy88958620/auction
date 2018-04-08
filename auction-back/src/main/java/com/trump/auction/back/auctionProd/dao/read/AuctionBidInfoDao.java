package com.trump.auction.back.auctionProd.dao.read;

import com.trump.auction.back.auctionProd.model.AuctionBidInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionBidInfoDao {
    int deleteByPrimaryKey(Integer id);

    int insert(AuctionBidInfo record);

    int insertSelective(AuctionBidInfo record);

    AuctionBidInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AuctionBidInfo record);

    int updateByPrimaryKey(AuctionBidInfo record);
}