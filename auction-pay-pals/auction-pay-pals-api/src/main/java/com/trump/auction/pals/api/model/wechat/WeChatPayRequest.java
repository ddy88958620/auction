package com.trump.auction.pals.api.model.wechat;

import lombok.Data;

import java.io.Serializable;

@Data
public class WeChatPayRequest implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8554260868843597555L;
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
    //支付IP
    private String ip;
}
