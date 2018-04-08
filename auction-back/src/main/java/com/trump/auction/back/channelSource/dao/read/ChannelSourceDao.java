package com.trump.auction.back.channelSource.dao.read;

import com.trump.auction.back.channelSource.model.ChannelSource;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author=hanliangliang
 * @Date=2018-01-29
 */
public interface ChannelSourceDao {
    /**
     * 查询渠道来源信息集合
     * @param params
     * @return
     */
    List<ChannelSource> findChannelSourceList(Map<String,Object> params);

    /**
     * 查询渠道来源信息
     * @param id
     * @return
     */
    ChannelSource findChannelSource (@Param("id")Integer id);

    /**
     * 查询渠道key
     * @param channelKey
     * @return
     */
    ChannelSource findChannelSourceKey (@Param("channelKey")String channelKey);

}
