package com.trump.auction.goods.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cf.common.utils.JsonResult;
import com.trump.auction.goods.api.ProductInfoSubService;
import com.trump.auction.goods.enums.ResultCode;
import com.trump.auction.goods.model.ProductInfoModel;
import com.trump.auction.goods.service.ProductInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description: 拍品信息
 * @author: zhangqingqiang
 * @date: 2017-12-22 13:36
 **/
@Service(version = "1.0.0")
@Slf4j
public class ProductInfoSubServiceImpl implements ProductInfoSubService {

    @Autowired
    private ProductInfoService productInfoService;

    @Override
    public ProductInfoModel getProductByProductId(Integer productId) {
        return productInfoService.getProductByProductId(productId);
    }

    /**
     * 商品添加
     * @param productInfoModel
     * @return
     */
    @Override
    public JsonResult saveProduct(ProductInfoModel productInfoModel) {
        JsonResult resultBean = new JsonResult();
        try {
            resultBean = productInfoService.saveProduct(productInfoModel);
        }catch (Exception e){
            resultBean.setCode(ResultCode.FAIL.getCode());
            resultBean.setMsg("商品添加失败："+e.getMessage());
            log.error(e.getMessage(),e);
        }
        return resultBean;
    }

    /**
     * 商品修改
     * @param productInfoModel
     * @return
     */
    @Override
    public JsonResult updateProduct(ProductInfoModel productInfoModel) {

        JsonResult resultBean = new JsonResult();
        try {
            resultBean = productInfoService.updateProduct(productInfoModel);
        }catch (Exception e){
            resultBean.setCode(ResultCode.FAIL.getCode());
            resultBean.setMsg("商品修改失败："+e.getMessage());
            log.error(e.getMessage(),e);
        }
        return resultBean;
    }

    /**
     * 修改收藏数量
     * @param productId
     * @param collectCount
     * @return
     */
    @Override
    public JsonResult updateCollectCount(Integer productId,int collectCount) {
        JsonResult result = new JsonResult();
        try {
            result = productInfoService.updateCollectCount(productId,collectCount);
        }catch (Exception e){
            result.setCode(ResultCode.FAIL.getCode());
            result.setMsg("修改收藏数量失败："+e.getMessage());
            log.error("updateCollectCount error",e);
        }
        return result;
    }
}
