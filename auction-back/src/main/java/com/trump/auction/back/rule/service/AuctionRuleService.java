package com.trump.auction.back.rule.service;

import com.cf.common.util.page.Paging;
import com.trump.auction.back.product.vo.ParamVo;
import com.trump.auction.back.rule.model.AuctionRule;

import java.util.List;
import java.util.Map;

/**
 * 竞拍规则管理
 *
 * @author zhangliyan
 * @create 2018-01-03 14:59
 **/
public interface AuctionRuleService {

    /**
     * 分页查询规则列表
     * @param params
     * @return
     */
    Paging<AuctionRule> findAuctionRulePage(ParamVo paramVo);

    /**
     * 查询
     * @return
     */
    List<AuctionRule> findAuctionRuleList();

    /**
     * 根据id查询规则列表
     * @param id
     * @return
     */
    AuctionRule findAuctionRuleById(Integer id);

    /**
     * 获取id
     * @param id
     * @return
     */
    AuctionRule getAuctionRule(Integer id);

    /**
     * 根据id查询
     * @param ruleId
     * @return
     */
    AuctionRule queryRuleById(int ruleId);
}
