package com.trump.auction.trade.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 规则
 *
 * @author zhangliyan
 * @create 2018-01-02 17:11
 **/
@Data
@ToString
public class AuctionRuleModel implements Serializable{
    /**
     * 主键
     */
    private Integer id;

    /**
     * 规则名称
     */
    private String ruleName;

    /**
     * 差价购买标识(1.可以2.不可以)
     */
    private Integer differenceFlag;

    /**
     * 每次可加价金额
     */
    private BigDecimal premiumAmount;

    /**
     * 计时描述
     */
    private Integer timingNum;

    /**
     * 退币比例
     */
    private BigDecimal refundMoneyProportion;

    /**
     * 起拍价
     */
    private BigDecimal openingBid;

    /**
     * 上架规则 1.定时 2.立即 3.暂不上架
     */
    private Integer shelvesRule;

    /**
     * 上架延迟时间
     */
    private Integer shelvesDelayTime;

    /**
     * 机器人出价权重(百分比)
     */
    private Integer robotRule;

    /**
     * 机器人是否必中
     */
    private Integer robotTakenIn;

    /**
     * 最高价可得
     */
    private Long highestPrice;

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
     * 类型状态 1启用 2禁用
     */
    private Integer status;

    /**
     * 手续费
     */
    private BigDecimal poundage;

    /**
     * 倒计时
     */
    private Integer countdown;

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
