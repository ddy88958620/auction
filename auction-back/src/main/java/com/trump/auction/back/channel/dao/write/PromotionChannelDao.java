package com.trump.auction.back.channel.dao.write;

import com.trump.auction.back.channel.model.PromotionChannel;
import org.apache.ibatis.annotations.Param;

/**
 * 推广渠道管理
 * @author wangjian 2018-1-19
 */
public interface PromotionChannelDao {
    /**
     * 根据渠道ID批量删除推广渠道信息
     * @mbggenerated 2018-01-18
     * @param channelIds
     * @return
     */
    int deleteByPrimaryKey(@Param("channelIds") String[] channelIds);

    /**
     * 新增推广渠道信息
     * @mbggenerated 2018-01-18
     * @param record
     * @return
     */
    int insert(PromotionChannel record);

    /**
     * 修改推广渠道信息
     * @mbggenerated 2018-01-18
     * @param record
     * @return
     */
    int updateByPrimaryKey(PromotionChannel record);
}