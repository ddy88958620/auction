package com.trump.auction.back.product.dao.read;


import com.trump.auction.back.product.model.ProductPic;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProductPicDao {

    /**
     *
     * @mbggenerated 2017-12-27
     */
    ProductPic selectByPrimaryKey(Integer id);

    /**
     * 查询商品图片
     * @param param
     * @return
     */
    List<ProductPic> selectByProductIdOrType(Map<String,Object> param);
}