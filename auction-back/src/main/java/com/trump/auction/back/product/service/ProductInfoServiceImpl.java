package com.trump.auction.back.product.service;

import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.github.pagehelper.PageHelper;
import com.trump.auction.back.product.dao.read.ProductInfoDao;
import com.trump.auction.back.product.dao.read.ProductInventoryLogDao;
import com.trump.auction.back.product.model.ProductInfo;
import com.trump.auction.back.product.vo.InventoryVo;
import com.trump.auction.back.product.vo.ParamVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商品service实现
 * @author Administrator
 * @date 2017/12/26
 */
@Service("productInfoService")
public class ProductInfoServiceImpl implements ProductInfoService {



    @Autowired
    private ProductInfoDao productInfoDao;

    /**
     * 获取商品信息通过ID
     * @param id
     * @return
     */
    @Override
    public ProductInfo getProductById(Integer id) {
        if(id == null){
            return  new ProductInfo();
        }
        return productInfoDao.getProductByProductId(id);
    }
}
