package com.trump.auction.activity.domain;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 抽奖记录实体类
 * @author wangbo 2018/1/8.
 */
@Data
@ToString
public class LotteryRecord {
    private Integer id;
    private String prizeNo;
    private String prizeName;
    private String prizePic;
    private Integer prizeType;
    private Integer prizeTypeSub;
    private Integer amount;
    private Integer userId;
    private String userName;
    private String userPhone;
    private String orderNo;
    private Date addTime;
    private Integer buyCoinType;
    private Integer productId;
    private String productName;
    private String productPic;
}
