package com.trump.auction.back.channel.dao.write;

import com.trump.auction.back.channel.model.PromotionChannel;
import com.trump.auction.back.channel.model.PromotionChannelRecord;
import org.apache.ibatis.annotations.Param;

/**
 * 推广渠道管理
 * @author hanliangliang 2018-02-27
 */
public interface PromotionChannelRecordDao {
    /**
     * 根据渠道ID批量删除推广渠道信息
     * @mbggenerated 2018-02-27
     * @param channelIds
     * @return
     */
    int deleteByPrimaryKey(@Param("channelIds") String[] channelIds);

    /**
     * 新增推广渠道记录信息
     * @mbggenerated 2018-02-27
     * @param record
     * @return
     */
    int insert(PromotionChannelRecord record);

    /**
     * 修改推广渠道信息
     * @mbggenerated 2018-02-27
     * @param record
     * @return
     */
    int updateByPrimaryKey(PromotionChannelRecord record);

    /**
     * 修改结束时间
     * @mbggenerated 2018-02-27
     * @param record
     * @return
     */
    int updateEndTime(PromotionChannelRecord record);
}