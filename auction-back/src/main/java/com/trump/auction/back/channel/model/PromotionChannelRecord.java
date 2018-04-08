package com.trump.auction.back.channel.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 推广渠道结算方式记录表
 * @author hanliangliang 2018-02-27
 */
@Data
public class PromotionChannelRecord {
    /**
     * 主键ID
     */
    @Getter
    @Setter
    private Integer id;

    /**
     * 推广渠道主键ID
     */
    @Getter
    @Setter
    private Integer promotionChannelId;

    /**
     * 结算方式
     */
    @Getter
    @Setter
    private String settlementMode;

    /**
     * 结算单价
     */
    @Getter
    @Setter
    private Integer settlementPrice;

    /**
     * 创建时间
     */
    @Getter
    @Setter
    private Date createTime;

    /**
     * 开始时间
     */
    @Getter
    @Setter
    private Date startTime;

    /**
     * 结束时间
     */
    @Getter
    @Setter
    private Date endTime;



}