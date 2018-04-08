package com.trump.auction.pals.domain;

import lombok.Data;

import java.util.Date;

@Data
public class PayRecord {
    private Long id;

    private String userId;

    private String merchantId;

    private String payType;

    private String payMode;

    private String payFrom;

    private Integer payAmount;

    private Integer realityAmount;

    private String orderNo;

    private String batchNo;

    private String cardNo;

    private Integer status;

    private Date orderTime;

    private Integer returnAmount;

    private Integer chargeFee;

    private String retMsg;

    private String remark;

    private Date createdAt;

    private Date updatedAt;
    
    private String prepayId;
    
}