package com.trump.auction.back.robot.dao.write;

import com.trump.auction.back.robot.model.RobotInfo;
import org.springframework.stereotype.Repository;


/**
 * 竞拍规则管理
 *
 * @author
 * @create 2018-01-03 15:57
 **/

@Repository
public interface RobotDaoWrite {

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
}
