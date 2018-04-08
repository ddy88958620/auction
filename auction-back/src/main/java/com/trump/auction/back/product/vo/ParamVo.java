package com.trump.auction.back.product.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

/**
 * 查询条件VO
 * @author Administrator
 * @date 2017/12/22
 */
@Data
public class ParamVo {

    private Integer id;
    private  Integer userId;
    private String userName;
    private Integer auctionProdId;
    private Integer userType;

    private Integer productId;

    /**
     * 分类
     */
    private Integer classify1;
    /**
     * 拍品名称
     */
    private String productName;
    /**
     * 拍品状态
     */
    private Integer productStatus;
    /**
     * 开拍时间/上架时间下限
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME,pattern = "yyyy-MM-dd")
    private Date beginTime;
    /**
     * 结束时间/上架时间上限
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME,pattern = "yyyy-MM-dd")
    private Date endTime;

    /**
     * 页码
     */
    private int page;

    /**
     * 页的数量
     */
    private int limit;

    /**
     * 成交数量
     */
    private int turnover;

    /**
     *成交价下限
     */
    private String beginOkPrice;

    /**
     * 成交价上限
     */
    private String endOkPrice;

    /**
     * 商品分类名称
     */
    private String classifyName;
    /**
     * 商品分类状态
     */
    private Integer classifyStatus;
    /**
     * 商品分类ID
     */
    private Integer classifyId;

    private Integer remainNum;

    private Integer bargain;

    private BigDecimal beginPrice;

    private BigDecimal endPrice;

    private Integer status;

    private String ruleName;



}
