package com.trump.auction.back.auctionProd.service;

import com.cf.common.util.page.Paging;
import com.trump.auction.back.auctionProd.model.AuctionBidDetail;

import java.util.Map;

/**
 * @description:拍品出价
 * @author: zhangliyan
 * @date: 2018.01.17 15:20:33
 **/
public interface AuctionBidDetailService {
    /**
     * 查询出价详情列表
     * @param params
     * @return
     */
    Paging<AuctionBidDetail> auctionBidDetailList(Map<String,Object> params);
}
