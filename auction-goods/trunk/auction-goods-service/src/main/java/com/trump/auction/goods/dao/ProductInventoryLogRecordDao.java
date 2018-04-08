package com.trump.auction.goods.dao;

import com.trump.auction.goods.domain.ProductInventoryLogRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by 罗显 on 2017/12/21.
 */
@Repository
public interface ProductInventoryLogRecordDao {
    /**
     * 增加库存使用记录
     * @param productInventoryLogRecord
     */
    int  addStock(ProductInventoryLogRecord productInventoryLogRecord);
    /**
     * 该商品是否有使用记录
     * @param productId
     * @return
     */
    ProductInventoryLogRecord validateRecord(@Param(value = "productId") int productId);
    /**
     * 修改使用记录
     * @param productInventoryLogRecord
     */
    int updateStock(ProductInventoryLogRecord productInventoryLogRecord);

}
