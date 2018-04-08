package com.trump.auction.goods.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.trump.auction.goods.api.ProductInventoryLogRecordSubService;
import com.trump.auction.goods.model.ProductInventoryLogRecordModel;
import com.trump.auction.goods.service.ProductInventoryLogRecordService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by 罗显 on 2017/12/21.
 */
@Service(version = "1.0.0")
public class ProductInventoryLogRecordSubServiceImpl implements ProductInventoryLogRecordSubService {

    @Autowired
    private ProductInventoryLogRecordService productInventoryLogRecordService;

    @Override
    public int addStock(ProductInventoryLogRecordModel productInventoryLogRecordModel) {
       return productInventoryLogRecordService.addStock(productInventoryLogRecordModel);
    }

    @Override
    public ProductInventoryLogRecordModel validateRecord(int productId) {
        ProductInventoryLogRecordModel validateRecord  = productInventoryLogRecordService.validateRecord(productId);
        if(validateRecord !=null ){
            return validateRecord;
        }else {
            return null;
        }
    }

    @Override
    public int updateStock(ProductInventoryLogRecordModel productInventoryLogRecordModel) {
        return productInventoryLogRecordService.updateStock(productInventoryLogRecordModel);
    }
}
