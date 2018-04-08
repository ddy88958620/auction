package com.trump.auction.back.frontUser.model;

import lombok.Data;

import java.util.Date;

/**
 * 用户交易记录列表实体
 * Created by Yize Xuan
 */
@Data
public class UserTransactionInfo {
    private Integer id;
    private String orderNo ;
    private Integer userId;
    private String userPhone;
    private Integer accountType;
    private String productName;
    private Integer transactionCoin;
    private Integer transactionType;
    private String transactionTag;
    private int balanceType;
    private Integer coin;
    private Integer freezeCoin;
    private String productThumbnail;
    private String productImage;
    private String orderId;
    private String orderSerial;
    private String remark;
    private Date createTime;
    private Date updateTime;
}
