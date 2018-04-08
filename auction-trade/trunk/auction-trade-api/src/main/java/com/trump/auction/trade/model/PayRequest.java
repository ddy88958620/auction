package com.trump.auction.trade.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * Title:支付所需参数的封装类， 应始终使用PayRequestBuilder这个构建器来构建该类的实例 
 * </p>
 * 
 * @author youlianlai
 * @date 2017年11月14日下午6:18:47
 */
@ToString
public class PayRequest {

	@Getter @Setter private String appId;
	
	@Getter @Setter private String partnerId;
	
	@Getter @Setter private String prepayId;
	
	@Getter @Setter private String nonceStr;

	@Getter @Setter private String timeStamp;

	@Getter @Setter private String paySign;

}
