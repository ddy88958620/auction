package com.trump.auction.goods.service.impl;

import com.alibaba.fastjson.JSON;
import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.utils.JsonResult;
import com.trump.auction.goods.dao.ProductInfoDao;
import com.trump.auction.goods.dao.ProductInventoryLogDao;
import com.trump.auction.goods.dao.ProductManageDao;
import com.trump.auction.goods.dao.ProductPicDao;
import com.trump.auction.goods.domain.ProductInfo;
import com.trump.auction.goods.domain.ProductInventoryLog;
import com.trump.auction.goods.domain.ProductManage;
import com.trump.auction.goods.domain.ProductPic;
import com.trump.auction.goods.enums.ProductStatusEnum;
import com.trump.auction.goods.enums.ResultCode;
import com.trump.auction.goods.enums.ResultEnum;
import com.trump.auction.goods.model.ProductInfoModel;
import com.trump.auction.goods.model.ProductInventoryLogModel;
import com.trump.auction.goods.model.ProductPicModel;
import com.trump.auction.goods.service.ProductInfoService;
import com.trump.auction.goods.service.ProductInventoryLogService;
import com.trump.auction.goods.util.DateUtil;
import com.trump.auction.goods.vo.ProductManageVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @description: 商品
 * @author: zhangqingqiang
 * @date: 2017-12-21 14:31
 **/
@Service
@Slf4j
public class ProductInfoServiceImpl implements ProductInfoService{

    @Autowired
    private ProductInfoDao productInfoDao;

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private ProductManageDao productManageDao;

    @Autowired
    private ProductInventoryLogService productInventoryLogService;


    @Autowired
    private ProductPicDao productPicDao;
    @Override
    public JsonResult saveProducts(List<ProductInfo> products) {

        if (CollectionUtils.isEmpty(products)){
            return new JsonResult(ResultEnum.NULL_PRODUCT.getCode(), ResultEnum.NULL_PRODUCT.getDesc());
        }

        JsonResult jsonResult = new JsonResult();
        List<Integer> failProductIds = new ArrayList<>();
        int result = 0;

        for (ProductInfo product : products){
            ProductInfo productInfo = productInfoDao.getProductByProductId(product.getProductId());
            if (null!=productInfo){
                failProductIds.add(productInfo.getProductId());
                continue;
            }

            result += productInfoDao.insert(product);
        }

        jsonResult.setCode("0000");
        jsonResult.setData(result);
        jsonResult.setMsg("保存成功"+result+"条数据,保存失败"+failProductIds.size()+"条,保存失败商品ID:"+failProductIds);

        return jsonResult;
    }


