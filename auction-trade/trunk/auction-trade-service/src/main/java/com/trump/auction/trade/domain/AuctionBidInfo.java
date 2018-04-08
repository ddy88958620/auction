package com.trump.auction.trade.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author Administrator
 */
@Data
public class AuctionBidInfo {
    private Integer id;

    private String txnSeqNo;

    private Integer userId;

    private String userName;

    private Integer bidCount;

    private Integer auctionProdId;

    private Integer auctionNo;

    private Integer usedCount;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    private String  subUserId;

    private Integer  pCoin;

    private Integer  zCoin;
    
    private String tableSuffix;
}