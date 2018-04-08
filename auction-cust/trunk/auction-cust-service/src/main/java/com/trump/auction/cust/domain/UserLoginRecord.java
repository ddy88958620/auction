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
public class UserLoginRecord {
    private Integer id;
    /**
     * 登陆用户id
     */
    private Long    UserId;
    /**
     * 登陆时间
     */
    private Date    loginTime;
    /**
     * 登陆用户ip
     */
    private String  loginIp;
    /**
     * 登陆地址
     */
    private String  address;
    /**
     * 登陆设备
     */
    private String  loginDevices;
    /**
     * 登陆方式  1:手机号，2:微信，3:qq,4:短信登陆
     */
    private String  loginType;
    /**
     * 添加时间
     */
    private Date    addTime;
}
