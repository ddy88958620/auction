package com.trump.auction.goods.service.impl;

import com.cf.common.util.mapping.BeanMapper;
import com.trump.auction.goods.dao.ProductClassifyDao;
import com.trump.auction.goods.domain.ProductClassify;
import com.trump.auction.goods.enums.ProductClassifyStatusEnum;
import com.trump.auction.goods.enums.ResultCode;
import com.trump.auction.goods.model.ProductClassifyModel;
import com.trump.auction.goods.service.ProductClassifyService;
import com.trump.auction.goods.util.DateUtil;
import com.trump.auction.goods.vo.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品分类service
 * @author Administrator
 * @date 2017/12/26
 */
@Service("productClassifyService")
@Slf4j
public class ProductClassifyServiceImpl implements ProductClassifyService {

    @Autowired
    private ProductClassifyDao productClassifyDao;

    @Autowired
    private BeanMapper beanMapper;


    /**
     * 商品分类添加
     * @param productClassifyModel
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    @Override
    public ResultBean<Integer> saveClassify(ProductClassifyModel productClassifyModel) throws Exception {
        ResultBean<Integer> resultBean = new ResultBean<>();
        log.info("saveClassify param :" +productClassifyModel);
        if(productClassifyModel == null || StringUtils.isBlank(productClassifyModel.getName())){
            resultBean.setCode(ResultCode.PARAM_MISSING.getCode());
            resultBean.setMsg( ResultCode.PARAM_MISSING.getMsg());
            return  resultBean;
        }

        ProductClassify productClassify = new ProductClassify();
        beanMapper.map(productClassifyModel,productClassify);
        productClassify.setCreateTime(DateUtil.getCurrentDate());
        productClassify.setStatus(ProductClassifyStatusEnum.DISABLED.getCode());
        try {
            productClassifyDao.insertSelective(productClassify);
            resultBean.setData(productClassify.getId());
        }catch (Exception e){
            throw new Exception("商品分类添加失败："+e.getMessage());
        }

        return resultBean;
    }

    /**
     * 商品分类批量删除
     * @param productClassifyModelList
     * @return
     * @throws Exception
     */
    @Override
    public ResultBean<Integer> deleteBatch(List<ProductClassifyModel> productClassifyModelList) throws Exception {
        log.info("deleteBatch param :" +productClassifyModelList);
        ResultBean<Integer> resultBean = new ResultBean<>();
        if(CollectionUtils.isEmpty(productClassifyModelList)){
            resultBean.setCode(ResultCode.PARAM_MISSING.getCode());
            resultBean.setCode(ResultCode.PARAM_MISSING.getMsg());
            return resultBean;
        }

        List<ProductClassify> models = new ArrayList<>();
        ProductClassify classify = null;
        for (ProductClassifyModel model:productClassifyModelList
             ) {
            classify = productClassifyDao.selectByPrimaryKey(model.getId());
            if(classify != null && ObjectUtils.notEqual(ProductClassifyStatusEnum.DELETED.getCode(),classify.getClassifyPic())){
                classify.setUpdateTime(DateUtil.getCurrentDate());
                classify.setUserId(model.getUserId());
                classify.setUserIp(model.getUserIp());
                classify.setStatus(ProductClassifyStatusEnum.DELETED.getCode());
                models.add(classify);
            }
        }
        if(CollectionUtils.isNotEmpty(models)){
            try {
                productClassifyDao.updateStatusBatch(models);
                resultBean.setData(models.size());
            }catch (Exception e){
                log.error("修改商品分类失败："+e.getMessage(),e);
                throw new Exception("修改商品分类失败："+e.getMessage());
            }

        }

        return resultBean;
    }

    @Override
    public ResultBean<Integer> enableBatch(List<ProductClassifyModel> productClassifyModelList) throws Exception {
        log.info("enableBatch param :" +productClassifyModelList);
        ResultBean<Integer> resultBean = new ResultBean<>();
        if(CollectionUtils.isEmpty(productClassifyModelList)){
            resultBean.setCode(ResultCode.PARAM_MISSING.getCode());
            resultBean.setCode(ResultCode.PARAM_MISSING.getMsg());
            return resultBean;
        }

        List<ProductClassify> models = new ArrayList<>();
        ProductClassify classify = null;
        for (ProductClassifyModel model:productClassifyModelList
                ) {
            classify = productClassifyDao.selectByPrimaryKey(model.getId());
            if(classify != null && ObjectUtils.notEqual(ProductClassifyStatusEnum.DELETED.getCode(),classify.getClassifyPic())){
                classify.setUpdateTime(DateUtil.getCurrentDate());
                classify.setUserId(model.getUserId());
                classify.setUserIp(model.getUserIp());
                classify.setStatus(ProductClassifyStatusEnum.NORMAL.getCode());
                models.add(classify);
            }
        }
        if(CollectionUtils.isNotEmpty(models)){
            try {
                productClassifyDao.updateStatusBatch(models);
                resultBean.setData(models.size());
            }catch (Exception e){
                log.error("修改商品分类状态失败："+e.getMessage(),e);
                throw new Exception("修改商品分类状态失败："+e.getMessage());
            }

        }

        return resultBean;
    }

    /**
     * 商品分类修改
     * @param productClassifyModel
     * @return
     * @throws Exception
     */
    @Override
    public ResultBean<Integer> updateClassify(ProductClassifyModel productClassifyModel) throws Exception {
        ResultBean<Integer> resultBean = new ResultBean<>();
        log.info("updateClassify param :" +productClassifyModel);
        if(productClassifyModel == null || productClassifyModel.getId() == null ||
                StringUtils.isBlank(productClassifyModel.getName())){
            resultBean.setCode(ResultCode.PARAM_MISSING.getCode());
            resultBean.setMsg( ResultCode.PARAM_MISSING.getMsg());
            return  resultBean;
        }

        //判断是否存在该分类
        ProductClassify classify = productClassifyDao.selectByPrimaryKey(productClassifyModel.getId());
        if(classify == null){
            resultBean.setCode(ResultCode.OBJECT_ISNOT_EXIST.getCode());
            resultBean.setMsg(ResultCode.OBJECT_ISNOT_EXIST.getMsg());
            return resultBean;
        }else{
            if(ProductClassifyStatusEnum.DELETED.getCode().equals(classify.getStatus())){
                resultBean.setCode(ResultCode.OBJECT_ISNOT_EXIST.getCode());
                resultBean.setMsg(ResultCode.OBJECT_ISNOT_EXIST.getMsg());

                return resultBean;
            }
        }

        ProductClassify productClassify = new ProductClassify();
        beanMapper.map(productClassifyModel,productClassify);
        productClassify.setUpdateTime(DateUtil.getCurrentDate());
        try {
            productClassifyDao.updateByPrimaryKeySelective(productClassify);
            resultBean.setData(productClassify.getId());
        }catch (Exception e){
            throw new Exception("商品分类修改失败："+e.getMessage());
        }

        return resultBean;
    }
}
