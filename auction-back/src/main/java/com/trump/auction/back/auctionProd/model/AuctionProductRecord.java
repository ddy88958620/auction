package com.trump.auction.back.auctionProd.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Administrator
 */
@Data
public class AuctionProductRecord {
    private Integer id;

    private Integer auctionNo;

    private Integer productId;

    private String productName;

    private BigDecimal productPrice;

    private Integer ruleId;

    private String classify1;

    private String classify2;

    private String classify3;

    private Integer brandId;

    private BigDecimal productAmount;

    private BigDecimal salesAmount;

    private String skuInfo;

    private String remarks;

    private String previewPic;

    private String masterPic;

    private String picUrls;

    private Date createTime;

    private Date updateTime;

    private Integer userId;

    private String userIp;

    private BigDecimal poundage;

    private Integer merchantId;

    private String merchantName;

    /**
     * 起拍名称
     */
    private String startBidName;

    /**
     * 每次加价名称
     */
    private String increaseBidName;

    /**
     * 手续费名称
     */
    private String poundageName;

    /**
     * 倒计时名称
     */
    private String countdownName;

    /**
     * 差价购买名称
     */
    private String differenceName;

    /**
     * 退币比例名称
     */
    private String proportionName;


}