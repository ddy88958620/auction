package com.trump.auction.pals.api.model.alipay;

import lombok.Data;

import java.io.Serializable;

@Data
public class AlipayQueryRequest implements Serializable {
    private String batchNo;
}
