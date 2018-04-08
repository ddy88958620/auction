package com.trump.auction.back.product.service;

import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.github.pagehelper.PageHelper;
import com.trump.auction.back.product.dao.read.ProductClassifyDao;
import com.trump.auction.back.product.dao.read.ProductInventoryLogDao;
import com.trump.auction.back.product.dao.read.ProductInventoryLogRecordDao;
import com.trump.auction.back.product.model.ProductClassify;
import com.trump.auction.back.product.model.ProductInventoryLogRecord;
import com.trump.auction.back.product.vo.InventoryVo;
import com.trump.auction.back.product.vo.ParamVo;
import com.trump.auction.back.sys.model.SysUser;
import com.trump.auction.back.util.common.Status;
import com.trump.auction.goods.api.ProductClassifyStubService;
import com.trump.auction.goods.model.ProductClassifyModel;
import com.trump.auction.goods.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品库存service
 * @author Administrator
 * @date 2017/12/26
 */
@Service("productInventoryService")
public class ProductInventoryServiceImpl implements ProductInventoryService {




    @Autowired
    private ProductInventoryLogDao productInventoryLogDao;

    @Autowired
    private ProductInventoryLogRecordDao productInventoryLogRecordDao;

    /**
     * 商品库存分页查询
     * @param paramVo
     * @return
     */
    @Override
    public Paging<InventoryVo> findByPage(ParamVo paramVo) {
        PageHelper.startPage(paramVo.getPage(),paramVo.getLimit());
        return PageUtils.page(productInventoryLogDao.selectByParamVo(paramVo));

    }

    /**
     * 获取库存信息通过商品ID
     * @param productId
     * @return
     */
    @Override
    public InventoryVo getInventoryByProductId(Integer productId) {
        if(productId == null){
            return new InventoryVo();
        }
        return productInventoryLogDao.getInventoryByProductId(productId);
    }

    /**
     * 获取库存记录列表通过商品ID
     * @param id
     * @return
     */
    @Override
    public List<ProductInventoryLogRecord> getInventoryRecordList(Integer id) {

        List<ProductInventoryLogRecord> recordList = new ArrayList<>();
        if(productInventoryLogRecordDao.getRecordListByProductId(id) == null){
            return recordList;
        }
        return productInventoryLogRecordDao.getRecordListByProductId(id);
    }
}
