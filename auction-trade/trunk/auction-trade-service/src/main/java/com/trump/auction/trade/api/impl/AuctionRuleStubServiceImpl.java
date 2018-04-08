package com.trump.auction.trade.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cf.common.util.mapping.BeanMapper;
import com.trump.auction.trade.api.AuctionRuleStubService;
import com.trump.auction.trade.domain.AuctionRule;
import com.trump.auction.trade.model.AuctionRuleModel;
import com.trump.auction.trade.service.AuctionRuleService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 拍卖详情
 *
 * @author zhangliyan
 * @create 2018-01-02 17:46
 **/
@Service(version = "1.0.0")
public class AuctionRuleStubServiceImpl implements AuctionRuleStubService {
    @Autowired
    private AuctionRuleService auctionRuleService;
    @Autowired
    private BeanMapper beanMapper;
    /**
     * 添加
     *
     * @param auctionRuleModel
     * @return
     */
    @Override
    public int insertAuctionRule(AuctionRuleModel auctionRuleModel) {
        return auctionRuleService.insertAuctionRule(beanMapper.map(auctionRuleModel, AuctionRule.class));
    }

    /**
     * 根据id删除
     *
     * @param ids
     * @return
     */
    @Override
    public int deleteAuctionRule(String[] ids) {
        return auctionRuleService.deleteAuctionRule(ids);
    }

    /**
     * 启用竞拍规则
     * @param auctionRuleModel
     * @return
     */
    @Override
    public int enable(AuctionRuleModel auctionRuleModel) throws Exception {
        return auctionRuleService.enable(auctionRuleModel);
    }

    /**
     * 根据id更新
     * @param auctionRuleModel
     * @return
     */
    @Override
    public int updateAuctionRule(AuctionRuleModel auctionRuleModel) {
        return auctionRuleService.updateAuctionRule(beanMapper.map(auctionRuleModel, AuctionRule.class));
    }
}
