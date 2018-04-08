package com.trump.auction.trade.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AuctionBidetail {
    private Integer id;

    private String bidId;

    private Integer userId;

    private String userName;

    private Integer bidType;

    private Integer auctionProdId;

    private Integer auctionNo;

    private String userIp;

    private Date createTime;

    private Date updateTime;

    private String headImg;

    private BigDecimal bidPrice;

    private String nickName;

    private String address;

    private Integer bidSubType;

    private String  subUserId;
    
    private String tableSuffix;
}