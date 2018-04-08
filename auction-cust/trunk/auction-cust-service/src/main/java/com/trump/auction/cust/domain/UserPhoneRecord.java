package com.trump.auction.cust.domain;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Author=hanliangliang
 * @Date=2018/03/15
 */
@Data
@ToString
public class UserPhoneRecord {

    private Integer id;
    /**
     * 用户id
     */
    private long userId;
    /**
     * 用户手机号码
     */
    private String userPhone;
    /**
     * 用户上次绑定手机号
     */
    private String userLastPhone;

    /**
     * 添加时间
     */
    private Date addTime;
}
