package com.trump.auction.back.product.service;

import com.trump.auction.back.product.dao.read.ProductInfoDao;
import com.trump.auction.back.product.dao.read.ProductPicDao;
import com.trump.auction.back.product.model.ProductInfo;
import com.trump.auction.back.product.model.ProductPic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 商品service实现
 * @author Administrator
 * @date 2017/12/26
 */
@Service("productPicService")
public class ProductPicServiceImpl implements ProductPicService {



    @Autowired
    private ProductPicDao productPicDao;


    /**
     * 获取商品图片信息通过商品ID
     * @param productId
     * @return
     */
    @Override
    public List<ProductPic> gePicByProductId(Integer productId) {
        Map<String,Object> param = new HashMap<>();
        param.put("productId",productId);
        return productPicDao.selectByProductIdOrType(param);
    }
}
