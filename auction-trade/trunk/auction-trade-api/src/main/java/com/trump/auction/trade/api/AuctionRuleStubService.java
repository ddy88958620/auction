package com.trump.auction.trade.api;


import com.trump.auction.trade.model.AuctionRuleModel;


/**
 * 拍卖
 *
 * @author zhangliyan
 * @create 2018-01-02 17:48
 **/
public interface AuctionRuleStubService {
    /**
     * 添加
     * @param auctionRuleModel
     * @return
     */
    int insertAuctionRule(AuctionRuleModel auctionRuleModel);


    /**
     * 更新竞拍规则
     * @param auctionRuleModel
     * @return
     */
    int updateAuctionRule(AuctionRuleModel auctionRuleModel);

    /**
     * 删除
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
