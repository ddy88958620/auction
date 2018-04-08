package com.trump.auction.back.product.dao.read;


import com.trump.auction.back.product.model.ProductInventoryLogRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductInventoryLogRecordDao {


    /**
     *获取库存记录列表通过商品ID
     * @param productId
     * @return
     */
    List<ProductInventoryLogRecord> getRecordListByProductId(@Param("productId") Integer productId);

}