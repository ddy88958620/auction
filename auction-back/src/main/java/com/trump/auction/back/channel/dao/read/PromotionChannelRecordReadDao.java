package com.trump.auction.back.channel.dao.read;


import com.trump.auction.back.channel.model.PromotionChannelRecord;

/**
 * 推广渠道管理
 * @author hanliangliang 2018-02-27
 */
public interface PromotionChannelRecordReadDao {

    PromotionChannelRecord  findPromotionChannelSettlementById(Integer promotionChannelId);

}