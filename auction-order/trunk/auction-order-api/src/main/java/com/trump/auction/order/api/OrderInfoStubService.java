
package com.trump.auction.order.api;

import java.util.Date;
import java.util.List;

import com.cf.common.util.page.Paging;
import com.cf.common.utils.ServiceResult;
import com.trump.auction.order.model.OrderInfoModel;
import com.trump.auction.order.model.OrderInfoQuery;


/**
 * 订单管理
 * Created by wangjian on 2017/12/20.
 */
public interface OrderInfoStubService {

    /**
     * 保存订单信息
     * @param orderInfo {"订单总金额","用戶id","收货地址id","商品数量","实付金额","出价次数","拍品期数ID","拍币数量","购物币ID"}
     * @return {code:"",msg:"",ext:"订单号"}
     */
    ServiceResult saveOrder(OrderInfoModel orderInfo);

    /**
     * 保存拍卖订单信息
     * @param orderInfo {"订单总金额","用戶id","商品数量","实付金额","出价次数","拍品期数ID","拍币数量",""流拍标志位}
     * @return {code:"",msg:"",ext:"订单号"}
     */
    ServiceResult saveAuctionOrder(OrderInfoModel orderInfo);

    /**
     * 所有订单列表数据查询
     * @return
     */
     Paging<OrderInfoModel> findAllOrder(OrderInfoQuery orderInfo, Integer pageNum, Integer pageSize);

    /**
     *  查询一条订单信息
     * @param orderInfo
     * @return
     */
    OrderInfoModel findOneOrder(OrderInfoQuery orderInfo);

    /**
     *  根据订单id更新订单状态
     * @param orderInfo
     * @return
     */
    ServiceResult updateOrderStatus(OrderInfoModel orderInfo);

    /**
     * 查询超时未支付订单
     * @param date 当前时间前xx小时时间
     * @param startTime  查询开始时间段
     * @param endTime  查询结束时间段
     * @return
     */
    List<String> queryUnpaidOrders(Date date, Date startTime, Date endTime);

    /**
     * 根据订单号更新地址信息
     * @param orderInfoModel
     * @return
     */
    ServiceResult updateAddressByOrderId(OrderInfoModel orderInfoModel);

    /**
     * 根据用户ID、拍品期数ID批量查询订单信息集合
     * @param userId
     * @param auctionNos
     * @return
     */
    List<OrderInfoModel> findOrderListByAcNo(Integer userId, List<Integer> auctionNos);

    /**
     * 根据用户ID、拍品期数ID查询单条订单信息
     * @param userId
     * @param auctionNo
     * @return
     */
    OrderInfoModel findOneOrderByAcNo(Integer userId, Integer auctionNo);
}

