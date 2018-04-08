package com.trump.auction.goods.service.impl;

import com.alibaba.fastjson.JSON;
import com.cf.common.util.mapping.BeanMapper;
import com.trump.auction.goods.dao.ProductInfoDao;
import com.trump.auction.goods.dao.ProductInventoryLogDao;
import com.trump.auction.goods.dao.ProductManageDao;
import com.trump.auction.goods.domain.ProductInfo;
import com.trump.auction.goods.domain.ProductManage;
import com.trump.auction.goods.enums.ProductManageStatusEnum;
import com.trump.auction.goods.enums.ProductStatusEnum;
import com.trump.auction.goods.enums.ResultCode;
import com.trump.auction.goods.model.ProductInfoModel;
import com.trump.auction.goods.service.ProductManageService;
import com.trump.auction.goods.util.DateUtil;
import com.trump.auction.goods.vo.ProductManageVo;
import com.trump.auction.goods.vo.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 商品管理实现类
 * @author Administrator
 * @date 2017/12/21
 */
@Service("productManageService")
@Slf4j
public class ProductManageServiceImpl implements ProductManageService {

    @Autowired
    private ProductManageDao productManageDao;

    @Autowired
    private ProductInfoDao productInfoDao;

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private ProductInventoryLogDao productInventoryLogDao;



