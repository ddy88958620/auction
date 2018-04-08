package com.trump.auction.activity.domain;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 用户签到实体
 * @author wangbo 2017/12/21.
 */
@Data
@ToString
public class UserSign {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 最近签到时间
     */
    private Date lastSignTime;
    /**
     * 连续签到天数
     */
    private Integer seriesSignDays;
}
