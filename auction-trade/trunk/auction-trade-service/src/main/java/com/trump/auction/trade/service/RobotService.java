package com.trump.auction.trade.service;

import com.trump.auction.reactor.api.model.Bidder;
import com.trump.auction.trade.domain.AuctionInfo;
import com.trump.auction.trade.domain.RobotInfo;

import java.util.List;

/**
 * 拍卖
 *
 * @author zhangliyan
 * @create 2018-01-02 17:48
 **/
public interface RobotService {

    /**
     * 添加
     * @param robotInfo
     * @return
     */
    Integer insertRobot(RobotInfo robotInfo);

    /**
     * 修改
     * @param robotInfo
     * @return
     */
    int saveUpdateRobot(RobotInfo robotInfo);

    /**
     * 删除
     * @param ids
     * @return
     */
    int deleteRobot(String[] ids);

    List<RobotInfo> findRobotInfo(Integer start, Integer end);

    RobotInfo findRobot(AuctionInfo auctionInfo, Bidder lastBidder);

    String addRedisRotbot(AuctionInfo auctionInfo);

}
