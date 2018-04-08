package com.trump.auction.back.channelSource.service;

import com.cf.common.util.page.Paging;
import com.trump.auction.back.channelSource.model.ChannelSource;

import java.util.Map;

/**
 * @Author=hanliangliang
 * @Date=2018-01-29
 */
public interface ChannelSourceService {

    /**
     * 分页查询客户渠道来源列表
     * @param params
     * @return
     */
    Paging<ChannelSource> findChannelSourceList(Map<String, Object> params);

    /**
     * 根据id查询渠道对象信息
     * @param id
     * @return
     */
    ChannelSource findChannelSourceById(Integer id);

    /**
     * 更新渠道对象信息
     * @param channelSource
     * @return
     */
    int updateChannelSource(ChannelSource channelSource);

    /**
     * 新增渠道对象信息
     * @param channelSource
     * @return
     */
    int saveChannelSource(ChannelSource channelSource);

    /**
     * 查询渠道key
     * @param key
     * @return
     */
    Boolean findChannelSourceKey(String key);

    /**
     * 批量删除渠道对象信息
     * @param ids
     * @return
     */
    int batchDelChannelSource(String [] ids);



}
