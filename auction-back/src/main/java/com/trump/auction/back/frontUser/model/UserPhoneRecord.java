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
public class UserPhoneRecord implements Serializable {
    private static final long serialVersionUID = 1818496174885623431L;
    private Integer id;
    /**用户id*/
    private long userId;
    /**手机号*/
    private String userPhone;
    /**上次绑定手机号*/
    private String userLastPhone;
    /**添加时间*/
    private Date addTime;
}
