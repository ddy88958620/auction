package com.trump.auction.back.product.service;

import com.cf.common.util.page.Paging;
import com.trump.auction.back.product.model.ProductClassify;
import com.trump.auction.back.product.vo.ParamVo;
import com.trump.auction.back.product.vo.ProductVo;
import com.trump.auction.back.sys.model.SysUser;
import com.trump.auction.goods.vo.ResultBean;

import java.util.List;

/**
 * 商品分类service接口
 * @author liuxueshen
 * @date 2017/12/26
 */
public interface ProductClassifyService {


    /**
     * 查询全部商品分类
     * @return
     */
    List<ProductClassify> selectAll();

    /**
     * 查询全部商品分类通过ID
     * @return
     */
    ProductClassify selectById(int id);


    /**
     * 查询全部商品分类通过分类名称
     * @return
     */
    List<ProductClassify> selectByName(String name);

    /**
     * 查询分类通过排序
     * @return
     */
    List<ProductClassify> selectBySort(Integer sort);


    /**
     * 查询全部商品分类通过分类状态
     * @param status
     * @return
     */
    List<ProductClassify> selectByStatus(Integer status);

    /**
     * 分页查询商品分类信息
     * @param paramVo
     * @return
     */
    Paging<ProductClassify> findByPage(ParamVo paramVo);
    /**
     * 批量删除商品分类
     * @param ids
     * @param sysUser
     * @return
     */
    ResultBean<Integer> productClassifyBatchDel(Integer[] ids, SysUser sysUser) throws Exception;
}
