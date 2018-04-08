package com.trump.auction.back.messagecenter.dao.read;

import com.trump.auction.back.messagecenter.model.MessageCenter;

import java.util.List;
import java.util.Map;

/**
 * 消息中心管理
 * @author Created by wangjian on 2018/1/3.
 */
public interface MessageCenterReadDao {
    /**
     * 根据ID获取消息中心信息
     * @param id
     * @return
     */
    MessageCenter selectByPrimaryKey(Integer id);

    /**
     * 根据条件查询消息中心信息
     * @param params
     * @return
     */
    List<MessageCenter> findMessageCenterList(Map<String,Object> params);
}