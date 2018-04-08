package com.trump.auction.back.robot.service;

import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.github.pagehelper.PageHelper;
import com.trump.auction.back.robot.dao.read.RobotDao;
import com.trump.auction.back.robot.model.RobotInfo;

import com.trump.auction.back.util.common.Base64Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 竞拍规则管理
 *
 * @author zhangliyan
 * @create 2018-01-03 15:54
 **/
@Slf4j
@Service
public class RobotServiceImpl implements RobotService{
    @Autowired
    private RobotDao robotDao;
    /**
     * 分页查询机器人
     * @param params
     * @return
     */
    @Override
    public Paging<RobotInfo> findRobotPage(Map<String, Object> params) {
        long startTime = System.currentTimeMillis();
        log.info("findRobotPage invoke,StartTime:{},params:{}", startTime, params);

        Paging<RobotInfo> result = null;
        try {
            result = new Paging<>();
            PageHelper.startPage(Integer.valueOf(String.valueOf(params.get("page") == null ? 1 : params.get("page"))),
                    Integer.valueOf(String.valueOf(params.get("limit") == null ? 10 : params.get("limit"))));
            result = PageUtils.page(robotDao.findRobotList(params));
            if(CollectionUtils.isNotEmpty(result.getList())){
                for (RobotInfo detail: result.getList()
                        ) {
                    detail.setName(Base64Utils.decodeStr(detail.getName()));

                }
            }
        } catch (NumberFormatException e) {
            log.error("findRobotPage error:", e);
        }

        long endTime = System.currentTimeMillis();
        log.info("findRobotPage end,duration:{}", endTime - startTime);
        return result;
    }

    @Override
    public RobotInfo findRobotById(Integer id) {
        long startTime = System.currentTimeMillis();
        log.info("findRobotById invoke,StartTime:{},params:{}", startTime, id);
        if (null == id) {
            throw new IllegalArgumentException("findRobotById param orderId is null");
        }
        RobotInfo result = null;
        try {
            result = robotDao.findRobotById(id);
        } catch (NumberFormatException e) {
            log.error("findAuctionRuleById error:", e);
        }

        long endTime = System.currentTimeMillis();
        log.info("findRobotById end,duration:{}", endTime - startTime);
        return result;
    }
}
