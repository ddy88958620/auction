package com.trump.auction.back.robot.dao.read;

import com.trump.auction.back.robot.model.RobotInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 竞拍规则管理
 *
 * @author
 * @create 2018-01-03 15:57
 **/

@Repository
public interface RobotDao {
    /**
     * 根据条件查询机器人
     * @param params
     * @return
     */
    List<RobotInfo> findRobotList(Map<String, Object> params);
    /**
     * 根据主键id查询机器人
     * @param id
     * @return
     */
    RobotInfo findRobotById(@Param(value = "id")Integer id);
}
