package com.trump.auction.back.auctionProd.dao.read;


import com.trump.auction.back.auctionProd.model.AuctionDetail;
import com.trump.auction.back.auctionProd.vo.AuctionOrderVo;
import com.trump.auction.back.product.vo.ParamVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 *  竞拍订单信息Dao
 * @author Administrator
 */
@Repository
public interface AuctionDetailDao {


    /**
     * 获取往期成交
     * @param auctionId
     * @param auctionStatus
     * @return
     */
    AuctionOrderVo selectByAuctionId(@Param("auctionId") Integer auctionId, @Param("auctionStatus") Integer auctionStatus);

    /**
     * 根据拍品id查看期数详情
     * @param id
     * @return
     */
    List<AuctionDetail> queryAuctionDetailByAuctionId(Integer id);

    /**
     * 根据期数id查看期数列表
     * @param paramVo
     * @return
     */
    List<AuctionDetail> viewAuctionInfoList(ParamVo paramVo);
}