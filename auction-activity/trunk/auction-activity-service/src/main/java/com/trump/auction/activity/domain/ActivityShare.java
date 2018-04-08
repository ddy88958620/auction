package com.trump.auction.activity.domain;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class ActivityShare {
    private Integer id;

    private String activityId;

    private Integer shareEntrance;

    private String activityName;

    private Date startTime;

    private Date endTime;

    private String activityUrl;

    private String picUrl;

    private String title;

    private String subTitle;

    private String sharerPoints;

    private String sharerCoin;

    private String registerPoints;

    private String registerCoin;

    private Date createTime;

    private Integer status;

}