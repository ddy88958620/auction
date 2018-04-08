package com.trump.auction.back.auctionProd.dao.read;

import com.trump.auction.back.auctionProd.model.AuctionProductRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionProductRecordDao {
    int deleteByPrimaryKey(Integer id);

    int insert(AuctionProductRecord record);

    int insertSelective(AuctionProductRecord record);

    AuctionProductRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AuctionProductRecord record);

    int updateByPrimaryKey(AuctionProductRecord record);

    /**
     * 根据拍品期数ID查询拍品详细信息
     * @param auctionNum
     * @return
     */
    AuctionProductRecord findProdRecordByAuctionNum(@Param("auctionNum") Integer auctionNum);

    AuctionProductRecord queryRecordByAuctionNo(Integer auctionNo);
}