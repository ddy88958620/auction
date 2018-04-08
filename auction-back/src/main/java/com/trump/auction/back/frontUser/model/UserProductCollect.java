package com.trump.auction.back.frontUser.model;


import lombok.Data;

import java.util.Date;

/**
 * 用户收藏实体
 * @author wangYaMin
 */
@Data
public class UserProductCollect{
    private Integer id;
    private Integer userId;
    private Integer productId;
    private String productDetail;
    private Integer productMoney;
    private Date createTime;
    private Date updateTime;
    private Integer status;

}
