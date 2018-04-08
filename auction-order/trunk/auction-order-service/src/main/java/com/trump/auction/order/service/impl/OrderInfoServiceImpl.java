package com.trump.auction.order.service.impl;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.trump.auction.account.api.AccountInfoStubService;
import com.trump.auction.goods.model.ProductInventoryLogModel;
import com.trump.auction.order.domain.Logistics;
import com.trump.auction.order.enums.EnumLogisticsStatus;
import com.trump.auction.order.enums.EnumOrderType;
import com.trump.auction.order.model.LogisticsModel;
import com.trump.auction.order.service.LogisticsService;
import com.trump.auction.trade.api.AuctionBidStubService;
import com.trump.auction.trade.api.AuctionOrderStubService;
import com.trump.auction.trade.model.BidResult;
import com.trump.auction.trade.vo.AuctionProductRecordVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.cf.common.id.IdGenerator;
import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.cf.common.utils.ServiceResult;
import com.github.pagehelper.PageHelper;
import com.trump.auction.cust.api.UserShippingAddressStuService;
import com.trump.auction.cust.model.UserShippingAddressModel;
import com.trump.auction.goods.api.ProductInventoryLogSubService;
import com.trump.auction.order.dao.OrderInfoDao;
import com.trump.auction.order.domain.OrderInfo;
import com.trump.auction.order.enums.EnumOrderStatus;
import com.trump.auction.order.enums.ResultEnum;
import com.trump.auction.order.model.OrderInfoModel;
import com.trump.auction.order.model.OrderInfoQuery;
import com.trump.auction.order.service.OrderInfoService;

import lombok.extern.slf4j.Slf4j;

/**
 * 订单管理
 * @author Created by wangjian on 2017/12/20.
 */
