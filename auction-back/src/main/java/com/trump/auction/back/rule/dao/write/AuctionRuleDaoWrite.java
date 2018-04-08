package com.trump.auction.back.rule.dao.write;

import com.trump.auction.back.rule.model.AuctionRule;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 竞拍规则管理
 *
 * @author
 * @create 2018-01-03 15:57
 **/

@Repository
public interface AuctionRuleDaoWrite {

    /**
     * 添加
     * @param auctionRule
     * @return
     */
    Integer insertAuctionRule(AuctionRule auctionRule);

    /**
     * 修改
     * @param auctionRule
     * @return
     */
    int updateAuctionRule(AuctionRule auctionRule);

    /**
     * 删除
     * @param ids
     * @return
     */
    int deleteAuctionRule(String[] ids);
}
