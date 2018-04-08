package com.trump.auction.goods.dao;

import com.trump.auction.goods.domain.ProductInfo;
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
    int deleteByPrimaryKey(Integer id);

    int insert(ProductInfo record);

    int insertSelective(ProductInfo record);

    ProductInfo getProductByProductId(@Param("productId") Integer productId);

    int updateByPrimaryKeySelective(ProductInfo record);

    int updateByPrimaryKey(ProductInfo record);

    int saveProducts(@Param("products") List<ProductInfo> products);

    int updateStatus(@Param("productId")Integer productId, @Param("productStatus")Integer productStatus);

    int batchDel(@Param("list") List<ProductInfo> list);

    /**
     * 修改收藏数量
     * @param productId
     * @param collectCount
     * @return
     */
    int updateCollectCount(@Param("productId") Integer productId,@Param("collectCount")  int collectCount);
}