package com.trump.auction.back.frontUser.model;

import lombok.Data;
import java.util.Date;
/**
 * 用户列表实体
 * Created by wangYaMin on 2017/12/26.
 */
@Data
public class UserInfo {
    private int id;
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
    /**
     * 用户类型 1:普通用户,2:机器人
     */
    private String userType;
    private Date addTime;
    /**
     * 充值状态:    1:未充值，2:首充，3:多次,4:首冲反币等待中,5:首冲反币成功,6:首冲拍品成功'
     */
    private String rechargeType;
    /**
     * 首冲金额
     */
    private String rechargeMoney; //单位(分)
    /**添加ip地址*/
    private String addIp;
    /**更新时间*/
    private Date updateTime;
    /**
     * 状态 1：正常，2：注销
     */
    private int status;
    private String userFrom;
    /**用户收货地址 (省)*/
    private String provinceName;
    /**用户收货地址 (市)*/
    private String cityName;


    /**用户账户的分类：1：拍币；2：赠币；3：积分；4：购物币*/
    private int accountType;
    /**用户id*/
    private int userId;
    private int coin;
    /**拍币数额*/
    private Integer coin1;
    /**赠币数额*/
    private Integer coin2;
    /**积分数额*/
    private Integer coin3;
    /**购物币数额*/
    private Integer coin4;

    /**用户昵称*/
    private  String nickName;
    /**头像地址*/
    private String headImg;

    /**app信息
     * {"clientType":"xx","appVersion":"版本号","deviceId":"设备id","deviceName":"设备名","osVersion":"xx","appName":"app名称","appMarket":"应用市场名"}*/
    private  String appInfo;

    /**终端标识*/
    private  String terminalSign ;

    /**用户来源*/
    private  String appInfoSource ;

    /**用户渠道来源*/
    private  String channelSource ;

}
