package com.trump.auction.goods.service;

import com.trump.auction.goods.model.ProductInfoModel;
import com.trump.auction.goods.vo.ProductManageVo;
import com.trump.auction.goods.vo.ResultBean;

import java.util.List;

/**
 *
 * 商品管理接口
 * @author Administrator
 * @date 2017/12/21
 */
public interface ProductManageService {

    /**
     * 商品上架
     * @param productManageVo
     * @return
     * @throws Exception
     */
    public ResultBean<Integer> productOn(ProductManageVo productManageVo) throws Exception;



    /**
     * 商品批量上架
     * @param productManageVos
     * @return
     * @throws Exception
     */
    public ResultBean<Integer> productBatchOn(List<ProductManageVo> productManageVos) ;
    /**
     * 商品下架
     * @param productManageVos
     * @return
     * @throws Exception
     */
    public ResultBean<Integer> productOff(List<ProductManageVo> productManageVos) throws Exception;

    /**
     * 商品删除
     * @param productManageVos
     * @return
     * @throws Exception
     */
    public ResultBean<Integer> productBatchDel(List<ProductInfoModel> productManageVos) ;
}
