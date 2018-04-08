package com.trump.auction.activity.domain;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 用户的活动相关信息
 * @author wangbo 2018/1/11.
 */
@Data
@ToString
public class ActivityUser {
    private Integer id;
    private Integer userId;
    private String userPhone;
    private Integer freeLotteryTimes;
    private Date lastFreeTime;
    private Date addTime;
    private Date updateTime;
}
