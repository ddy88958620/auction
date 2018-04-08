package com.trump.auction.trade.dao;

import com.trump.auction.trade.domain.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 出价服务所需所有查询
 */
@Repository
public interface AuctionBidSystemDao {
    /**
     * 查询最后一条出价记录
     * @param auctionId 拍品期数表主键id
     * @return
     */
    AuctionBidetail findLastBidD(@Param("auctionId") Integer auctionId);

    /**
     * 查询委托出价最后一条记录
     * @param userId 用户id
     * @param status 状态 1出价未处理 2出价处理中 3出价完成
     * @param auctionNo 拍品期数表主键id
     * @return
     */
    AuctionBidInfo findLastBid(@Param("userId") Integer userId, @Param("status") Integer status, @Param("auctionNo") Integer auctionNo);

    /**
     * 查询拍品主记录
     * @param auctionId 拍品期数表主键id
     * @return
     */
    AuctionInfo  findAuctionInfo(@Param("auctionId") Integer auctionId);

    /**
     *扣款记录先落地
     * @param auctionTxnDetail 支付记录表
     * @return
     */
    int  txnSave(AuctionTxnDetail auctionTxnDetail);

    /**
     * 修改支付记录表
     * @param Id
     * @param txnStatus   支付状态
     * @param freeCount   赠币处理
     * @param validCount  拍币处理
     * @param currency     币种
     * @return
     */
    int updTxnStatus(@Param("id") String Id,@Param("txnStatus") Integer txnStatus,@Param("freeCount")  Integer freeCount,@Param("validCount")  Integer validCount,@Param("currency")  Integer currency);

    /**
     * 插入用户竞拍记录
     * @param auctionDetail 用户竞拍记录
     * @return
     */
    int saveAuctionDetail(AuctionDetail auctionDetail);
}
