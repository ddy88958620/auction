package com.trump.auction.trade.dao;

import com.trump.auction.trade.domain.ProductInventoryLog;
import org.springframework.stereotype.Repository;

/**
 * Created by 罗显 on 2017/12/21.
 */
@Repository
public interface ProductInventoryLogDao {


    /**
     * 扣除库存
     * @param productInventoryLog
     */
    int  subtractStock(ProductInventoryLog productInventoryLog);


}
