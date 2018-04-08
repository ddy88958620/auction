package com.trump.auction.trade.service.impl;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trump.auction.trade.dao.AuctionInfoDao;
import com.trump.auction.trade.dao.sharding.ShardingAuctionDetailDao;
import com.trump.auction.trade.domain.AuctionDetail;
import com.trump.auction.trade.domain.AuctionInfo;
import com.trump.auction.trade.model.BidResult;
import com.trump.auction.trade.model.UserBidModel;
import com.trump.auction.trade.service.AuctionBidService;
import com.trump.auction.trade.service.AuctionInfoService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuctionBidServiceImpl implements AuctionBidService {

    @Autowired
    private ShardingAuctionDetailDao shardingAuctionDetailDao;

    @Autowired
    private AuctionInfoService auctionInfoService;

    @Autowired
    private AuctionInfoDao  auctionInfoDao;

    @Override
    public UserBidModel userBidInfo(Integer userId, Integer auctionId) {
        AuctionInfo auctionInfo=auctionInfoService.selectByPrimaryKey(auctionId);
        AuctionDetail auctionDetail=shardingAuctionDetailDao.selectByUserId(userId,auctionId,String.valueOf(userId));
        UserBidModel userBidModel = new UserBidModel();
        if(null!=auctionDetail) {
            userBidModel.setPaymentPrice(null==auctionInfo.getStartPrice()?null:auctionInfo.getStartPrice());
            userBidModel.setPreviewPic(auctionInfo.getPreviewPic());
            userBidModel.setReturnPrice(null == auctionDetail.getReturnPrice() ? new BigDecimal("0") : auctionDetail.getReturnPrice());
            userBidModel.setBidCount(auctionDetail.getBidCount());
            userBidModel.setSalePrice(auctionInfo.getProductPrice());
            userBidModel.setFinalPrice(auctionInfo.getFinalPrice()==null?null:auctionInfo.getFinalPrice());
            userBidModel.setUserName(auctionInfo.getWinUserDesc());
            userBidModel.setEndTime(auctionInfo.getEndTime()==null?null:auctionInfo.getEndTime());
        }
        return userBidModel;
    }

    @Override
    public BidResult upADetailOrderStatus(Integer userId, Integer auctioId) {
            return BidResult.successStatus();
    }

    @Override
    public BidResult updCollectCount(Integer auctionId) {
        auctionInfoDao.updCollectCount(auctionId);
        return BidResult.successStatus();
    }

    @Override
    public BidResult updPageViewCount(Integer auctionId) {
        auctionInfoDao.updPageViewCount(auctionId);
        return BidResult.successStatus();
    }

    @Override
    public BidResult CancelCollectCount(Integer auctionId) {
        auctionInfoDao.CancelCollectCount(auctionId);
        return BidResult.successStatus();
    }


}
