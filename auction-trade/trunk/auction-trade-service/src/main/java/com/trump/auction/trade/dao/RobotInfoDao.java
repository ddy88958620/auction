package com.trump.auction.trade.dao;

import com.trump.auction.trade.domain.RobotInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 机器人
 *
 * @author zhangliyan
 * @create 2018-01-02 17:48
 **/
@Service
public interface RobotInfoDao {

    /**
     * 添加
     * @param robotInfo
     * @return
     */
    Integer insertRobot(RobotInfo robotInfo);

    /**
     * 修改
     * @param robotInfo
     * @return
     */
    int saveUpdateRobot(RobotInfo robotInfo);

    /**
     * 删除
     * @param ids
     * @return
     */
    int deleteRobot(String[] ids);

    /**
     * 列表
     * @return
     */
    List<RobotInfo> findRobotInfo(@Param("start") Integer start,@Param("end")  Integer end);


}