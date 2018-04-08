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
public class ProductVo {

    /**
     * 商品ID
     */
    private Integer productId;

    /**
     * 商品管理ID
     */
    private Integer productManageId;

    /**
     * 商品标识
     */
    private String colorTitle;

    /**
     * 商品规格
     */
    private String skuTitle;

    /**
     * 商品所属商家名称
     */
    private String merchantName;

    private Integer merchantId;

    private String classifyName;

    /**
     * 拍品库存
     */
    private Integer productNum;

    /**
     * 商品销售价格
     */
    private BigDecimal salesAmount;



    /**
     * 商品价格
     */
    private BigDecimal productPrice;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 商品状态
     */
    private Integer productStatus;

    /**
     * 管理状态
     */
    private Integer status;

    /**
     * 商品标题商品名称
     */
    private String productName;
    /**
     * 分类名称
     */
    private String brandName;
    /**
     * 商品标题
     */
    private String productTitle;

    /**
     * 预览图
     */
    private String previewPic;
    /**
     * 上架时间
     */
    private Date onShelfTime;

    private Date beginTime;

    private Date endTime;

    /**
     * 市场价格
     */
    private BigDecimal productAmount;


    /**
     * 拍品图片
     */
    private List<String> urls;

    /**
     * 竞拍规则
     */
    private String rules;

    /**
     * 拍品参数
     */
    private String auctionParam;

    /**
     * 预设成交价格
     */
    private BigDecimal okPrice;

    /**
     * 预设销售利润
     */
    private String beginProfit;

    /**
     *预设销售利润
     */
    private String endProfit;

    /**
     * 图片列表
     */
    private List<ProductPic> pics;

}
