package com.trump.auction.back.auctionProd.service.impl;

import com.trump.auction.back.auctionProd.dao.read.AuctionProductRecordDao;
import com.trump.auction.back.auctionProd.model.AuctionProductRecord;
import com.trump.auction.back.auctionProd.service.AuctionProductRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: 拍品快照
 * @author: zhangqingqiang
 * @date: 2018-01-25 20:51
 **/
@Service
public class AuctionProductRecordServiceImpl implements AuctionProductRecordService {
    @Autowired
    private AuctionProductRecordDao auctionProductRecordDao;

    @Override
    public AuctionProductRecord getRecordByAuctionId(Integer auctionId) {
        return auctionProductRecordDao.findProdRecordByAuctionNum(auctionId);
    }

    @Override
    public AuctionProductRecord queryRecordByAuctionNo(Integer auctionNo) {
        return auctionProductRecordDao.queryRecordByAuctionNo(auctionNo);
    }
}
