package com.trump.auction.account.domain;

import lombok.Data;

import java.util.Date;

/**
 * Created by dingxp on 2018/1/5 0005.
 * 竞拍交易订单
 */
@Data
public class AccountAuctionInfo {

   private Integer id;
    private String orderNo;//流水号
    private Integer  userId;//用户id,
    private Integer transactionCoin;//'交易订单金额(分)',
    private Integer balanceType;//'收支类型：1：收入；2：支出',
    private String orderId;//'订单id',
    private int status ;//状态0：初始状态，1：失败，2：成功
    private Date createTime;
    private Date updateTime;
}
