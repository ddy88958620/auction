package com.trump.auction.back.sendSms.model;

import lombok.Data;

import java.util.Date;

/**
 * @Author:limingwu
 * @Description: 发送短信实体类
 * @Date: Create in 11:07 2018/3/16
 */
@Data
public class SendSmsTemplate {

    /**
     * 主键ID
     */
    private int id;

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 模板标题
     */
    private String templateTitle;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 类型
     */
    private int sendType;

    /**
     * 创建人
     */
    private String founder;

    /**
     * 内容
     */
    private String content;

}
