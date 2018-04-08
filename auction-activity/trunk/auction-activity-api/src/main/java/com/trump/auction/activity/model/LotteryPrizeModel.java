package com.trump.auction.activity.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 抽奖奖品Model
 * @author wangbo 2018/1/8.
 */
@Data
@ToString
public class LotteryPrizeModel implements Serializable {
    private static final long serialVersionUID = -2888683518206943692L;
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
