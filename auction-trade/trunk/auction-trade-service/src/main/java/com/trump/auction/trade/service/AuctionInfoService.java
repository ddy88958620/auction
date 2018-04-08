package com.trump.auction.trade.service;

import com.cf.common.util.page.Paging;
import com.cf.common.utils.JsonResult;
import com.trump.auction.trade.domain.AuctionInfo;
import com.trump.auction.trade.model.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: zhangqingqiang.
 * @date: 2018/1/6 0006.
 * @Description: 拍品信息 .
 */
public interface AuctionInfoService {

    /**
     * 按分类查询拍品信息
     * @param auctionQuery 根据分类条件查询
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return Paging<OrderInfoModel> 分页后的拍品信息列表信息
     */
    Paging<AuctionInfoModel> queryAuctionInfoByClassify (AuctionInfoQuery auctionQuery, int pageNum, int pageSize);

    /**
     * 最新成交拍品
     * @param auctionQuery 条件查询{status:2}
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return Paging<OrderInfoModel> 分页后的拍品信息列表信息
     */
    Paging<AuctionInfoModel> queryNewestAuctionInfo(AuctionInfoQuery auctionQuery, int pageNum, int pageSize);

    /**
     * 热拍中
     * @param auctionQuery 条件查询 redis中查出的主键id集合
     * @return 拍品信息列表信息
     */
    List<AuctionInfoModel> queryHotAuctionInfo(AuctionInfoQuery auctionQuery);

     AuctionInfo   selectByPrimaryKey(Integer id);
    /**
     *保存拍品信息
     * @param auctionInfoModel
     * @return {code:xx,msg:xx,data:xx}
     */
    JsonResult saveAuctionInfo(AuctionInfoModel auctionInfoModel);

    /**
     * 根据拍品id和期数查拍卖信息
     * @param auctionQuery 拍品id和期数
     * @return 拍品名称 当前售价 拍品图片 是否成交
     */
    AuctionInfoModel queryAuctionByProductIdAndNo(AuctionInfoQuery auctionQuery);

    /**
     * 获取拍品信息通过拍品期数ID
     * @param auctionId
     * @return
     */
    AuctionInfoModel getAuctionInfoById(Integer auctionId);

    List<AuctionInfo>  findAuctionInfoStatus(Integer status);

    List<String>  findIds( Integer status);

    int upAcutionStatus(Integer id,Integer status);

    JsonResult doAuctionTask(AuctionProductInfoModel prod, AuctionRuleModel rule, AuctionInfoModel auctionInfo,
                             AuctionProductRecordModel lastRecord);

     Integer findAuctionById(Integer auctionProdId);
}
