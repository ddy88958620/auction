package com.trump.auction.order.domain;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 地址实体
 * @author wangbo
 */
@Data
@ToString
public class AddressInfo {
    private Integer id;
    private Integer parentId;
    private String addressName;
    private Integer type;
    private Date createTime;
    private Date updateTime;
    private String userIp;
    private Integer userId;
}