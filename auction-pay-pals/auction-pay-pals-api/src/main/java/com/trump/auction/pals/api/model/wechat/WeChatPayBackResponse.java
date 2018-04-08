package com.trump.auction.pals.api.model.wechat;

import com.cf.common.utils.ServiceResult;

import lombok.Data;

@Data
public class WeChatPayBackResponse extends ServiceResult {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String batchNo;
    // 支付来源
    private String payFrom;

    public WeChatPayBackResponse(String code) {
        super(code);
    }

    public WeChatPayBackResponse(String code, String msg) {
        super(code, msg);
    }

    public WeChatPayBackResponse(String code, String msg, String batchNo,String payFrom) {
        super(code, msg);
        this.batchNo = batchNo;
        this.payFrom = payFrom;
    }
}
