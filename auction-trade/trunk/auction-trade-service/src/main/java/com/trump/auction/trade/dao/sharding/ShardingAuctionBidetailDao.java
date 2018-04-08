package com.trump.auction.trade.dao.sharding;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.trump.auction.trade.domain.AuctionBidetail;
import com.trump.auction.trade.vo.BidVo;
import com.trump.auction.trade.vo.ParamVo;

/**
 * @author Administrator
 */
@Repository
public interface ShardingAuctionBidetailDao {


    /**
     * 获取最新的出价记录
     * @param paramVo
     * @return
     */
    List<BidVo> getLastBidRecord(ParamVo paramVo);

    /**
     * 分页查询出价记录
     * @param paramVo
     * @return
     */
    List<BidVo> find(ParamVo paramVo);

    /**
     * 最新出价记录
     * @param auctionId
     * @return
     */
    AuctionBidetail   findBidTime(int auctionId);

    /**
     * 最新3出价记录
     * @param auctionId
     * @return
     */
    List<AuctionBidetail>   findBidTimes(@Param("auctionId") int auctionId,@Param("number") int number);
}