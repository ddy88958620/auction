package com.trump.auction.trade.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Administrator
 * @date 2018/1/6
 */
@Data
public class AuctionProductRecord {

    /**
     * 主键自增
     */
    private Integer id;

    /**
     * 拍卖期数
     */
    private Integer auctionNo;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品价格
     */
    private BigDecimal productPrice;

    /**
     * 规则表
     */
    private Integer ruleId;

    /**
     * 1级分类
     */
    private String classify1;

    /**
     * 2级分类
     */
    private String classify2;

    /**
     * 3级分类
     */
    private String classify3;

    /**
     * 品牌
     */
    private Integer brandId;

    /**
     * 商品金额
     */
    private BigDecimal productAmount;

    /**
     * 销售金额
     */
    private BigDecimal salesAmount;

    /**
     * 规格说明
     */
    private String skuInfo;

    /**
     * 售后说明
     */
    private String remarks;

    /**
     * 预览图
     */
    private String previewPic;

    /**
     * 商品主图逗号隔开
     */
    private String masterPic;

    /**
     * 商品详细逗号隔开
     */
    private String picUrls;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 操作人id
     */
    private Integer userId;

    /**
     * 操作人ip
     */
    private String userIp;

    /**
     * 手续费
     */
    private BigDecimal poundage;

    /**
     * 商家ID
     */
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
