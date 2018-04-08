package com.trump.auction.back.product.vo;

import com.trump.auction.back.product.model.ProductPic;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author
 * @date 2017/12/22
 */
@Data
public class InventoryVo {

    /**
     * 商品ID
     */
    private Integer productId;
    /**
     * 商品库存
     */
    private Integer productNum;

    /**
     * 商品状态
     */
    private Integer productStatus;

    /**
     * 商品标题商品名称
     */
    private String productName;


}
