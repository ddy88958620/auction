package com.trump.auction.trade.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class AuctionTxnDetail {
    private Integer id;

    private String txnSeqNo;

    private String reqSeqNo;

    private String outSeqNo;

    private Integer txnStatus;

    private Integer currency;

    private Integer userId;

    private BigDecimal txnAmt;

    private Integer auctionProdId;

    private String auctionNo;

    private Date txnFinishTime;

    private Date createTime;

    private Integer bidStatus;

    private String remarks;

    private Integer freeCount;

    private Integer validCount;


}