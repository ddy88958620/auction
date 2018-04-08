package com.trump.auction.trade.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cf.common.util.page.Paging;
import com.trump.auction.trade.api.AuctionOrderStubService;
import com.trump.auction.trade.service.AuctionOrderService;
import com.trump.auction.trade.vo.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 *  竞拍订单信息接口实现(对外提供)
 * @author Administrator
 * @date 2018/1/6
 */
@Service(version = "1.0.0")
public class AuctionOrderStubServiceImpl implements AuctionOrderStubService {


    @Autowired
    private AuctionOrderService auctionOrderService;
    /**
     * 查询竞拍订单信息分页列表
     * @param paramVo
     * @return
     */
    @Override
    public Paging<AuctionOrderVo> findAuctionOrder(ParamVo paramVo) {
        return auctionOrderService.findAuctionOrder(paramVo);
    }

    /**
     * 查询出价订单信息分页列表
     * @param paramVo
     * @return
     */
    @Override
    public Paging<BidVo> findBidPage(ParamVo paramVo) {
        return auctionOrderService.getBidRecordPage(paramVo);
    }

    /**
     * 获取最新的出价记录
     * @param paramVo
     * @return
     */
    @Override
    public List<BidVo> getLastBidRecord(ParamVo paramVo) {
        return auctionOrderService.getLastBidRecord(paramVo);
    }

    /**
     * 查询竞拍订单信息分页列表
     * @param paramVo
     * @return
     */
    @Override
    public Paging<BidVo> getBidRecordPage(ParamVo paramVo) {
        return auctionOrderService.getBidRecordPage(paramVo);
    }

    /**
     * 获取动态数据
     * @param paramVo
     * @return
     */
    @Override
    public AuctionOrderVo getDynamicData(ParamVo paramVo) {
        return auctionOrderService.getDynamicData(paramVo);
    }

    /**
     * 获取列表上的价格和倒计时动态数据
     * @param paramVo
     * @return
     */
    @Override
    public AuctionOrderForListVo getDynamicDataForList(ParamVo paramVo) {
        return auctionOrderService.getDynamicDataForList(paramVo);
    }

    /**
     * 获取拍品基础数据
     * @param paramVo
     * @return
     */
    @Override
    public AuctionOrderVo getAuctionBaseData(ParamVo paramVo) {
        return auctionOrderService.getAuctionBaseData(paramVo);
    }

    @Override
    public AuctionProductRecordVo getRecordByAuctionId(Integer id) {
        return auctionOrderService.getRecordByAuctionId(id);
    }

    /**
     * 往期订单信息分页列表
     * @param paramVo
     * @return
     */
    @Override
    public Paging<AuctionOrderVo> findPastOrder(ParamVo paramVo) {
        return auctionOrderService.findPastOrder(paramVo);
    }

    /**
     * 获取往期订单信息
     * @param auctionId
     * @return
     */
    @Override
    public AuctionOrderVo getPastOrder(Integer auctionId) {
        return auctionOrderService.getPastOrder(auctionId);
    }

    /**
     * 获取指定用户的拍中订单
     * @param paramVos
     * @return
     * @throws Exception
     */
    @Override
    public List<AuctionVo> getUserOrder(List<ParamVo> paramVos) throws Exception {
        return auctionOrderService.getUserOrder(paramVos);
    }
}
