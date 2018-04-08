package com.trump.auction.trade.domain;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 拍品信息
 * @author zhangqingqiang
 * @since 2017-01-06
 */
@Data
@ToString
public class AuctionInfo implements Serializable {
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

    private Integer auctionProdId;

    private BigDecimal floorPrice;

    private String classifyName;

    private String floatPrice;

    private Integer  floorBidCount;

    /**
     * 手续费
     */
    private Integer poundage;
}