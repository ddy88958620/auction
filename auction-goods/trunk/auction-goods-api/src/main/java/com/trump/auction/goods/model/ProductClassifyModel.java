package com.trump.auction.goods.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品分类MODEL
 */
@Data
public class ProductClassifyModel implements Serializable{
    private static final long serialVersionUID = 2113960696240198848L;
    /**
     * 主键,不为空
     */
    private Integer id;

    /**
     * 父类id
     */
    private String parentId;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 类型状态 0启用 1删除 2禁用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 操作人id
     */
    private Integer userId;

    /**
     * 操作人ip
     */
    private String userIp;

    /**
     * 图标
     */
    private String classifyPic;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 备注
     */
    private String remarks;



}