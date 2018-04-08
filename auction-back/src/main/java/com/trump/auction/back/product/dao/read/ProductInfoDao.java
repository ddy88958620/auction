package com.trump.auction.back.product.dao.read;

import com.trump.auction.back.product.model.ProductInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品信息
 * @author zhangqingqiang
 * @since 2017-12-21
 */

@Repository
public interface ProductInfoDao {

    ProductInfo getProductByProductId(@Param("productId") Integer productId);
}