package com.trump.auction.goods.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.trump.auction.goods.api.ProductInventoryLogSubService;
import com.trump.auction.goods.model.ProductInventoryLogModel;
import com.trump.auction.goods.service.ProductInventoryLogService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by 罗显 on 2017/12/21.
 */
@Service(version = "1.0.0")
public class ProductInventoryLogSubServiceImpl implements ProductInventoryLogSubService {
    @Autowired
    private ProductInventoryLogService productInventoryLogService;

    @Override
    public boolean validateStock(int productId, int productNum) {
        boolean flag;
        flag = productInventoryLogService.validateStock(productId,productNum);
        if(flag){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 减少库存时，库存使用记录即增加相应的记录
     * @param productInventoryLogModel
     * @return
     */
    @Override
    public int subtractStock(ProductInventoryLogModel productInventoryLogModel) {
        return   productInventoryLogService.subtractStock(productInventoryLogModel);
    }

    @Override
    public int addStock(ProductInventoryLogModel productInventoryLogModel) {
       return productInventoryLogService.addStock(productInventoryLogModel);
    }

    @Override
    public ProductInventoryLogModel getProductInventoryLog(int productId) {
        ProductInventoryLogModel getProductInventoryLog = productInventoryLogService.getProductInventoryLog(productId);
        if(getProductInventoryLog != null){
            return getProductInventoryLog;
        }
        return null;
    }

    @Override
    public int updateStock(ProductInventoryLogModel productInventoryLogModel) {
        return productInventoryLogService.updateStock(productInventoryLogModel);
    }
}
