package com.trump.auction.back.labelManager.model;

import lombok.Data;

/**
 * @Author:limingwu
 * @Description:
 * @Date: Create in 11:31 2018/3/23
 * @Modified By:
 */
@Data
public class LabelAuctionProduct {


    /**
     * 拍品名称
     */
    private String auctionProductName;

    /**
     * 拍品ID
     */
    private int auctionProductId;

    /**
     * 拍品价格
     */
    private double auctionProductPrice;

    /**
     * 拍品库存
     */
    private int auctionProductStock;

    /**
     * 标签显示
     */
    private String labelShow;

    /**
     * 搜索条件
     */
    private String condition;



}
