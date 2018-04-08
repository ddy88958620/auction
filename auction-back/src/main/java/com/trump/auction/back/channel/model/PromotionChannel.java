package com.trump.auction.back.channel.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 推广渠道表
 * @author wangjian 2018-01-19
 */
@Data
public class PromotionChannel {
    /**
     * 主键ID
     */
    @Getter
    @Setter
    private Integer id;

    /**
     * 渠道ID
     */
    @Getter
    @Setter
    private String channelId;

    /**
     * 渠道名称
     */
    @Getter
    @Setter
    private String channelName;

    /**
     * 
     */
    @Getter
    @Setter
    private Integer provinceCode;

    /**
     * 
     */
    @Getter
    @Setter
    private Integer cityCode;

    /**
     * 
     */
    @Getter
    @Setter
    private Integer townCode;

    /**
     * 省
     */
    @Getter
    @Setter
    private String provinceName;

    /**
     * 市
     */
    @Getter
    @Setter
    private String cityName;

    /**
     * 区
     */
    @Getter
    @Setter
    private String townName;

    /**
     * 合作方式
     */
    @Getter
    @Setter
    private String cooperationMode;

    /**
     * 结算方式
     */
    /*
    @Getter
    @Setter
    private String settlementMode;*/

    /**
     * 结算单价
     */
    @Getter
    @Setter
    private Integer settlementPrice;

    /**
     * 负责人
     */
    @Getter
    @Setter
    private String personInCharge;

    /**
     * 对接人
     */
    @Getter
    @Setter
    private String pickUp;

    /**
     * 联系电话
     */
    @Getter
    @Setter
    private String contactPhone;

    /**
     * 联系邮箱
     */
    @Getter
    @Setter
    private String contactEmail;

    /**
     * 推广链接
     */
    @Getter
    @Setter
    private String extensionUrl;

    /**
     * 二维码链接URL
     */
    @Getter
    @Setter
    private String extensionQrc;

    /**
     * 创建时间
     */
    @Getter
    @Setter
    private Date createTime;

    /**
     * 修改时间
     */
    @Getter
    @Setter
    private Date updateTime;

    /**
     * 开始时间
     */
    @Getter
    @Setter
    private String startTime;

    /**
     * 结束时间
     */
    @Getter
    @Setter
    private Date endTime;

    /**
     * 用户ID
     */
    @Getter
    @Setter
    private Integer userId;

    /**
     * 用户IP
     */
    @Getter
    @Setter
    private String userIp;
    /**
     * 渠道来源
     */
    @Getter
    @Setter
    private String channelSource;
    /**
     * 描述
     */
    @Getter
    @Setter
    private String description;

    /**
     * 状态
     */
    @Getter
    @Setter
    private String status;

}