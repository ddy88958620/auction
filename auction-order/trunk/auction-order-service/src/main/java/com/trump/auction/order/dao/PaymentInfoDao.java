package com.trump.auction.order.dao;

import com.trump.auction.order.domain.PaymentInfo;

import java.util.List;
import java.util.Map;

public interface PaymentInfoDao {

    PaymentInfo selectByPrimaryKey(Integer id);

    PaymentInfo getOrderInfoByOrderId(String orderId);

	Integer insertPaymentInfo(PaymentInfo map);

	PaymentInfo selectBySerialNo(String serialNo);

	Integer updatePaymentInfoStatus(PaymentInfo paymentInfo);

	Integer queryIsPaidByBatchNo(String batchNo);

    List<PaymentInfo> queryUnpaidInfo(Map<String,Object> map);

	Integer queryIsPaidByPreId(String preId);

	PaymentInfo getPaymentInfoBySerialNo(String serialNo);

}