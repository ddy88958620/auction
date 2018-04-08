package com.trump.auction.order.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 地址实体
 * @author wangbo
 */
@Data
@ToString
public class AddressInfoModel implements Serializable {
    private static final long serialVersionUID = -1L;
    private Integer id;
    private Integer parentId;
    private String addressName;
    private Integer type;
    private Date createTime;
    private Date updateTime;
    private String userIp;
    private Integer userId;
}