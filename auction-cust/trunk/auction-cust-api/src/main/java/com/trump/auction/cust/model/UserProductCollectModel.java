package com.trump.auction.cust.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户收藏实体
 * @author wangbo
 */
@Data
@ToString
public class UserProductCollectModel implements Serializable {
    private static final long serialVersionUID = -1L;
    private Integer id;
    private Integer userId;
    private Integer productId;
    private String productDetail;
    private Integer periodsId;
    private Integer productMoney;
    private Date createTime;
    private Date updateTime;
    private Integer status;
    private String picUrl;
}
