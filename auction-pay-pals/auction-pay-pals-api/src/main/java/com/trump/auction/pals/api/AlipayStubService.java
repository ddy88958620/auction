package com.trump.auction.pals.api;

import com.trump.auction.pals.api.model.alipay.AliPayQueryResponse;
import com.trump.auction.pals.api.model.alipay.AlipayBackRequest;
import com.trump.auction.pals.api.model.alipay.AlipayBackResponse;
import com.trump.auction.pals.api.model.alipay.AlipayPayRequest;
import com.trump.auction.pals.api.model.alipay.AlipayPayResponse;
import com.trump.auction.pals.api.model.alipay.AlipayQueryRequest;


/**
 *
 */
public interface AlipayStubService {

    AlipayPayResponse toAlipayPay(AlipayPayRequest apr);

    AliPayQueryResponse toAlipayQuery(AlipayQueryRequest aqr);

    AlipayBackResponse toAlipayBack(AlipayBackRequest abr);
    
    String queryBatchNoByOrderNo(String orderNo);

}
