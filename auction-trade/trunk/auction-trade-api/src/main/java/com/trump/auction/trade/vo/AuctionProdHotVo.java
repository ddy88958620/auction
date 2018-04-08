package com.trump.auction.trade.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 热门拍品VO
 * @author Administrator
 */
public class AuctionProdHotVo implements Serializable{

    private static final long serialVersionUID = -941474062974511323L;
    /**
     * 拍品状态
     */
    @Getter
    @Setter
    private  String status;
    /**
     * 拍品id
     */
    @Getter
    @Setter
    private String auctionProdId;
    /**
     * 商品图片
     */
    @Getter
    @Setter
    private String previewPic;
    /**
     * 拍品期次ID
     */
    @Getter
    @Setter
    private  String auctionId;
    /**
     * 出价价格
     */
    @Getter
    @Setter
    private  String bidPrice;

    /**
     * 商品名称
     */
    @Getter
    @Setter
    private  String productName;
    /**
     * 排序
     */
    @Getter
    @Setter
    private Integer sort;

    /**
     * 商品ID
     */
    @Getter
    @Setter
    private Integer productId;


    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()){ return false;
        }

        AuctionProdHotVo hotVo = (AuctionProdHotVo) o;

        return auctionProdId.equals(hotVo.auctionProdId);
    }

    @Override
    public int hashCode() {
        return auctionProdId.hashCode();
    }
}
