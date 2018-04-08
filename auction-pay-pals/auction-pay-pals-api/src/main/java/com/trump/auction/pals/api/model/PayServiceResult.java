package com.trump.auction.pals.api.model;

import com.cf.common.utils.ServiceResult;
import lombok.Data;

@Data
public class PayServiceResult extends ServiceResult {
    public static String ERROR = "-100";
    private String token;
    private String noAgree;

    public PayServiceResult(String code) {
       super(code);
    }

    public PayServiceResult(String code, String msg) {
        super(code, msg);
    }
}
