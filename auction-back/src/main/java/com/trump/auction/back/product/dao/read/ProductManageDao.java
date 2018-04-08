package com.trump.auction.back.product.dao.read;

import com.trump.auction.back.product.model.ProductManage;
import com.trump.auction.back.product.vo.ParamVo;
import com.trump.auction.back.product.vo.ProductVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品管理Dao
 * @author liuxueshen
 * @since 2017-12-21
 */

@Repository
public interface ProductManageDao {
    /**
     * 查询商品
     * @param id
     * @return
     */
    ProductManage selectByPrimaryKey(Integer id);

    /**
     * 通过商品ID查询商品
     * @param productId
     * @return
     */
    ProductManage selectByProductId(@Param("productId") Integer productId);

    /**
     * 通过条件查询拍品
     * @param paramVo
     * @return
     */
    List<ProductVo> selectByParamVo(ParamVo paramVo);

    /**
     * 根据拍品ID查询拍品信息
     * @param productId
     * @return
     */
    ProductVo findProductInfoById(@Param("productId") Integer productId);
}