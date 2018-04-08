package com.trump.auction.trade.api;


import com.trump.auction.trade.model.RobotInfoModel;


/**
 * 拍卖
 *
 * @author zhangliyan
 * @create 2018-01-02 17:48
 **/
public interface RobotStubService {
    /**
     * 添加
     * @param robotInfoModel
     * @return
     */
    Integer insertRobot(RobotInfoModel robotInfoModel);

    /**
     * 修改
     * @param robotInfoModel
     * @return
     */
    int saveUpdateRobot(RobotInfoModel robotInfoModel);

    /**
     * 删除
     * @param ids
     * @return
     */
    int deleteRobot(String[] ids);

}
