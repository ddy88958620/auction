package com.trump.auction.trade.dao;

import com.trump.auction.trade.domain.AuctionRule;
import org.springframework.stereotype.Repository;


/**
 * 拍卖
 *
 * @author
 * @create 2018-01-02 17:49
 **/
@Repository
public interface AuctionRuleDao {

    AuctionRule selectByPrimaryKey(Integer id);
    /**
     * 添加
     * @param auctionRule
     * @return
     */
    int insertAuctionRule(AuctionRule auctionRule);
    /**
     * 修改竞拍规则
     * @param auctionRule
     * @return
     */
    int updateAuctionRule(AuctionRule auctionRule);
    /**
     * 修改竞拍规则
     * @param auctionRule
     * @return
     */
    int updateByPrimaryKeySelective(AuctionRule auctionRule);

    /**
     * 删除
     * @param ids
     * @return
     */
    int deleteAuctionRule(String[] ids);


    /**
     * 启用
     * @param rule
     * @return
     */
    int updateStatus(AuctionRule rule);

}
