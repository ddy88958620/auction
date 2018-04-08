package com.trump.auction.activity.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户的活动相关信息
 * @author wangbo 2018/1/11.
 */
@Data
@ToString
public class ActivityUserModel implements Serializable {
    private static final long serialVersionUID = 2520416000801181276L;
    private Integer id;
    private Integer userId;
    private String userPhone;
    private Integer freeLotteryTimes;
    private Date lastFreeTime;
    private Date addTime;
    private Date updateTime;
}
