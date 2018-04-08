package com.trump.auction.back.product.dao.read;


import com.trump.auction.back.product.model.ProductClassify;
import com.trump.auction.back.product.vo.ParamVo;
import com.trump.auction.back.product.vo.ProductVo;
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
     *
     * @mbggenerated 2017-12-26
     */
    List<ProductClassify> selectByName(@Param("name")String name);

    List<ProductClassify> selectBySort(@Param("sort")Integer sort);

    /**
     * 查询全部商品分类
     * @param status 分类状态
     * @return
     */
    List<ProductClassify> selectAllByStatus(@Param("status") int status);

    /**
     * 通过条件查询商品分类
     * @param paramVo
     * @return
     */
    List<ProductClassify> selectByParamVo(ParamVo paramVo);


}