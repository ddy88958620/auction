package com.trump.auction.trade.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Administrator
 */
@Data
public class AuctionProductInfo {
    /**
     *
     */
    private Integer id;

    /**
     *
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
     * 上架数量
     */
    private Integer productNum;

    /**
     * 规则id
     */
    private Integer ruleId;

    /**
     * 竞拍开始时间
     */
    private Date auctionStartTime;

    /**
     * 状态（1开拍中 2准备中 3定时）
     */
    private Integer status;

    /**
     * 开始时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 操作者id
     */
    private Integer userId;

    /**
     * 操作者ip
     */
    private String userIp;

    /**
     * 拍品每期延迟时间
     */
    private Integer shelvesDelayTime;

    /**
     * 分类id
     */
    private Integer classifyId;

    /**
     *
     */
    private String classifyName;

    /**
     * 保留价
     */
    private BigDecimal floorPrice;

    /**
     * 浮动金额比列
     */
    private String floatPrice;

    /**
     * 上架时间
     */
    private Date onShelfTime;

    /**
     * 下架时间
     */
    private Date underShelfTime;

    /**
     * 竞拍规则
     */
    private String auctionRule;
}