package com.trump.auction.goods.dao;

import com.trump.auction.goods.domain.ProductInventoryLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by 罗显 on 2017/12/21.
 */
@Repository
public interface ProductInventoryLogDao {
    /**
     * 验证库存是否足够
     * @param productId
     * @param productNum
     * @return true 足够，false 缺货
     */
    Boolean validateStock(@Param(value = "productId") int productId, @Param(value = "productNum") int productNum);

    /**
     * 扣除库存
     * @param productInventoryLog
     */
    int  subtractStock(ProductInventoryLog productInventoryLog);
    /**
     * 增加库存
     * @param productInventoryLog
     */
    int  addStock(ProductInventoryLog productInventoryLog);
    /**
     * 根据条件查询
     */
    ProductInventoryLog getProductInventoryLog(@Param(value = "productId") int productId);
    /**
     * 如果没有就添加
     */
    int insertStock(ProductInventoryLog productInventoryLog);
    /**
     * 修改库存
     * @param productInventoryLog
     */
    int updateStock(ProductInventoryLog productInventoryLog);
}
