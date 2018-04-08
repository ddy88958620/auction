package com.trump.auction.back.sensitiveWord.model;

import lombok.Data;

import java.util.Date;

@Data
public class SensitiveWord {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 敏感词分类 1：昵称 2：晒单
     */
    private Integer type;

    /**
     * 状态 1：开启 2：关闭
     */
    private Integer status;

    /**
     * 标题
     */
    private String title;

    /**
     * 是否删除 0：未删除 1：删除
     */
    private Integer deleted;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 用户ip
     */
    private String userIp;

    /**
     * 敏感词库
     */
    private String sensitiveWord;
}