package com.trump.auction.back.rule.service;

import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.github.pagehelper.PageHelper;
import com.trump.auction.back.auctionProd.dao.read.AuctionProductInfoDao;
import com.trump.auction.back.product.vo.ParamVo;
import com.trump.auction.back.rule.dao.read.AuctionRuleDao;
import com.trump.auction.back.rule.model.AuctionRule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 竞拍规则管理
 *
 * @author zhangliyan
 * @create 2018-01-03 15:54
 **/
@Slf4j
@Service
public class AuctionRuleServiceImpl implements AuctionRuleService {
    @Autowired
    private AuctionRuleDao auctionRuleDao;

    @Autowired
    private AuctionProductInfoDao auctionProductInfoDao;
    /**
     * 分页查询订单列表
     * @param paramVo
     * @return
     */
    @Override
    public Paging<AuctionRule> findAuctionRulePage(ParamVo paramVo) {
        long startTime = System.currentTimeMillis();
        log.info("findAuctionRulePage invoke,StartTime:{},params:{}", startTime, paramVo);

        Paging<AuctionRule> result = null;
        try {
            result = new Paging<>();
            PageHelper.startPage(paramVo.getPage(),paramVo.getLimit());
            result = PageUtils.page(auctionRuleDao.findAuctionRuleList(paramVo));
            if(CollectionUtils.isNotEmpty(result.getList())){
                for (AuctionRule rule:result.getList()
                     ) {
                    rule.setProductNum(auctionProductInfoDao.getProductNumByRuleId(rule.getId()));
                }
            }
        } catch (NumberFormatException e) {
            log.error("findAuctionRulePage error:", e);
        }

        long endTime = System.currentTimeMillis();
        log.info("findAuctionRulePage end,duration:{}", endTime - startTime);
        return result;
    }

    @Override
    public List<AuctionRule> findAuctionRuleList() {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("status",1);
        ParamVo paramVo = new ParamVo();
        paramVo.setStatus(1);
        return auctionRuleDao.findAuctionRuleList(paramVo);
    }

    @Override
    public AuctionRule findAuctionRuleById(Integer id) {
        long startTime = System.currentTimeMillis();
        log.info("findAuctionRuleById invoke,StartTime:{},params:{}", startTime, id);
        if (null == id) {
            throw new IllegalArgumentException("findAuctionRuleById param orderId is null");
        }
        AuctionRule result = null;
        try {
            result = auctionRuleDao.findAuctionRuleById(id);
        } catch (NumberFormatException e) {
            log.error("findAuctionRuleById error:", e);
        }

        long endTime = System.currentTimeMillis();
        log.info("findAuctionRuleById end,duration:{}", endTime - startTime);
        return result;
    }

    @Override
    public AuctionRule getAuctionRule(Integer id) {
        AuctionRule rule = auctionRuleDao.getAuctionRule(id);
        return rule;
    }


    @Override
    public AuctionRule queryRuleById(int ruleId) {
        return auctionRuleDao.getAuctionRule(ruleId);
    }
}
