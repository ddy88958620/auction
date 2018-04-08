package com.trump.auction.back.order.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wangjian
 */
@Data
public class Logistics {
    /**
     * 主键,不为空
     */
    private Integer id;

    /**
     * 物流第三发发货单号
     */
    private String logisticsId;

    /**
     * 物流公司名称
     */
    private String logisticsName;

    /**
     * 物流信息 JSON 大字段
     */
    private String logisticsInfo;

    /**
     * 物流公司code
     */
    private String logisticsCode;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 物流发货时间
     */
    private Date startTime;

    /**
     * 物流状态：0未发货，1已发货
     */
    private Integer logisticsStatus;

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 收货人
     */
    private String transName;

    /**
     * 收货人电话
     */
    private String transTelphone;

    /**
     * 收货人手机号
     */
    private String transPhone;

    /**
     *  省
     */
    private Integer provinceCode;

    /**
     *  城市
     */
    private Integer cityCode;

    /**
     *  区
     */
    private Integer districtCode;

    /**
     *  镇
     */
    private Integer townCode;

    /**
     * 收货人详细地址
     */
    private String address;

    /**
     * 收货人 邮编
     */
    private String receiverCode;

    /**
     * 省
     */
    private String provinceName;

    /**
     *  城市
     */
    private String cityName;

    /**
     * 区
     */
    private String districtName;

    /**
     * 镇
     */
    private String townName;

    /**
     * 发货人详细地址
     */
    private String sendAddress;

    /**
     * 发货人联系电话
     */
    private String sendPhone;

    /**
     * 发货人
     */
    private String sendName;

    /**
     * 发货人 邮编
     */
    private String receiverName;

    /**
     * 运费
     */
    private Long freight;

    /**
     * 订单总额
     */
    private Long totalOrder;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 后台操作人ID
     */
    private Integer backUserId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 商品渠道价
     */
    private BigDecimal productAmount;

    /**
     * 商品销售价
     */
    private BigDecimal productPrice;

    /**
     * 订单类型
     */
    private Integer orderType;

    /**
     * 收货人姓名
     */
    private String userName;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 订单总金额
     */
    private BigDecimal orderAmount;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 实付款
     */
    private BigDecimal paidMoney;

    /**
     * 出价次数
     */
    private Integer bidTimes;
}