package com.trump.auction.pals.api.model.alipay;

import com.cf.common.utils.ServiceResult;
import lombok.Data;

@Data
public class AlipayBackResponse extends ServiceResult {

    private String batchNo;
    // 支付来源
    private String payFrom;

    public AlipayBackResponse(String code) {
        super(code);
    }

    public AlipayBackResponse(String code, String msg) {
        super(code, msg);
    }

    public AlipayBackResponse(String code, String msg, String batchNo,String payFrom) {
        super(code, msg);
        this.batchNo = batchNo;
        this.payFrom = payFrom;
    }
}
