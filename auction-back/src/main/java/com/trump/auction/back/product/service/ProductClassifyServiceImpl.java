package com.trump.auction.back.product.service;

import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.github.pagehelper.PageHelper;
import com.trump.auction.back.auctionProd.dao.read.AuctionProductInfoDao;
import com.trump.auction.back.product.dao.read.ProductClassifyDao;
import com.trump.auction.back.product.model.ProductClassify;
import com.trump.auction.back.product.vo.ParamVo;
import com.trump.auction.back.product.vo.ProductVo;
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
 * 商品分类service
 * @author Administrator
 * @date 2017/12/26
 */
@Service("productClassifyService")
public class ProductClassifyServiceImpl implements ProductClassifyService {

    @Autowired
    private ProductClassifyDao productClassifyDao;

    @Autowired
    private ProductClassifyStubService productClassifyStubService;

    @Autowired
    private AuctionProductInfoDao auctionProductInfoDao;
    @Override
    public List<ProductClassify> selectAll() {
        //启用分类ddy
        int enable = 0;
        return productClassifyDao.selectAllByStatus(enable);
    }

    /**
     * 通过商品分类ID查询商品分类
     * @param id
     * @return
     */
    @Override
    public ProductClassify selectById(int id) {
        return productClassifyDao.selectByPrimaryKey(id);
    }

    /**
     * 查询全部商品分类通过分类名称
     * @param name
     * @return
     */
    @Override
    public List<ProductClassify> selectByName(String name) {
        return productClassifyDao.selectByName(name);
    }

    /**
     * 查询分类通过排序
     * @param sort
     * @return
     */
    @Override
    public List<ProductClassify> selectBySort(Integer sort) {
        return productClassifyDao.selectBySort(sort);
    }

    /**
     * 查询全部商品分类通过分类状态
     * @param status
     * @return
     */
    @Override
    public List<ProductClassify> selectByStatus(Integer status) {
        return productClassifyDao.selectAllByStatus(status);
    }

    /**
     * 分页查询商品分类信息
     * @param paramVo
     * @return
     */
    @Override
    public Paging<ProductClassify> findByPage(ParamVo paramVo) {
        PageHelper.startPage(paramVo.getPage(),paramVo.getLimit());
        return PageUtils.page(productClassifyDao.selectByParamVo(paramVo));
    }

    /**
     * 商品分类批量删除
     * @param ids
     * @param sysUser
     * @return
     */
    @Override
    public ResultBean<Integer> productClassifyBatchDel(Integer[] ids, SysUser sysUser) throws Exception {
        ResultBean<Integer> resultBean = new ResultBean<>();
        if(ids == null || ids.length <=0 ){
            resultBean.setCode(Status.ERROR.getName());
            resultBean.setMsg("参数缺失");
            return resultBean;
        }
        List<ProductClassifyModel> models = new ArrayList<>();
        ProductClassifyModel model = null;
        for (Integer id: ids
             ) {
            int classifyCount = auctionProductInfoDao.getByClassifyId(id);
            //该分类下有拍品时不能删除
            if(classifyCount > 0){
                throw new Exception("删除失败：选中的分类下有拍品存在");
            }
            model = new ProductClassifyModel();
            model.setId(id);
            model.setUserIp(sysUser.getAddIp());
            model.setUserId(sysUser.getId());
            models.add(model);
        }
        resultBean =  productClassifyStubService.deleteBatch(models);
        return resultBean;
    }
}
