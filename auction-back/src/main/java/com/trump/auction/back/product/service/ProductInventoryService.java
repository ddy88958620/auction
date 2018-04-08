package com.trump.auction.back.product.service;

import com.cf.common.util.page.Paging;
import com.trump.auction.back.product.model.ProductClassify;
import com.trump.auction.back.product.model.ProductInventoryLogRecord;
import com.trump.auction.back.product.vo.InventoryVo;
import com.trump.auction.back.product.vo.ParamVo;
import com.trump.auction.back.sys.model.SysUser;
import com.trump.auction.goods.vo.ResultBean;

import java.util.List;

/**
 * 商品库存service接口
 * @author liuxueshen
 * @date 2017/12/26
 */
public interface ProductInventoryService {



    /**
     * 分页查询商品库存信息
     * @param paramVo
     * @return
     */
    Paging<InventoryVo> findByPage(ParamVo paramVo);


    /**
     * 获取库存信息通过商品ID
     * @param productId
     * @return
     */
    InventoryVo getInventoryByProductId(Integer productId);


    /**
     * 获取库存记录列表通过商品ID
     * @param id
     * @return
     */
    List<ProductInventoryLogRecord> getInventoryRecordList(Integer id);

}
