package com.trump.auction.back.auctionProd.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 热门拍品VO
 * @author Administrator
 */
public class AuctionProdUpdateVo implements Serializable{


    private static final long serialVersionUID = -559550464505592440L;

    /**
     * 拍品id
     */
    @Getter
    @Setter
    private String auctionProdId;




    /**
     * 排序
     */
    @Getter
    @Setter
    private Integer sort;

    @Getter
    @Setter
    private Integer oldSort;
    @Getter
    @Setter
    private Integer hotSize;






}
