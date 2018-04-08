package com.trump.auction.order.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wangjian
 */
@Data
public class OrderAppraisesQuery implements Serializable {

    private static final long serialVersionUID = -6346811246211191505L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 交易订单id
     */
    private String orderId;

    /**
     * 购买人id
     */
    private String buyId;

    /**
     * 商家id
     */
    private String merchantId;

    /**
     * 商品Id
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
     * 评价图片
     */
    private String appraisesPic;

    /**
     * 是否显示
     */
    private Integer isshow;

    /**
     * 点评内容
     */
    private String content;
}