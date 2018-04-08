package com.trump.auction.back.sendSms.model;

import lombok.Data;

import java.util.Date;

/**
 * @Author:limingwu
 * @Description: 发送短信记录实体类
 * @Date: Create in 19:24 2018/3/16
 * @Modified By:
 */
@Data
public class SendSmsRecord {
    /**
     * 主键
     */
    private int id;

    /**
     * '目标号码'
     */
    private String phone;

    /**
     * '发送条数'
     */
    private int count;

    /**
     * '返回状态'
     */
    private int code;

    /**
     * '创建时间'
     */
    private Date createTime;

    /**
     * '发布人'
     */
    private String publisher;

    /**
     * '发布时间'
     */
    private Date releaseTime;

    /**
     * '短信模板id'
     */
    private int smsTemplateId;

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 模板标题
     */
    private String templateTitle;

    /**
     * 类型
     */
    private int sendType;
}
