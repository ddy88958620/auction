package com.trump.auction.pals.api.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class PayModel implements Serializable {
    private String userId;

    private String cardNo;

    private String realName;

    private String phone;

    private String idNumber;

}
