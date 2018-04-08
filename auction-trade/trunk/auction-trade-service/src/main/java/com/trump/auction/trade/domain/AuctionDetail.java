package com.trump.auction.trade.domain;

import lombok.Data;


import java.math.BigDecimal;
import java.util.Date;

@Data
public class AuctionDetail {
    /**
     *
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 拍品id
     */
    private Integer auctionProdId;

    /**
     * 拍品期数id
     */
    private Integer auctionId;

    /**
     * 开始时间
     */
    private Date createTime;

    /**
     * 出价总数
     */
    private Integer bidCount;

    /**
     * 返还购物币
     */
    private BigDecimal returnPrice;

    /**
     * 状态（1正在拍 2已拍中 3未拍中）
     */
    private Integer auctionStatus;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String headImg;

    /**
     * 用户类型 1机器人，2真实用户
     */
    private Integer userType;

    /**
     * 用户地址
     */
    private String address;

    /**
     * 拍币
     */
    private Integer pCoin;

    /**
     * 赠币
     */
    private Integer zCoin;
    /**
     * 特殊用户id
     */
    private String subUserId;
    
	private String tableSuffix;

}