package com.trump.auction.back.push.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@ToString
@Data
public class NotificationRecord implements Serializable {

    private Integer id;

    private String subject;

    private String title;

    private String content;

    private Integer notiType;

    private Integer notiDeviceId;

    private Integer sendType;

    private Integer sendStatus;

    private Date createTime;

    private Integer timeType;

    private Date sendTime;

    private String url;

    private Integer activityId;

    private Integer productId;

    private String userIp;

    private Integer userId;

    private String userName;

    private Integer deviceCount;

    private Integer status;

}