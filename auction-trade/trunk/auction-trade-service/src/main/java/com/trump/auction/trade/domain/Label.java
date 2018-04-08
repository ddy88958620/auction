package com.trump.auction.trade.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class Label implements Serializable {

    private static final long serialVersionUID = -1L;

    private Integer id;

    private String labelName;

    private String labelPic;

    private Integer labelStatus;

    private Integer status;

    private Integer labelSort;

    private Date createTime;

    private String auctionProductId;
}