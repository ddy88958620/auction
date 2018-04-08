package com.trump.auction.order.model;


import lombok.Data;

/**
 * 晒单评价规则
 * @author hanliangliang
 */
@Data
public class OrderAppraisesRulesModel {
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
     * 出境奖励
     */
    private Integer showRewords;

    /**
     * 状态 1:正常 2:已删除
     */
    private Integer status;

}