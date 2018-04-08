package com.trump.auction.trade.dao;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.trump.auction.trade.domain.AuctionInfo;
@Repository
public interface AuctionInfoDao {
    int deleteByPrimaryKey(Integer id);

    int insert(AuctionInfo record);


    AuctionInfo selectByPrimaryKey(@Param("auctionId") Integer id);

    AuctionInfo getLastInfo(@Param("auctionProdId")Integer auctionProdId);


    List<AuctionInfo> queryAuctionInfoByClassify(@Param("classifyId") Integer classifyId);


    List<AuctionInfo> queryNewestAuctionInfo(@Param("status") Integer status);

    List<AuctionInfo> queryHotAuctionInfo(@Param("auctionIds") List<Integer> auctionIds);

    AuctionInfo queryAuctionByProductIdAndNo(@Param("auctionProdId") Integer auctionProdId,@Param("auctionId") Integer auctionNo);

    /**
     * 获取最新一期
     * @param auctionProdId
     * @param
     * @return
     */
    AuctionInfo getNextAuctionInfo(@Param("auctionProdId") Integer auctionProdId);


    /**
     * 获取用户拍中的指定期次
     * @param map
     * @return
     */
    AuctionInfo getShotAuctionInfo(Map<String,Object> map);


    List<AuctionInfo>   findAuctionInfoStatus(@Param("status") Integer status);

    int updAuctionBidInfo(AuctionInfo auctionInfo);

    int  upAcutionStatus(@Param("id") Integer id,@Param("status") Integer status);

    int  upAcutionSuccess(@Param("id") Integer id, @Param("status") Integer status, @Param("userId") Integer userId, @Param("userName") String userName, @Param("finalPrice")BigDecimal finalPrice);

    List<String>  findIds(@Param("status") Integer Status);

    /**
     * 查询最新一期拍品信息
     * @param auctionProdId 拍品id
     * @return
     */
    AuctionInfo queryLastAuction(@Param("auctionProdId") Integer auctionProdId);

    int   updCollectCount(@Param("auctionId")Integer auctionId);

    int   updPageViewCount(@Param("auctionId")Integer auctionId);

    int   CancelCollectCount(@Param("auctionId")Integer auctionId);

    int  findById(@Param("auctionProdId") Integer auctionProdId);
}