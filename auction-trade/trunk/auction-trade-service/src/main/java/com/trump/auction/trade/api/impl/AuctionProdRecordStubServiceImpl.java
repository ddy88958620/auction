package com.trump.auction.trade.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cf.common.utils.JsonResult;
import com.trump.auction.trade.api.AuctionProdRecordStubService;
import com.trump.auction.trade.service.AuctionProdRecordService;
import com.trump.auction.trade.vo.AuctionProductRecordVo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description: 拍品快照
 * @author: zhangqingqiang
 * @date: 2018-01-09 14:24
 **/
@Service(version = "1.0.0")
public class AuctionProdRecordStubServiceImpl implements AuctionProdRecordStubService {

    @Autowired
    private AuctionProdRecordService auctionProdRecordService;
    @Override
    public JsonResult saveRecord(AuctionProductRecordVo record) {
        return auctionProdRecordService.saveRecord(record);
    }
}
