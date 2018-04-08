package com.trump.auction.cust.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.trump.auction.cust.api.PromotionChannelStubService;
import com.trump.auction.cust.model.PromotionChannelModel;
import com.trump.auction.cust.service.PromotionChannelService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 推广渠道信息相关
 * @author wangbo 2018/2/5.
 */
@Service(version = "1.0.0")
public class PromotionChannelStubServiceImpl implements PromotionChannelStubService {
    @Autowired
    private PromotionChannelService promotionChannelService;

    @Override
    public PromotionChannelModel findChannelByName(String channelName) {
        return promotionChannelService.findChannelByName(channelName);
    }

    @Override
    public PromotionChannelModel findByChannelId(String channelId) {
        return promotionChannelService.findByChannelId(channelId);
    }
}
