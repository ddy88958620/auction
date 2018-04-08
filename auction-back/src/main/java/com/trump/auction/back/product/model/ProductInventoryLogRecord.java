package com.trump.auction.back.product.model;

import lombok.Data;

import java.util.Date;

@Data
public class ProductInventoryLogRecord {
    /**
     * 主键,不为空
     */
    private Integer id;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 修改后库存
     */
    private Integer updProductNum;

    /**
     * 商品之前库存
     */
    private Integer oldProductNum;

    /**
     * 颜色id
     */
    private Integer colorId;

    /**
     * 规格id
     */
    private Integer skuId;

    /**
     * 1.后台修改 2.出库 3.入库
     */
    private Integer type;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 操作人id
     */
    private Integer userId;

    /**
     * 操作人ip
     */
    private String userIp;


}