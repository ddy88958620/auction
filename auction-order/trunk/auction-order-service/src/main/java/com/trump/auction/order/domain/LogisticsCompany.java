package com.trump.auction.order.domain;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 物流公司实体
 * @author wangbo
 */
@Data
@ToString
public class LogisticsCompany {
    private Integer id;
    private String companyName;
    private String companyUrl;
    private String companyTitle;
    private Date createTime;
    private Date updateTime;
    private Integer enable;
    private Integer userId;
    private String shipperCode;
}