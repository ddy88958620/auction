package com.trump.auction.cust.constant;

/**
 * Created by dingxp on 2017/12/21 0021.
 */
public class CustConstant {

    /** config表中网站参数的sys_type */
    public static final String WEBSITE = "WEBSITE";
    /** config表中短信的sys_type */
    public static final String SMS = "sms_kxjp";
    /** config表中营销短信的sys_type */
    public static final String SMS_MARKET = "sms_kxjp_market";
    /** 短信类型：验证码类 */
    public static final String VERIFY_CODE = "verify_code";
    /** 短信类型：通知类类 */
    public static final String NOTICE = "notice";
    /** 短信类型：营销类 */
    public static final String ADVERT = "advert";

    public static final String SMS_VERIFY_CONTENT = "尊敬的用户，本次验证码为#cont#请勿泄露，5分钟内有效！";
    public static final String SMS_SEND_SUCC = "000000";
}
