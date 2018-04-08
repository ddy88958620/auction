package com.trump.auction.back.channel.dao.read;

import com.trump.auction.back.channel.model.PromotionChannel;
import com.trump.auction.back.channelSource.model.ChannelSource;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 推广渠道管理
 * @author wangjian 2018-1-19
 */
public interface PromotionChannelReadDao {

    /**
     * 根据参数查询
     * @param promotionChannel
     * @return
     */
     PromotionChannel findByParam(PromotionChannel promotionChannel);

    /**
     *  根据渠道ID查询单条推广渠道信息
     * @mbggenerated 2018-01-18
     * @param channelId
     * @return
     */
    PromotionChannel selectByPrimaryKey(String channelId);

    /**
     *  根据渠道ID查询单条推广渠道信息
     * @mbggenerated 2018-01-18
     * @param params
     * @return
     */
    List<PromotionChannel> findPromotionChannelList(Map<String, Object> params);
    /**
     *  根据渠道来源查询信息
     * @mbggenerated 2018-02-02
     * @param channelSource
     * @return
     */
    PromotionChannel  selectByChannelSourceSource(String channelSource);

    /**
     * 根据id查询渠道信息
     * @param id
     * @return
     */
    PromotionChannel selectById (Integer id);
}