    /**
     * 商品添加
     * @param productInfoModel
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public JsonResult saveProduct(ProductInfoModel productInfoModel) throws Exception {

        log.info("saveProduct param :" + JSON.toJSONString(productInfoModel));
        JsonResult jsonResult = new JsonResult();
        //必传参数
        if(productInfoModel == null || StringUtils.isBlank(productInfoModel.getProductName())||
                StringUtils.isBlank(productInfoModel.getProductTitle()) ||
                productInfoModel.getProductNum() == null || productInfoModel.getProductAmount() == null ||
                CollectionUtils.isEmpty(productInfoModel.getPics())){
            jsonResult.setCode(ResultCode.PARAM_MISSING.getCode());
            jsonResult.setMsg( ResultCode.PARAM_MISSING.getMsg());
            return  jsonResult;
        }

        //判断商品是否存在
        ProductInfo productInfo = productInfoDao.getProductByProductId(productInfoModel.getProductId());
        if (null!=productInfo){
            jsonResult.setCode(ResultCode.OBJECT_IS_EXIST.getCode());
            jsonResult.setMsg( ResultCode.OBJECT_IS_EXIST.getMsg());
            return  jsonResult;
        }
        //判断商品状态 ，如果已上架不能修改
        ProductManage manage = productManageDao.selectByProductId(productInfoModel.getProductId());
        if(manage != null &&  manage.getStatus().equals(1)){
            jsonResult.setCode(ResultCode.PRODUCT_GROUNDING.getCode());
            jsonResult.setMsg( ResultCode.PRODUCT_GROUNDING.getMsg());
            return  jsonResult;
        }
        productInfo = new ProductInfo();
        beanMapper.map(productInfoModel,productInfo);
        productInfo.setProductStatus(0);
        productInfo.setCreateTime(DateUtil.getCurrentDate());
        int result = productInfoDao.insertSelective(productInfo);

        if (result <= 0){
            jsonResult.setCode(ResultCode.FAIL.getCode());
            jsonResult.setMsg(ResultCode.FAIL.getMsg());
            return  jsonResult;
        }else{
            //库存添加
            ProductInventoryLogModel productInventoryLog = new ProductInventoryLogModel();
            productInventoryLog.setProductId(productInfo.getId());
            productInventoryLog.setUserId(productInfoModel.getUserId());
            productInventoryLog.setUserIp(productInfoModel.getUserIp());
            productInventoryLog.setProductNum(productInfoModel.getProductNum());
            productInventoryLog.setCreateTime(DateUtil.getCurrentDate());
            //添加库存
            int stockResult = productInventoryLogService.addStock(productInventoryLog);
            if(stockResult <= 0){
                throw new Exception("库存添加失败");
            }
            //商品图片添加
            productInfoModel.setId(productInfo.getId());
            List<ProductPic> pics = getPics(productInfoModel);
            if(CollectionUtils.isNotEmpty(pics)) {
                productPicDao.batchInsert(pics);
            }
        }
        jsonResult.setCode(ResultEnum.SAVE_SUCCESS.getCode());
        jsonResult.setData(productInfo.getProductId());
        jsonResult.setMsg(ResultEnum.SAVE_SUCCESS.getDesc());
        return jsonResult;
    }

    /**
     * 商品修改
     * @param productInfoModel
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public JsonResult updateProduct(ProductInfoModel productInfoModel) throws Exception {
        log.info("updateProduct param :" + JSON.toJSONString(productInfoModel));
        JsonResult jsonResult = new JsonResult();
        if(productInfoModel.getProductId() == null){
            jsonResult.setCode(ResultCode.PARAM_MISSING.getCode());
            jsonResult.setMsg( ResultCode.PARAM_MISSING.getMsg());
            return  jsonResult;
        }

        ProductInfo productInfo = productInfoDao.getProductByProductId(productInfoModel.getProductId());
        if (null==productInfo){
            jsonResult.setCode(ResultCode.OBJECT_ISNOT_EXIST.getCode());
            jsonResult.setMsg(ResultCode.OBJECT_ISNOT_EXIST.getMsg());
            return jsonResult;
        }
        productInfoModel.setId(productInfo.getId());
        productInfoModel.setUpdateTime(DateUtil.getCurrentDate());
        beanMapper.map(productInfoModel,productInfo);
        int result = productInfoDao.updateByPrimaryKeySelective(beanMapper.map(productInfoModel,ProductInfo.class));
        //库存修改
        if(result > 0) {
            if (productInfoModel.getProductNum() != null) {
                //库存修改
                ProductInventoryLogModel productInventoryLog = new ProductInventoryLogModel();
                productInventoryLog.setProductId(productInfo.getId());
                productInventoryLog.setUserId(productInfoModel.getUserId());
                productInventoryLog.setUserIp(productInfoModel.getUserIp());
                productInventoryLog.setProductNum(productInfoModel.getProductNum());
                //修改库存
                int updateStockResult = productInventoryLogService.updateStock(productInventoryLog);
                if(updateStockResult <= 0){
                    throw new Exception("库存修改失败");
                }
            }
            if(CollectionUtils.isNotEmpty(productInfoModel.getPics())){
                //先删除后添加
                Map<String,Object> map = new HashMap<>();
                //map.put("picType",0);
                map.put("productId",productInfo.getId());
                productPicDao.deleteByProductIdOrType(map);
                //商品图片添加
                productInfoModel.setId(productInfo.getId());
                List<ProductPic> pics = getPics(productInfoModel);
                if(CollectionUtils.isNotEmpty(pics)) {
                    productPicDao.batchInsert(pics);
                }
            }
        }
        if (result==0){
            jsonResult.setCode(ResultCode.FAIL.getCode());
            jsonResult.setMsg( ResultCode.FAIL.getMsg());
            return  jsonResult;
        }
        jsonResult.setCode(ResultCode.SUCCESS.getCode());
        jsonResult.setData(productInfoModel.getProductId());
        jsonResult.setMsg(ResultCode.SUCCESS.getMsg());
        return jsonResult;
    }


    /**
     * 获取需要更新的图片
     * @param productInfoModel
     * @return
     */
    private List<ProductPic> getUpdatePics(ProductInfoModel productInfoModel) {
        List<ProductPic> pics = new ArrayList<>();
        ProductPic pic = null;
        for (ProductPicModel model:productInfoModel.getPics()
                ) {
            pic = new ProductPic();
            beanMapper.map(model,pic);
            pic.setCreateTime(DateUtil.getCurrentDate());
            pic.setProductId(productInfoModel.getId());
            pic.setPicType(model.getPicType());

            pics.add(pic);
        }
        return pics;
    }

    private List<ProductPic> getPics(ProductInfoModel productInfoModel) {
        List<ProductPic> pics = new ArrayList<>();
        ProductPic pic = null;
        for (ProductPicModel model:productInfoModel.getPics()
                ) {
            pic = new ProductPic();
            beanMapper.map(model,pic);
            pic.setCreateTime(DateUtil.getCurrentDate());
            pic.setProductId(productInfoModel.getId());
            pics.add(pic);
        }
        return pics;
    }

    @Override
    public JsonResult updateStatus(Integer productId){
        int result = productInfoDao.updateStatus(productId, ProductStatusEnum.DELETED.getCode());
        if (result==0){
            return new JsonResult(ResultEnum.DELETE_PRODUCT_FAIL.getCode(), "删除失败，productId:"+productId);
        }
        return new JsonResult(ResultEnum.DELETE_PRODUCT_SUCCESS.getCode(), "删除成功，productId:"+productId);
    }

    @Override
    public ProductInfoModel getProductByProductId(Integer productId) {
        ProductInfo productInfo = productInfoDao.getProductByProductId(productId);
        return beanMapper.map(productInfo,ProductInfoModel.class);
    }

    @Override
    public JsonResult updateCollectCount(Integer productId, int collectCount) {
        try {
            int result = productInfoDao.updateCollectCount(productId, collectCount);
            if (result==0){
                return new JsonResult(ResultEnum.UPDATE_PRODUCT_FAIL.getCode(), "修改收藏数量失败，productId:"+productId);
            }

        } catch (Exception e) {
            log.error("updateCollectCount error. product:{},error:{}",productId,e);
            return new JsonResult(ResultEnum.UPDATE_PRODUCT_FAIL.getCode(), "修改收藏数量失败，productId:"+productId);
        }
        return new JsonResult(ResultEnum.UPDATE_PRODUCT_SUCCESS.getCode(), "修改收藏数量成功，productId:"+productId);
    }
}
