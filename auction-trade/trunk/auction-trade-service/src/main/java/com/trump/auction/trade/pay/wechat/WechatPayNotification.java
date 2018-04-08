package com.trump.auction.trade.pay.wechat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class WechatPayNotification {

	@Getter @Setter private String returnCode;
	@Getter @Setter private String returnMsg;
	@Getter @Setter private String appid; 
	@Getter @Setter private String mchId; 
	@Getter @Setter private String deviceInfo;
	@Getter @Setter private String nonceStr;
	@Getter @Setter private String sign;
	@Getter @Setter private String resultCode; 
	@Getter @Setter private String errCode;
	@Getter @Setter private String errCodeDes;
	@Getter @Setter private String openid;
	@Getter @Setter private String isSubscribe;
	@Getter @Setter private String tradeType;
	@Getter @Setter private String bankType;
	@Getter @Setter private Integer totalFee;
	@Getter @Setter private String feeType;
	@Getter @Setter private Integer cashFee;
	@Getter @Setter private String cashFeeType;
	@Getter @Setter private String couponFee;
	@Getter @Setter private String couponCount;
	@Getter @Setter private String transactionId;
	@Getter @Setter private String outTradeNo;
	@Getter @Setter private String attach;
	@Getter @Setter private String timeEnd;

}
