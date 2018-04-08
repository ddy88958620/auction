package com.trump.auction.back.channel.service;

import com.cf.common.utils.JsonResult;
import com.trump.auction.back.channel.model.PromotionChannel;

import java.util.Map;

/**
 * 推广渠道管理
 * @author Created by wangjian on 2018/1/19.
 */
public interface PromotionChannelService {

    /**
     *
     * @param promotionChannel
     * @return
     */
    PromotionChannel findByParam(PromotionChannel promotionChannel);
    /**
     * 保存推广渠道信息
     * @param promotionChannel
     * @return
     */
    JsonResult savePromotionChannel(PromotionChannel promotionChannel);

    /**
     * 根据ID删除推广渠道信息
     * @param channelIds
     * @return
     */
    JsonResult deletePromotionChannel(String[] channelIds);

    /**
     * 修改消息中心信息
     * @param promotionChannel
     * @return
     */
    JsonResult updatePromotionChannel(PromotionChannel promotionChannel);

    /**
     * 根据条件查询推广渠道列表
     * @param params
     * @return
     */
    JsonResult findPromotionChannelList(Map<String, Object> params);

    /**
     * 根据渠道ID查询单条推广渠道信息
     * @param channelId
     * @return
     */
    JsonResult findPromotionChannelOne(String channelId);

    /**
     * 查询渠道key保证其唯一性
     * @param key
     * @return
     */
    JsonResult findChannelSource(String key);

    /**
     * 根据id查询渠道信息
     * @param id
     * @return
     */
    PromotionChannel selectById (Integer id);
}
