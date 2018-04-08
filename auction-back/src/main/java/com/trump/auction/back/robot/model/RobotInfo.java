package com.trump.auction.back.robot.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author Administrator
 * @date 2018/1/6
 */
@Data
@ToString
public class RobotInfo {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 机器人名称
     */
    private String name;
    /**
     * 机器人地址
     */
    private String address;
    /**
     * 机器人状态 1停用 2 启用
     */
    private Integer status;
    /**
     * 启用时间
     */
    private Date sDate;
    /**
     * 禁用时间
     */
    private Date eDate;
    /**
     * 机器人使用次数
     */
    private Integer numbers;
    /**
     * 机器人头像
     */
    private String headImg;

}
