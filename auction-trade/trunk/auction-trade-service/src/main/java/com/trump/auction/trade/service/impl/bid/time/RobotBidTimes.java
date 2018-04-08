package com.trump.auction.trade.service.impl.bid.time;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.trump.auction.reactor.api.AuctionService;
import com.trump.auction.reactor.api.model.AuctionContext;
import com.trump.auction.reactor.api.model.BidRequest;
import com.trump.auction.reactor.api.model.BidType;
import com.trump.auction.reactor.repository.BidRepository;
import com.trump.auction.trade.dao.AuctionInfoDao;
import com.trump.auction.trade.domain.AuctionInfo;
import com.trump.auction.trade.dto.AuctionInfoDto;
import com.trump.auction.trade.service.RobotService;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisCluster;

@Configuration
@EnableScheduling
@Slf4j
public class RobotBidTimes {
    @Autowired
    private AuctionInfoDao auctionInfoDao;

    @Autowired
    private BidRepository bidRepository;
    @Autowired
    private AuctionService auctionService;
    @Autowired
    private RobotService robotService;
    @Autowired
    private JedisCluster jedisCluster;

    @Scheduled(cron="0/2 * * * * ?")
    public void  updsbidtask(){
        log.info("RobotBidTimes BID task ");
        List<AuctionInfo> auctionInfoList=auctionInfoDao.findAuctionInfoStatus(3);
        for(AuctionInfo auctionInfo:auctionInfoList){
            AuctionContext auctionContext = bidRepository.getContext(String.valueOf(auctionInfo.getId()));
            if(null==auctionContext){
                if(auctionInfo.getStartTime().getTime()<=System.currentTimeMillis()){
                    robotService.addRedisRotbot(auctionInfo);
                    auctionInfoDao.upAcutionStatus(auctionInfo.getId(),1);
                    auctionService.onStart(String.valueOf(auctionInfo.getId()));
                }
            }
        }
    }

    /**
     * 封装调用参数我（单一出价）
     * @param
     * @return
     */
    private static BidRequest getBidRequest(AuctionInfo auctionInfo){
        //用户信息
        BidRequest  bidRequest=new BidRequest();
        bidRequest.setAuctionNo(String.valueOf(auctionInfo.getId()));
        bidRequest.setBidder(null);
        bidRequest.setBidType(BidType.AUTO);
        bidRequest.setBizNo(null);
        bidRequest.setExpectLastPrice(auctionInfo.getStartPrice());
        return  bidRequest;
    }
    private AuctionInfoDto  getAuctionInfoDto(AuctionInfo auctionInfo){
         AuctionInfoDto auctionInfoDto=new AuctionInfoDto();
         auctionInfoDto.setAuctionId(auctionInfo.getId());
         auctionInfoDto.setRequestId(auctionInfo.getTotalBidCount());
         auctionInfoDto.setRequestReason(1);
         auctionInfoDto.setProductionPrice(auctionInfo.getFloorPrice().doubleValue());
         auctionInfoDto.setValidBiddingAmount(auctionInfo.getIncreasePrice().multiply(new BigDecimal(auctionInfo.getValidBidCount().toString())).add(auctionInfo.getStartPrice()).doubleValue());
         auctionInfoDto.setTotalBiddingAmount(auctionInfo.getIncreasePrice().multiply(new BigDecimal(auctionInfo.getFloorBidCount().toString())).add(auctionInfo.getStartPrice()).doubleValue());
         auctionInfoDto.setBidderCountNow(auctionInfo.getPersonCount().intValue());
         auctionInfoDto.setBidderCountFinal(auctionInfo.getTotalBidCount());
         auctionInfoDto.setHighestBiddingPriceNow(auctionInfo.getIncreasePrice().multiply(new BigDecimal(auctionInfo.getTotalBidCount().toString())).add(auctionInfo.getStartPrice()).doubleValue());
         auctionInfoDto.setHighestPriceBidder(0);
         auctionInfoDto.setRecent3Bidder(0);
         return  auctionInfoDto;
    }
}
