package com.trump.auction.pals.service;

import com.trump.auction.pals.api.model.alipay.AliPayQueryResponse;
import com.trump.auction.pals.api.model.alipay.AlipayBackRequest;
import com.trump.auction.pals.api.model.alipay.AlipayBackResponse;
import com.trump.auction.pals.api.model.alipay.AlipayPayRequest;
import com.trump.auction.pals.api.model.alipay.AlipayPayResponse;
import com.trump.auction.pals.api.model.alipay.AlipayQueryRequest;

/**
 * 益码通相关API
 */
public interface AlipayService {

    AlipayPayResponse toAlipayPay(AlipayPayRequest apr);

    AliPayQueryResponse toAlipayQuery(AlipayQueryRequest aqr);

    AlipayBackResponse toAlipayBack(AlipayBackRequest abr);

	String queryBatchNoByOrderNo(String orderNo);
}
