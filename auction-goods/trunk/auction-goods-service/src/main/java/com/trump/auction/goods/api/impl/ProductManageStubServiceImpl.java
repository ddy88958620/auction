package com.trump.auction.goods.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.trump.auction.goods.api.ProductManageStubService;
import com.trump.auction.goods.enums.ResultCode;
import com.trump.auction.goods.model.ProductInfoModel;
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
public class ProductManageStubServiceImpl implements ProductManageStubService {

    @Autowired
    private ProductManageService productManageService;
    /**
     * 商品上架
     * @param productManageVo
     * @return
     */
    @Override
    public ResultBean<Integer> productOn(ProductManageVo productManageVo) {
        ResultBean<Integer> resultBean = new ResultBean<>();
        try {
            resultBean = productManageService.productOn(productManageVo);
        }catch (Exception e){
            resultBean.setCode(ResultCode.FAIL.getCode());
            resultBean.setMsg("商品上架失败："+e.getMessage());
            log.error(e.getMessage(),e);
        }
        return resultBean;
    }

    /**
     * 商品批量上架
     * @param productManageVos
     * @return
     */
    @Override
    public ResultBean<Integer> productBatchOn(List<ProductManageVo> productManageVos) {
        ResultBean<Integer> resultBean = new ResultBean<>();
        try {
            resultBean = productManageService.productBatchOn(productManageVos);
        }catch (Exception e){
            resultBean.setCode(ResultCode.FAIL.getCode());
            resultBean.setMsg("商品批量上架失败："+e.getMessage());
            log.error(e.getMessage(),e);
        }
        return resultBean;
    }

    /**
     * 商品下架
     * @param productManageVos
     * @return
     */
    @Override
    public ResultBean<Integer> productOff(List<ProductManageVo> productManageVos) {
        ResultBean<Integer> resultBean = new ResultBean<>();
        try {
            resultBean = productManageService.productOff(productManageVos);
        }catch (Exception e){
            resultBean.setCode(ResultCode.FAIL.getCode());
            resultBean.setMsg("商品下架失败："+e.getMessage());
            log.error(e.getMessage(),e);
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
        try {
            resultBean = productManageService.productBatchDel(productManageVos);
        }catch (Exception e){
            resultBean.setCode(ResultCode.FAIL.getCode());
            resultBean.setMsg("商品删除失败："+e.getMessage());
            log.error(e.getMessage(),e);
        }
        return resultBean;
    }
}
