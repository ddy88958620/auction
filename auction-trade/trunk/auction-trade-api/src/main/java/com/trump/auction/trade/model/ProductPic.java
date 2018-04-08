package com.trump.auction.trade.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ProductPic implements Serializable{
    private static final long serialVersionUID = -7315438062272532712L;
    /**
     * 主键,不为空
     */
    private Integer id;

    /**
     * 图片路径
     */
    private String picUrl;

    /**
     * 规格id
     */
    private Integer skuId;

    /**
     * 颜色ID
     */
    private Integer colorId;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 图片类型 0详情 1商品 2.商品缩略图 3.质保图片
     */
    private String picType;

    /**
     * 操作人id
     */
    private String userId;

    /**
     * 操作人ip
     */
    private String userIp;


}