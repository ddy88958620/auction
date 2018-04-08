package com.trump.auction.trade.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BidOperationDto {
    private Integer  auctionId;

    private Integer  auctionProdId;

    private Integer  bidType;

    private Integer  userId;

    private String   userPhone;

    private Integer  bidCount;

    private String   userName;

    private String   address;

    private String   hdeaImg;

    private String   txnId;

    private Integer  pCoin;

    private Integer  zCoin;

    private String   subUserId;

    private BigDecimal lastprice;
}
