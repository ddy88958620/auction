package com.trump.auction.trade.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cf.common.util.mapping.BeanMapper;
import com.trump.auction.trade.api.RobotStubService;
import com.trump.auction.trade.domain.RobotInfo;
import com.trump.auction.trade.model.RobotInfoModel;
import com.trump.auction.trade.service.RobotService;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 机器人
 *
 * @author zhangliyan
 * @create 2018-01-08 13:46
 **/
@Service(version = "1.0.0")
public class RobotStubServiceImpl implements RobotStubService {
    @Autowired
    private RobotService robotService;
    @Autowired
    private BeanMapper beanMapper;

    /**
     * 保存新建机器人
     *
     * @param robotInfoModel
     * @return
     */
    @Override
    public Integer insertRobot(RobotInfoModel robotInfoModel) {
        return robotService.insertRobot(beanMapper.map(robotInfoModel, RobotInfo.class));
    }

    /**
     * 修改机器人
     *
     * @param robotInfoModel
     * @return
     */
    @Override
    public int saveUpdateRobot(RobotInfoModel robotInfoModel) {
        return robotService.saveUpdateRobot(beanMapper.map(robotInfoModel, RobotInfo.class));
    }
    /**
     * 删除机器人
     *
     * @param ids
     * @return
     */
    @Override
    public int deleteRobot(String[] ids) {
        return robotService.deleteRobot(ids);
    }

}
