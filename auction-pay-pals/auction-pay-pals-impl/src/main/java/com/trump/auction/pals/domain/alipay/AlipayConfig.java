package com.trump.auction.pals.domain.alipay;

public class AlipayConfig {  
  
    public static final String SIGN_ALGORITHMS = "SHA256withRsa";
    // 字符编码格式 目前支持 gbk 或 utf-8  
    public static String input_charset = "UTF-8";  
    // 签名方式 不需修改  
    public static String sign_type = "RSA2";  
    public static String format = "json";  
}