    /**
     * 商品上架
     * @param productManageVo
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public ResultBean<Integer> productOn(ProductManageVo productManageVo) throws Exception {
        log.info("商品上架参数(productOn)：" + JSON.toJSONString(productManageVo));
        ResultBean<Integer> resultBean = new ResultBean<>() ;
        resultBean = productOn(productManageVo,resultBean);
        return resultBean;
    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    private ResultBean<Integer> productOn(ProductManageVo productManageVo, ResultBean<Integer> resultBean) {
        if(productManageVo == null || productManageVo.getProductId() == null ||
                StringUtils.isBlank(productManageVo.getProductName()) ||
                productManageVo.getClassify1() == null || productManageVo.getSalesPrice() == null){
            resultBean.setCode(ResultCode.PARAM_MISSING.getCode());
            resultBean.setMsg(ResultCode.PARAM_MISSING.getMsg());
            return resultBean;
        }
        Integer productId = productManageVo.getProductId();
        //判断商品是否存在
        ProductInfo productInfo = productInfoDao.getProductByProductId(productId);
        if(productInfo == null){
            resultBean.setCode(ResultCode.OBJECT_ISNOT_EXIST.getCode());
            resultBean.setMsg(ResultCode.OBJECT_ISNOT_EXIST.getMsg());
            return resultBean;
        }
        //判断库存是否存在
        int productNum = 1;
        Boolean isExistProductNum = productInventoryLogDao.validateStock(productId,productNum);
        if(isExistProductNum == null || !isExistProductNum){
            resultBean.setCode(ResultCode.PRODUCT_INVENTORY_LT_ZERO.getCode());
            resultBean.setMsg(ResultCode.PRODUCT_INVENTORY_LT_ZERO.getMsg());
            return resultBean;
        }
        ProductManage manage = productManageDao.selectByProductId(productId);
        if(manage != null){
            if(ProductManageStatusEnum.PRODUCT_ON.getCode().equals(manage.getStatus())){
                //说明该商品已上架
                resultBean.setCode(ResultCode.PRODUCT_GROUNDING.getCode());
                resultBean.setMsg(ResultCode.PRODUCT_GROUNDING.getMsg());
                return resultBean;
            }else{
                ProductManage updateManage = new ProductManage();
                //把该商品更新为已上架状态
                updateManage.setOnShelfTime(DateUtil.getCurrentDate());
                updateManage.setStatus(ProductManageStatusEnum.PRODUCT_ON.getCode());
                updateManage.setId(manage.getId());
                //上架时下架时间置为空
                updateManage.setUnderShelfTime(null);
                productManageDao.updateByPrimaryKeySelective(updateManage);
                resultBean.setData(updateManage.getId());
            }
        }else{
            //插入商品管理
            manage = new ProductManage();
            beanMapper.map(productManageVo,manage);
            manage.setOnShelfTime(DateUtil.getCurrentDate());
            manage.setStatus(ProductManageStatusEnum.PRODUCT_ON.getCode());
            manage.setCreateTime(DateUtil.getCurrentDate());
            productManageDao.insertSelective(manage);
            resultBean.setData(manage.getId());
        }
        return resultBean;
    }

    /**
     * 商品批量上架
     * @param productManageVos
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public ResultBean<Integer> productBatchOn(List<ProductManageVo> productManageVos) {
        log.info("商品批量上架参数："+JSON.toJSONString(productManageVos));
        ResultBean<Integer> resultBean = new ResultBean<>();
        int failNum = 0;
        int successNum = 0;
        for (ProductManageVo vo : productManageVos
             ) {
            try {
                resultBean = productOn(vo,resultBean);
                if(!ResultCode.SUCCESS.getCode().equals(resultBean.getCode())){
                    failNum++;
                }else{
                    successNum++;
                }
            }catch (Exception e){
                log.error(e.getMessage(),e);
                log.error("上架失败："+vo.getProductId());
                failNum++;
            }
        }
        if(successNum == 0){
            resultBean.setCode(ResultCode.FAIL.getCode());
        }
        resultBean.setMsg("成功："+successNum+","+"失败："+failNum);
        return resultBean;
    }

    /**
     * 商品下架
     * @param productManageVos
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public ResultBean<Integer> productOff(List<ProductManageVo> productManageVos) throws Exception {
        ResultBean<Integer> resultBean = new ResultBean<>();
        log.info("商品下架参数(productOff)：" + JSON.toJSONString(productManageVos));
        if(CollectionUtils.isNotEmpty(productManageVos)){
            List<ProductManage> manageList = new ArrayList<>() ;
            ProductManage manage = null ;
            for (ProductManageVo id:productManageVos
                 ) {
                //查询该记录是否存在
                manage = productManageDao.selectByPrimaryKey(id.getId());
                if(manage != null){
                    if(!ProductManageStatusEnum.PRODUCT_OFF.getCode().equals(manage.getStatus())) {
                        manage.setStatus(ProductManageStatusEnum.PRODUCT_OFF.getCode());
                        manage.setUnderShelfTime(DateUtil.getCurrentDate());
                        manage.setUserIp(id.getUserIp());
                        manage.setUserId(id.getId());
                        manageList.add(manage);
                    }else {
                        resultBean.setCode(ResultCode.PRODUCT_GROUNDING.getCode());
                        resultBean.setMsg(ResultCode.PRODUCT_GROUNDING.getMsg());
                    }
                }else {
                    resultBean.setCode(ResultCode.OBJECT_ISNOT_EXIST.getCode());
                    resultBean.setMsg(ResultCode.OBJECT_ISNOT_EXIST.getMsg());
                }
            }
            if(CollectionUtils.isNotEmpty(manageList)){
                //批量修改下架状态
                productManageDao.updateStatusBatch(manageList);
                resultBean.setData(manageList.size());
            }else {
                resultBean.setCode(ResultBean.FAIL);
                resultBean.setMsg("下架失败,请核查您的数据!");
            }
        }

        return resultBean;
    }

    /**
     * 商品删除
     * @param productManageVos
     * @return
     */
    @Override
    public ResultBean<Integer> productBatchDel(List<ProductInfoModel> productManageVos) {
        ResultBean<Integer> resultBean = new ResultBean<>();
        if(CollectionUtils.isEmpty(productManageVos)){
            resultBean.setCode(ResultCode.PARAM_MISSING.getCode());
            resultBean.setCode(ResultCode.PARAM_MISSING.getMsg());
            return resultBean;
        }
        int failNum = 0;
        int successNum = 0;
        List<ProductInfo> manageList = new ArrayList<>() ;
        ProductInfo info = null ;
        for (ProductInfoModel vo : productManageVos
                ) {
            //查询该记录是否存在
            info = productInfoDao.getProductByProductId(vo.getId());
            if(info != null){
                if(!ProductStatusEnum.DELETED.getCode().equals(info.getProductStatus())) {
                    info.setProductStatus(ProductStatusEnum.DELETED.getCode());
                    info.setUserIp(vo.getUserIp());
                    info.setUserId(vo.getId());
                    info.setUpdateTime(DateUtil.getCurrentDate());
                    manageList.add(info);
                    successNum++;
                }else {
                    failNum++;
                }
            }else {
               failNum++;
            }
        }
        if(CollectionUtils.isNotEmpty(manageList)){
            productInfoDao.batchDel(manageList);
        }
        if(successNum == 0){
            resultBean.setCode(ResultCode.FAIL.getCode());
        }
        return resultBean;
    }
}
