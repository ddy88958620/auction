package com.trump.auction.pals.api.model.alipay;

import com.cf.common.utils.ServiceResult;
import lombok.Data;

@Data
public class AlipayPayResponse extends ServiceResult {

    private String payBody;
    private String outTradeNo;
    

    public AlipayPayResponse(String code) {
        super(code);
    }

    public AlipayPayResponse(String code, String msg) {
        super(code, msg);
    }

    public AlipayPayResponse(String code, String msg, String payBody,String outTradeNo) {
        super(code, msg);
        this.payBody = payBody;
        this.outTradeNo = outTradeNo;
    }
}
