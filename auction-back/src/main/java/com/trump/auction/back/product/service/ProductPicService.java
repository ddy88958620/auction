package com.trump.auction.back.product.service;

import com.trump.auction.back.product.model.ProductInfo;
import com.trump.auction.back.product.model.ProductPic;

import java.util.List;

/**
 * 商品图片service接口
 * @author liuxueshen
 * @date 2017/12/26
 */
public interface ProductPicService {


    /**
     * 获取商品图片信息通过商品ID
     * @param productId
     * @return
     */
    List<ProductPic> gePicByProductId(Integer productId);

}
