package com.trump.auction.back.labelManager.model;

import lombok.Data;

import java.util.Date;

/**
 * @Author:limingwu
 * @Description: 标签实体类
 * @Date: Create in 16:02 2018/3/21
 * @Modified By:
 */
@Data
public class LabelManager {

    /**
     * 标签Id
     */
    private int id;

    /**
     * 标签名称
     */
    private String labelName;

    /**
     * 标签图片
     */
    private String labelPic;

    /**
     * 标签状态
     */
    private int labelStatus;

    /**
     * 状态
     */
    private int status;

    /**
     * 标签排序
     */
    private int labelSort;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 拍品Id
     */
    private String auctionProductId;
}
