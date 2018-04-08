package com.trump.auction.order.dao;

import com.trump.auction.order.domain.OrderInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface OrderInfoDao {

    /**
     * 保存订单信息
     * @param record
     * @return
     */
    int insert(OrderInfo record);

    int insertSelective(OrderInfo record);

    /**
     * 根据订单号查询单条订单信息
     * @param orderId
     * @return
     */
    OrderInfo selectByPrimaryKey(String orderId);

    /**
     * 根据条件查询订单信息集合
     * @param orderInfo
     * @return 订单信息集合
     */
    List<OrderInfo> findAllOrder(OrderInfo orderInfo);

    /**
     * 修改订单状态
     * @param orderInfo
     * @return
     */
    int updateOrderStatus(OrderInfo orderInfo);

    /**
     * 根据订单号更新地址信息
     * @param orderInfo
     * @return
     */
    int updateAddressByOrderId(OrderInfo orderInfo);

    /**
     * 查询超时未支付订单信息
     * @return
     */
    List<String> findOvertimeOrderInfo(@Param("beforeDate") Date date,@Param("startTime") Date startTime,@Param("endTime") Date endTime);



    /**
     * 根据用户ID、拍品期数ID批量查询订单信息集合
     * @param userId
     * @param auctionNos
     * @return
     */
    List<OrderInfo> findOrderListByAcNo(@Param("userId") Integer userId,@Param("auctionNos") List<Integer> auctionNos);

    /**
     * 根据用户ID、拍品期数ID查询单条订单信息
     * @param userId
     * @param auctionNo
     * @return
     */
    OrderInfo findOneOrderByAcNo(@Param("userId")Integer userId,@Param("auctionNo") Integer auctionNo);
}