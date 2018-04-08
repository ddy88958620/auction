package com.trump.auction.goods.domain;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 商品图片
 * @author zhangqingqiang
 * @since 2017-12-21
 */
@Data
@ToString
public class ProductPic {
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