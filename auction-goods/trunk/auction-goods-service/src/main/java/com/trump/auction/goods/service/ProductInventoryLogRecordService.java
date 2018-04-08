package com.trump.auction.goods.service;

import com.trump.auction.goods.model.ProductInventoryLogRecordModel;

/**
 * Created by 罗显 on 2017/12/21.
 */
public interface ProductInventoryLogRecordService {
    /**
     * 增加库存使用记录
     * @param productInventoryLogRecordModel
     */
    int  addStock(ProductInventoryLogRecordModel productInventoryLogRecordModel);
    /**
     * 该商品是否有使用记录
     * @param productId
     * @return
     */
    ProductInventoryLogRecordModel validateRecord(int productId);
    /**
     * 修改使用记录
     * @param productInventoryLogRecordModel
     */
    int updateStock(ProductInventoryLogRecordModel productInventoryLogRecordModel);
}
