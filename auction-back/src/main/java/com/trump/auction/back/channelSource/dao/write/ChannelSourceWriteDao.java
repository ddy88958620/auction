package com.trump.auction.back.channelSource.dao.write;

import com.trump.auction.back.channelSource.model.ChannelSource;

/**
 * @Author=hanliangliang
 * @Date=2018-01-29
 */
public interface ChannelSourceWriteDao {
    /**
     * 更新渠道信息
     * @param channelSource 渠道信息
     * @return
     */
    int updateChannelSource(ChannelSource channelSource);

    /**
     * 新增渠道信息
     * @param channelSource 渠道信息
     * @return
     */
    int saveChannelSource(ChannelSource channelSource);

    /**
     * 批量删除渠道信息
     * @param ids
     * @return
     */
    int batchDelChannelSource(String [] ids);
}
