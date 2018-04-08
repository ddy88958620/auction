package com.trump.auction.back.task;

import com.alibaba.fastjson.JSON;
import com.cf.common.utils.DateUtil;
import com.cf.common.utils.ServiceResult;
import com.trump.auction.back.auctionProd.dao.read.AuctionProductInfoDao;
import com.trump.auction.back.auctionProd.model.AuctionProductInfo;
import com.trump.auction.back.auctionProd.service.AuctionProductInfoService;
import com.trump.auction.back.product.dao.read.ProductClassifyDao;
import com.trump.auction.back.product.model.ProductClassify;
import com.trump.auction.back.product.vo.ClassifyVo;
import com.trump.auction.order.api.OrderInfoStubService;
import com.trump.auction.order.enums.EnumOrderStatus;
import com.trump.auction.order.model.OrderInfoModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @description: 更新拍品状态
 * @author: zhangliyan
 * @date: 2018-1-12
 **/
@Slf4j
@Service
public class AuctionOn {

    @Autowired
    private AuctionProductInfoService auctionProductInfoService;
    @Autowired
    private AuctionProductInfoDao auctionProductInfoDao;

    @Autowired
    private ProductClassifyDao productClassifyDao;
    @Autowired
    private JedisCluster jedisCluster;



    public void cancelOrder() {
        try {

            List<AuctionProductInfo> auctionProductInfos = auctionProductInfoService.getByStatusAndDate(null,0);

            List<String> ids = new ArrayList<>();
            for (AuctionProductInfo info:auctionProductInfos) {
                String infos = String.valueOf(info.getId());
                ids.add(infos);
            }

            log.info("ids="+ JSON.toJSONString(ids));
            if(CollectionUtils.isNotEmpty(ids)){
                auctionProductInfoDao.updAuctionProdStatus(ids,1);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
