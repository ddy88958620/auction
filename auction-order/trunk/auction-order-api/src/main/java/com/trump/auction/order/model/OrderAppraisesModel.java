package com.trump.auction.order.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;



/**
 * @author wangjian
 */
@Data
public class OrderAppraisesModel implements Serializable {

    private static final long serialVersionUID = -3114692563725101949L;

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
     * 购买人昵称
     */
    private String buyNickName;
    
    /**
     * 购买人昵称
     */
    private String buyPic;

    /**
     * 商家id
     */
    private String merchantId;

    /**
     * 商品Id
     */
    private Integer productId;
    
    /**
     * 商品名称
     */
    private String productName;

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
    private Integer isShow;

    /**
     * 点评内容
     */
    private String content;
    
    /**
     * 类型
     */
    private Integer type;
    
    /**
     * 状态
     */
    private String status;
    
    /**
     * 出价次数
     */
    private Integer bidTimes;
    
    /**
     * 拍品期数ID
     */
    private Integer auctionNo;

    /**
     * 晒单级别
     */
    private String appraisesLevel;

}