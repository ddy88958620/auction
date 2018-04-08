package com.trump.auction.back.auctionProd.service;


import com.cf.common.util.page.Paging;
import com.trump.auction.back.auctionProd.model.AuctionProductInfo;
import com.trump.auction.back.auctionProd.vo.AuctionProdVo;
import com.trump.auction.back.product.model.ProductClassify;
import com.trump.auction.back.product.model.ProductInfo;
import com.trump.auction.back.product.model.ProductPic;
import com.trump.auction.back.product.vo.ParamVo;
import com.trump.auction.back.rule.model.AuctionRule;
import com.trump.auction.back.sys.model.SysUser;
import com.trump.auction.goods.vo.ResultBean;
import com.trump.auction.trade.vo.AuctionProductInfoVo;
import com.trump.auction.trade.vo.AuctionProductPriceRuleVo;

import java.util.Date;
import java.util.List;

public  interface AuctionProductInfoService {
     Paging<AuctionProductInfo> findAuctionProdList(AuctionProdVo auctionProdVo);

     //String   auctionProdSave(AuctionProductInfo save, ProductInfo productInfo, List<ProductPic> productPics, AuctionRule auctionRule) throws Exception;

     String auctionProdSave(AuctionProductInfoVo vo) throws Exception;
     /**
      * 查询状态为3的定时拍品信息
      * @param status {status：1开拍中 2准备中 3定时}
      * @param date
      * @return
      */
     List<AuctionProductInfo> queryTimingProduct(Date date, Integer status);

     /**
      * 添加热门拍品
      * @param ids
      * @param key
      */
     void saveAuctionProdHot(Integer[] ids,String key) throws Exception;
     /**
      * 添加推荐拍品
      * @param ids
      * @param key
      */
     void saveAuctionProdRecommend(Integer[] ids,String key) throws Exception;

     /**
      * 根据id查询拍品信息
      * @param id
      * @return
      */
     AuctionProductInfo findAuctionProductInfoById(Integer id);

     /**
      * 批量更新拍品状态
      * @param ids
      * @param status
      * @return
      */
     int updAuctionProdStatus(String[] ids,Integer status);

     /**
      * 更新拍品定时的时间和状态
      * @param auctionProductInfo
      * @return
      */
     int updAuctionProdDateAndStatus(AuctionProductInfo auctionProductInfo);

     /**
      * 根据状态和和设定的时间查询所有的符合条件的拍品
      * @param dateStr
      * @param status
      * @return
      */
     List<AuctionProductInfo> getByStatusAndDate(String dateStr, int status);

     /**
      * 修改拍品状态
      * @param prodId  拍品id
      * @param status 改变后的状态（1开拍中 2准备中 3定时 4完结）
      * @return
      */
     int updateProductStatus(Integer prodId,Integer status);

     /**
      * 修改上架数量
      * @param prodId 拍品id
      * @param prodNum  拍品上架数量
      * @return
      */
     int updateProductNum(Integer prodId, int prodNum);

     /**
      * 分页查询拍品上架信息
      * @param paramVo
      * @return
      */
     Paging<AuctionProdVo> findByPage(ParamVo paramVo);

     /**
      * 分页查询拍品下架信息
      * @param paramVo
      * @return
      */
     Paging<AuctionProdVo> findByPageOff(ParamVo paramVo);

     /**
      * 批量拍品下架
      * @param ids
      * @param sysUser
      * @return
      */
     ResultBean<Integer> auctionBatchOff(Integer[] ids, SysUser sysUser) throws Exception;

     /**
      * 拍品上架
      * @param vo
      * @return
      */
     int auctionOn(AuctionProductInfoVo vo) throws Exception;

     /**
      * 保存修改拍品信息
      * @param auctionProductInfo
      * @param productInfo
      * @param pics
      * @param auctionRule
      * @return
      */
     String saveEditAuctionProd(AuctionProductInfoVo auctionProductInfo, ProductInfo productInfo, List<ProductPic> pics, AuctionRule auctionRule);

     /**
      * 根据规则Id查询拍品数量
      * @param integer
      * @return
      */
     Integer getProductNumByRuleId(Integer integer); 
     /**
      * 根据拍品id获取拍品保底价规则
      * @param productInfoId
      * @return
      */
     List<AuctionProductPriceRuleVo> findRulesByProductInfoId(Integer productInfoId);
}
