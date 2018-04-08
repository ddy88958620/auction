package com.trump.auction.goods.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品图片model
 * @author zhangqingqiang
 * @since 2017-12-21
 */
@Data
@ToString
public class ProductPicModel implements Serializable{
    private Integer id;

    private String picUrl;

    private Integer skuId;

    private Integer colorId;

    private Integer productId;

    private Date createTime;

    private Date updateTime;

    private String picType;

    private String userId;

    private String userIp;
}