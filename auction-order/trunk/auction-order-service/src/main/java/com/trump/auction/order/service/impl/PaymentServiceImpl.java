package com.trump.auction.order.service.impl;

import com.cf.common.utils.ServiceResult;
import com.trump.auction.order.enums.EnumPaymentType;
import com.trump.auction.pals.api.AlipayStubService;
import com.trump.auction.pals.api.WeChatPayStubService;
import com.trump.auction.pals.api.constant.EnumAlipayStatus;
import com.trump.auction.pals.api.model.alipay.AliPayQueryResponse;
import com.trump.auction.pals.api.model.alipay.AlipayQueryRequest;
import com.trump.auction.pals.api.model.wechat.WeChatPayQueryRequest;
import com.trump.auction.pals.api.model.wechat.WeChatPayQueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cf.common.util.mapping.BeanMapper;
import com.trump.auction.order.dao.PaymentInfoDao;
import com.trump.auction.order.domain.PaymentInfo;
import com.trump.auction.order.enums.EnumOrderStatus;
import com.trump.auction.order.enums.EnumPayStatus;
import com.trump.auction.order.enums.EnumPaymentFlag;
import com.trump.auction.order.model.OrderInfoModel;
import com.trump.auction.order.model.PaymentInfoModel;
import com.trump.auction.order.service.OrderInfoService;
import com.trump.auction.order.service.PaymentService;

import java.util.List;
import java.util.Map;


/**
 *
 */
@Service
public class PaymentServiceImpl implements PaymentService{

	private static Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

	@Autowired
    private BeanMapper beanMapper;
	
	@Autowired
	private PaymentInfoDao auctionPaymentInfoDao;
	
	@Autowired
	private OrderInfoService orderInfoService ;

	@Autowired
    private AlipayStubService alipayStubService;

	@Autowired
    private WeChatPayStubService weChatPayStubService;

	@Override
	public PaymentInfoModel getPaymentInfoByOrderId(String orderId) {
		return beanMapper.map(auctionPaymentInfoDao.getOrderInfoByOrderId(orderId), PaymentInfoModel.class);
	}


	@Override
	public PaymentInfoModel selectById(Integer id) {
		return beanMapper.map(auctionPaymentInfoDao.selectByPrimaryKey(id), PaymentInfoModel.class);
	}


	@Override
	public Integer createPaymentInfo(PaymentInfoModel paymentInfoModel) {
		return auctionPaymentInfoDao.insertPaymentInfo(beanMapper.map(paymentInfoModel, PaymentInfo.class));
	}

	@Override
	public Integer updatePaymentInfoStatusSuc(PaymentInfoModel paymentInfoModel) {
		OrderInfoModel orderInfoModel = new OrderInfoModel();
		
		logger.error("支付成功更改支付状态，SerialNo:{}",paymentInfoModel.getSerialNo());
		PaymentInfo paymentInfo =auctionPaymentInfoDao.selectBySerialNo(paymentInfoModel.getSerialNo());
		
		if(EnumPaymentFlag.PAY.getValue() .equals(paymentInfoModel.getPayflag()) ){
			//支付成功更改订单状态
			try {
				orderInfoModel.setOrderId(paymentInfo.getOrderId());
				orderInfoModel.setOrderStatus(EnumOrderStatus.PAID.getValue());
				orderInfoService.updateOrderStatus(orderInfoModel);
			} catch (Exception e) {
				logger.error("更改订单状态失败:{}", e);
			}
		}

        PaymentInfo newPaymentInfo = new PaymentInfo();
		newPaymentInfo.setSerialNo(paymentInfoModel.getSerialNo());
		newPaymentInfo.setPaymentStatus(EnumPayStatus.PAYSUC.getValue());
		return auctionPaymentInfoDao.updatePaymentInfoStatus(newPaymentInfo);
	}

	@Override
	public Boolean queryIsPaidByBatchNo(String batchNo) {
		Integer paymentStatus = auctionPaymentInfoDao.queryIsPaidByBatchNo(batchNo);
		if(EnumPayStatus.PAYSUC.getValue().equals(paymentStatus) ){
			return true;
		}else{
			return false;
		}
	}

