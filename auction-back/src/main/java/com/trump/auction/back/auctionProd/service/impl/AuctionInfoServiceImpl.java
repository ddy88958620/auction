package com.trump.auction.back.auctionProd.service.impl;

import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.github.pagehelper.PageHelper;
import com.trump.auction.back.auctionProd.dao.read.AuctionInfoDao;
import com.trump.auction.back.auctionProd.model.AuctionInfo;
import com.trump.auction.back.auctionProd.service.AuctionInfoService;
import com.trump.auction.back.auctionProd.vo.AuctionCondition;
import com.trump.auction.back.util.common.Base64Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @description: 拍卖信息
 * @author: zhangqingqiang
 * @date: 2018-01-08 14:12
 **/
@Service
@Slf4j
public class AuctionInfoServiceImpl implements AuctionInfoService {

    @Autowired
    private AuctionInfoDao auctionInfoDao;

    @Override
    public List<AuctionInfo> queryLastAuction(AuctionCondition condition) {
        return auctionInfoDao.queryLastAuction(condition);
    }

    /**
     * 查询列表
     *
     * @param params
     * @return
     */
    @Override
    public Paging<AuctionInfo> findList(Map<String, Object> params) {
        long startTime = System.currentTimeMillis();
        log.info("findList invoke,StartTime:{},params:{}", startTime, params);

        Paging<AuctionInfo> result = null;
        try {
            result = new Paging<>();
            PageHelper.startPage(Integer.valueOf(String.valueOf(params.get("page") == null ? 1 : params.get("page"))),
                    Integer.valueOf(String.valueOf(params.get("limit") == null ? 10 : params.get("limit"))));
            result = PageUtils.page(auctionInfoDao.findList(params));
        } catch (NumberFormatException e) {
            log.error("findList error:", e);
        }
        long endTime = System.currentTimeMillis();
        log.info("findList end,duration:{}", endTime - startTime);
        return result;
    }

    /**
     * 根据id查询正在拍详情
     *
     * @param id
     * @return
     */
    @Override
    public AuctionInfo findAuctionInfoById(Integer id) {
        long startTime = System.currentTimeMillis();
        log.info("findAuctionInfoById invoke,StartTime:{},params:{}", startTime, id);

        if (null == id) {
            throw new IllegalArgumentException("findAuctionInfoById param id is null");
        }

        AuctionInfo result = null;
        try {
            result = auctionInfoDao.selectByPrimaryKey(id);
            if (result != null) {
                if(StringUtils.isNotBlank(result.getWinUserDesc())) {
                    result.setWinUserDesc(Base64Utils.decodeStr(result.getWinUserDesc()));
                }
            }
        } catch (NumberFormatException e) {
            log.error("findAuctionInfoById error:", e);
        }

        long endTime = System.currentTimeMillis();
        log.info("findAuctionInfoById end,duration:{}", endTime - startTime);
        return result;
    }

    @Override
    public List<AuctionInfo> queryAuctionByStatus(Integer status) {
        return auctionInfoDao.queryAuctionByStatus(status);
    }

    @Override
    public AuctionInfo queryLastOneAuctionByAuctionProdId(Integer id) {
        return auctionInfoDao.queryLastOneAuctionByAuctionProdId(id);
    }
}
