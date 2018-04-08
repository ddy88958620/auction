package com.trump.auction.web.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class DynamicAuctionInfoVo {
    
    @Getter @Setter private BigDecimal bidPrice;
    
    @Getter @Setter private Integer pageView = 0;
    
    @Getter @Setter private Integer personCount = 0;
    
    @Getter @Setter private Integer collectCount = 0;
    
    @Getter @Setter private Integer countdown = 0;
    
    @Getter @Setter private List<BidInfoVo> bidInfoList =  new ArrayList<>();
    
    @Getter @Setter private Integer bidCount = 0;//出价次数
    
    @Getter @Setter private Integer pCoin = 0;
    
    @Getter @Setter private Integer zCoin = 0;
    
    /**
     * 状态（1正在拍 2已完结 3未开始）
     */
    @Getter @Setter private Integer status;
    
    @Getter @Setter private Integer nextAuctionId = 0;
    
    @Getter @Setter private Integer nextAuctionProdId = 0;
    
    @Getter @Setter private Integer nextCountdown = 0;
    
    @Getter @Setter private long dynamicCountdown = 0;
    
    @Getter @Setter private long currentCountDown = 0;
    
    /**
     * 用户出价总次数
     */
    @Getter @Setter private Integer userBidCount = 0;

    /**
     * 已使用次数
     */
    @Getter @Setter private Integer remainBidCount =0;
    
    @Getter @Setter private JSONObject lastSuccssBids;
    
    
    
}
