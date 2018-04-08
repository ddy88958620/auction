package com.trump.auction.order.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 物流公司实体
 * @author wangbo
 */
@Data
@ToString
public class LogisticsCompanyModel implements Serializable {
    private static final long serialVersionUID = -1L;
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