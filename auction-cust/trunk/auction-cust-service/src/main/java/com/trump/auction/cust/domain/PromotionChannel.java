package com.trump.auction.cust.domain;

import lombok.Data;

import java.util.Date;

/**
 * 推广渠道信息
 * @author wangbo 2018/2/5.
 */
@Data
public class PromotionChannel {
    private Integer id;

    private String channelId;

    private String channelName;

    private Integer provinceCode;

    private Integer cityCode;

    private Integer townCode;

    private String provinceName;

    private String cityName;

    private String townName;

    private String cooperationMode;

    private Integer settlementPrice;

    private String settlementMode;

    private String personInCharge;

    private String pickUp;

    private String contactPhone;

    private String contactEmail;

    private String extensionUrl;

    private String extensionQrc;

    private Date createTime;

    private Date updateTime;

    private Integer userId;

    private String userIp;

    private String channelSource;

    private String description;

    private Integer status;
}
