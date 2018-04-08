package com.trump.auction.goods.dao;

import com.trump.auction.goods.domain.ProductPic;

import java.util.List;
import java.util.Map;

public interface ProductPicDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductPic record);

    int insertSelective(ProductPic record);

    ProductPic selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductPic record);

    int updateByPrimaryKey(ProductPic record);

    /**
     * 批量添加商品图片
     * @param pics
     * @return
     */
    int batchInsert(List<ProductPic> pics);

    /**
     * 根据商品ID或图片类型删除图片
     * @param param
     * @return
     */
    int deleteByProductIdOrType(Map<String,Object> param);
}