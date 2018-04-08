package com.trump.auction.back.activity.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 中奖记录
 * @author wangbo 2018/1/23.
 */
@Data
public class LotteryRecord implements Serializable {
    private static final long serialVersionUID = 6638253615104986636L;
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
