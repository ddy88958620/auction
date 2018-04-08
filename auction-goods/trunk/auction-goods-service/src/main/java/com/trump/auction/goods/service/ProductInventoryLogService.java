package com.trump.auction.goods.service;

import com.trump.auction.goods.model.ProductInventoryLogModel;

/**
 * Created by 罗显 on 2017/12/21.
 * 库存表
 */
public interface ProductInventoryLogService {

    /**
     * 验证库存是否足够
     * @param productId
     * @param productNum
     * @return true 足够，false 缺货
     */
    boolean validateStock(int productId, int productNum);
    /**
     * 扣除库存
     * @param productInventoryLogModel
     */
    int  subtractStock(ProductInventoryLogModel productInventoryLogModel);
    /**
     * 增加库存使用记录
     * @param  productInventoryLogModel
     */
    int  addStock(ProductInventoryLogModel productInventoryLogModel);
    /**
     * 根据条件查询
     */
    ProductInventoryLogModel getProductInventoryLog(int productId);
    /**
     * 修改库存
     * @param productInventoryLogModel
     */
    int updateStock(ProductInventoryLogModel productInventoryLogModel);
}
