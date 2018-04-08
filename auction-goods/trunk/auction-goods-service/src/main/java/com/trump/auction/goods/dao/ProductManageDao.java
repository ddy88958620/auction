package com.trump.auction.goods.dao;

import com.trump.auction.goods.domain.ProductManage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品管理Dao
 * @author zhangqingqiang
 * @since 2017-12-21
 */

@Repository
public interface ProductManageDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductManage record);

    /**
     * 插入上架商品
     * @param record
     * @return
     */
    int insertSelective(ProductManage record);

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

    int updateByPrimaryKeySelective(ProductManage record);

    int updateByPrimaryKey(ProductManage record);

    /**
     * 下架时批量修改状态
     * @param manageList
     * @return
     */
    int updateStatusBatch(List<ProductManage> manageList);
}