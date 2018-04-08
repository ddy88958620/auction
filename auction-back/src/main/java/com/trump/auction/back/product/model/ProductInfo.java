package com.trump.auction.back.product.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品信息
 * @author zhangqingqiang
 * @since 2017-12-21
 */
@Data
@ToString
public class ProductInfo implements Serializable {
    private Integer id;

    private Integer merchantId;

    private String orderId;

    private String productName;

    private Date createTime;

    private Date updateTime;

    private Integer productId;

    private String productTitle;

    private Integer classify1;

    private Integer classify2;

    private Integer classify3;

    private Integer brandId;

    private Long productAmount;

    private Long salesAmount;

    private Integer productStatus;

    private Integer flag;

    private Integer userId;

    private String userIp;

    private Integer stagesId;

    private Integer skuId;

    private BigDecimal freight;

    private String skuInfo;

    private String remarks;

    private String picUrl;

    private String skuTitle;

    private String colorTitle;

    private BigDecimal proportion;

}