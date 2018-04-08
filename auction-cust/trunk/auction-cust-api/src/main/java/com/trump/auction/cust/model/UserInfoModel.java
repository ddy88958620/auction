package com.trump.auction.cust.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * 用户model
 * @author wangbo
 */
@Data
@ToString
public class UserInfoModel implements Serializable{
    private static final long serialVersionUID = 5312128649831315999L;
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
    private String provinceName;
    private String cityName;
    /*添加登录类型*/
    private String loginType;

    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像
     */
    private String headImg;
    private String appInfo;
}
