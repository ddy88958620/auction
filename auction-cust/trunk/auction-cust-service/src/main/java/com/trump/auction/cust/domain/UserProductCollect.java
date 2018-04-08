package com.trump.auction.cust.domain;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 用户收藏实体
 * @author wangbo
 */
@Data
@ToString
public class UserProductCollect {
    private Integer id;
    private Integer userId;
    private Integer productId;
    private String productDetail;
    private Integer productMoney;
    private Integer periodsId;
    private Date createTime;
    private Date updateTime;
    private Integer status;
    private String picUrl;
}
