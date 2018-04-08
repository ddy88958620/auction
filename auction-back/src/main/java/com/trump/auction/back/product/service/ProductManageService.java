package com.trump.auction.back.product.service;

import com.cf.common.util.page.Paging;
import com.trump.auction.back.product.vo.ParamVo;
import com.trump.auction.back.product.vo.ProductVo;
import com.trump.auction.back.sys.model.SysUser;

/**
 * 商品管理-----上下架接口
 * @author liuxueshen
 * @date 2017/12/22
 */
public interface ProductManageService {

    /**
     * 分布查询拍品信息
     * @param paramVo
     * @return
     */
    Paging<ProductVo> findByPage(ParamVo paramVo);

    /**
     * 拍品批量上架
     * @param ids 拍品的集合
     */
    void productBatchOn(Integer[] ids, SysUser user) throws Exception;

    /**
     * 拍品批量下架
     * @param ids 拍品的ID集合
     */
    void productBatchOff(Integer[] ids, SysUser user) throws Exception;

    /**
     * 拍品删除
     * @param ids id集合
     * @param user 用户信息
     * @throws Exception
     */
    void productBatchDel(Integer[] ids, SysUser user) throws Exception;


    /**
     * 通过拍品ID获取拍品信息
     * @param id
     * @return
     */
    public ProductVo getProduct(Integer id);
}
