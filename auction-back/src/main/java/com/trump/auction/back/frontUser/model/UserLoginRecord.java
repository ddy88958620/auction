package com.trump.auction.back.frontUser.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author=hanliangliang
 * @Date=2018/03/19
 */
@Data
@ToString
public class UserLoginRecord implements Serializable {
    private static final long serialVersionUID = 1818496174885623431L;
    private Integer id;
    /**用户id*/
    private Integer userId;
    /**登陆时间*/
    private Date loginTime;
    /**登陆ip*/
    private String loginIp;
    /**登陆地区*/
    private String address;
    /**登陆设备*/
    private String loginDevices;
    /**添加时间*/
    private Date addTime;
    /**登录类型(Phone,WX,QQ,SMS)'*/
    private String loginType;

}
