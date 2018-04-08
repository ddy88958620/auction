package com.trump.auction.cust.domain;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@ToString
@Data
public class NotificationDevice implements Serializable {
    private Integer id;

    private String deviceId;

    private String deviceTokenUmeng;

    private Integer deviceType;

    private Integer userId;

    private Date createTime;

    private Date updateTime;

}