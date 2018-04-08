package com.trump.auction.pals.api.model.alipay;

import com.cf.common.utils.ServiceResult;
import lombok.Data;

@Data
public class AliPayQueryResponse extends ServiceResult {

    public AliPayQueryResponse(String code) {
        super(code);
    }

    public AliPayQueryResponse(String code, String msg) {
        super(code, msg);
    }
}
