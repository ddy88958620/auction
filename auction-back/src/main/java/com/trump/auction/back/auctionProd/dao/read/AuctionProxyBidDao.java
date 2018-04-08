package com.trump.auction.back.auctionProd.dao.read;

import com.trump.auction.back.auctionProd.model.AuctionProxyBid;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionProxyBidDao {
    int deleteByPrimaryKey(Integer id);

    int insert(AuctionProxyBid record);

    int insertSelective(AuctionProxyBid record);

    AuctionProxyBid selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AuctionProxyBid record);

    int updateByPrimaryKey(AuctionProxyBid record);
}