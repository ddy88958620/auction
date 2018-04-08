package com.trump.auction.trade.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cf.common.util.page.Paging;
import com.cf.common.utils.JsonResult;
import com.trump.auction.trade.api.AuctionInfoStubService;
import com.trump.auction.trade.model.*;
import com.trump.auction.trade.service.AuctionInfoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @description: 拍品信息
 * @author: zhangqingqiang
 * @date: 2018-01-06 11:41
 **/
@Service(version = "1.0.0")
public class AuctionInfoStubServiceImpl implements AuctionInfoStubService{

    @Autowired
    private AuctionInfoService auctionInfoService;

    @Override
    public Paging<AuctionInfoModel> queryAuctionInfoByClassify(AuctionInfoQuery auctionQuery, int pageNum, int pageSize) {
        return auctionInfoService.queryAuctionInfoByClassify(auctionQuery,pageNum,pageSize);
    }

    @Override
    public Paging<AuctionInfoModel> queryNewestAuctionInfo(AuctionInfoQuery auctionQuery, int pageNum, int pageSize) {
        return auctionInfoService.queryNewestAuctionInfo(auctionQuery,pageNum,pageSize);
    }

    @Override
    public List<AuctionInfoModel> queryHotAuctionInfo(AuctionInfoQuery auctionQuery) {
        return auctionInfoService.queryHotAuctionInfo(auctionQuery);
    }

    @Override
    public JsonResult saveAuctionInfo(AuctionInfoModel auctionInfoModel) {
        return auctionInfoService.saveAuctionInfo(auctionInfoModel);
    }

    @Override
    public AuctionInfoModel queryAuctionByProductIdAndNo(AuctionInfoQuery auctionQuery) {
        return auctionInfoService.queryAuctionByProductIdAndNo(auctionQuery);
    }

    /**
     * 获取拍品信息通过拍品期数ID
     * @param auctionId
     * @return
     */
    @Override
    public AuctionInfoModel getAuctionInfoById(Integer auctionId) {
        return auctionInfoService.getAuctionInfoById(auctionId);
    }

    @Override
    public Integer findAuctionById(Integer auctionProdId) {
        return auctionInfoService.findAuctionById(auctionProdId);
    }


    @Override
    public JsonResult doAuctionTask(AuctionProductInfoModel prod, AuctionRuleModel rule, AuctionInfoModel auctionInfo,
                                    AuctionProductRecordModel lastRecord) {
        return auctionInfoService.doAuctionTask(prod,rule,auctionInfo,lastRecord);
    }



}
