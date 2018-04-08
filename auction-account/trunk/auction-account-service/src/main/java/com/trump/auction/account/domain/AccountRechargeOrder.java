package com.trump.auction.account.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wangyichao on 2017-12-20 下午 05:36.
 * 账户充值记录表
 */
@Data
public class AccountRechargeOrder implements Serializable {
	private static final long serialVersionUID = -8180060253863406975L;

	private Integer id;
	private Integer userId;
	private String userName;
	private String userPhone;
	private Integer outMoney;//花费的money
	private Integer intoCoin;//充的拍币数量
	private Integer rechargeType;//充值类型 2：微信充值  3：支付宝充值
	private String rechargeTypeName;//充值类型
	private Integer tradeStatus;//平台订单状态
	private Integer payStatus;//调用服务返回状态
	private String payRemark;//调用服务返回信息
	private String resultJson;
	private String outTradeNo;//交易流水号
	private String orderNo;
	private Integer orderStatus;//订单的状态
	private Date createTime;
	private Date updateTime;

}
