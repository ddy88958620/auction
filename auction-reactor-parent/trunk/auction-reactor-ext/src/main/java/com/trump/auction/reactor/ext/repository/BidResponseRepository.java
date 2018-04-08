package com.trump.auction.reactor.ext.repository;

import com.trump.auction.reactor.api.model.BidResponse;

import java.util.List;

/**
 * 出价记录结果仓储
 *
 * @author Owen
 * @since 2018/1/24
 */
public interface BidResponseRepository {

    /**
     * 查询最近出价记录
     * @param auctionNo 竞拍编号
     * @param count 查询数量
     */
    List<BidResponse> getLatest(String auctionNo, int count);

    /**
     * 只保留最近出价的记录
     * @param auctionNo 竞拍编号
     * @param count 保留的数量
     */
    void remainLatest(String auctionNo, int count);
}
