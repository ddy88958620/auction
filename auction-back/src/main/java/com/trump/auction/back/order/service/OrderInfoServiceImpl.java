package com.trump.auction.back.order.service;

import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.github.pagehelper.PageHelper;
import com.trump.auction.back.auctionProd.dao.read.AuctionProductRecordDao;
import com.trump.auction.back.frontUser.dao.read.UserInfoDao;
import com.trump.auction.back.frontUser.model.UserInfo;
import com.trump.auction.back.order.dao.read.LogisticsDao;
import com.trump.auction.back.order.dao.read.OrderInfoDao;
import com.trump.auction.back.order.model.OrderInfo;
import com.trump.auction.order.enums.EnumOrderStatus;
import com.trump.auction.order.util.Base64Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 订单管理
 * @author Created by wangjian on 2017/12/25.
 */
@Slf4j
@Service("orderInfoService")
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    private OrderInfoDao orderInfoDao;

    @Autowired
    private LogisticsDao logisticsDao;

    @Autowired
    private AuctionProductRecordDao productRecordDao;

    @Autowired
    private UserInfoDao userInfoDao;

    /**
     * 分页查询订单列表
     * @param params
     * @return
     */
    @Override
    public Paging<OrderInfo> findOrderInfoPage(Map<String,Object> params){
        long startTime = System.currentTimeMillis();
        log.info("findOrderInfoPage invoke,StartTime:{},params:{}", startTime, params);

        Paging<OrderInfo> result = null;
        try {
            PageHelper.startPage(Integer.valueOf(String.valueOf(params.get("page") == null ? 1 : params.get("page"))),
                    Integer.valueOf(String.valueOf(params.get("limit") == null ? 10 : params.get("limit"))));
            result = PageUtils.page(orderInfoDao.findOrderInfoList(params));

            //将流拍订单的用户姓名解码供页面展示
            List<OrderInfo> list = result.getList();
            for (OrderInfo item : list) {
                if (EnumOrderStatus.LIUPAI.getValue().equals(item.getOrderStatus())) {
                    item.setUserName(Base64Utils.decodeStr(item.getUserName()));
                }
            }
            result.setList(list);
        } catch (NumberFormatException e) {
            log.error("findOrderInfoPage error:", e);
        }

        long endTime = System.currentTimeMillis();
        log.info("findOrderInfoPage end,duration:{}", endTime - startTime);
        return result;
    }

    /**
     * 根据订单号查询订单详细信息
     * @param orderId
     * @return
     */
    @Override
    public OrderInfo findOrderInfoView(String orderId){
        long startTime = System.currentTimeMillis();
        log.info("findOrderInfoView invoke,StartTime:{},params:{}", startTime, orderId);

        if (null == orderId) {
            throw new IllegalArgumentException("findOrderInfoView param orderId is null");
        }

        OrderInfo result = null;
        try {
            result = orderInfoDao.selectByPrimaryKey(orderId);

            if (!EnumOrderStatus.LIUPAI.getValue().equals(result.getOrderStatus())) {
                UserInfo userInfo = userInfoDao.selectOneUserInfo(Integer.valueOf(result.getBuyId()));
                if (null != userInfo) {
                    userInfo.setWxNickName(userInfo.getWxNickName() == null ? null : Base64Utils.decodeStr(userInfo.getWxNickName()));
                    userInfo.setQqNickName(userInfo.getQqNickName() == null ? null : Base64Utils.decodeStr(userInfo.getQqNickName()));
                    result.setUserInfo(userInfo);
                }
            } else {
                result.setUserName(Base64Utils.decodeStr(result.getUserName()));
            }

            result.setLogistics(logisticsDao.selectByPrimaryKey(orderId));
            result.setProductRecord(productRecordDao.findProdRecordByAuctionNum(result.getAuctionNo()));
        } catch (NumberFormatException e) {
            log.error("findOrderInfoView error:", e);
        }

        long endTime = System.currentTimeMillis();
        log.info("findOrderInfoView end,duration:{}", endTime - startTime);
        return result;
    }
    /**
     * 根据用户ID查询订单数量
     * @param userId
     * @return
     */
    @Override
    public int countOrder(Integer userId) {
        return orderInfoDao.countOrder(userId);
    }
}
