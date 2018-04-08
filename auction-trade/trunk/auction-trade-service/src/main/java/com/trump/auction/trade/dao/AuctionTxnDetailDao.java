package com.trump.auction.trade.dao;

import com.trump.auction.trade.domain.AuctionTxnDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionTxnDetailDao {

    int insert(AuctionTxnDetail record);

    AuctionTxnDetail selectByPrimaryKey(Integer id);

    int updTxnStatus(@Param("id") String Id,@Param("txnStatus") Integer txnStatus,@Param("freeCount")  Integer freeCount,@Param("validCount")  Integer validCount,@Param("currency")  Integer currency);


    int updBidStatus(@Param("id") String id,@Param("bidStatus") String status);


    AuctionTxnDetail findByTxn(@Param("txnSeqNo") String Id);

}