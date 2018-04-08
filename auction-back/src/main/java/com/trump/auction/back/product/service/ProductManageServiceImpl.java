package com.trump.auction.back.product.service;

import com.alibaba.fastjson.JSON;
import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.github.pagehelper.PageHelper;
import com.trump.auction.back.auctionProd.dao.read.AuctionProductInfoDao;
import com.trump.auction.back.auctionProd.model.AuctionProductInfo;
import com.trump.auction.back.product.dao.read.ProductInfoDao;
import com.trump.auction.back.product.dao.read.ProductManageDao;
import com.trump.auction.back.product.dao.read.ProductPicDao;
import com.trump.auction.back.product.model.ProductInfo;
import com.trump.auction.back.product.model.ProductManage;
import com.trump.auction.back.product.model.ProductPic;
import com.trump.auction.back.product.vo.ParamVo;
import com.trump.auction.back.product.vo.ProductVo;
import com.trump.auction.back.sys.model.SysUser;
import com.trump.auction.goods.api.ProductInfoSubService;
import com.trump.auction.goods.api.ProductManageStubService;
import com.trump.auction.goods.model.ProductInfoModel;
import com.trump.auction.goods.vo.ProductManageVo;
import com.trump.auction.goods.vo.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品管理----上下架service实现
 * @author liuxueshen
 * @date 2017/12/22
 */
@Service("productManageService")
@Slf4j
public class ProductManageServiceImpl implements ProductManageService {


    @Autowired
    private ProductManageDao productManageDao;


    @Autowired
    private ProductManageStubService productManageStubService;

    @Autowired
    private ProductInfoSubService productInfoSubService;

    @Autowired
    private ProductInfoDao productInfoDao;
    @Autowired
    private BeanMapper beanMapper;
    @Autowired
    private ProductPicDao productPicDao;

    @Autowired
    private AuctionProductInfoDao auctionProductInfoDao;

    private final String FAIL_CODE = "999";
    private final String SUCCESS_CODE = "000";
    /**
     * 分页查询拍品信息
     * @param paramVo
     * @return
     */
    @Override
    public Paging<ProductVo> findByPage(ParamVo paramVo) {
        PageHelper.startPage(paramVo.getPage(),paramVo.getLimit());
        return PageUtils.page(productManageDao.selectByParamVo(paramVo));
    }


    /**
     * 通过拍品ID获取拍品信息
     * @param id
     * @return
     */
    @Override
    public ProductVo getProduct(Integer id){
        ParamVo vo = new ParamVo();
        vo.setProductId(id);
        List<ProductVo> vos = productManageDao.selectByParamVo(vo);
        if(CollectionUtils.isNotEmpty(vos)){
            ProductVo productVo = vos.get(0);
            //查询图片
            Map<String,Object> param = new HashMap<>();
            param.put("productId",id);
            param.put("picType",0);
            List<ProductPic> pics = productPicDao.selectByProductIdOrType(param);
            productVo.setPics(pics);
            System.out.println("pics================"+ JSON.toJSONString(productVo));
            return productVo;
        }
        return new ProductVo();
    }

    /**
     * 拍品批量上架
     * @param ids 拍品的ID集合
     */
    @Override
    public void productBatchOn(Integer[] ids, SysUser user) throws Exception {
        log.info("productBatchOn param : " + JSON.toJSONString(ids));
       if (ids == null || ids.length <= 0){
            throw new Exception("productBatchOn参数缺失(vos)");
        }
        ProductManageVo manageVo = null;
        ProductInfo manage = null ;
        List<ProductManageVo> manageVos = new ArrayList<>();
        ResultBean<Integer> resultBean = new ResultBean<>();
        ProductInfo info = null;
        for (Integer id:ids) {
            manageVo = new ProductManageVo();
            manage = productInfoDao.getProductByProductId(id);
            if(manage != null) {
                beanMapper.map(manage,manageVo);
                manageVo.setUserId(user.getId());
                manageVo.setUserIp(user.getAddIp());
                manageVo.setProductId(id);
                manageVo.setSalesPrice(manage.getSalesAmount());
                manageVos.add(manageVo);
            }
        }
        if(CollectionUtils.isNotEmpty(manageVos)) {
            resultBean = productManageStubService.productBatchOn(manageVos);
            log.info("productBatchOn result : " + JSON.toJSONString(resultBean));
            if (!SUCCESS_CODE.equals(resultBean.getCode())) {
                throw new Exception(resultBean.getMsg());
            }
        }else {
            throw new Exception("未知异常");
        }

    }

    /**
     * 批量下架
     * @param ids 拍品的ID集合
     * @throws Exception
     */
    @Override
    public void productBatchOff(Integer[] ids, SysUser user) throws Exception {
        if (ids == null || ids.length <=0){
            throw new Exception("productBatchOff参数缺失(ids)");
        }
        List<ProductManageVo> vos = new ArrayList<>();
        ProductManageVo vo = null;
        for (Integer id:ids
             ) {
            vo = new ProductManageVo();
            vo.setId(id);
            vo.setUserId(user.getId());
            vo.setUserIp(user.getAddIp());
            vos.add(vo);
        }
        ResultBean<Integer> resultBean = productManageStubService.productOff(vos);
        if(FAIL_CODE.equals(resultBean.getCode())){
            throw new Exception(resultBean.getMsg());
        }
    }

    /**
     * 拍品删除
     * @param ids id集合
     * @param user 用户信息
     * @throws Exception
     */
    @Override
    public void productBatchDel(Integer[] ids, SysUser user) throws Exception {
        if (ids == null || ids.length <=0){
            throw new Exception("productBatchDel(ids)");
        }
        List<ProductInfoModel> vos = new ArrayList<>();
        ProductInfoModel vo = null;
        List<AuctionProductInfo> list = null;
        for (Integer id:ids
                ) {
            vo = new ProductInfoModel();
            vo.setId(id);
            vo.setUserId(user.getId());
            vo.setUserIp(user.getAddIp());
            /*list = auctionProductInfoDao.findOffByProductId(id);
            if(CollectionUtils.isEmpty(list)) {
                vos.add(vo);
            }*/
            vos.add(vo);
        }
        ResultBean<Integer> resultBean = null;
        if(CollectionUtils.isNotEmpty(vos)){
            //调用删除拍品接口;
            resultBean = productManageStubService.productBatchDel(vos);
            if (!SUCCESS_CODE.equals(resultBean.getCode())) {
                throw new Exception(resultBean.getMsg());
            }
        }else {
            throw new Exception("删除失败：请仔细核查您的数据");
        }
    }
}
