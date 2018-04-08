package com.trump.auction.trade.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

import lombok.Data;


/**
 * @author Administrator
 */
@Data
public class AuctionProductRecordVo implements Serializable {
    private static final long serialVersionUID = -3456334697361926954L;
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

    private String picUrls;

    private Date createTime;

    private Date updateTime;

    private Integer userId;

    private String userIp;

    private String previewPic;

    private String masterPic;
    /**
     * 手续费
     */
    private BigDecimal poundage;

    private Integer merchantId;

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