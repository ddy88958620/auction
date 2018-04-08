package com.trump.auction.pals.api.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.trump.auction.pals.api.WeChatPayStubService;
import com.trump.auction.pals.api.model.wechat.WeChatPayBackRequest;
import com.trump.auction.pals.api.model.wechat.WeChatPayBackResponse;
import com.trump.auction.pals.api.model.wechat.WeChatPayQueryRequest;
import com.trump.auction.pals.api.model.wechat.WeChatPayQueryResponse;
import com.trump.auction.pals.api.model.wechat.WeChatPayRequest;
import com.trump.auction.pals.api.model.wechat.WeChatPayResponse;
import com.trump.auction.pals.service.WeChatPayService;

@Service(version = "1.0.0")
public class WeChatPayStubServiceImpl implements WeChatPayStubService {

    @Autowired
    private WeChatPayService weChatPayService;

    @Override
    public WeChatPayResponse toWeChatPay(WeChatPayRequest apr) {
        return weChatPayService.toWeChatPay(apr);
    }

    @Override
    public WeChatPayBackResponse toWeChatPayBack(WeChatPayBackRequest abr) {
        return weChatPayService.toWeChatPayBack(abr);
    }

	@Override
	public WeChatPayQueryResponse queryWeChatPay(WeChatPayQueryRequest wcp) {
		return weChatPayService.queryWeChatPay(wcp);
	}

	@Override
	public String queryBatchNoByPrePayId(String prepayId) {
		return weChatPayService.queryBatchNoByPrePayId(prepayId);
	}
}
