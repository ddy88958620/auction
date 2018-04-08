package com.trump.auction.goods.service.impl;

import com.cf.common.util.mapping.BeanMapper;
import com.trump.auction.goods.dao.ProductInventoryLogDao;
import com.trump.auction.goods.dao.ProductInventoryLogRecordDao;
import com.trump.auction.goods.domain.ProductInventoryLog;
import com.trump.auction.goods.domain.ProductInventoryLogRecord;
import com.trump.auction.goods.enums.InventoryTypeEnum;
import com.trump.auction.goods.model.ProductInventoryLogModel;
import com.trump.auction.goods.service.ProductInventoryLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;

/**
 * Created by 罗显 on 2017/12/21.
 */
@Service
@Slf4j
public class ProductInventoryLogServiceImpl implements ProductInventoryLogService {

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private ProductInventoryLogDao productInventoryLogDao;

    @Autowired
    private ProductInventoryLogRecordDao productInventoryLogRecordDao;

    @Override
    public boolean validateStock(int productId, int productNum) {
        boolean flag;
        flag = productInventoryLogDao.validateStock(productId,productNum);
        if(flag){
            return true;
        }else{
            return false;
        }
    }

    @Override
    @Transactional
    public int subtractStock(ProductInventoryLogModel productInventoryLogModel) {
        int result  = 0;
        try {
            //原来的库存
            ProductInventoryLog productInventoryLog = productInventoryLogDao.getProductInventoryLog(productInventoryLogModel.getProductId());
            //原来的库存数量
            int oldProductNum = 0;
            if(productInventoryLog != null){
                oldProductNum = productInventoryLog.getProductNum();
            }
           // int updProductNum = oldProductNum - productNum;
            //传过来修改的值
            int paramNum =  productInventoryLogModel.getProductNum();
            //最终要变成的值
            int productNum = oldProductNum - paramNum;

            ProductInventoryLog productInventory = new ProductInventoryLog();
            productInventory.setProductId(productInventoryLogModel.getProductId());
            productInventory.setProductNum(productNum);
            productInventory.setUserIp(productInventoryLogModel.getUserIp());
            productInventory.setUserId(productInventoryLogModel.getUserId());

            result  = productInventoryLogDao.subtractStock(productInventory);
            //记录表出库
            int type = InventoryTypeEnum.SUBTRACTSTOCK.getCode();
            if (result >0 ){
                ProductInventoryLogRecord productInventoryLogRecord = new ProductInventoryLogRecord();
                productInventoryLogRecord.setProductId(productInventoryLogModel.getProductId());
                productInventoryLogRecord.setUpdProductNum(productNum);
                productInventoryLogRecord.setOldProductNum(oldProductNum);
                productInventoryLogRecord.setType(type);
                productInventoryLogRecord.setCreateTime(new Date());
                productInventoryLogRecord.setUpdateTime(new Date());
                productInventoryLogRecord.setUserId(productInventoryLogModel.getUserId());
                productInventoryLogRecord.setUserIp(productInventoryLogModel.getUserIp());
                productInventoryLogRecordDao.addStock(productInventoryLogRecord);
            }else{
                return 0;
            }
        } catch (Exception e) {
            //事务手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    @Override
    @Transactional
    public int addStock(ProductInventoryLogModel productInventoryLogModel) {
       int result  = 0;
         try {
            //原来的库存
            ProductInventoryLog productInventoryLog = productInventoryLogDao.getProductInventoryLog(productInventoryLogModel.getProductId());
             int updProductNum = 0 ;
             int oldProductNum = 0 ;
             if(productInventoryLog != null){
                 //原来的库存数量
                 oldProductNum = productInventoryLog.getProductNum();
             }
            //传过来修改的值
             int paramNum =  productInventoryLogModel.getProductNum();
             //最终要变成的值
             int productNum = oldProductNum + paramNum;
            //库存记录表入库
            int type = InventoryTypeEnum.ADDSTOCK.getCode();

            if(productInventoryLog ==null){
                ProductInventoryLog productInventoryNull = new ProductInventoryLog();
                productInventoryNull.setProductId(productInventoryLogModel.getProductId());
                productInventoryNull.setProductNum(productInventoryLogModel.getProductNum());
                productInventoryNull.setUserId(productInventoryLogModel.getUserId());
                productInventoryNull.setUserIp(productInventoryLogModel.getUserIp());
                productInventoryNull.setCreateTime(new Date());
                productInventoryNull.setUpdateTime(new Date());
                //如果为空则新增
                result = productInventoryLogDao.insertStock(productInventoryNull);
                log.info("结果是："+result);
            }else{
                //操作库存表
                ProductInventoryLog productInventory = new ProductInventoryLog();
                productInventory.setProductId(productInventoryLogModel.getProductId());
                productInventory.setProductNum(productNum);
                productInventory.setUserIp(productInventoryLogModel.getUserIp());
                productInventory.setUserId(productInventoryLogModel.getUserId());

                result = productInventoryLogDao.addStock(productInventory);
            }

            if (result >0 ){
                //操作库存记录表
                ProductInventoryLogRecord productInventoryLogRecord = new ProductInventoryLogRecord();
                productInventoryLogRecord.setProductId(productInventoryLogModel.getProductId());
                productInventoryLogRecord.setUpdProductNum(productNum);
                productInventoryLogRecord.setOldProductNum(oldProductNum);
                productInventoryLogRecord.setType(type);
                productInventoryLogRecord.setCreateTime(new Date());
                productInventoryLogRecord.setUpdateTime(new Date());
                productInventoryLogRecord.setUserId(productInventoryLogModel.getUserId());
                productInventoryLogRecord.setUserIp(productInventoryLogModel.getUserIp());
                productInventoryLogRecordDao.addStock(productInventoryLogRecord);
            }else{
                return 0;
            }
        } catch (Exception e) {
            //事务手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    @Override
    public ProductInventoryLogModel getProductInventoryLog(int productId) {
        ProductInventoryLog getProductInventoryLog = productInventoryLogDao.getProductInventoryLog(productId);
        if(getProductInventoryLog != null){
            return beanMapper.map(getProductInventoryLog,ProductInventoryLogModel.class) ;
        }
        return null;
    }

    @Override
    @Transactional
    public int updateStock(ProductInventoryLogModel productInventoryLogModel) {
        int result  = 0;
        try {
            //原来的库存
            ProductInventoryLog productInventoryLog = productInventoryLogDao.getProductInventoryLog(productInventoryLogModel.getProductId());
            //如果不存在库存则直接添加
            if(productInventoryLog == null){
                return addStock(productInventoryLogModel);
            }

            int updProductNum = 0 ;
            int oldProductNum = 0 ;
            if(productInventoryLog != null){
                //原来的库存数量
                oldProductNum = productInventoryLog.getProductNum();
            }
            //传过来修改的值
            int paramNum =  productInventoryLogModel.getProductNum();
            //最终要变成的值
            int productNum =  paramNum;

            ProductInventoryLog productInventory = null;

            //库存记录表后台操作
            int type = InventoryTypeEnum.BACKSTAGEUPDATE.getCode();
            if(productInventoryLog !=null){
                //操作库存表
                 productInventory = new ProductInventoryLog();
                productInventory.setProductId(productInventoryLogModel.getProductId());
                productInventory.setProductNum(productNum);
                productInventory.setUpdateTime(new Date());
                productInventory.setUserIp(productInventoryLogModel.getUserIp());
                productInventory.setUserId(productInventoryLogModel.getUserId());
                result = productInventoryLogDao.updateStock(productInventory);
            }else{
                ProductInventoryLog productInventoryNull = new ProductInventoryLog();
                productInventoryNull.setProductId(productInventoryLogModel.getProductId());
                productInventoryNull.setProductNum(productInventoryLogModel.getProductNum());
                productInventoryNull.setUserId(productInventoryLogModel.getUserId());
                productInventoryNull.setUserIp(productInventoryLogModel.getUserIp());
                productInventoryNull.setCreateTime(new Date());
                productInventoryNull.setUpdateTime(new Date());
                //如果为空则新增
                result = productInventoryLogDao.insertStock(productInventoryNull);
            }

            if (result >0 ){
                //操作库存记录表
                ProductInventoryLogRecord productInventoryLogRecord = new ProductInventoryLogRecord();
                productInventoryLogRecord.setProductId(productInventoryLogModel.getProductId());
                productInventoryLogRecord.setUpdProductNum(productNum);
                productInventoryLogRecord.setOldProductNum(oldProductNum);
                productInventoryLogRecord.setType(type);
                productInventoryLogRecord.setCreateTime(new Date());
                productInventoryLogRecord.setUpdateTime(new Date());
                productInventoryLogRecord.setUserId(productInventoryLogModel.getUserId());
                productInventoryLogRecord.setUserIp(productInventoryLogModel.getUserIp());

                productInventoryLogRecordDao.addStock(productInventoryLogRecord);
            }else{
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            //事务手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }
}
