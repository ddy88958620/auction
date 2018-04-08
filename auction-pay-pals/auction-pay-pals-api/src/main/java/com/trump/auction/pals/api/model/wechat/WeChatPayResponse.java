package com.trump.auction.pals.api.model.wechat;

import com.cf.common.utils.ServiceResult;
import lombok.Data;

@Data
public class WeChatPayResponse extends ServiceResult {

    private String payBody;

    public WeChatPayResponse(String code) {
        super(code);
    }

    public WeChatPayResponse(String code, String msg) {
        super(code, msg);
    }

    public WeChatPayResponse(String code, String msg, String payBody) {
        super(code, msg);
        this.payBody = payBody;
    }
}
