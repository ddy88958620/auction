package com.trump.auction.back.auctionProd.dao.read;

import com.trump.auction.back.auctionProd.model.AuctionTxnDetail;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionTxnDetailDao {
    int deleteByPrimaryKey(Integer id);

    int insert(AuctionTxnDetail record);

    int insertSelective(AuctionTxnDetail record);

    AuctionTxnDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AuctionTxnDetail record);

    int updateByPrimaryKey(AuctionTxnDetail record);
}