package com.trump.auction.back.auctionProd.service;

import com.cf.common.util.page.Paging;
import com.trump.auction.back.auctionProd.model.AuctionInfo;
import com.trump.auction.back.auctionProd.vo.AuctionCondition;

import java.util.List;
import java.util.Map;

/**
 * @author: zhangqingqiang.
 * @date: 2018/1/8 0008.
 * @Description: 拍卖信息 .
 */
public interface AuctionInfoService {
    List<AuctionInfo> queryLastAuction(AuctionCondition condition);

    /**
     * 查找正在拍商品
     * @param params
     * @return
     */
    Paging<AuctionInfo> findList(Map<String, Object> params);

    /**
     * 获取正在拍详情
     * @param id
     * @return
     */
    AuctionInfo findAuctionInfoById(Integer id);

    /**
     * 通过状态查询拍卖信息
     * @param status 拍品状态（1正在拍，2已完结，3未开始）
     * @return
     */
    List<AuctionInfo> queryAuctionByStatus(Integer status);

    /**
     * 根据拍品id查询最新一期拍品信息
     * @param id
     * @return
     */
    AuctionInfo queryLastOneAuctionByAuctionProdId(Integer id);
}
