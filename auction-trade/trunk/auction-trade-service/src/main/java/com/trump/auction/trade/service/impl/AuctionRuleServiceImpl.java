package com.trump.auction.trade.service.impl;

import com.cf.common.util.mapping.BeanMapper;
import com.trump.auction.trade.dao.AuctionRuleDao;
import com.trump.auction.trade.domain.AuctionRule;
import com.trump.auction.trade.model.AuctionRuleModel;
import com.trump.auction.trade.service.AuctionRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 拍卖
 *
 * @author zhangliyan
 * @create 2018-01-02 18:04
 **/
@Service
public class AuctionRuleServiceImpl implements AuctionRuleService {
    @Autowired
    private AuctionRuleDao auctionRuleDao;

    @Autowired
    private BeanMapper beanMapper;

    @Override
    public int insertAuctionRule(AuctionRule auctionRule) {
        auctionRule.setCreateTime(new Date());
        auctionRule.setUpdateTime(new Date());
        //新建时置为下架
        auctionRule.setStatus(2);
        auctionRule.setDifferenceName("差价购买");
        auctionRule.setCountdownName("倒计时");
        auctionRule.setIncreaseBidName("加价幅度");
        auctionRule.setPoundageName("手续费");
        auctionRule.setStartBidName("起拍价");
        auctionRule.setProportionName("退币比例");
        return auctionRuleDao.insertAuctionRule(auctionRule);
    }
    @Override
    public int updateAuctionRule(AuctionRule auctionRule) {
        return auctionRuleDao.updateByPrimaryKeySelective(auctionRule);
    }

    @Override
    public int deleteAuctionRule(String[] ids) {
        return auctionRuleDao.deleteAuctionRule(ids);
    }

    /**
     * 启用竞拍规则
     * @param auctionRuleModel
     * @return
     */
    @Override
    public int enable(AuctionRuleModel auctionRuleModel) throws Exception {
        if(auctionRuleModel == null || auctionRuleModel.getId() == null){
            throw new Exception("参数缺失");
        }
        AuctionRule rule = new AuctionRule();
        rule.setStatus(1);
        rule.setOnShelfTime(new Date());
        rule.setId(auctionRuleModel.getId());

        return auctionRuleDao.updateStatus(rule);
    }

}
