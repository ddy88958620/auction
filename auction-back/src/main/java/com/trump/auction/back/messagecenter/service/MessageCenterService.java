package com.trump.auction.back.messagecenter.service;

import com.cf.common.utils.JsonResult;
import com.trump.auction.back.messagecenter.model.MessageCenter;

import java.util.List;
import java.util.Map;

/**
 * 消息中心管理
 * @author Created by wangjian on 2018/1/3.
 */
public interface MessageCenterService {
    /**
     * 保存消息中心信息
     * @param messageCenter
     * @return
     */
    JsonResult saveMessageCenter(MessageCenter messageCenter);

    /**
     * 根据ID删除消息中心信息
     * @param id
     * @return
     */
    JsonResult deleteMessageCenter(String[] id);

    /**
     * 修改消息中心信息
     * @param messageCenter
     * @return
     */
    JsonResult updateMessageCenter(MessageCenter messageCenter);

    /**
     * 根据条件查询消息中心列表
     * @param params
     * @return
     */
    JsonResult findMessageCenterList(Map<String,Object> params);

    /**
     * 根据ID查询单条消息中心信息
     * @param id
     * @return
     */
    JsonResult findMessageCenterOne(Integer id);

    /**
     * 获取所有消息信息
     * @return
     */
    List<MessageCenter> findMessageCenterAll();
}
