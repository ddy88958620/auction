package com.trump.auction.goods.service.impl;

import com.cf.common.util.mapping.BeanMapper;
import com.trump.auction.goods.dao.ProductInventoryLogRecordDao;
import com.trump.auction.goods.domain.ProductInventoryLogRecord;
import com.trump.auction.goods.model.ProductInventoryLogRecordModel;
import com.trump.auction.goods.service.ProductInventoryLogRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 罗显 on 2017/12/21.
 */
@Service
@Slf4j
public class ProductInventoryLogRecordServiceImpl implements ProductInventoryLogRecordService {

    @Autowired
    private ProductInventoryLogRecordDao productInventoryLogRecordDao;
    @Autowired
    private BeanMapper beanMapper;

    @Override
    public int addStock(ProductInventoryLogRecordModel productInventoryLogRecordModel) {
            return  productInventoryLogRecordDao.addStock(beanMapper.map(productInventoryLogRecordModel,ProductInventoryLogRecord.class));
    }

    @Override
    public ProductInventoryLogRecordModel validateRecord(int productId) {
        ProductInventoryLogRecord validateRecord = productInventoryLogRecordDao.validateRecord(productId);
        return beanMapper.map(validateRecord,ProductInventoryLogRecordModel.class);
    }

    @Override
    public int updateStock(ProductInventoryLogRecordModel productInventoryLogRecordModel) {
        return productInventoryLogRecordDao.updateStock(beanMapper.map(productInventoryLogRecordModel,ProductInventoryLogRecord.class));
    }
}
