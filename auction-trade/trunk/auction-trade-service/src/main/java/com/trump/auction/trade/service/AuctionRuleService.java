package com.trump.auction.trade.service;

import com.trump.auction.trade.domain.AuctionRule;
import com.trump.auction.trade.model.AuctionRuleModel;

/**
 * 拍卖
 *
 * @author zhangliyan
 * @create 2018-01-02 17:48
 **/
public interface AuctionRuleService {
    /**
     * 添加
     * @param auctionRule
     * @return
     */
    int insertAuctionRule(AuctionRule auctionRule);

    /**
     * 根据id更新
     * @param auctionRule
     * @return
     */
    int updateAuctionRule(AuctionRule auctionRule);

    /**
     * 根据id删除
     * @param ids
     * @return
     */
    int deleteAuctionRule(String[] ids);

    /**
     * 启用竞拍规则
     * @param auctionRuleModel
     * @return
     */
    int enable(AuctionRuleModel auctionRuleModel) throws Exception;

}
