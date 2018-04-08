package com.trump.auction.goods.dao;

import com.trump.auction.goods.domain.ProductClassify;
import com.trump.auction.goods.domain.ProductManage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品分类Dao
 * @author Administrator
 */
@Repository
public interface ProductClassifyDao {


    /**
     *
     * @mbggenerated 2017-12-26
     */
    ProductClassify selectByPrimaryKey(Integer id);

    /**
     * 查询全部商品分类
     * @param status 分类状态
     * @return
     */
    List<ProductClassify> selectAllByStatus(@Param("status") int status);

    /**
     * 商品分类添加
     * @param productClassify
     * @return
     */
    int insertSelective(ProductClassify productClassify);

    /**
     * 商品分类修改
     * @param productClassify
     * @return
     */
    int updateByPrimaryKeySelective(ProductClassify productClassify);

    /**
     * 删除批量修改状态
     * @param classifyList
     * @return
     */
    int updateStatusBatch(List<ProductClassify> classifyList);


}