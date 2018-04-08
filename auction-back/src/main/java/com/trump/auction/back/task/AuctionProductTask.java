package com.trump.auction.back.task;

import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.utils.DateUtil;
import com.cf.common.utils.JsonResult;
import com.trump.auction.back.auctionProd.dao.read.AuctionInfoDao;
import com.trump.auction.back.auctionProd.dao.read.AuctionProductInfoDao;
import com.trump.auction.back.auctionProd.model.AuctionInfo;
import com.trump.auction.back.auctionProd.model.AuctionProductInfo;
import com.trump.auction.back.auctionProd.model.AuctionProductRecord;
import com.trump.auction.back.auctionProd.service.AuctionInfoService;
import com.trump.auction.back.auctionProd.service.AuctionProductInfoService;
import com.trump.auction.back.auctionProd.service.AuctionProductRecordService;
import com.trump.auction.back.auctionProd.vo.AuctionCondition;
import com.trump.auction.back.enums.AuctionInfoStatus;
import com.trump.auction.back.enums.AuctionProductEnum;
import com.trump.auction.back.enums.ResultEnum;
import com.trump.auction.back.rule.model.AuctionRule;
import com.trump.auction.back.rule.service.AuctionRuleService;
import com.trump.auction.trade.api.AuctionInfoStubService;
import com.trump.auction.trade.model.AuctionInfoModel;
import com.trump.auction.trade.model.AuctionProductInfoModel;
import com.trump.auction.trade.model.AuctionProductRecordModel;
import com.trump.auction.trade.model.AuctionRuleModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;
import java.util.Date;
import java.util.List;

/**
 * @description: 拍品相关定时
 * @author: zhangqingqiang
 * @date: 2017-01-08
 **/
@Slf4j
@Component("auctionProdJobComponent")
public class AuctionProductTask {

    @Autowired
    private AuctionProductInfoService auctionProductInfoService;
    @Autowired
    private AuctionRuleService auctionRuleService;
    @Autowired
    private AuctionInfoService auctionInfoService;
    @Autowired
    private AuctionInfoStubService auctionInfoStubService;
    @Autowired
    private BeanMapper beanMapper;
    @Autowired
    private AuctionProductRecordService auctionProdRecordService;
    @Autowired
    private JedisCluster jedisCluster;

    public void auctionProductTask() {
        JsonResult auctionResult = null;
                log.info("task:auctionProductTask start.");
        try {
            log.info("task:auctionProductTask .queryLastAuction start.");
            //查询近31天拍品信息
            AuctionCondition condition = new AuctionCondition();
            condition.setStartTime(DateUtil.addDay(new Date(),-31));
            condition.setEndTime(new Date());
            List<AuctionInfo>  auctions = auctionInfoService.queryLastAuction(condition);
            if (CollectionUtils.isEmpty(auctions)){
                log.info("task:auctionProductTask .queryLastAuction . result null");
                return;
            }
            log.info("task:auctionProductTask .queryLastAuction end.");
            for (AuctionInfo auctionInfo:auctions){
                if (!AuctionInfoStatus.FINISHING.getCode().equals(auctionInfo.getStatus())){
                    continue;
                }
                AuctionProductInfo prod = auctionProductInfoService.findAuctionProductInfoById(auctionInfo.getAuctionProdId());

                if (null==prod|| !AuctionProductEnum.AUCTIONING.getCode().equals(prod.getStatus())){
                    log.info("task:auctionProductTask .findAuctionProductInfoById . result null or status is 4");
                    continue;
                }
                log.info("task:auctionProductTask .queryRuleById . start.");
                AuctionRule rule = auctionRuleService.queryRuleById(prod.getRuleId());
                if (null==rule){
                    log.info("task:auctionProductTask .queryRuleById . result null.");
                    continue;
                }
                //查询上一期快照
                AuctionProductRecord lastRecord = auctionProdRecordService.queryRecordByAuctionNo(auctionInfo.getId());
                if (null==lastRecord){
                    log.info("task:auctionProductTask .getRecordByAuctionId . for null.");
                    continue;
                }
                String result=jedisCluster.get("auction.back.trade.add");
                if(StringUtils.isNotBlank(result)){
                    continue;
                }else{
                    jedisCluster.set("auction.back.trade.add."+auctionInfo.getId(),"start");
                }
                auctionResult = auctionInfoStubService.doAuctionTask(beanMapper.map(prod, AuctionProductInfoModel.class),beanMapper.map(rule, AuctionRuleModel.class),
                        beanMapper.map(auctionInfo, AuctionInfoModel.class),beanMapper.map(lastRecord, AuctionProductRecordModel.class));
                    jedisCluster.del("auction.back.trade.add."+auctionInfo.getId());
                if (null==auctionResult|| !ResultEnum.SAVE_SUCCESS.getCode().equals(auctionResult.getCode())){
                    continue;
                }
            }
        } catch (Exception e) {
            log.error("task:AuctionProductTask error.  msg:{}",e);
        }
    }



}
