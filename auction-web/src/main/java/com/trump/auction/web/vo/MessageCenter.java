package com.trump.auction.web.vo;

import lombok.Data;

import java.util.Date;

/**
 * 消息中心
 * @author wangjian 2017-12-27
 */
@Data
public class MessageCenter {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 栏目类别
     */
    private Integer channelType;

    /**
     * 标题
     */
    private String contentTitle;

    /**
     * 摘要
     */
    private String contentSummary;

    /**
     * 添加时间
     */
    private Date addTime;

    /**
     * 发布人
     */
    private String addUserId;

    /**
     * 添加IP
     */
    private String addIp;

    /**
     * 最后修改人
     */
    private Integer updateUserId;

    /**
     * 最后更新时间
     */
    private Date updateTime;

    /**
     * 文章点击量
     */
    private Integer viewCount;

    /**
     * 1.正常；2删除
     */
    private Integer isDeleted;

    /**
     * 备注
     */
    private String remark;

    /**
     * 排序，数字小的在上边
     */
    private Integer orderNum;

    /**
     * 文章来源
     */
    private String fromUrl;

    /**
     * 图片URL
     */
    private String imgUrl;

    /**
     * 外部链接
     */
    private String externalUrl;

    /**
     * 内容
     */
    private String contentTxt;
}