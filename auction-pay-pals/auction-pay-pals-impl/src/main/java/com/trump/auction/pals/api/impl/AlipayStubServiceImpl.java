package com.trump.auction.pals.api.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.trump.auction.pals.api.AlipayStubService;
import com.trump.auction.pals.api.model.alipay.AliPayQueryResponse;
import com.trump.auction.pals.api.model.alipay.AlipayBackRequest;
import com.trump.auction.pals.api.model.alipay.AlipayBackResponse;
import com.trump.auction.pals.api.model.alipay.AlipayPayRequest;
import com.trump.auction.pals.api.model.alipay.AlipayPayResponse;
import com.trump.auction.pals.api.model.alipay.AlipayQueryRequest;
import com.trump.auction.pals.service.AlipayService;

@Service(version = "1.0.0")
public class AlipayStubServiceImpl implements AlipayStubService {

    @Autowired
    private AlipayService alipayService;

    @Override
    public AlipayPayResponse toAlipayPay(AlipayPayRequest apr) {
        return alipayService.toAlipayPay(apr);
    }

    @Override
    public AlipayBackResponse toAlipayBack(AlipayBackRequest abr) {
        return alipayService.toAlipayBack(abr);
    }
    @Override
    public AliPayQueryResponse toAlipayQuery(AlipayQueryRequest aqr){
        return alipayService.toAlipayQuery(aqr);
    }

	@Override
	public String queryBatchNoByOrderNo(String orderNo) {
		 return alipayService.queryBatchNoByOrderNo(orderNo);
	}
}
