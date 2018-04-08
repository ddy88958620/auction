package com.trump.auction.order.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 物流实体
 * @author wangbo
 */
@Data
@ToString
public class LogisticsModel implements Serializable {
    private static final long serialVersionUID = -1L;
    private Integer id;
    private String logisticsId;
    private String logisticsName;
    private String logisticsInfo;
    private String logisticsCode;
    private Date createDate;
    private Date startTime;
    private Integer logisticsStatus;
    private String orderId;
    private String transName;
    private String transTelphone;
    private String transPhone;
    private Integer provinceCode;
    private Integer cityCode;
    private Integer districtCode;
    private Integer townCode;
    private String address;
    private String receiverCode;
    private String provinceName;
    private String cityName;
    private String districtName;
    private String townName;
    private String sendAddress;
    private String sendPhone;
    private String sendName;
    private String receiverName;
    private Long freight;
    private Long totalOrder;
    private Date updateTime;
    private Integer backUserId;
    private String remark;
}