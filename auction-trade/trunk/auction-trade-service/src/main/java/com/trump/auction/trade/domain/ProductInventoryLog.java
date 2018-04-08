package com.trump.auction.trade.domain;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * 库存表
 */
@Data
@ToString
public class ProductInventoryLog implements Serializable{


    private static final long serialVersionUID = -3672872663423701557L;
    private Integer id;
    private Integer productId;
    private Integer productNum;
    private Date    createTime;
    private Date    updateTime;
    private Integer userId;
    private String  userIp;
}
