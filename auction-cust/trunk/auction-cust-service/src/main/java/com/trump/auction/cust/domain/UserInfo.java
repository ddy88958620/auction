package com.trump.auction.cust.domain;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 用户实体
 * @author wangbo
 */
@Data
@ToString
public class UserInfo {
    private Integer id;
    private String userType;
    private String userPhone;
    private String realName;
    private String idNumber;
    private String loginPassword;
    private String payPassword;
    private String wxNickName;
    private String wxOpenId;
    private String wxHeadImg;
    private String qqNickName;
    private String qqOpenId;
    private String qqHeadImg;
    private Date addTime;
    private String addIp;
    private Date updateTime;
    private String status;
    private String userFrom;
    private Integer rechargeType;
    private Integer rechargeMoney;
    private String loginType;
    private String provinceName;
    private String cityName;
    private String appInfo;

    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像
     */
    private String headImg;
}
