package com.trump.auction.cust.dao;

import com.trump.auction.cust.domain.PromotionChannel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 推广渠道数据操作相关
 * @author wangbo 2018/2/5.
 */
@Repository
public interface PromotionChannelDao {
    /**
     * 根据渠道名称查询渠道信息
     * @param channelName 渠道名称
     * @return 渠道信息
     */
    PromotionChannel selectChannelByName(@Param("channelName") String channelName);

    /**
     * 根据渠道id查询渠道信息
     * @param channelId 渠道id
     * @return 渠道信息
     */
    PromotionChannel selectByChannelId(@Param("channelId") String channelId);
}
