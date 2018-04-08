package com.trump.auction.goods.api;

import com.cf.common.utils.JsonResult;
import com.trump.auction.goods.model.ProductInfoModel;

/**
 * @author: zhangqingqiang.
 * @date: 2017/12/22 0022.
 * @Description: 商品信息 .
 */
public interface ProductInfoSubService {

    ProductInfoModel  getProductByProductId(Integer productId);

    JsonResult saveProduct(ProductInfoModel productInfoModel);

    JsonResult updateProduct(ProductInfoModel productInfoModel);

    /**
     * 修改收藏数量
     * @param productId
     * @param collectCount
     * @return
     */
    JsonResult updateCollectCount(Integer productId,int collectCount);
}