@Slf4j
@Service("orderInfoService")
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    private OrderInfoDao orderInfoDao;

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private UserShippingAddressStuService addressStuService;

    @Autowired
    private AccountInfoStubService accountInfoStubService;

    @Autowired
    private ProductInventoryLogSubService inventoryLogSubService;

    @Autowired
    private AuctionOrderStubService auctionOrderStubService;

    @Autowired
    private IdGenerator snowflakeOrderId;

    @Autowired
    private LogisticsService logisticsService;

    /**
     * 保存订单信息
     * @param orderInfo {"订单总金额","用戶id","收货地址id","商品数量","实付金额","出价次数","拍品期数ID","拍币数量","购物币ID"}
     * @return {code:"",msg:"",ext:"订单号"}
     */
    @Override
    @Transactional
    public ServiceResult saveOrder(OrderInfoModel orderInfo){
        long startTime = System.currentTimeMillis();
        log.info("saveOrder invoke,StartTime:{},params:{}", startTime, orderInfo);
        ServiceResult jsonResult = new ServiceResult(ServiceResult.FAILED,ResultEnum.SAVE_FAIL.getDesc());

        //调用参数验证方法
        ServiceResult checkResult = createOrderCheck(orderInfo);

        //验证不通过则return
        if (!ServiceResult.SUCCESS.equals(checkResult.getCode())) {
            return checkResult;
        }

        //收货地址ID不可为空
        if (null == orderInfo.getUserShippingId()) {
            return new ServiceResult(ServiceResult.WAITING, ResultEnum.ADDRESS_ID_NOTNULL.getDesc());
        }

        //调用雪花算法生成订单ID
        String orderId = String.valueOf(snowflakeOrderId.next());
        orderInfo.setOrderId(orderId);
        Integer productId = null;
        Integer productNum = orderInfo.getProductNum();
        Integer userShippingId = orderInfo.getUserShippingId();
        boolean stackFlag = false;
        boolean buyCoinFlag = false;

        try {

            //获取拍品信息
            AuctionProductRecordVo productRecordVo = null;
            try {
                productRecordVo = auctionOrderStubService.getRecordByAuctionId(orderInfo.getAuctionNo());
                log.info("auctionProductRecordVo:{}", productRecordVo);

                if (null == productRecordVo) {
                    return new ServiceResult(ServiceResult.WAITING,ResultEnum.NULL_PRODUCT.getDesc());
                }
            } catch (Exception e) {
                log.error("saveOrder...getRecordByAuctionId error:", e);
                jsonResult.setMsg("call auctionOrderStubService error");
                return jsonResult;
            }
            productId = productRecordVo.getProductId();

            //验证库存
            try {
                boolean bo = inventoryLogSubService.validateStock(productId, productNum);
                if (!bo) {
                    return new ServiceResult(ServiceResult.WAITING,ResultEnum.STOCK_NOTENOUGH.getDesc());
                }
            } catch (Exception e) {
                log.error("saveOrder...validateStock error:", e);
                jsonResult.setMsg("call inventoryLogSubService error");
                return jsonResult;
            }

            //验证收货地址
            UserShippingAddressModel userAddressModel = null;
            try {
                userAddressModel = addressStuService.findUserAddressItemByAddressId(userShippingId);
                log.info("UserShippingAddressModel:{}", userAddressModel);

                if (null == userAddressModel) {
                    return new ServiceResult(ServiceResult.WAITING,ResultEnum.NULL_ADDRESS.getDesc());
                }
            } catch (Exception e) {
                log.error("saveOrder...findUserAddressItemByAddressId error:", e);
                jsonResult.setMsg("call addressStuService error");
                return jsonResult;
            }

            //创建订单前先减库存
            try {
                ProductInventoryLogModel model = new ProductInventoryLogModel();
                model.setProductId(productId);
                model.setProductNum(productNum);
                int subStock = inventoryLogSubService.subtractStock(model);

                if (subStock > 0) {
                    stackFlag = true;
                    log.info("subtractStock {}", "success");
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                log.error("saveOrder...inventoryLogSubService error:", e);
                jsonResult.setMsg(ResultEnum.REDUCE_STOCK_FAIL.getDesc());
            }

            //如果订单使用了购物币则冻结相应的用户购物币
            ServiceResult accountResult = null;
            if (null != orderInfo.getBuyCoinMoney() && orderInfo.getBuyCoinMoney().doubleValue() > 0) {
                try {
                    accountResult = accountInfoStubService.reduceBuyCoin(Integer.valueOf(orderInfo.getBuyId()),
                            null, orderInfo.getBuyCoinMoney(),
                            orderInfo.getAuctionNo().toString(),  1);

                    if (ServiceResult.SUCCESS.equals(accountResult.getCode())) {
                        buyCoinFlag = true;
                        log.info("frozen buyCoin, result:success");
                    } else {
                        //冻结用户购物币失败则还原库存并return
                        if (stackFlag) {
                            ProductInventoryLogModel model = new ProductInventoryLogModel();
                            model.setProductId(productId);
                            model.setProductNum(productNum);
                            int subStock = inventoryLogSubService.addStock(model);
                            log.info("reduction {}", subStock > 0 ? "success" : "failed");
                        }
                        throw new Exception();
                    }
                } catch (Exception e) {
                    log.error("saveOrder...accountInfoStubService error:", e);
                    jsonResult.setMsg(ResultEnum.REDUCE_BUY_COIN_FAILED.getDesc());
                    return jsonResult;
                }
            }

            //封装订单信息
            orderInfo.setOrderStatus(EnumOrderStatus.UNPAID.getValue());
            orderInfo.setOrderType(EnumOrderType.DIFFERENTIAL.getValue());
            getOrderInfo(orderInfo, productRecordVo, userAddressModel);

            //保存订单信息
            log.info("begin save order... orderInfo:{}", orderInfo);
            int result = orderInfoDao.insert(beanMapper.map(orderInfo, OrderInfo.class));

            log.info("save order end...orderId:{}, result:{}", orderId, result <= 0 ? "failed" : "success");

            if (result > 0) {
                jsonResult.setCode(ServiceResult.SUCCESS);
                jsonResult.setMsg(ResultEnum.SAVE_SUCCESS.getDesc());
                jsonResult.setExt(orderId);
            }
        } catch (Exception e) {
            log.error("save order error orderId:{},error:{}", orderId, e);

            //事务手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            //创建订单失败则还原库存
            if (stackFlag) {
                try {
                    ProductInventoryLogModel model = new ProductInventoryLogModel();
                    model.setProductId(productId);
                    model.setProductNum(productNum);
                    int subStock = inventoryLogSubService.addStock(model);
                    log.info("reduction {}", subStock > 0 ? "success" : "failed");
                } catch (Exception e2) {
                    log.error("saveOrder...inventoryLogSubService error:", e2);
                }
            }

            //创建订单失败则解冻用户购物币
            if (buyCoinFlag) {
                try {
                    ServiceResult accountResult = accountInfoStubService.reduceBuyCoin(Integer.valueOf(orderInfo.getBuyId()), null, orderInfo.getBuyCoinMoney(), orderInfo.getAuctionNo().toString(),  3);
                    log.info("thaw buyCoin, result:{}", ServiceResult.SUCCESS.equals(accountResult.getCode()) ? "success" : "failed");
                } catch (Exception e2) {
                    log.error("saveOrder...accountInfoStubService error:", e2);
                }
            }
        }

        long endTime = System.currentTimeMillis();
        log.info("saveOrder end,duration:{}", endTime - startTime);
        return jsonResult;
    }

    /**
     * 保存拍卖订单信息
     * @param orderInfo {"订单总金额","用戶id","商品数量","实付金额","出价次数","拍品期数ID","拍币数量",""流拍标志位}
     * @return {code:"",msg:"",ext:"订单号"}
     */
    @Override
    @Transactional
    public ServiceResult saveAuctionOrder(OrderInfoModel orderInfo){
        long startTime = System.currentTimeMillis();
        log.info("saveAuctionOrder invoke,StartTime:{},params:{}", startTime, orderInfo);
        ServiceResult jsonResult = new ServiceResult(ServiceResult.FAILED,ResultEnum.SAVE_FAIL.getDesc());

        //调用参数验证方法
        ServiceResult checkResult = createOrderCheck(orderInfo);

        //验证不通过则return
        if (!ServiceResult.SUCCESS.equals(checkResult.getCode())) {
            return checkResult;
        }

        //调用雪花算法生成订单ID
        String orderId = String.valueOf(snowflakeOrderId.next());
        orderInfo.setOrderId(orderId);
        Integer productNum = orderInfo.getProductNum();

        try {

            //获取拍品信息
            AuctionProductRecordVo productRecordVo = null;
            try {
                productRecordVo = auctionOrderStubService.getRecordByAuctionId(orderInfo.getAuctionNo());
                log.info("auctionProductRecordVo:{}", productRecordVo);

                if (null == productRecordVo) {
                    return new ServiceResult(ServiceResult.WAITING,ResultEnum.NULL_PRODUCT.getDesc());
                }
            } catch (Exception e) {
                log.error("saveAuctionOrder...getRecordByAuctionId error:", e);
                jsonResult.setMsg("call auctionOrderStubService error");
                return jsonResult;
            }

            UserShippingAddressModel userAddressModel = null;

            //如果是机器人拍中的订单则将订单状态改为：已流拍
            if (orderInfo.getIsLiuPai()) {
                orderInfo.setOrderStatus(EnumOrderStatus.LIUPAI.getValue());
            } else {
                //如果是用户拍中的订单则绑定收货地址
                try {
                    orderInfo.setOrderStatus(EnumOrderStatus.UNPAID.getValue());
                    userAddressModel = addressStuService.findDefaultUserAddressItemByUserId(Integer.valueOf(orderInfo.getBuyId()));
                    log.info("UserShippingAddressModel:{}", userAddressModel);
                } catch (Exception e) {
                    log.error("saveAuctionOrder...findDefaultUserAddressItemByUserId error:", e);
                }
            }

            //封装订单信息
            orderInfo.setAddress(null);
            orderInfo.setOrderType(EnumOrderType.AUCTION.getValue());
            getOrderInfo(orderInfo, productRecordVo, userAddressModel);

            //保存订单信息
            log.info("begin saveAuctionOrder... orderInfo:{}", orderInfo);
            int result = orderInfoDao.insert(beanMapper.map(orderInfo, OrderInfo.class));

            log.info("saveAuctionOrder end...orderId:{}, result:{}", orderId, result <= 0 ? "failed" : "success");

            if (result > 0) {
                jsonResult.setCode(ServiceResult.SUCCESS);
                jsonResult.setMsg(ResultEnum.SAVE_SUCCESS.getDesc());
                jsonResult.setExt(orderId);
            }
        } catch (Exception e) {
            log.error("saveAuctionOrder error orderId:{},error:{}", orderId, e);

            //事务手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        long endTime = System.currentTimeMillis();
        log.info("saveAuctionOrder end,duration:{}", endTime - startTime);
        return jsonResult;
    }

    /**
     * 根据条件分页查询订单列表数据
     * @return
     */
    @Override
    public Paging<OrderInfoModel> findAllOrder(OrderInfoQuery orderInfo, Integer pageNum, Integer pageSize){
        long startTime = System.currentTimeMillis();
        log.info("findAllOrder invoke,StartTime:{},params:{},{},{}", startTime, orderInfo, pageNum, pageSize);
        if (null == orderInfo || null == orderInfo.getBuyId()) {
            throw new IllegalArgumentException("findAllOrder param buyId is null!");
        }
        PageHelper.startPage(pageNum, pageSize);
        Paging<OrderInfoModel> orderInfos = null;
        try {
            orderInfos = PageUtils.page(
                    orderInfoDao.findAllOrder(beanMapper.map(orderInfo, OrderInfo.class)),
                    OrderInfoModel.class, beanMapper);
        } catch (Exception e) {
            log.error("findAllOrder error:", e);
        }

        long endTime = System.currentTimeMillis();
        log.info("findAllOrder end,duration:{}", endTime - startTime);
        return orderInfos;
    }
    /**
     *  查询一条订单信息
     * @param orderInfo
     * @return
     */
    @Override
    public OrderInfoModel findOneOrder(OrderInfoQuery orderInfo){
        long startTime = System.currentTimeMillis();
        log.info("findOneOrder invoke,StartTime:{},params:{}", startTime, orderInfo);

        if (null == orderInfo || StringUtils.isBlank(orderInfo.getOrderId())) {
            throw new IllegalArgumentException("findOneOrder param orderInfo is null!");
        }

        OrderInfoModel orderInfoModel = null;
        try {
            orderInfoModel = beanMapper.map(orderInfoDao.selectByPrimaryKey(orderInfo.getOrderId()), OrderInfoModel.class);
            orderInfoModel.setLogisticsModel(beanMapper.map(logisticsService.selectByPrimaryKey(orderInfo.getOrderId()), LogisticsModel.class));
        } catch (Exception e) {
            log.error("findOneOrder error:", e);
        }

        long endTime = System.currentTimeMillis();
        log.info("findOneOrder end,duration:{}", endTime - startTime);
        return orderInfoModel;
    }

    /**
     *  根据订单id更新订单状态
     * @param orderInfo
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ServiceResult updateOrderStatus(OrderInfoModel orderInfo){
        long startTime = System.currentTimeMillis();
        log.info("updateOrderStatus invoke,StartTime:{},params:{}", startTime, orderInfo);
        ServiceResult jsonResult = new ServiceResult(ServiceResult.FAILED, ResultEnum.UPDATE_ORDER_FAIL.getDesc());

        if (null == orderInfo) {
            return new ServiceResult(ServiceResult.WAITING, ResultEnum.PARAM_EXCEPTION.getDesc());
        }

        if (null == orderInfo.getOrderId() || StringUtils.isBlank(orderInfo.getOrderId())) {
            return new ServiceResult(ServiceResult.WAITING, ResultEnum.ORDER_ID_NOTNULL.getDesc());
        }

        if (null == orderInfo.getOrderStatus() || 0 == orderInfo.getOrderStatus()) {
            return new ServiceResult(ServiceResult.WAITING, ResultEnum.ORDER_STATUS_NOTNULL.getDesc());
        }

        String orderId = orderInfo.getOrderId();
        Integer orderStatus = orderInfo.getOrderStatus();
        log.info("update order status. params:orderId:{},orderStatus:{}", orderId,orderStatus);

        if(EnumOrderStatus.SHIPPED.getValue().equals(orderStatus)){
            log.info("order delivery status:{}",orderStatus);
            orderInfo.setOrderDeliveryTime(new Date());
        }
        if(EnumOrderStatus.RECEIVED.getValue().equals(orderStatus)){
            log.info("order receive status:{}", orderStatus);
            orderInfo.setOrderReceiveTime(new Date());
        }
        if(EnumOrderStatus.PAID.getValue().equals(orderStatus)){
            log.info("order pay status:{}", orderStatus);
            orderInfo.setBuyerPayTime(new Date());
            orderInfo.setOrderDispatchTime(new Date());
        }
        if(EnumOrderStatus.CLOSE.getValue().equals(orderStatus)){
            log.info("order canceled status:{}",orderStatus);
            orderInfo.setCancelTime(new Date());
        }

        try {
            int executeCount = orderInfoDao.updateOrderStatus(beanMapper.map(orderInfo, OrderInfo.class));

            if (executeCount > 0) {

                //已付款>>创建待发货的物流信息
                if (EnumOrderStatus.PAID.getValue().equals(orderInfo.getOrderStatus())) {
                    log.info("create logistics information after payment...orderId:{}", orderId);

                    //获取订单信息
                    OrderInfo info = orderInfoDao.selectByPrimaryKey(orderId);

                    Logistics logistics = new Logistics();

                    //封装物流信息
                    getLogisticsInfo(info, logistics);
                    logistics.setBackUserId(orderInfo.getUserId());

                    int executeCounts = logisticsService.insert(logistics);
                    log.info("create logisticsInfo,execute size:{}", executeCounts);

                    //修改失败则回滚
                    if (executeCounts <= 0) {
                        throw new RuntimeException();
                    }

                    //如果订单使用了购物币则扣除相应的购物币
                    if (null != info.getBuyCoinMoney() && info.getBuyCoinMoney().doubleValue() > 0) {
                        try {
                            ServiceResult accountResult = accountInfoStubService.reduceBuyCoin(Integer.valueOf(info.getBuyId()), null, info.getBuyCoinMoney(), info.getAuctionNo().toString(),  2);
                            log.info("deduction buyCoin, result:{}", ServiceResult.SUCCESS.equals(accountResult.getCode()) ? "success" : "failed");
                        } catch (Exception e) {
                            log.error("orderPaid...accountInfoStubService error:", e);
                        }
                    }
                }

                //确认收货>>同步更新物流状态为已签收
                if (EnumOrderStatus.RECEIVED.getValue().equals(orderInfo.getOrderStatus())) {
                    log.info("confirm receipt and update logistics information...orderId:{}", orderId);

                    Logistics logistics = new Logistics();
                    logistics.setOrderId(orderId);
                    logistics.setLogisticsStatus(EnumLogisticsStatus.DISPATCH_FALSE.getValue());
                    int executeCounts = logisticsService.updateByPrimaryKeySelective(logistics);

                    log.info("update logisticsStatus to received,execute size:{}", executeCounts);

                    //修改失败则回滚
                    if (executeCounts <= 0) {
                        throw new RuntimeException();
                    }
                }

                jsonResult.setCode(ServiceResult.SUCCESS);
                jsonResult.setMsg(ResultEnum.UPDATE_ORDER_SUCCESS.getDesc());

                OrderInfo orderInfos =  orderInfoDao.selectByPrimaryKey(orderId);
                //如果是取消订单则还原商品库存和购物币
                if (EnumOrderStatus.CLOSE.getValue().equals(orderStatus)) {
                    try {
                        log.info("cancel order...restore inventory,orderId:{}", orderId);
                        ProductInventoryLogModel pilModel = new ProductInventoryLogModel();
                        pilModel.setProductId(orderInfos.getProductId());
                        pilModel.setProductNum(orderInfos.getProductNum());
                        int addStock = inventoryLogSubService.addStock(pilModel);
                        log.info("restore inventory {}", addStock > 0 ? "success" : "failed");
                    } catch (Exception e) {
                        log.error("cancelOrder...inventoryLogSubService error:", e);
                    }

                    //如果订单使用了购物币则冻结相应的用户购物币
                    if (null != orderInfos.getBuyCoinMoney() && orderInfos.getBuyCoinMoney().doubleValue() > 0) {
                        try {
                            ServiceResult accountResult = accountInfoStubService.reduceBuyCoin(Integer.valueOf(orderInfos.getBuyId()), null, orderInfos.getBuyCoinMoney(), orderInfos.getAuctionNo().toString(),  3);
                            log.info("thaw buyCoin, result:{}", ServiceResult.SUCCESS.equals(accountResult.getCode()) ? "success" : "failed");
                        } catch (Exception e) {
                            log.error("cancelOrder...accountInfoStubService error:", e);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("updateOrderStatus error:", e);
            throw new RuntimeException(e);
        }

        long endTime = System.currentTimeMillis();
        log.info("updateOrderStatus end,duration:{}", endTime - startTime);
        return jsonResult;
    }

    /**
     * 查询超时未支付订单
     * @param date 当前时间前xx小时时间
     * @param startTime  查询开始时间段
     * @param endTime  查询结束时间段
     * @return
     */
    @Override
    public List<String> queryUnpaidOrders(Date date, Date startTime, Date endTime){
        long startTimes = System.currentTimeMillis();
        log.info("queryUnpaidOrders invoke,StartTime:{},params:{},{},{}", startTime, date, startTime, endTime);

        List<String> list = null;
        try {
            list = orderInfoDao.findOvertimeOrderInfo(date, startTime, endTime);
        } catch (Exception e) {
            log.error("queryUnpaidOrders error:", e);
        }

        long endTimes = System.currentTimeMillis();
        log.info("queryUnpaidOrders end,duration:{}", endTimes - startTimes);
        return list;
    }

    /**
     * 根据订单号更新地址信息
     * @param orderInfoModel
     * @return
     */
    @Override
    public ServiceResult updateAddressByOrderId(OrderInfoModel orderInfoModel){
        long startTime = System.currentTimeMillis();
        log.info("updateAddressByOrderId invoke,StartTime:{},params:{}", startTime, orderInfoModel);
        ServiceResult jsonResult = new ServiceResult(ServiceResult.FAILED,ResultEnum.UPDATE_ADDRESS_FAIL.getDesc());

        int result = 0;
        if (null == orderInfoModel||null==orderInfoModel.getOrderId()){
            log.error("updateAddressByOrderId param orderId is null");
            return new ServiceResult(ServiceResult.WAITING,ResultEnum.ORDER_ID_NOTNULL.getDesc());
        }

        try {
            orderInfoModel.setUpdateTime(new Date());
            result = orderInfoDao.updateAddressByOrderId(beanMapper.map(orderInfoModel, OrderInfo.class));
            log.info("updateAddressByOrderId update result:{}",result);

            if (result > 0) {
                jsonResult.setCode(ServiceResult.SUCCESS);
                jsonResult.setMsg(ResultEnum.UPDATE_ADDRESS_SUCCESS.getDesc());
            }

        } catch (Exception e) {
            log.error("updateAddressByOrderId error:{}",e);
        }

        long endTime = System.currentTimeMillis();
        log.info("updateAddressByOrderId end,duration:{}", endTime - startTime);
        return jsonResult;
    }

    /**
     * 根据用户ID、拍品期数ID批量查询订单信息集合
     * @param userId
     * @param auctionNos
     * @return
     */
    public List<OrderInfoModel> findOrderListByAcNo(Integer userId, List<Integer> auctionNos){
        long startTime = System.currentTimeMillis();
        log.info("findOrderListByAcNo invoke,StartTime:{},params:{},{}", startTime, userId, auctionNos);

        if (null == userId || null == auctionNos) {
            throw new IllegalArgumentException("findOrderListByAcNum param is null!");
        }

        List<OrderInfoModel> orderInfos = null;
        try {
            orderInfos = beanMapper.mapAsList(orderInfoDao.findOrderListByAcNo(userId, auctionNos), OrderInfoModel.class);
        } catch (Exception e) {
            log.error("findOrderListByAcNo error:", e);
        }

        long endTime = System.currentTimeMillis();
        log.info("findOrderListByAcNo end,duration:{}", endTime - startTime);
        return orderInfos;
    }

    /**
     * 根据用户ID、拍品期数ID查询单条订单信息
     * @param userId
     * @param auctionNo
     * @return
     */
    public OrderInfoModel findOneOrderByAcNo(Integer userId, Integer auctionNo){
        long startTime = System.currentTimeMillis();
        log.info("findOneOrderByAcNo invoke,StartTime:{},params:{},{}", startTime, userId, auctionNo);

        if (null == userId || null == auctionNo) {
            throw new IllegalArgumentException("findOneOrderByAcNum param is null!");
        }

        OrderInfoModel orderInfo = null;
        try {
            orderInfo = beanMapper.map(orderInfoDao.findOneOrderByAcNo(userId, auctionNo), OrderInfoModel.class);
        } catch (Exception e) {
            log.error("findOneOrderByAcNo error:", e);
        }

        long endTime = System.currentTimeMillis();
        log.info("findOneOrderByAcNo end,duration:{}", endTime - startTime);
        return orderInfo;
    }

    /**
     * 封装订单信息
     * @param orderInfo
     * @param productRecordVo
     * @param userAddressModel
     */
    private void getOrderInfo(OrderInfoModel orderInfo, AuctionProductRecordVo productRecordVo,
                              UserShippingAddressModel userAddressModel){

        orderInfo.setProductPrice(productRecordVo.getProductPrice());
        orderInfo.setProductName(productRecordVo.getProductName());
        orderInfo.setProductPic(productRecordVo.getPreviewPic());
        orderInfo.setMerchantId(String.valueOf(productRecordVo.getMerchantId()));
        orderInfo.setProductId(productRecordVo.getProductId());

        if (null != userAddressModel) {
            orderInfo.setProvinceCode(userAddressModel.getProvinceCode());
            orderInfo.setCityCode(userAddressModel.getPostCode());
            orderInfo.setDistrictCode(userAddressModel.getDistrictCode() == null ? null : userAddressModel.getDistrictCode().toString());
            orderInfo.setTownCode(userAddressModel.getTownCode() == null ? null : userAddressModel.getTownCode().toString());
            orderInfo.setAddress(userAddressModel.getAddress());
            orderInfo.setProvinceName(userAddressModel.getProvinceName());
            orderInfo.setDistrictName(userAddressModel.getDistrictName());
            orderInfo.setTownName(userAddressModel.getTownName());
            orderInfo.setCityName(userAddressModel.getCityName());
            orderInfo.setUserName(userAddressModel.getUserName());
            orderInfo.setUserPhone(userAddressModel.getUserPhone());
        }

        orderInfo.setCreateTime(new Date());
        orderInfo.setOrderCreateTime(new Date());
    }

    /**
     * 创建订单参数验证
     * @param orderInfo
     * @return
     */
    private ServiceResult createOrderCheck(OrderInfoModel orderInfo) {

        if (null == orderInfo) {
            return new ServiceResult(ServiceResult.WAITING, ResultEnum.PARAM_EXCEPTION.getDesc());
        }

        if (null == orderInfo.getBuyId()) {
            return new ServiceResult(ServiceResult.WAITING, ResultEnum.USER_ID_NOTNULL.getDesc());
        }

        if (null == orderInfo.getProductNum()) {
            return new ServiceResult(ServiceResult.WAITING, ResultEnum.PRODUCT_NUM_NOTNULL.getDesc());
        }

        if (null == orderInfo.getOrderAmount()) {
            return new ServiceResult(ServiceResult.WAITING, ResultEnum.ORDER_AMOUNT_NOTNULL.getDesc());
        }

        if (null == orderInfo.getPaidMoney()) {
            return new ServiceResult(ServiceResult.WAITING, ResultEnum.PAID_MONEY_NOTNULL.getDesc());
        }

        if (null == orderInfo.getBidTimes()) {
            return new ServiceResult(ServiceResult.WAITING, ResultEnum.BID_TIMES_NOTNULL.getDesc());
        }

        if (null == orderInfo.getAuctionNo()) {
            return new ServiceResult(ServiceResult.WAITING, ResultEnum.AUCTION_NO_NOTNULL.getDesc());
        }
        return new ServiceResult(ServiceResult.SUCCESS);
    }

    /**
     * 封装物流信息
     * @param info
     * @param logistics
     */
    private void getLogisticsInfo(OrderInfo info, Logistics logistics){
        logistics.setOrderId(info.getOrderId());
        logistics.setTotalOrder(info.getOrderAmount().longValue());
        logistics.setCityCode(info.getCityCode() == null ? null : Integer.valueOf(info.getCityCode()));
        logistics.setCityName(info.getCityName());
        logistics.setDistrictCode(info.getDistrictCode() == null ? null : Integer.valueOf(info.getDistrictCode()));
        logistics.setDistrictName(info.getDistrictName());
        logistics.setProvinceCode(info.getProvinceCode());
        logistics.setProvinceName(info.getProvinceName());
        logistics.setTransName(info.getUserName());
        logistics.setTransPhone(info.getUserPhone());
        logistics.setAddress(info.getAddress());
        logistics.setTownCode(info.getTownCode() == null ? null : Integer.valueOf(info.getTownCode()));
        logistics.setTownName(info.getTownName());
    }
}