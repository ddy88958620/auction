package com.trump.auction.order.api.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.trump.auction.order.api.PaymentStubService;
import com.trump.auction.order.model.PaymentInfoModel;
import com.trump.auction.order.service.PaymentService;

import java.util.Map;


@Service(version = "1.0.0")
public class PaymentStubServiceImpl implements PaymentStubService {

	@Autowired
	private PaymentService paymentService ;

	@Override
	public PaymentInfoModel getPaymentInfoByOrderId(String orderId) {
		return paymentService.getPaymentInfoByOrderId(orderId);
	}

	@Override
	public PaymentInfoModel selectById(Integer id) {
		return paymentService.selectById(id);
	}

	@Override
	public Integer createPaymentInfo(PaymentInfoModel paymentInfoModel) {
		return paymentService.createPaymentInfo(paymentInfoModel);
	}

	@Override
	public Integer updatePaymentInfoStatusSuc(PaymentInfoModel paymentInfoModel) {
		return paymentService.updatePaymentInfoStatusSuc(paymentInfoModel);
	}

	@Override
	public Boolean queryIsPaidByBatchNo(String batchNo) {
		return paymentService.queryIsPaidByBatchNo(batchNo);
	}

    /**
     * 查询未支付的信息并更新状态
     */
    @Override
    public void queryUnpaidInfoAndUpdate(Map<String,Object> map){
        paymentService.queryUnpaidInfoAndUpdate(map);
    }

	@Override
	public Boolean queryIsPaidByPreId(String preId) {
		return paymentService.queryIsPaidByPreId(preId);
	}

	@Override
	public PaymentInfoModel getPaymentInfoBySerialNo(String serialNo) {
		return paymentService.getPaymentInfoBySerialNo(serialNo);
	}

}
