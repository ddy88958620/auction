package com.trump.auction.goods.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 罗显 on 2017/12/21.
 * 库存表
 */
@Data
@ToString
public class ProductInventoryLogModel implements Serializable{
    private static final long serialVersionUID = -904054668835444507L;
    private Integer id;
    private Integer productId;
    private Integer productNum;
    private Date    createTime;
    private Date    updateTime;
    private Integer userId;
    private String  userIp;
}
