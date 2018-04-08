package com.trump.auction.web.vo;

import lombok.Data;
import lombok.ToString;

/**
 * @Author=hanliangliang
 * @Date=2018/03/15
 */
@Data
@ToString
public class SmsLoginVo {
    private String userPhone;
    private String smsCode;
    private String province;
    private String city;
}
