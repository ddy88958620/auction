package com.trump.auction.back.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.trump.auction.back.auctionProd.dao.read.AuctionInfoDao;
import com.trump.auction.back.auctionProd.dao.read.AuctionProductInfoDao;
import com.trump.auction.back.auctionProd.model.AuctionInfo;
import com.trump.auction.back.auctionProd.model.AuctionProductInfo;
import com.trump.auction.back.auctionProd.vo.AuctionProdHotVo;
import com.trump.auction.back.auctionProd.vo.AuctionProdRecommendVo;
import com.trump.auction.back.constant.SysConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 拍品redis相关定时
 * @author:
 * @date: 2017-01-08
 **/
@Slf4j
@Component("hotAndReocmmendJobComponent")
public class AuctionHotAndRecommendTask {


    @Autowired
    private JedisCluster jedisCluster;
    @Autowired
    private AuctionProductInfoDao auctionProductInfoDao;
    @Autowired
    private AuctionInfoDao auctionInfoDao;

    /**
     * 定时任务，每隔5秒更新热拍和推荐redis里面的数据
     */
    public void updateAuctionHot(){
        String jedis  = jedisCluster.get(SysConstant.AUCTION_BACK_AUCTION_PROD_HOT);
        List<AuctionProdHotVo> hotVos = JSONArray.parseArray(jedis, AuctionProdHotVo.class);
        List<AuctionProdHotVo> cancelhotVos=new ArrayList<AuctionProdHotVo>();
        if(CollectionUtils.isNotEmpty(hotVos)){
            AuctionProductInfo auctionProductInfo = null;
            AuctionInfo auctionInfo = null;
            for (AuctionProdHotVo hot:hotVos) {
                auctionProductInfo = auctionProductInfoDao.selectByPrimaryKey(Integer.parseInt(hot.getAuctionProdId()));
                if(auctionProductInfo != null){
                    hot.setProductName(auctionProductInfo.getProductName());
                    hot.setProductId(auctionProductInfo.getProductId());
                    auctionInfo = auctionInfoDao.queryLastOneAuctionByAuctionProdId(Integer.parseInt(hot.getAuctionProdId()));
                    if(4==auctionProductInfo.getStatus() || (0==auctionProductInfo.getProductNum() && auctionInfo.getStatus()==2)){
                        cancelhotVos.add(hot);
                    }else if(auctionInfo != null){
                        hot.setAuctionId(String.valueOf(auctionInfo.getId()));
                        hot.setPreviewPic(auctionInfo.getPreviewPic());
                        hot.setBidPrice(String.valueOf(getBidPrice(auctionInfo)));
                        hot.setStatus(String.valueOf(auctionInfo.getStatus()));
                    }
                }
            }
            if(cancelhotVos.size()>0) {
                hotVos.removeAll(cancelhotVos);
            }
            jedisCluster.set(SysConstant.AUCTION_BACK_AUCTION_PROD_HOT, JSON.toJSONString(hotVos));
        }
        //推荐redis更新
        updateAuctionRecommend();
    }

    /**
     * 定时任务，每隔30秒更新推荐redis里面的数据
     */
    public void updateAuctionRecommend(){
        String jedis  = jedisCluster.get(SysConstant.AUCTION_BACK_AUCTION_PROD_RECOMMEND);
        List<AuctionProdRecommendVo> recommendVos = JSONArray.parseArray(jedis, AuctionProdRecommendVo.class);
        List<AuctionProdRecommendVo> cancelrecommendVos=new ArrayList<AuctionProdRecommendVo>();
        if(CollectionUtils.isNotEmpty(recommendVos)){
            AuctionProductInfo auctionProductInfo = null;
            AuctionInfo auctionInfo = null;
            for (AuctionProdRecommendVo recommend:recommendVos
                    ) {
                auctionProductInfo = auctionProductInfoDao.selectByPrimaryKey(Integer.parseInt(recommend.getAuctionProdId()));
                if(auctionProductInfo != null){
                    recommend.setProductName(auctionProductInfo.getProductName());
                    recommend.setProductId(auctionProductInfo.getProductId());
                    auctionInfo = auctionInfoDao.queryLastOneAuctionByAuctionProdId(Integer.parseInt(recommend.getAuctionProdId()));
                    if(4==auctionProductInfo.getStatus() || (0==auctionProductInfo.getProductNum() && auctionInfo.getStatus()==2)){
                        cancelrecommendVos.add(recommend);
                    }else if(auctionInfo != null){
                        recommend.setAuctionId(String.valueOf(auctionInfo.getId()));
                        recommend.setPreviewPic(auctionInfo.getPreviewPic());
                        recommend.setBidPrice(String.valueOf(getBidPrice(auctionInfo)));
                        recommend.setStatus(String.valueOf(auctionInfo.getStatus()));
                    }
                }
            }
            if(cancelrecommendVos.size()>0) {
                recommendVos.removeAll(cancelrecommendVos);
            }
                jedisCluster.set(SysConstant.AUCTION_BACK_AUCTION_PROD_RECOMMEND, JSON.toJSONString(recommendVos));
        }

    }

    private BigDecimal getBidPrice(AuctionInfo auctionInfo){
        if (auctionInfo.getStartPrice() == null) {
            auctionInfo.setStartPrice(BigDecimal.ZERO);
        }
        if (auctionInfo.getIncreasePrice() == null) {
            auctionInfo.setIncreasePrice(BigDecimal.ZERO);
        }
        BigDecimal bidCount = new BigDecimal("0");
        if (auctionInfo.getTotalBidCount() != null) {
            bidCount = new BigDecimal(auctionInfo.getTotalBidCount());
        }
        return auctionInfo.getStartPrice().add(bidCount.multiply(auctionInfo.getIncreasePrice()));
    }
}
