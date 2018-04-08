package com.trump.auction.back.robot.service;

import com.cf.common.util.page.Paging;
import com.trump.auction.back.robot.model.RobotInfo;

import java.util.Map;

/**
 * 机器人
 *
 * @author zhangliyan
 * @create 2018-01-03 14:59
 **/
public interface RobotService {

    /**
     * 分页查询机器人
     * @param params
     * @return
     */
    Paging<RobotInfo> findRobotPage(Map<String, Object> params);

    /**
     * 根据id查询机器人
     * @param id
     * @return
     */
    RobotInfo findRobotById(Integer id);

}
