package com.trump.auction.back.frontUser.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author=hanliangliang
 * @Date=2018/03/15
 */
@Data
@ToString
public class UserLoginRecordModel implements Serializable {
    /**
	 * @author Liuminglu
	 * @Date 2018年3月24日 下午12:29:34
	 */
	private static final long serialVersionUID = 5140632549638781488L;
	
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
     * 登录类型(Phone,WX,QQ,SMS)
     */
    private String  loginType;
    /**
     * 添加时间
     */
    private Date    addTime;

}
