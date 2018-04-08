package com.trump.auction.trade.model;


import lombok.Data;
import java.io.Serializable;
@Data
public class BidParam implements Serializable{

    private Integer  auctionId;

    private Integer  auctionProdId;

    private Integer  bidType;

    private Integer  userId;

    private String   userPhone;

    private Integer  bidCount;

    private String   userName;

    private String   address;

    private String   hdeaImg;

}
