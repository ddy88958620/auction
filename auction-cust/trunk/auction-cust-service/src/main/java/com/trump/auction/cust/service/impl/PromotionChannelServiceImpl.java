package com.trump.auction.cust.service.impl;

import com.cf.common.util.mapping.BeanMapper;
import com.trump.auction.cust.dao.PromotionChannelDao;
import com.trump.auction.cust.model.PromotionChannelModel;
import com.trump.auction.cust.service.PromotionChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 推广渠道相关
 * @author wangbo 2018/2/5.
 */
@Service
public class PromotionChannelServiceImpl implements PromotionChannelService {
    @Autowired
    private BeanMapper beanMapper;
    @Autowired
    private PromotionChannelDao promotionChannelDao;

    @Override
    public PromotionChannelModel findChannelByName(String channelName) {
        return beanMapper.map(promotionChannelDao.selectChannelByName(channelName),PromotionChannelModel.class);
    }

    @Override
    public PromotionChannelModel findByChannelId(String channelId) {
        return beanMapper.map(promotionChannelDao.selectByChannelId(channelId),PromotionChannelModel.class);
    }
}