    /**
     * 查询未支付的信息并更新状态
     */
    @Override
    public void queryUnpaidInfoAndUpdate(Map<String,Object> map){
        long beginTime = System.currentTimeMillis();
        logger.info("queryUnpaidInfoAndUpdate invoke,beginTime:{},params:{}", beginTime, map);

        try {
            List<PaymentInfo> unpaidInfo = auctionPaymentInfoDao.queryUnpaidInfo(map);
            logger.info("unpaidInfo size:{}", unpaidInfo.size());

            AlipayQueryRequest alipayQueryRequest;
            AliPayQueryResponse aliPayQueryResponse;
            WeChatPayQueryRequest weChatPayQueryRequest;
            WeChatPayQueryResponse weChatPayQueryResponse;
            PaymentInfo paymentInfo;

            for (PaymentInfo item : unpaidInfo) {
                logger.info("queryUnpaidInfoAndUpdate batchNo:{}", item.getSerialNo());
                paymentInfo = new PaymentInfo();
                paymentInfo.setSerialNo(item.getSerialNo());

                //支付宝支付的信息
                if (EnumPaymentType.ALIPAY.getValue().equals(item.getPaymentType())) {
                    alipayQueryRequest = new AlipayQueryRequest();
                    alipayQueryRequest.setBatchNo(item.getSerialNo());
                    aliPayQueryResponse = alipayStubService.toAlipayQuery(alipayQueryRequest);
                    logger.info("aliPayQueryResponse: code:{}, msg:{}", aliPayQueryResponse.getCode(), aliPayQueryResponse.getMsg());

                    if (ServiceResult.SUCCESS.equals(aliPayQueryResponse.getCode())) {
                        String tradeStatus = aliPayQueryResponse.getMsg();

                        if (EnumAlipayStatus.TRADE_SUCCESS.getType().equals(tradeStatus) || EnumAlipayStatus.TRADE_FINISHED.getType().equals(tradeStatus)) {
                            //支付成功后同步修改订单状态
                            PaymentInfoModel paymentInfoModel = new PaymentInfoModel();
                            paymentInfoModel.setSerialNo(item.getSerialNo());
                            paymentInfoModel.setPayflag(EnumPaymentFlag.PAY.getValue());
                            int sucResult = this.updatePaymentInfoStatusSuc(paymentInfoModel);
                            logger.info("updatePaymentInfoStatusSuc execute size:{}", sucResult);
                        } else if (EnumAlipayStatus.WAIT_BUYER_PAY.getType().equals(tradeStatus)) {
                            paymentInfo.setPaymentStatus(EnumPayStatus.PAYING.getValue());
                        } else {
                            paymentInfo.setPaymentStatus(EnumPayStatus.PAYFAIL.getValue());
                        }
                    } else {
                        paymentInfo.setPaymentStatus(EnumPayStatus.PAYFAIL.getValue());
                    }
                    //微信支付的信息
                } else if (EnumPaymentType.WECHAT.getValue().equals(item.getPaymentType())) {
                    weChatPayQueryRequest = new WeChatPayQueryRequest();
                    weChatPayQueryRequest.setBatchNo(item.getSerialNo());
                    weChatPayQueryResponse = weChatPayStubService.queryWeChatPay(weChatPayQueryRequest);
                    logger.info("weChatPayQueryResponse:: code:{}, msg:{}", weChatPayQueryResponse.getCode(), weChatPayQueryResponse.getMsg());

                    String tradeStatus = weChatPayQueryResponse.getCode();
                    if(WeChatPayQueryResponse.SUCCESS.equals(tradeStatus)){
                        //支付成功后同步修改订单状态
                        PaymentInfoModel paymentInfoModel = new PaymentInfoModel();
                        paymentInfoModel.setSerialNo(item.getSerialNo());
                        paymentInfoModel.setPayflag(EnumPaymentFlag.PAY.getValue());
                        int sucResult = this.updatePaymentInfoStatusSuc(paymentInfoModel);
                        logger.info("updatePaymentInfoStatusSuc execute size:{}", sucResult);
                    }else if(WeChatPayQueryResponse.WAITING.equals(tradeStatus)){
                        paymentInfo.setPaymentStatus(EnumPayStatus.PAYING.getValue());
                    }else{
                        paymentInfo.setPaymentStatus(EnumPayStatus.PAYFAIL.getValue());
                    }
                }

                if (null != paymentInfo.getPaymentStatus()) {
                    int updateResult = auctionPaymentInfoDao.updatePaymentInfoStatus(paymentInfo);
                    logger.info("updatePaymentInfoStatusSuc, executeSize:{}", updateResult);
                }
            }
        } catch (Exception e) {
            logger.error("queryUnpaidInfoAndUpdate error:", e);
        }

        long endTime = System.currentTimeMillis();
        logger.info("queryUnpaidInfoAndUpdate end,duration:{}", endTime - beginTime);
    }

	@Override
	public Boolean queryIsPaidByPreId(String preId) {
		Integer paymentStatus = auctionPaymentInfoDao.queryIsPaidByPreId(preId);
		if(EnumPayStatus.PAYSUC.getValue().equals(paymentStatus)){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public PaymentInfoModel getPaymentInfoBySerialNo(String serialNo) {
		return beanMapper.map(auctionPaymentInfoDao.getPaymentInfoBySerialNo(serialNo), PaymentInfoModel.class);
	}
}
