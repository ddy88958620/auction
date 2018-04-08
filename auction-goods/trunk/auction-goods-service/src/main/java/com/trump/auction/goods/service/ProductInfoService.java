package com.trump.auction.goods.service;

import com.cf.common.utils.JsonResult;
import com.trump.auction.goods.domain.ProductInfo;
import com.trump.auction.goods.model.ProductInfoModel;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: zhangqingqiang.
 * @date: 2017/12/21 0021.
 * @Description: 商品 .
 */
@Service
public interface ProductInfoService {

    JsonResult saveProducts(List<ProductInfo> products);
    JsonResult saveProduct(ProductInfoModel productInfoModel) throws Exception;
    JsonResult updateProduct(ProductInfoModel productInfoModel) throws Exception;
    JsonResult updateStatus(Integer productId);

    ProductInfoModel getProductByProductId(Integer productId);

    /**
     * 修改收藏数量
     * @param productId
     * @param collectCount
     * @return
     */
    JsonResult updateCollectCount(Integer productId, int collectCount);
}
