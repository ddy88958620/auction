package com.trump.auction.trade.dao;

import com.trump.auction.trade.domain.AuctionProductInfo;
import com.trump.auction.trade.vo.AuctionProductInfoVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Administrator
 */
@Repository
public interface AuctionProductInfoDao {

    /**
     * 查询拍品信息
     * @param id
     * @return
     */
    AuctionProductInfo selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

    int insert(AuctionProductInfo record);

    int insertSelective(AuctionProductInfo record);

    int updateByPrimaryKeySelective(AuctionProductInfo record);

    int updateByPrimaryKey(AuctionProductInfo record);



    /**
     * 批量拍品下架
     * @param auctionProductInfoVos
     * @return
     */
    int batchOff(@Param("vos") List<AuctionProductInfoVo> auctionProductInfoVos);

    /**
     * 拍品上架
     * @param auctionProductInfoVo
     * @return
     */
    int auctionOn(AuctionProductInfoVo auctionProductInfoVo);
    /**
     * 批量更新拍品状态
     * @param ids
     * @param status
     * @return
     */
    int updAuctionProdStatus(@Param("ids") List<String> ids,
                             @Param("status") Integer status);

    /**
     * 定时更新拍品状态
     * @param auctionProductInfo
     * @return
     */
    int updAuctionProdDateAndStatus(AuctionProductInfo auctionProductInfo);

    int updAuctionProdStatusTime(AuctionProductInfo auctionProductInfo);

    /**
     * 修改拍品状态
     * @param auctionProduct
     * @return
     */
    int updateProductStatus(AuctionProductInfoVo auctionProduct);

    /**
     * 根据分类id查询上架拍品
     * @param classifyId
     * @return
     */
    List<AuctionProductInfo> queryProdByClassify(@Param("classifyId") Integer classifyId);

    /**
     * 修改上架数量
     * @param prodId 拍品id
     * @param prodNum  拍品上架数量
     * @return
     */
    int updateProductNum(@Param("prodId")Integer prodId,@Param("prodNum")int prodNum);
}