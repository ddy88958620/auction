package com.trump.auction.goods.service;


import com.trump.auction.goods.domain.ProductClassify;
import com.trump.auction.goods.model.ProductClassifyModel;
import com.trump.auction.goods.vo.ResultBean;

import java.util.List;

/**
 * 商品分类service接口
 * @author liuxueshen
 * @date 2017/12/26
 */
public interface ProductClassifyService {



    /**
     * 商品分类添加
     * @param productClassifyModel
     * @return
     */
    public ResultBean<Integer> saveClassify(ProductClassifyModel productClassifyModel) throws Exception;


    /**
     * 批量删除商品分类
     * @param productClassifyModelList
     * @return
     */
    public ResultBean<Integer> deleteBatch(List<ProductClassifyModel> productClassifyModelList) throws Exception;


    /**
     * 批量启用商品分类
     * @param productClassifyModelList
     * @return
     */
    public ResultBean<Integer> enableBatch(List<ProductClassifyModel> productClassifyModelList) throws Exception;



    /**
     * 修改商品分类
     * @param productClassifyModel
     * @return
     */
    public ResultBean<Integer> updateClassify(ProductClassifyModel productClassifyModel) throws Exception;

}
