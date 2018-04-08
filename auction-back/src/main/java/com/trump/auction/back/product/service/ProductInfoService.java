package com.trump.auction.back.product.service;

import com.cf.common.util.page.Paging;
import com.trump.auction.back.product.model.ProductInfo;
import com.trump.auction.back.product.vo.InventoryVo;
import com.trump.auction.back.product.vo.ParamVo;

/**
 * 商品service接口
 * @author liuxueshen
 * @date 2017/12/26
 */
public interface ProductInfoService {


    /**
     * 获取商品信息通过ID
     * @param id
     * @return
     */
    ProductInfo getProductById(Integer id);

}
