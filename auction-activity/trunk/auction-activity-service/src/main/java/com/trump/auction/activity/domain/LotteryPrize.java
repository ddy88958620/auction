package com.trump.auction.activity.domain;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 抽奖奖品实体类
 * @author wangbo 2018/1/8.
 */
@Data
@ToString
public class LotteryPrize {
    private Integer id;
    private String prizeNo;
    private String prizeName;
    private String prizePic;
    private Integer prizeType;
    private Integer prizeTypeSub;
    private Integer amount;
    private BigDecimal prizeRate;
    private Integer store;
    private Integer isOpen;
    private Integer orderNumber;
    private String isPlan1;
    private String isPlan2;
    private String remark;
    private Date addTime;
    private String updateUser;
    private Date updateTime;
    private Integer buyCoinType;
    private Integer productId;
    private String productName;
    private String productPic;
}
