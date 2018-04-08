package com.trump.auction.account.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by dingxp on 2018/1/9 0009.
 */
@Data
public class AccountInfoRecordListDto implements Serializable{
    private static final long serialVersionUID = 7165310999130492626L;

    private Integer id;
    private String orderNo;
    private Integer userId;
    /*本次交易拍币数量*/
    private Integer transactionCoin;
    private String orderId;//第三方订单号
    private String orderSerial;//第三方交易流水号
    /*类型：1：拍币；2：赠币；3：积分；4：开心币*/
    private Integer accountType;


}
