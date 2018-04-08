package com.trump.auction.back.auctionProd.dao.read;

import com.trump.auction.back.auctionProd.model.AuctionBidDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AuctionBidDetailDao {
    int deleteByPrimaryKey(Integer id);

    int insert(AuctionBidDetail record);

    int insertSelective(AuctionBidDetail record);

    AuctionBidDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AuctionBidDetail record);

    int updateByPrimaryKey(AuctionBidDetail record);

    AuctionBidDetail selectByAuctionId(@Param("auctionId") Integer id);

    /**
     * 根据用户id和拍品id查询列表
     * @param params
     * @return
     */
    List<AuctionBidDetail> auctionBidDetailList(Map<String,Object> params);
}