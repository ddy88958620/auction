package com.trump.auction.goods.api;

import com.trump.auction.goods.model.ProductInfoModel;
import com.trump.auction.goods.vo.ProductManageVo;
import com.trump.auction.goods.vo.ResultBean;

import java.util.List;

/**
 * 商品管理对外提供接口
 * @author Administrator
 * @date 2017/12/21
 */
public interface ProductManageStubService {
    /**
     * 商品上架
     * @param productManageVo
     * @return
     * @throws Exception
     */
    public ResultBean<Integer> productOn(ProductManageVo productManageVo) ;


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
    public ResultBean<Integer> productOff(List<ProductManageVo> productManageVos) ;

    /**
     * 商品删除
     * @param productManageVos
     * @return
     * @throws Exception
     */
    public ResultBean<Integer> productBatchDel(List<ProductInfoModel> productManageVos) ;
}
