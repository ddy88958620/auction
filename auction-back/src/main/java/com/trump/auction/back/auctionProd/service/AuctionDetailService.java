package com.trump.auction.back.auctionProd.service;


import com.cf.common.util.page.Paging;
import com.trump.auction.back.auctionProd.model.AuctionDetail;
import com.trump.auction.back.auctionProd.vo.AuctionOrderVo;
import com.trump.auction.back.product.vo.ParamVo;

import java.util.List;
import java.util.Map;

/**
 * @author: .
 * @date: 2018/1/8 0008.
 * @Description:  .
 */
public interface AuctionDetailService {


    /**
     * 获取往期订单信息
     * @param auctionId
     * @return
     */
    AuctionOrderVo getPastOrder(Integer auctionId);

    /**
     * 根据拍品id查看期数详情
     * @param id
     * @return
     */
    List<AuctionDetail> queryAuctionDetailByAuctionId(Integer id);

    /**
     * 查看期数详情列表
     * @param paramVo
     * @return
     */
    Paging<AuctionDetail> viewAuctionInfoList(ParamVo paramVo);
}
