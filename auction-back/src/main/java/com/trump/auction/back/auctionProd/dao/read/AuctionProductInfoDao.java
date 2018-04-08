package com.trump.auction.back.auctionProd.dao.read;

import com.trump.auction.back.auctionProd.model.AuctionProductInfo;
import com.trump.auction.back.auctionProd.vo.AuctionProdVo;
import com.trump.auction.back.product.vo.ParamVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AuctionProductInfoDao {
    int deleteByPrimaryKey(Integer id);

    int insert(AuctionProductInfo record);

    List<AuctionProductInfo> findAuctionProdList(AuctionProdVo auctionProdVo);


    AuctionProductInfo selectByPrimaryKey(Integer id);


    int updateByPrimaryKey(AuctionProductInfo record);

    /**
     * 更新
     * @param auctionProductInfo
     * @return
     */
    int updateByPrimaryKeySelective(AuctionProductInfo auctionProductInfo);
    List<AuctionProductInfo> queryTimingProduct(@Param("date") Date date, @Param("status") Integer status);

    /**
     * 根据id查询详情
     * @param id
     * @return
     */
    AuctionProductInfo findAuctionProductInfoById(Integer id);

    List<AuctionProductInfo> findOffByProductId(@Param("productId") Integer productId);

    /**
     * 批量更新拍品状态
     * @param ids
     * @param status
     * @return
     */
    int updAuctionProdStatus(@Param("ids") List<String> ids,@Param("status")Integer status);

    /**
     * 更新拍品定时的时间和状态
     * @param auctionProductInfo
     * @return
     */
    int updAuctionProdDateAndStatus(AuctionProductInfo auctionProductInfo);
    /**
     * 根据状态和和设定的时间查询所有的符合条件的拍品
     * @param date
     * @param status
     * @return
     */
    List<AuctionProductInfo> getByStatusAndDate(String date, int status);

    /**
     * 修改拍品状态
     * @param prodId  拍品id
     * @param status 改变后的状态（1开拍中 2准备中 3定时 4完结）
     * @return
     */
    int updateProductStatus(@Param("prodId") Integer prodId,@Param("status") Integer status);

    /**
     * 修改上架数量
     * @param prodId 拍品id
     * @param prodNum  拍品上架数量
     * @return
     */
    int updateProductNum(@Param("prodId")Integer prodId,@Param("prodNum")int prodNum);

    /**
     * 获取拍品管理
     * @param paramVo
     * @return
     */
    List<AuctionProdVo> findOn(ParamVo paramVo);

    /**
     * 获取拍品管理（下架）
     * @param paramVo
     * @return
     */
    List<AuctionProdVo> findOff(ParamVo paramVo);


    Integer getProductNumByRuleId(@Param("ruleId") Integer ruleId);

    /**
     * 根据分类查询拍品
     * @param classifyId
     * @return
     */
    Integer getByClassifyId(@Param("classifyId") Integer classifyId);
}