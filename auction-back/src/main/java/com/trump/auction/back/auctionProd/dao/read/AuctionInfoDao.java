package com.trump.auction.back.auctionProd.dao.read;

import com.trump.auction.back.auctionProd.model.AuctionInfo;
import com.trump.auction.back.auctionProd.vo.AuctionCondition;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AuctionInfoDao {
    /**
     * 删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 添加
     * @param record
     * @return
     */
    int insert(AuctionInfo record);

    /**
     * 查询
     * @param id
     * @return
     */
    AuctionInfo selectByPrimaryKey(Integer id);

    /**
     * 更新
     * @param record
     * @return
     */
    int updateByPrimaryKey(AuctionInfo record);

    /**
     * 查询列表
     * @param params
     * @return
     */
    List<AuctionInfo> findList(Map<String, Object> params);
    List<AuctionInfo> queryLastAuction(AuctionCondition condition);

    /**
     * 获取最新的一条期次信息通过拍品ID
     * @param auctionProdId
     * @return
     */
    AuctionInfo queryLastOneAuctionByAuctionProdId(Integer auctionProdId);

    /**
     * 根据状态查询拍卖信息
     * @param status
     * @return
     */
    List<AuctionInfo> queryAuctionByStatus(@Param("status") Integer status);
}