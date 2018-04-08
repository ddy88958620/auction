package com.trump.auction.activity.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户签到信息Model
 * @author wangbo 2017/12/21.
 */
@Data
@ToString
public class UserSignModel implements Serializable {
    private static final long serialVersionUID = 6257263590658034747L;
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
