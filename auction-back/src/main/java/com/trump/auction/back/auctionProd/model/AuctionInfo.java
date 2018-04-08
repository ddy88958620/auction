package com.trump.auction.back.auctionProd.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class AuctionInfo {
    private Integer id;

    private Integer auctionNo;

    private Integer productId;

    private String productName;

    private BigDecimal productPrice;

    private Integer ruleId;

    private Integer pageView;

    private Integer collectCount;

    private Integer validBidCount;

    private Integer freeBidCount;

    private Integer totalBidCount;

    private Integer robotBidCount;

    private String winUserDesc;

    private Integer personCount;

    private Date startTime;

    private Date endTime;

    private String ruleName;

    private Integer buyFlag;

    private BigDecimal increasePrice;

    private Integer countDown;

    private BigDecimal returnPercent;

    private BigDecimal startPrice;

    private Integer status;

    private Integer robotWinFlag;

    private Date createTime;

    private BigDecimal finalPrice;

    private String previewPic;

    private Integer winUserId;

    private Date updateTime;

    private Integer classifyId;

    private  Integer auctionProdId;

    private String classifyName;

    private BigDecimal floorPrice;

    private String  floatPrice;


    /**
     * 拍品最低有效出价次数
     */
    private Integer floorBidCount;

    /**
     * 手续费
     */
    private Integer poundage;
    /**
     * 当前售价
     */
    private BigDecimal bidPrice;
}