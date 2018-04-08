package com.trump.auction.back.product.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 商品管理实体
 * @author zhangqingqiang
 * @since 2017-12-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductManage {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 订单状态上下架状态(0未上架 1.已上架)
     */
    private Integer status;

    /**
     * 1级分类
     */
    private String classify1;

    /**
     * 2级分类
     */
    private String classify2;


    /**
     * 3级分类
     */
    private String classify3;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 销售价格
     */
    private Long salesPrice;

    /**
     * 分期方案id
     */
    private Integer stagesId;

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

    /**
     * 上架时间
     */
    private Date onShelfTime;

    /**
     * 下架时间
     */
    private Date underShelfTime;

}