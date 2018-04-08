package com.trump.auction.goods.api;

import com.trump.auction.goods.model.ProductClassifyModel;
import com.trump.auction.goods.model.ProductInfoModel;
import com.trump.auction.goods.vo.ProductManageVo;
import com.trump.auction.goods.vo.ResultBean;

import java.util.List;

/**
 * 商品分类对外提供接口
 * @author Administrator
 * @date 2017/12/21
 */
public interface ProductClassifyStubService {


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
