package com.trump.auction.back.auctionProd.service.impl;

import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.github.pagehelper.PageHelper;
import com.trump.auction.back.auctionProd.dao.read.AuctionBidDetailDao;
import com.trump.auction.back.auctionProd.model.AuctionBidDetail;
import com.trump.auction.back.auctionProd.model.AuctionDetail;
import com.trump.auction.back.auctionProd.service.AuctionBidDetailService;
import com.trump.auction.back.util.common.Base64Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @description: 出价详情列表
 * @author: zhangliyan
 * @date: 2018.01.18 15:02:46
 **/
@Service("auctionBidDetailService")
@Slf4j
public class AuctionBidDetailServiceImpl implements AuctionBidDetailService {
    @Autowired
    private AuctionBidDetailDao auctionBidDetailDao;

    @Override
    public Paging<AuctionBidDetail> auctionBidDetailList(Map<String, Object> params) {
        long startTime = System.currentTimeMillis();
        log.info("auctionBidDetailList invoke,StartTime:{},params:{}", startTime, params);
        Integer page = Integer.valueOf(String.valueOf(params.get("page") == null ? 1 : params.get("page")));
        Integer limit = Integer.valueOf(String.valueOf(params.get("limit") == null ? 10 : params.get("limit")));
        Paging<AuctionBidDetail> result = null;
        try {
            result = new Paging<>();
            PageHelper.startPage(page,limit);
            result = PageUtils.page(auctionBidDetailDao.auctionBidDetailList(params));
            if(CollectionUtils.isNotEmpty(result.getList())){
                for (AuctionBidDetail detail: result.getList()
                        ) {
                    detail.setUserName(Base64Utils.decodeStr(detail.getUserName()));
                }
            }
        } catch (NumberFormatException e) {
            log.error("auctionBidDetailList error:", e);
        }
        long endTime = System.currentTimeMillis();
        log.info("auctionBidDetailList end,duration:{}", endTime - startTime);
        return result;
    }
}
