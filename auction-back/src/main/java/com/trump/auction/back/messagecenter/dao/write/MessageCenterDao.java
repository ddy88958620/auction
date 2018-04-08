package com.trump.auction.back.messagecenter.dao.write;

import com.trump.auction.back.messagecenter.model.MessageCenter;
import org.apache.ibatis.annotations.Param;

/**
 * 消息中心管理
 * @author Created by wangjian on 2018/1/3.
 */
public interface MessageCenterDao {
    /**
     * 根据ID删除消息中心信息
     * @param id
     * @return
     */
    int deleteMessageCenter(@Param("ids") String[] id);

    /**
     * 保存消息中心信息
     * @param messageCenter
     * @return
     */
    int saveMessageCenter(MessageCenter messageCenter);

    /**
     * 修改消息中心信息
     * @param messageCenter
     * @return
     */
    int updateMessageCenter(MessageCenter messageCenter);
}