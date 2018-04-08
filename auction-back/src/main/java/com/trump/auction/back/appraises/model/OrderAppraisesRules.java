package com.trump.auction.back.appraises.model;


import lombok.Data;
/**
 * 晒单评价规则
 * @author hanliangliang 2018-03-06
 */
@Data
public class OrderAppraisesRules {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 评论级别
     */
    private String appraisesLevel;

    /**
     * 评论字数
     */
    private String appraisesWords;

    /**
     * 评论字数最小值
     */
    private String minAppraisesWords;

    /**
     * 评论字数最大值
     */
    private String maxAppraisesWords;

    /**
     * 图片数量
     */
    private String picNumber;

    /**
     * 图片数量最小值
     */
    private String minPicNum;

    /**
     * 图片数量最大值
     */
    private String maxPicNum;

    /**
     * 基础奖励
     */
    private Integer baseRewords;

    /**
     * 出镜奖励
     */
    private Integer showRewords;

    /**
     * 状态 1:正常 2:已删除
     */
    private Integer status;

    /**
     * 操作用户id
     */
    private Integer userId;

    /**
     * 操作用户ip
     */
    private String userIp;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String updateTime;

}