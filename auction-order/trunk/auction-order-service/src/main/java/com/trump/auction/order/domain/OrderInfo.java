package com.trump.auction.order.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderInfo {
    /**
     * 主键不为空
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
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 商品图片
     */
    private String productPic;

    /**
     * 订单总金额
     */
    private BigDecimal orderAmount;

    /**
     * 商品金额
     */
    private BigDecimal productPrice;

    /**
     * 运费
     */
    private BigDecimal freight;

    /**
     * 付款时间
     */
    private Date buyerPayTime;

    /**
     *
     */
    private Integer orderStatus;

    /**
     * 子状态
     */
    private Integer orderSubstatus;

    /**
     * 下单时间
     */
    private Date orderCreateTime;

    /**
     * 派单时间
     */
    private Date orderDispatchTime;

    /**
     * 发货时间
     */
    private Date orderDeliveryTime;

    /**
     * 收货时间
     */
    private Date orderReceiveTime;

    /**
     * 订单标志
     */
    private Integer orderFlag;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品Id
     */
    private Integer productId;

    /**
     * 商品数量
     */
    private Integer productNum;

    /**
     * 商品sku
     */
    private String productSku;

    /**
     * 省
     */
    private Integer provinceCode;

    /**
     * 市
     */
    private String cityCode;

    /**
     * 县
     */
    private String districtCode;

    /**
     * 镇
     */
    private String townCode;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 省
     */
    private String provinceName;

    /**
     * 市
     */
    private String cityName;

    /**
     * 县
     */
    private String districtName;

    /**
     * 区
     */
    private String townName;

    /**
     * 收货人姓名
     */
    private String userName;

    /**
     * 收货人手机号
     */
    private String userPhone;

    /**
     * 删除标志 0正常 1删除 2作废
     */
    private Integer delStatus;

    /**
     * 备注
     */
    private String remark;

    /**
     * 订单取消时间
     */
    private Date cancelTime;

    /**
     * 购物币抵扣金额
     */
    private BigDecimal buyCoinMoney;

    /**
     * 实付款
     */
    private BigDecimal paidMoney;

    /**
     * 购物币id
     */
    private Integer buyCoinId;

    /**
     * 购物币数量
     */
    private Integer buyCoinNum;

    /**
     * 购物币类型
     */
    private Integer buyCoinType;

    /**
     * 拍币数量
     */
    private Integer auctionCoinNum;

    /**
     * 订单类型
     */
    private Integer orderType;

    /**
     * 出价次数
     */
    private Integer bidTimes;

    /**
     * 拍品期数ID
     */
    private Integer auctionNo;
}