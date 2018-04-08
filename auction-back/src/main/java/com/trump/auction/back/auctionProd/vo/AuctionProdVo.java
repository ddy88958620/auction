package com.trump.auction.back.auctionProd.vo;

import com.trump.auction.back.rule.model.AuctionRule;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 查询返回VO
 * @author Administrator
 */
@Data
public class AuctionProdVo {

    private  Integer id;
    private  String productName;
    private  Integer productId;
    private  Integer ruleId;
    private  Integer status;
    private  Integer classifyId;
    private  String classifyName;
    private Integer productNum;

    /**
     * 商品价格
     */
    private BigDecimal productPrice;

    /**
     * 上架时间
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME,pattern = "yyyy-MM-dd HH:mm:ss")
    private Date onShelfTime;

    /**
     * 下架时间
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME,pattern = "yyyy-MM-dd HH:mm:ss")
    private Date underShelfTime;
    private  Integer page=10;
    private  Integer  limit=50;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 成交量
     */
    private Integer bargain;
    /**
     * 拍品规则
     */
    private AuctionRule rule;

    /**
     * 竞拍开始时间
     */
    private Date auctionStartTime;
    /**
     *总成交价
     */
    private BigDecimal bargainPrice;

    private String ruleName;
    private BigDecimal premiumAmount;
    private BigDecimal openingBid;
    private String sort;
    private Integer type;
    /**
     * 竞拍规则
     */
    private String auctionRule;

}
