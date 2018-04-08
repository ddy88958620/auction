package com.trump.auction.cust.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@ToString
@Data
public class NotificationDeviceModel implements Serializable {

    private static final long serialVersionUID = -1L;

    private Integer id;

    private String deviceId;

    private String deviceTokenUmeng;

    private Integer deviceType;

    private Integer userId;

    private Date createTime;

    private Date updateTime;

}