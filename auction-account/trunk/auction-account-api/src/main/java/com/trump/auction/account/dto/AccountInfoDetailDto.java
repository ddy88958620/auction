package com.trump.auction.account.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dingxp on 2017-12-26 0027.
 * 用户账户详情DTO
 */
@Data
public class AccountInfoDetailDto implements Serializable {

	private static final long serialVersionUID = 2162574692304583898L;


	private Integer userId ;//用户id,
	private String userPhone;//用户手机号,
	private Integer	coinType;//'类型：1：拍币；2：赠币；3：积分；4：开心币',
	private Integer	transactionType;//'来源(1:注册赠送；2:微信充值；3:支付宝充值 4:充值赠送；6:订单拍币返回；7:取消差价购买)',
	private Integer	buyCoinType; //'开心币类型:1：全场币，2：特定商品币',
	private Integer	coin; // '数量/金钱(分)',
	private Integer productId;//商品ID
	private String productName;//商品名称
	private Date validStartTime;//'开心币有效开始时间',
	private Date	validEndTime; // '开心币有效结束时间',
	private String	remark; //'备注',

}
