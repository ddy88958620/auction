package com.trump.auction.goods.domain;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 罗显 on 2017/12/21.
 * 商品库存使用表
 */
@Data
@ToString
public class ProductInventoryLogRecord implements Serializable {
    private static final long serialVersionUID = -8450599589288041194L;
    private Integer id;
    private Integer productId;
    private Integer updProductNum;
    private Integer oldProductNum;
    private Integer colorId;
    private Integer skuId;
    private Integer type;
    private Date    createTime;
    private Date    updateTime;
    private Integer userId;
    private String  userIp;
}






