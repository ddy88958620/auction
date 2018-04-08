package com.trump.auction.back.auctionProd.service.impl;

import com.alibaba.fastjson.JSON;
import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.github.pagehelper.PageHelper;
import com.trump.auction.back.auctionProd.dao.read.AuctionDetailDao;
import com.trump.auction.back.auctionProd.dao.read.AuctionInfoDao;
import com.trump.auction.back.auctionProd.model.AuctionDetail;
import com.trump.auction.back.auctionProd.model.AuctionInfo;
import com.trump.auction.back.auctionProd.service.AuctionDetailService;
import com.trump.auction.back.auctionProd.vo.AuctionOrderVo;
import com.trump.auction.back.product.vo.ParamVo;
import com.trump.auction.back.util.common.Base64Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @description: 拍卖信息
 * @author: zhangqingqiang
 * @date: 2018-01-08 14:12
 **/
@Service("auctionDetailService")
@Slf4j
public class AuctionDetailServiceImpl implements AuctionDetailService {

    @Autowired
    private AuctionDetailDao auctionDetailDao;
    @Autowired
    private AuctionInfoDao auctionInfoDao;


    /**
     * 获取往期订单信息
     * @param auctionId
     * @return
     */
    @Override
        public AuctionOrderVo getPastOrder(Integer auctionId) {
        AuctionOrderVo vo = new AuctionOrderVo();
        log.info("getPastOrder:参数："+auctionId);
        if(auctionId == null){
            return vo;
        }
        vo = auctionDetailDao.selectByAuctionId(auctionId,2);
        if(vo != null){
            AuctionInfo  auctionInfo = auctionInfoDao.selectByPrimaryKey(vo.getAuctionId());
            if(auctionInfo != null){
                vo.setEndTime(auctionInfo.getEndTime());
                vo.setPreviewPic(auctionInfo.getPreviewPic());
                vo.setProductPrice(auctionInfo.getProductPrice());
                vo.setProductName(auctionInfo.getProductName());
                if(auctionInfo.getStartPrice() == null){
                    auctionInfo.setStartPrice(new BigDecimal("0"));
                }
                if(auctionInfo.getIncreasePrice() == null){
                    auctionInfo.setIncreasePrice(new BigDecimal("0"));
                }
                BigDecimal bidCount = new BigDecimal("0");
                if(auctionInfo.getTotalBidCount() != null){
                    bidCount = new BigDecimal(auctionInfo.getTotalBidCount());
                }
                vo.setBidPrice(auctionInfo.getStartPrice().add(bidCount.multiply(auctionInfo.getIncreasePrice())));
            }

        }
        return vo;
    }

    /**
     * 根据期数id查看期数详情
     * @param id
     * @return
     */
    @Override
    public List<AuctionDetail> queryAuctionDetailByAuctionId(Integer id) {
        List<AuctionDetail> auctionDetails = auctionDetailDao.queryAuctionDetailByAuctionId(id);
        return auctionDetails;
    }

    /**
     * 根据拍品id查看拍品期数(一对多)
     * @param paramVo
     * @return
     */
    @Override
    public Paging<AuctionDetail> viewAuctionInfoList(ParamVo paramVo) {
        long startTime = System.currentTimeMillis();
        log.info("viewAuctionInfoList invoke,StartTime:{},paramVo:{}", startTime, paramVo);

        Paging<AuctionDetail> result = null;
        try {
            result = new Paging<>();
            PageHelper.startPage(paramVo.getPage(),paramVo.getLimit());
            if(StringUtils.isNotBlank(paramVo.getUserName())){
                paramVo.setUserName(Base64Utils.encodeStr(paramVo.getUserName()).trim());
            }
            result = PageUtils.page(auctionDetailDao.viewAuctionInfoList(paramVo));
        if(CollectionUtils.isNotEmpty(result.getList())){
            for (AuctionDetail detail: result.getList()
                 ) {
                detail.setUserName(Base64Utils.decodeStr(detail.getUserName()));
                detail.setNickName(Base64Utils.decodeStr(detail.getNickName()));

            }
        }
        } catch (NumberFormatException e) {
            log.error("viewAuctionInfoList error:", e);
        }
        long endTime = System.currentTimeMillis();
        log.info("viewAuctionInfoList end,duration:{}", endTime - startTime);
        System.out.println("result==="+ JSON.toJSONString(result)+"===");
        return result;
    }
}
