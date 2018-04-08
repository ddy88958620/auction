package com.trump.auction.pals.api.model.alipay;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class AlipayBackRequest implements Serializable {
    private Map<String,String> params;
    private String dataJson;
}
