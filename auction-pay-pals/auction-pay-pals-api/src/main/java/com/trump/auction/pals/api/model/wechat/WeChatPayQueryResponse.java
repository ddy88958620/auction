package com.trump.auction.pals.api.model.wechat;

import com.cf.common.utils.ServiceResult;

import lombok.Data;

@Data
public class WeChatPayQueryResponse extends ServiceResult {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String batchNo;
    // 支付来源
    private String payFrom;

    public WeChatPayQueryResponse(String code) {
        super(code);
    }

    public WeChatPayQueryResponse(String code, String msg) {
        super(code, msg);
    }

    public WeChatPayQueryResponse(String code, String msg, String batchNo,String payFrom) {
        super(code, msg);
        this.batchNo = batchNo;
        this.payFrom = payFrom;
    }
}
