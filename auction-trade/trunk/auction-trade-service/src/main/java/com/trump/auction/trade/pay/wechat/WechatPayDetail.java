package com.trump.auction.trade.pay.wechat;

import java.util.Date;

import lombok.Data;

/**
 * <p>
 * Title:微信支付详情 
 * </p>
 * 
 * @author youlianlai
 * @date 2017年11月14日下午6:34:10
 */
@Data
public class WechatPayDetail {


	private String prepayId;// 预支付交易会话标识

	private String nonceStr;// 随机字符串,可用于响应时的校验

	private String paysign;// 统一下单支付签名

	private String sign;// 支付签名

	private String body;// 商品描述

	private String detail;// 商品详情

	private String attach;// 附加数据

	private Long orderId;// 内部订单号

	private Integer totalFee;// 总金额

	private String spbillCreateIp;// 终端IP

	private Date timeStart;// 交易起始时间

	private Date timeExpire;// 交易结束时间

	private String goodsTag;// 商品标记

	private String tradeType;// 交易类型

	private String openId;// 用户微信标识

	private Date prepayTime;// 预支付完成的时间,24小时内有效

	private Date payTime;// 用户支付时间

	private String outTradeNo;// 商户订单编号，即系统内部的订单编号

}
