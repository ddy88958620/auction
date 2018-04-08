package com.trump.auction.pals.api.model.alipay;

import lombok.Data;

import java.io.Serializable;

@Data
public class AlipayPayRequest implements Serializable {
    // 付款金额
    private Integer money;
    // 用户ID
    private String userId;
    // 批次号
    private String batchNo;
    // 主体
    private String body;
    // 项目类型
    private String subject;
    // 支付来源
    private String payFrom;
}
