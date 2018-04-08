package com.trump.auction.goods.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.trump.auction.goods.api.ProductClassifyStubService;
import com.trump.auction.goods.api.ProductManageStubService;
import com.trump.auction.goods.enums.ResultCode;
import com.trump.auction.goods.model.ProductClassifyModel;
import com.trump.auction.goods.model.ProductInfoModel;
import com.trump.auction.goods.service.ProductClassifyService;
import com.trump.auction.goods.service.ProductManageService;
import com.trump.auction.goods.vo.ProductManageVo;
import com.trump.auction.goods.vo.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 商品管理对外提供接口实现类
 * @author Administrator
 * @date 2017/12/21
 */
@Service(version = "1.0.0")
@Slf4j
public class ProductClassifyStubServiceImpl implements ProductClassifyStubService {

    @Autowired
    private ProductClassifyService productClassifyService;

    /**
     * 商品分类添加
     * @param productClassifyModel
     * @return
     */
    @Override
    public ResultBean<Integer> saveClassify(ProductClassifyModel productClassifyModel) throws Exception {
        ResultBean<Integer> resultBean = new ResultBean<>();
        try {
            resultBean = productClassifyService.saveClassify(productClassifyModel);
        }catch (Exception e){
            resultBean.setCode(ResultCode.FAIL.getCode());
            resultBean.setMsg("商品分类添加："+e.getMessage());
            log.error(e.getMessage(),e);
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
        ResultBean<Integer> resultBean = new ResultBean<>();
        try {
            resultBean = productClassifyService.deleteBatch(productClassifyModelList);
        }catch (Exception e){
            resultBean.setCode(ResultCode.FAIL.getCode());
            resultBean.setMsg("商品分类批量删除："+e.getMessage());
            log.error(e.getMessage(),e);
        }
        return resultBean;
    }

    /**
     * 商品分类批量修改
     * @param productClassifyModelList
     * @return
     * @throws Exception
     */
    @Override
    public ResultBean<Integer> enableBatch(List<ProductClassifyModel> productClassifyModelList) throws Exception {
        ResultBean<Integer> resultBean = new ResultBean<>();
        try {
            resultBean = productClassifyService.enableBatch(productClassifyModelList);
        }catch (Exception e){
            resultBean.setCode(ResultCode.FAIL.getCode());
            resultBean.setMsg("商品分类启用失败："+e.getMessage());
            log.error(e.getMessage(),e);
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
        try {
            resultBean = productClassifyService.updateClassify(productClassifyModel);
        }catch (Exception e){
            resultBean.setCode(ResultCode.FAIL.getCode());
            resultBean.setMsg("商品分类修改失败："+e.getMessage());
            log.error(e.getMessage(),e);
        }
        return resultBean;
    }
}
