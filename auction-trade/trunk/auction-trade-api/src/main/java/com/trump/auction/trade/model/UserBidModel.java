package com.trump.auction.trade.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户差价购信息
 */
@Data
public class UserBidModel implements Serializable {

    private BigDecimal salePrice;
    private BigDecimal returnPrice;
    private BigDecimal paymentPrice;
    private String     previewPic;
    private Integer    bidCount;
    private String     UserName;
    private BigDecimal finalPrice;
    private Date     endTime;

}
