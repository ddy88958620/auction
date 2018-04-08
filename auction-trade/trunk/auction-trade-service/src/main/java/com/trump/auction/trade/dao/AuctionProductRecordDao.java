package com.trump.auction.trade.dao;

import com.trump.auction.trade.domain.AuctionProductRecord;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 */
@Repository
public interface AuctionProductRecordDao {

    /**
     * 获取记录
     * @param auctionId
     * @return
     */
    AuctionProductRecord getRecordByAuctionNum(@Param("auctionId") Integer auctionId);

    int insert(AuctionProductRecord record);
}