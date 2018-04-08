package com.trump.auction.activity.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
public class ActivityShareModel implements Serializable{
    private Integer id;

    private String activityId;

    private Integer shareEntrance;

    private String activityName;

    private Date startTime;

    private Date endTime;

    private String startDate;

    private Date endDate;

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