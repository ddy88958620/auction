package com.trump.auction.order.api;


import com.trump.auction.order.model.PaymentInfoModel;

import java.util.Map;


public interface PaymentStubService {

	/**
	 * 根据商品订单id获取支付记录
	 */
	PaymentInfoModel getPaymentInfoByOrderId (String orderId) ;
	
	
	/**
	 * 根据serial_no获取支付记录
	 */
	PaymentInfoModel getPaymentInfoBySerialNo (String serialNo) ;
	
	
	PaymentInfoModel selectById(Integer id);
	
	/**
	 * 创建支付记录
	 * @param paymentInfoModel
	 * @return
	 */
	Integer createPaymentInfo(PaymentInfoModel paymentInfoModel);
	
	/**
	 * 更新支付记录为成功
	 * @param paymentInfoModel
	 * @return
	 */
	Integer updatePaymentInfoStatusSuc(PaymentInfoModel paymentInfoModel);
	
	/**
	 * 查询支付宝支付信息是否成功
	 */
	Boolean queryIsPaidByBatchNo(String batchNo);

    /**
     * 查询未支付的信息并更新状态
     */
	void queryUnpaidInfoAndUpdate(Map<String,Object> map);
	/**
	 * 查询微信支付信息是否成功
	 */
	Boolean queryIsPaidByPreId(String preId);
	
}
